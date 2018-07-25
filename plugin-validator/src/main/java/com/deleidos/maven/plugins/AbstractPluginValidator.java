package com.deleidos.maven.plugins;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

public abstract class AbstractPluginValidator implements PluginValidator {

	private Log log;

	private Map<String, String> services;

	private List<String> checks;

	public AbstractPluginValidator(Map<String, String> services) {
		this.services = services;
	}

	public boolean validate(File artifact) {
		boolean passed = false;

		Iterator<String> iterator = services.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			log.info("Service definition found:" + key + " : "
					+ services.get(key));
			for (String check : checks) {
				Class<?> v;
				try {
					String validationClassName = "com.deleidos.maven.plugins.checks."
							+ check;
					log.info("Loading check: " + validationClassName);
					v = Class.forName(validationClassName);
					ValidationCondition vc = (ValidationCondition) v
							.newInstance();

					passed = vc.check(key, artifact);

					// Fail fast
					if (!passed) {
						log.info(String
								.format("%s class does not exist in %s.  Review/correct the services definition under this project's src/main/resources/META-INF/services/<service>",
										key, artifact));
						return passed;
					}

				} catch (ClassNotFoundException e) {
					log.error(e);
				} catch (InstantiationException e) {
					log.error(e);
				} catch (IllegalAccessException e) {
					log.error(e);
				}
			}
		}
		return passed;
	}

	public void setLogger(Log log) {
		this.log = log;

	}

	public Log getLogger() {
		return this.log;
	}

	public void setChecks(List<String> validationRules) {
		this.checks = validationRules;

	}

}
