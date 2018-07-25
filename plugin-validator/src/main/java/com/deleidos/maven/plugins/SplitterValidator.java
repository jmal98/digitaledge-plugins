package com.deleidos.maven.plugins;

import java.util.Map;


public class SplitterValidator extends AbstractPluginValidator {

	public SplitterValidator(Map<String, String> services) {
		super(services);

	}

	public String getReason() {
		return "Failed to validate Splitter Plugin";
	}


}
