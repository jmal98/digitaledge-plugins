package com.deleidos.maven.plugins;

import java.util.Map;

public class TranslatorValidator extends AbstractPluginValidator {

	public TranslatorValidator(Map<String, String> services) {
		super(services);

	}

	public String getReason() {

		return "Failed to validate Datasink Plugin";
	}

}
