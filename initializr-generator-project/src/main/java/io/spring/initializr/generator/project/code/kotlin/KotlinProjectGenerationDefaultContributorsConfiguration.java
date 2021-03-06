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

package io.spring.initializr.generator.project.code.kotlin;

import io.spring.initializr.generator.ProjectDescription;
import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.gradle.ConditionalOnGradle;
import io.spring.initializr.generator.buildsystem.maven.ConditionalOnMaven;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.language.Parameter;
import io.spring.initializr.generator.language.kotlin.KotlinCompilationUnit;
import io.spring.initializr.generator.language.kotlin.KotlinExpressionStatement;
import io.spring.initializr.generator.language.kotlin.KotlinFunctionDeclaration;
import io.spring.initializr.generator.language.kotlin.KotlinFunctionInvocation;
import io.spring.initializr.generator.language.kotlin.KotlinModifier;
import io.spring.initializr.generator.language.kotlin.KotlinReifiedFunctionInvocation;
import io.spring.initializr.generator.language.kotlin.KotlinReturnStatement;
import io.spring.initializr.generator.language.kotlin.KotlinTypeDeclaration;
import io.spring.initializr.generator.packaging.war.ConditionalOnWarPackaging;
import io.spring.initializr.generator.project.build.BuildCustomizer;
import io.spring.initializr.generator.project.code.MainCompilationUnitCustomizer;
import io.spring.initializr.generator.project.code.ServletInitializerCustomizer;
import io.spring.initializr.generator.project.code.TestApplicationTypeCustomizer;
import io.spring.initializr.generator.springboot.ConditionalOnSpringBootVersion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default Kotlin language contributors.
 *
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 */
@Configuration
class KotlinProjectGenerationDefaultContributorsConfiguration {

	@Bean
	public TestApplicationTypeCustomizer<KotlinTypeDeclaration> testMethodContributor() {
		return (typeDeclaration) -> {
			KotlinFunctionDeclaration function = KotlinFunctionDeclaration
					.function("contextLoads").body();
			function.annotate(Annotation.name("org.junit.Test"));
			typeDeclaration.addFunctionDeclaration(function);
		};
	}

	@Bean
	public BuildCustomizer<Build> kotlinDependenciesConfigurer(
			ProjectDescription project) {
		return new KotlinDependenciesConfigurer(project.getSpringBootVersion());
	}

	/**
	 * Configuration for Kotlin projects using Spring Boot 1.5.
	 */
	@Configuration
	@ConditionalOnSpringBootVersion("[1.5.0.M1, 2.0.0.M1)")
	static class SpringBoot15KotlinProjectGenerationConfiguration {

		@Bean
		public KotlinProjectSettings kotlinProjectSettings() {
			return new KotlinProjectSettings("1.2.51");
		}

		@Bean
		public MainCompilationUnitCustomizer<KotlinTypeDeclaration, KotlinCompilationUnit> boot15MainFunctionContributor() {
			return (compilationUnit) -> {
				compilationUnit.addTopLevelFunction(KotlinFunctionDeclaration
						.function("main")
						.parameters(new Parameter("Array<String>", "args"))
						.body(new KotlinExpressionStatement(new KotlinFunctionInvocation(
								"org.springframework.boot.SpringApplication", "run",
								"DemoApplication::class.java", "*args"))));
			};
		}

	}

	/**
	 * Configuration for Kotlin projects using Spring Boot 2.0 and later.
	 */
	@Configuration
	@ConditionalOnSpringBootVersion("2.0.0.M1")
	static class SpringBoot2AndLaterKotlinProjectGenerationConfiguration {

		@Bean
		public KotlinProjectSettings kotlinProjectSettings() {
			return new KotlinProjectSettings("1.2.70");
		}

		@Bean
		public MainCompilationUnitCustomizer<KotlinTypeDeclaration, KotlinCompilationUnit> mainFunctionContributor() {
			return (compilationUnit) -> {
				compilationUnit
						.addTopLevelFunction(KotlinFunctionDeclaration.function("main")
								.parameters(new Parameter("Array<String>", "args"))
								.body(new KotlinExpressionStatement(
										new KotlinReifiedFunctionInvocation(
												"org.springframework.boot.runApplication",
												"DemoApplication", "*args"))));
			};
		}

	}

	/**
	 * Kotlin source code contributions for projects using war packaging.
	 */
	@Configuration
	@ConditionalOnWarPackaging
	static class WarPackagingConfiguration {

		@Bean
		public ServletInitializerCustomizer<KotlinTypeDeclaration> javaServletInitializerCustomizer() {
			return (typeDeclaration) -> {
				KotlinFunctionDeclaration configure = KotlinFunctionDeclaration
						.function("configure").modifiers(KotlinModifier.OVERRIDE)
						.returning(
								"org.springframework.boot.builder.SpringApplicationBuilder")
						.parameters(new Parameter(
								"org.springframework.boot.builder.SpringApplicationBuilder",
								"application"))
						.body(new KotlinReturnStatement(
								new KotlinFunctionInvocation("application", "sources",
										"DemoApplication::class.java")));
				typeDeclaration.addFunctionDeclaration(configure);
			};
		}

	}

	/**
	 * Configuration for Kotlin projects built with Maven.
	 */
	@Configuration
	@ConditionalOnMaven
	static class KotlinMavenProjectConfiguration {

		@Bean
		public KotlinMavenBuildCustomizer kotlinBuildCustomizer(
				KotlinProjectSettings kotlinProjectSettings) {
			return new KotlinMavenBuildCustomizer(kotlinProjectSettings);
		}

	}

	/**
	 * Configuration for Kotlin projects built with Gradle.
	 *
	 * @author Andy Wilkinson
	 */
	@Configuration
	@ConditionalOnGradle
	static class KotlinGradleProjectConfiguration {

		@Bean
		public KotlinGradleBuildCustomizer kotlinBuildCustomizer(
				KotlinProjectSettings kotlinProjectSettings) {
			return new KotlinGradleBuildCustomizer(kotlinProjectSettings);
		}

	}

}
