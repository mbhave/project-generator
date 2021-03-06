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

import java.io.IOException;
import java.nio.file.Path;

/**
 * A callback for contributing on a generated project.
 *
 * @author Andy Wilkinson
 */
public interface ProjectContributor {

	/**
	 * Contribute additional resources to the project in the specified root directory.
	 * @param projectRoot the root directory of the project
	 * @throws IOException if contributing a resource failed
	 */
	void contribute(Path projectRoot) throws IOException;

}
