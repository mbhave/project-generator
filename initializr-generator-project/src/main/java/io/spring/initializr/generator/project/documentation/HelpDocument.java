/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.project.documentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.spring.initializr.generator.Link;

/**
 * Project's help document intended to give additional references to the users. Contains
 * general entries, links and additional sections.
 *
 * @author Stephane Nicoll
 */
public class HelpDocument {

	private final List<Link> links = new ArrayList<>();

	private final List<RequiredDependency> requiredDependencies = new ArrayList<>();

	private final List<SupportingInfrastructureElement> infrastructureElements = new ArrayList<>();

	private final LinkedList<Section> sections = new LinkedList<>();

	public HelpDocument addLink(Link link) {
		this.links.add(link);
		return this;
	}

	public HelpDocument addSection(Section section) {
		this.sections.add(section);
		return this;
	}

	public HelpDocument addRequiredDependency(RequiredDependency dependency) {
		this.requiredDependencies.add(dependency);
		return this;
	}

	public HelpDocument addSupportingInfrastructureElement(
			SupportingInfrastructureElement element) {
		this.infrastructureElements.add(element);
		return this;
	}

	public LinkedList<Section> getSections() {
		return this.sections;
	}

	public void write(PrintWriter writer) throws IOException {
		writeGettingStartedSection(writer);
		for (Section section : this.sections) {
			section.write(writer);
		}
	}

	private void writeGettingStartedSection(PrintWriter writer) throws IOException {
		if (this.links.isEmpty() && this.infrastructureElements.isEmpty()
				&& this.requiredDependencies.isEmpty()) {
			return;
		}
		Map<String, Object> model = new HashMap<>();
		List<Link> referenceLinks = this.links.stream()
				.filter((link) -> "reference".equals(link.getRel()))
				.collect(Collectors.toList());
		List<Link> guideLinks = this.links.stream()
				.filter((link) -> "guide".equals(link.getRel()))
				.collect(Collectors.toList());
		List<Link> otherLinks = this.links.stream().filter(
				(link) -> !referenceLinks.contains(link) && !guideLinks.contains(link))
				.collect(Collectors.toList());
		add(model, "referenceLinks", referenceLinks);
		add(model, "guideLinks", guideLinks);
		add(model, "otherLinks", otherLinks);
		add(model, "supportingInfrastructure", this.infrastructureElements);
		add(model, "requiredDependencies", this.requiredDependencies);
		Section section = new MustacheSection("getting-started", model);
		section.write(writer);
	}

	private void add(Map<String, Object> model, String name, Collection<?> value) {
		model.put(name, value);
		model.put(name + "Present", !value.isEmpty());
	}

}
