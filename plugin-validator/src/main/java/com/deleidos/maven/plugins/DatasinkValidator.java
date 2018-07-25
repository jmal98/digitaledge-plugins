package com.deleidos.maven.plugins;

import java.util.Map;

public class DatasinkValidator extends AbstractPluginValidator {

	public DatasinkValidator(Map<String, String> services) {
		super(services);

	}

	public String getReason() {

		return "Failed to validate Datasink Plugin";
	}

}
