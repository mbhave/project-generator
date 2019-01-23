/*
 * Copyright 2012-2019 the original author or authors.
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

import java.util.List;

/**
 * Resolve a {@link ProjectDescription} to a {@link ResolvedProjectDescription}.
 *
 * @author Madhura Bhave
 */
public class ProjectDescriptionResolver {

	private final List<ProjectDescriptionCustomizer> customizers;

	public ProjectDescriptionResolver(List<ProjectDescriptionCustomizer> customizers) {
		this.customizers = customizers;
	}

	public ResolvedProjectDescription resolve(ProjectDescription description) {
		for (ProjectDescriptionCustomizer customizer : this.customizers) {
			customizer.customize(description);
		}
		return new ResolvedProjectDescription(description);
	}

}
