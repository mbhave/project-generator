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

package io.spring.initializr.generator.project.code.groovy;

import java.lang.reflect.Modifier;

import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.groovy.GroovyExpressionStatement;
import io.spring.initializr.generator.language.groovy.GroovyMethodDeclaration;
import io.spring.initializr.generator.language.groovy.GroovyMethodInvocation;
import io.spring.initializr.generator.language.groovy.GroovyReturnStatement;
import io.spring.initializr.generator.language.groovy.GroovyTypeDeclaration;
import io.spring.initializr.generator.packaging.war.ConditionalOnWarPackaging;
import io.spring.initializr.generator.project.code.MainApplicationTypeCustomizer;
import io.spring.initializr.generator.project.code.ServletInitializerCustomizer;
import io.spring.initializr.generator.project.code.TestApplicationTypeCustomizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default Groovy language contributors.
 *
 * @author Stephane Nicoll
 */
@Configuration
class GroovyProjectGenerationDefaultContributorsConfiguration {

	@Bean
	public MainApplicationTypeCustomizer<GroovyTypeDeclaration> mainMethodContributor() {
		return (typeDeclaration) -> typeDeclaration
				.addMethodDeclaration(GroovyMethodDeclaration.method("main")
						.modifiers(Modifier.PUBLIC | Modifier.STATIC).returning("void")
						.parameters(new Parameter("java.lang.String[]", "args"))
						.body(new GroovyExpressionStatement(new GroovyMethodInvocation(
								"org.springframework.boot.SpringApplication", "run",
								typeDeclaration.getName(), "args"))));
	}

	@Bean
	public TestApplicationTypeCustomizer<GroovyTypeDeclaration> testMethodContributor() {
		return (typeDeclaration) -> {
			GroovyMethodDeclaration method = GroovyMethodDeclaration
					.method("contextLoads").modifiers(Modifier.PUBLIC).returning("void")
					.body();
			method.annotate(Annotation.name("org.junit.Test"));
			typeDeclaration.addMethodDeclaration(method);
		};
	}

	/**
	 * Groovy source code contributions for projects using war packaging.
	 */
	@Configuration
	@ConditionalOnWarPackaging
	static class WarPackagingConfiguration {

		@Bean
		public ServletInitializerCustomizer<GroovyTypeDeclaration> javaServletInitializerCustomizer() {
			return (typeDeclaration) -> {
				GroovyMethodDeclaration configure = GroovyMethodDeclaration
						.method("configure").modifiers(Modifier.PROTECTED)
						.returning(
								"org.springframework.boot.builder.SpringApplicationBuilder")
						.parameters(new Parameter(
								"org.springframework.boot.builder.SpringApplicationBuilder",
								"application"))
						.body(new GroovyReturnStatement(new GroovyMethodInvocation(
								"application", "sources", "DemoApplication")));
				configure.annotate(Annotation.name("java.lang.Override"));
				typeDeclaration.addMethodDeclaration(configure);
			};
		}

	}

}
