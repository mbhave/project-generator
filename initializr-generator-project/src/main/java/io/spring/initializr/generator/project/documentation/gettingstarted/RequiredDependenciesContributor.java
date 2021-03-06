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

import java.util.Map;

import io.spring.initializr.generator.ProjectDescription;

/**
 * @author Madhura Bhave
 */
public class RequiredDependenciesContributor implements GettingStartedContributor {

	private final ProjectDescription description;

	public RequiredDependenciesContributor(ProjectDescription description) {
		this.description = description;
	}

	@Override
	public void contribute(GettingStarted.Builder builder) {

	}

}
