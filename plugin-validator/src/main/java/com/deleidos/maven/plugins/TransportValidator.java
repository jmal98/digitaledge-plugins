package com.deleidos.maven.plugins;

import java.util.Map;

public class TransportValidator extends AbstractPluginValidator {

	public TransportValidator(Map<String, String> services) {
		super(services);

	}

	public String getReason() {

		return "Failed to validate Datasink Plugin";
	}

}
