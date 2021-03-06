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

package io.spring.start.extension.azure;

import io.spring.initializr.generator.Dependency;
import io.spring.initializr.generator.DependencyType;
import io.spring.initializr.generator.ProjectDescription;
import io.spring.initializr.generator.project.documentation.HelpDocument;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AzureHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class AzureHelpDocumentCustomizerTests {

	@Test
	void customizeWithAzureDependency() {
		ProjectDescription description = new ProjectDescription();
		description.addDependency(
				new Dependency("com.microsoft.azure", "azure", DependencyType.COMPILE));
		HelpDocument document = new HelpDocument();
		new AzureHelpDocumentCustomizer(description).customize(document);
		assertThat(document.getSections()).hasSize(1);
	}

	@Test
	void customizeWitoutAzureDependency() {
		ProjectDescription description = new ProjectDescription();
		description.addDependency(
				new Dependency("com.example.another", "test", DependencyType.COMPILE));
		HelpDocument document = new HelpDocument();
		new AzureHelpDocumentCustomizer(description).customize(document);
		assertThat(document.getSections()).isEmpty();
	}

}
