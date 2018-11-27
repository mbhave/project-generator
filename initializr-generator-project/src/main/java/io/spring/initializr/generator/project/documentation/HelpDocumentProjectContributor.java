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
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import io.spring.initializr.generator.ProjectContributor;
import io.spring.initializr.generator.util.template.TemplateRenderer;

/**
 * {@link ProjectContributor} for the project's {@code HELP.md} file.
 *
 * @author Stephane Nicoll
 */
public class HelpDocumentProjectContributor implements ProjectContributor {

	private final TemplateRenderer templateRenderer;

	private final HelpDocument helpDocument;

	public HelpDocumentProjectContributor(TemplateRenderer templateRenderer,
			HelpDocument helpDocument) {
		this.helpDocument = helpDocument;
		this.templateRenderer = templateRenderer;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		if (this.helpDocument.isEmpty()) {
			return;
		}
		Path file = Files.createFile(projectRoot.resolve("HELP.md"));
		try (PrintWriter writer = new PrintWriter(Files.newOutputStream(file))) {
			for (Section section : this.helpDocument.getSections()) {
				writer.println(section.getText(this.templateRenderer));
				writer.println();
			}
		}
	}

}
