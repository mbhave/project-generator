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

import java.io.IOException;

import io.spring.initializr.generator.util.template.TemplateRenderer;

/**
 * Define the section of a document.
 *
 * @author Stephane Nicoll
 */
@FunctionalInterface
public interface Section {

	/**
	 * Render the section using the specified {@link TemplateRenderer}.
	 * @param renderer the renderer to use
	 * @return the rendered section
	 * @throws IOException if rendering the section failed
	 */
	String render(TemplateRenderer renderer) throws IOException;

}
