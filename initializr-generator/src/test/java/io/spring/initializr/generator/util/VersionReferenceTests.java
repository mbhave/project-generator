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

package io.spring.initializr.generator.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link VersionReference}.
 *
 * @author Stephane Nicoll
 */
class VersionReferenceTests {

	@Test
	void ofPropertyWithVersionProperty() {
		VersionProperty property = VersionProperty.of("test.version", true);
		VersionReference reference = VersionReference.ofProperty(property);
		assertThat(reference.isProperty()).isTrue();
		assertThat(reference.getProperty()).isEqualTo(property);
		assertThat(reference.getValue()).isNull();
		assertThat(reference).hasToString("${test.version}");
	}

	@Test
	void ofPropertyWithInternalProperty() {
		VersionReference reference = VersionReference.ofProperty("test.version");
		assertThat(reference.isProperty()).isTrue();
		assertThat(reference.getProperty().toStandardFormat()).isEqualTo("test.version");
		assertThat(reference.getValue()).isNull();
		assertThat(reference).hasToString("${test.version}");
	}

	@Test
	void ofPropertyWithValue() {
		VersionReference reference = VersionReference.ofValue("1.2.3.RELEASE");
		assertThat(reference.isProperty()).isFalse();
		assertThat(reference.getProperty()).isNull();
		assertThat(reference.getValue()).isEqualTo("1.2.3.RELEASE");
		assertThat(reference).hasToString("1.2.3.RELEASE");
	}

}
