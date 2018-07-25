package com.deleidos.maven.plugins;

import java.util.Map;

public class ParserValidator extends AbstractPluginValidator {

	public ParserValidator(Map<String, String> services) {
		super(services);
	}

	public String getReason() {

		return "Failed to validate Parser Plugin";
	}

}
