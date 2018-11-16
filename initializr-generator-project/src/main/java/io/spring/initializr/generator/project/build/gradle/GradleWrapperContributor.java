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

package io.spring.initializr.generator.project.build.gradle;

import io.spring.initializr.generator.MultipleResourcesFileContributor;

/**
 * A {@link MultipleResourcesFileContributor} that contributes Gradle's wrapper to a
 * project.
 *
 * @author Andy Wilkinson
 */
class GradleWrapperContributor extends MultipleResourcesFileContributor {

	GradleWrapperContributor(String gradleVersion) {
		super("classpath:gradle/" + gradleVersion + "/wrapper",
				(filename) -> filename.equals("gradlew")
						|| filename.equals("gradlew.bat"));
	}

}