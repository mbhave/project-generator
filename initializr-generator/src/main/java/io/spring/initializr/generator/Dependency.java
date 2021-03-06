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

package io.spring.initializr.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A dependency to be declared in a project's build configuration.
 *
 * @author Andy Wilkinson
 */
public class Dependency implements Comparable<Dependency> {

	private final String groupId;

	private final String artifactId;

	private final String version;

	private final DependencyType type;

	private final List<Link> links;

	private final Collection<String> facets;

	public Dependency(String groupId, String artifactId, DependencyType type) {
		this(groupId, artifactId, null, type, null, null);
	}

	public Dependency(String groupId, String artifactId, String version,
			DependencyType type, List<Link> links, Collection<String> facets) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.type = type;
		this.links = (links != null) ? new ArrayList<>(links) : Collections.emptyList();
		this.facets = (facets != null) ? new ArrayList<>(facets)
				: Collections.emptyList();
	}

	/**
	 * The group ID of the dependency.
	 * @return the group ID
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * The artifact ID of the dependency.
	 * @return the artifact ID
	 */
	public String getArtifactId() {
		return this.artifactId;
	}

	/**
	 * The version of the dependency. May be {@code null} for a dependency whose version
	 * is expected to be provided by dependency management.
	 * @return the version or {@code null}
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * The {@link DependencyType type} of the dependency.
	 * @return the type
	 */
	public DependencyType getType() {
		return this.type;
	}

	/**
	 * The {@code links} of the dependency.
	 * @return the links or an empty list
	 */
	public List<Link> getLinks() {
		return this.links;
	}

	/**
	 * The {@code facets} of the dependency.
	 * @return the facets or an empty collection
	 */
	public Collection<String> getFacets() {
		return this.facets;
	}

	@Override
	public int compareTo(Dependency another) {
		int typeComparison = Integer.compare(getType().ordinal(),
				another.getType().ordinal());
		if (typeComparison != 0) {
			return typeComparison;
		}
		int groupComparison = getGroupId().compareTo(another.getGroupId());
		if (groupComparison != 0) {
			return groupComparison;
		}
		return getArtifactId().compareTo(another.getArtifactId());
	}

}
