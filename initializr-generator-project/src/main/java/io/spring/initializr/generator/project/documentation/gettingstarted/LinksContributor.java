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

package io.spring.initializr.generator.project.documentation.gettingstarted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.spring.initializr.generator.Dependency;
import io.spring.initializr.generator.Link;
import io.spring.initializr.generator.ProjectDescription;

/**
 * Links contributor.
 *
 * @author Madhura Bhave
 */
public class LinksContributor implements GettingStartedContributor {

	private final ProjectDescription description;

	public LinksContributor(ProjectDescription description) {
		this.description = description;
	}

	@Override
	public void contribute(GettingStarted.Builder builder) {
		List<Link> links = getAllDependencyLinks();
		if (links.isEmpty()) {
			return;
		}
		Map<String, Object> linksMap = new HashMap<>();
		List<Link> referenceLinks = getLinks(links, "reference");
		List<Link> guideLinks = getLinks(links, "guide");
		List<Link> otherLinks = links.stream().filter(
				(link) -> !referenceLinks.contains(link) && !guideLinks.contains(link))
				.collect(Collectors.toList());
		add(linksMap, "referenceLinks", referenceLinks);
		add(linksMap, "guideLinks", guideLinks);
		add(linksMap, "otherLinks", otherLinks);
		builder.withSubSections(linksMap);
	}

	private List<Link> getAllDependencyLinks() {
		List<Link> links = new ArrayList<>();
		for (Dependency dependency : this.description.getDependencies()) {
			dependency.getLinks().forEach((link) -> {
				if (link.getDescription() != null && link.getRel() != null) {
					links.add(link);
				}
			});
		}
		return links;
	}

	private List<Link> getLinks(List<Link> links, String reference) {
		return links.stream().filter((link) -> reference.equals(link.getRel()))
				.collect(Collectors.toList());
	}

	private void add(Map<String, Object> model, String name, Collection<?> value) {
		model.put(name, value);
		model.put(name + "Present", !value.isEmpty());
	}

}
