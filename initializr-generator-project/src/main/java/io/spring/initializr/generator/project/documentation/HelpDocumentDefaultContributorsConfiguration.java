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

import java.util.List;

import io.spring.initializr.generator.ProjectDescription;
import io.spring.initializr.generator.project.documentation.gettingstarted.GettingStartedContributor;
import io.spring.initializr.generator.project.documentation.gettingstarted.GettingStartedHelpDocumentCustomizer;
import io.spring.initializr.generator.project.documentation.gettingstarted.LinksContributor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default help document contributors.
 *
 * @author Stephane Nicoll
 */
@Configuration
class HelpDocumentDefaultContributorsConfiguration {

	@Bean
	public GettingStartedContributor linksContributor(ProjectDescription description) {
		return new LinksContributor(description);
	}

	@Bean
	public GettingStartedHelpDocumentCustomizer customizer(
			List<GettingStartedContributor> contributors) {
		return new GettingStartedHelpDocumentCustomizer(contributors);
	}

}
