package com.deleidos.maven.plugins;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

public interface PluginValidator {

	public boolean validate(File artifact);

	public String getReason();

	public void setLogger(Log log);

	public Log getLogger();

	public void setChecks(List<String> validationRules);
}
