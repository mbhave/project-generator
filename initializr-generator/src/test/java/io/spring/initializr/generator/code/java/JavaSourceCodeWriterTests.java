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

package io.spring.initializr.generator.code.java;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;
import java.util.List;

import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.java.JavaCompilationUnit;
import io.spring.initializr.generator.language.java.JavaExpressionStatement;
import io.spring.initializr.generator.language.java.JavaMethodDeclaration;
import io.spring.initializr.generator.language.java.JavaMethodInvocation;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;
import org.junitpioneer.jupiter.TempDirectory.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JavaSourceCodeWriter}.
 *
 * @author Andy Wilkinson
 */
@ExtendWith(TempDirectory.class)
class JavaSourceCodeWriterTests {

	private final Path directory;

	private final JavaSourceCodeWriter writer = new JavaSourceCodeWriter();

	JavaSourceCodeWriterTests(@TempDir Path directory) {
		this.directory = directory;
	}

	@Test
	void emptyCompilationUnit() throws IOException {
		JavaSourceCode sourceCode = new JavaSourceCode();
		sourceCode.createCompilationUnit("com.example", "Test");
		List<String> lines = writeSingleType(sourceCode, "com/example/Test.java");
		assertThat(lines).containsExactly("package com.example;", "");
	}

	@Test
	void emptyTypeDeclaration(@TempDir Path directory) throws IOException {
		JavaSourceCode sourceCode = new JavaSourceCode();
		JavaCompilationUnit compilationUnit = sourceCode
				.createCompilationUnit("com.example", "Test");
		compilationUnit.createTypeDeclaration("Test");
		List<String> lines = writeSingleType(sourceCode, "com/example/Test.java");
		assertThat(lines).containsExactly("package com.example;", "",
				"public class Test {", "", "}", "");
	}

	@Test
	void springBootApplication(@TempDir Path directory) throws IOException {
		JavaSourceCode sourceCode = new JavaSourceCode();
		JavaCompilationUnit compilationUnit = sourceCode
				.createCompilationUnit("com.example", "Test");
		JavaTypeDeclaration test = compilationUnit.createTypeDeclaration("Test");
		test.annotate(Annotation
				.name("org.springframework.boot.autoconfigure.SpringBootApplication"));
		test.addMethodDeclaration(JavaMethodDeclaration.method("main")
				.modifiers(Modifier.PUBLIC | Modifier.STATIC).returning("void")
				.parameters(new Parameter("java.lang.String[]", "args"))
				.body(new JavaExpressionStatement(new JavaMethodInvocation(
						"org.springframework.boot.SpringApplication", "run", "Test.class",
						"args"))));
		List<String> lines = writeSingleType(sourceCode, "com/example/Test.java");
		assertThat(lines).containsExactly("package com.example;", "",
				"import org.springframework.boot.SpringApplication;",
				"import org.springframework.boot.autoconfigure.SpringBootApplication;",
				"", "@SpringBootApplication", "public class Test {", "",
				"    public static void main(String[] args) {",
				"        SpringApplication.run(Test.class, args);", "    }", "", "}", "");
	}

	@Test
	void annotationWithSimpleStringAttribute() throws IOException {
		List<String> lines = writeClassAnnotation(
				Annotation.name("org.springframework.test.TestApplication",
						(builder) -> builder.attribute("name", String.class, "test")));
		assertThat(lines).containsExactly("package com.example;", "",
				"import org.springframework.test.TestApplication;", "",
				"@TestApplication(name = \"test\")", "public class Test {", "", "}", "");
	}

	@Test
	void annotationWithOnlyValueAttribute() throws IOException {
		List<String> lines = writeClassAnnotation(
				Annotation.name("org.springframework.test.TestApplication",
						(builder) -> builder.attribute("value", String.class, "test")));
		assertThat(lines).containsExactly("package com.example;", "",
				"import org.springframework.test.TestApplication;", "",
				"@TestApplication(\"test\")", "public class Test {", "", "}", "");
	}

	@Test
	void annotationWithSimpleEnumAttribute() throws IOException {
		List<String> lines = writeClassAnnotation(
				Annotation.name("org.springframework.test.TestApplication",
						(builder) -> builder.attribute("unit", Enum.class,
								"java.time.temporal.ChronoUnit.SECONDS")));
		assertThat(lines).containsExactly("package com.example;", "",
				"import java.time.temporal.ChronoUnit;",
				"import org.springframework.test.TestApplication;", "",
				"@TestApplication(unit = ChronoUnit.SECONDS)", "public class Test {", "",
				"}", "");
	}

	@Test
	void annotationWithClassArrayAttribute() throws IOException {
		List<String> lines = writeClassAnnotation(
				Annotation.name("org.springframework.test.TestApplication",
						(builder) -> builder.attribute("target", Class.class,
								"com.example.One", "com.example.Two")));
		assertThat(lines).containsExactly("package com.example;", "",
				"import com.example.One;", "import com.example.Two;",
				"import org.springframework.test.TestApplication;", "",
				"@TestApplication(target = { One.class, Two.class })",
				"public class Test {", "", "}", "");
	}

	@Test
	void annotationWithSeveralAttributes() throws IOException {
		List<String> lines = writeClassAnnotation(Annotation.name(
				"org.springframework.test.TestApplication",
				(builder) -> builder.attribute("target", Class.class, "com.example.One")
						.attribute("unit", ChronoUnit.class,
								"java.time.temporal.ChronoUnit.NANOS")));
		assertThat(lines).containsExactly("package com.example;", "",
				"import com.example.One;", "import java.time.temporal.ChronoUnit;",
				"import org.springframework.test.TestApplication;", "",
				"@TestApplication(target = One.class, unit = ChronoUnit.NANOS)",
				"public class Test {", "", "}", "");
	}

	private List<String> writeClassAnnotation(Annotation annotation) throws IOException {
		JavaSourceCode sourceCode = new JavaSourceCode();
		JavaCompilationUnit compilationUnit = sourceCode
				.createCompilationUnit("com.example", "Test");
		JavaTypeDeclaration test = compilationUnit.createTypeDeclaration("Test");
		test.annotate(annotation);
		return writeSingleType(sourceCode, "com/example/Test.java");
	}

	@Test
	void methodWithSimpleAnnotation() throws IOException {
		JavaSourceCode sourceCode = new JavaSourceCode();
		JavaCompilationUnit compilationUnit = sourceCode
				.createCompilationUnit("com.example", "Test");
		JavaTypeDeclaration test = compilationUnit.createTypeDeclaration("Test");
		JavaMethodDeclaration method = JavaMethodDeclaration.method("something")
				.returning("void").parameters().body();
		method.annotate(Annotation.name("com.example.test.TestAnnotation"));
		test.addMethodDeclaration(method);
		List<String> lines = writeSingleType(sourceCode, "com/example/Test.java");
		assertThat(lines).containsExactly("package com.example;", "",
				"import com.example.test.TestAnnotation;", "", "public class Test {", "",
				"    @TestAnnotation", "    public void something() {", "    }", "", "}",
				"");
	}

	private List<String> writeSingleType(JavaSourceCode sourceCode, String location)
			throws IOException {
		Path source = writeSourceCode(sourceCode).resolve(location);
		assertThat(source).isRegularFile();
		return Files.readAllLines(source);
	}

	private Path writeSourceCode(JavaSourceCode sourceCode) throws IOException {
		Path projectDirectory = Files.createTempDirectory(this.directory, "project-");
		this.writer.writeTo(projectDirectory, sourceCode);
		return projectDirectory;
	}

}
