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

package io.spring.initializr.generator.project.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;
import org.junitpioneer.jupiter.TempDirectory.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ApplicationPropertiesContributor}.
 *
 * @author Stephane Nicoll
 */
@ExtendWith(TempDirectory.class)
class ApplicationPropertiesContributorTests {

	private final Path directory;

	ApplicationPropertiesContributorTests(@TempDir Path directory) {
		this.directory = directory;
	}

	@Test
	void applicationConfigurationWithDefaultSettings() throws IOException {
		Path projectDir = Files.createTempDirectory(this.directory, "project-");
		new ApplicationPropertiesContributor().contribute(projectDir);
		Path configuration = projectDir
				.resolve("src/main/resources/application.properties");
		assertThat(configuration).isRegularFile();
		List<String> lines = Files.readAllLines(configuration);
		assertThat(lines).containsOnly("");
	}

}
