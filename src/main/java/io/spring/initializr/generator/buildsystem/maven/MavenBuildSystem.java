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

package io.spring.initializr.generator.buildsystem.maven;

import java.io.File;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.language.Language;

/**
 * Maven {@link BuildSystem}.
 *
 * @author Andy Wilkinson
 */
public class MavenBuildSystem implements BuildSystem {

	static final String ID = "maven";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public File getMainDirectory(File projectRoot, Language language) {
		return new File(projectRoot, "src/main/" + language.id());
	}

	@Override
	public File getTestDirectory(File projectRoot, Language language) {
		return new File(projectRoot, "src/test" + language.id());
	}

}
