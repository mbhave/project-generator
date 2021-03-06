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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.spring.initializr.generator.Link;

/**
 * Project's help document intended to give additional references to the users. Contains
 * general entries, links and additional sections.
 *
 * @author Stephane Nicoll
 */
public class HelpDocument {

	private final List<String> generalEntries = new ArrayList<>();

	private final List<Link> links = new ArrayList<>();

	private final LinkedList<Section> sections = new LinkedList<>();

	public HelpDocument generalEntry(String entry) {
		this.generalEntries.add(entry);
		return this;
	}

	public HelpDocument link(Link link) {
		this.links.add(link);
		return this;
	}

	public HelpDocument section(Section section) {
		this.sections.add(section);
		return this;
	}

	public boolean isEmpty() {
		return this.generalEntries.isEmpty() && this.links.isEmpty()
				&& this.sections.isEmpty();
	}

	public List<String> getGeneralEntries() {
		return this.generalEntries;
	}

	public List<Link> getLinks() {
		return this.links;
	}

	public LinkedList<Section> getSections() {
		return this.sections;
	}

}
