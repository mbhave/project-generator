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

package io.spring.initializr.generator.project.documentation.gettingstarted;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Madhura Bhave
 */
public class GettingStarted {

	private Map<String, Object> parts;

	public GettingStarted(Builder builder) {
		Map<String, Object> content = new LinkedHashMap<>(builder.subSection);
		this.parts = Collections.unmodifiableMap(content);
	}

	public Map<String, Object> getParts() {
		return this.parts;
	}

	static class Builder {

		private final Map<String, Object> subSection;

		public Builder() {
			this.subSection = new LinkedHashMap<>();
		}

		public Builder withSubSection(String key, Object value) {
			this.subSection.put(key, value);
			return this;
		}

		public Builder withSubSections(Map<String, Object> details) {
			this.subSection.putAll(details);
			return this;
		}

		public GettingStarted build() {
			return new GettingStarted(this);
		}

	}

}
