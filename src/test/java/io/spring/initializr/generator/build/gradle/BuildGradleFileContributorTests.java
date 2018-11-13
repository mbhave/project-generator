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

package io.spring.initializr.generator.build.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BuildGradleFileContributor}.
 *
 * @author Andy Wilkinson
 */
public class BuildGradleFileContributorTests {

	@Rule
	public final TemporaryFolder temp = new TemporaryFolder();

	@Test
	public void gradleBuildWithBuildscriptDependency() throws IOException {
		GradleBuild build = new GradleBuild();
		build.addMavenRepository(MavenRepository.MAVEN_CENTRAL);
		build.buildscript((buildscript) -> buildscript.dependency(
				"org.springframework.boot:spring-boot-gradle-plugin:2.1.0.RELEASE"));
		BuildGradleFileContributor contributor = new BuildGradleFileContributor(build);
		contributor.contribute(this.temp.getRoot());
		File buildGradle = new File(this.temp.getRoot(), "build.gradle");
		assertThat(buildGradle).isFile();
		List<String> lines = Files.readAllLines(buildGradle.toPath());
		assertThat(lines).containsSequence("buildscript {", "    repositories {",
				"        mavenCentral()", "    }", "    dependencies {",
				"        classpath \"org.springframework.boot:spring-boot-gradle-plugin:2.1.0.RELEASE\"",
				"    }", "}");
	}

	@Test
	public void gradleBuildWithBuildscriptExtProperty() throws IOException {
		GradleBuild build = new GradleBuild();
		build.addMavenRepository(MavenRepository.MAVEN_CENTRAL);
		build.buildscript((buildscript) -> buildscript.ext("kotlinVersion", "'1.2.51'"));
		BuildGradleFileContributor contributor = new BuildGradleFileContributor(build);
		contributor.contribute(this.temp.getRoot());
		File buildGradle = new File(this.temp.getRoot(), "build.gradle");
		assertThat(buildGradle).isFile();
		List<String> lines = Files.readAllLines(buildGradle.toPath());
		assertThat(lines).containsSequence("buildscript {", "    ext {",
				"        kotlinVersion = '1.2.51'", "    }", "}");
	}

	@Test
	public void gradleBuildWithMavenCentralRepository() throws IOException {
		GradleBuild build = new GradleBuild();
		build.addMavenRepository(MavenRepository.MAVEN_CENTRAL);
		BuildGradleFileContributor contributor = new BuildGradleFileContributor(build);
		contributor.contribute(this.temp.getRoot());
		File buildGradle = new File(this.temp.getRoot(), "build.gradle");
		assertThat(buildGradle).isFile();
		List<String> lines = Files.readAllLines(buildGradle.toPath());
		assertThat(lines).containsSequence("repositories {", "    mavenCentral()", "}");
	}

	@Test
	public void gradleBuildWithMavenRepository() throws IOException {
		GradleBuild build = new GradleBuild();
		build.addMavenRepository("spring-milestones", "Spring Milestones",
				"https://repo.spring.io/milestone");
		BuildGradleFileContributor contributor = new BuildGradleFileContributor(build);
		contributor.contribute(this.temp.getRoot());
		File buildGradle = new File(this.temp.getRoot(), "build.gradle");
		assertThat(buildGradle).isFile();
		List<String> lines = Files.readAllLines(buildGradle.toPath());
		assertThat(lines).containsSequence("repositories {",
				"    maven { url 'https://repo.spring.io/milestone' }", "}");
	}

	@Test
	public void gradleBuildWithSnapshotMavenRepository() throws IOException {
		GradleBuild build = new GradleBuild();
		build.addSnapshotMavenRepository("spring-snapshots", "Spring Snapshots",
				"https://repo.spring.io/snapshot");
		BuildGradleFileContributor contributor = new BuildGradleFileContributor(build);
		contributor.contribute(this.temp.getRoot());
		File buildGradle = new File(this.temp.getRoot(), "build.gradle");
		assertThat(buildGradle).isFile();
		List<String> lines = Files.readAllLines(buildGradle.toPath());
		assertThat(lines).containsSequence("repositories {",
				"    maven { url 'https://repo.spring.io/snapshot' }", "}");
	}

}
