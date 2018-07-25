package com.deleidos.maven.plugins.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.deleidos.maven.plugins.DatasinkValidator;
import com.deleidos.maven.plugins.ParserValidator;
import com.deleidos.maven.plugins.PluginValidator;
import com.deleidos.maven.plugins.ProcessorValidator;
import com.deleidos.maven.plugins.SplitterValidator;
import com.deleidos.maven.plugins.TranslatorValidator;
import com.deleidos.maven.plugins.TransportValidator;

public class PluginValidatorChooser {

	private static String JAR_SERVICES_FILE_PATTERN = "^META-INF/services/.+";

	public static PluginValidator getValidator(File artifact) {
		PluginValidator validator = null;
		Map<String, String> services = getServicesClass(artifact);

		/*
		 * Assumes all services implement the same parent interface
		 */
		if (services.size() > 0) {
			Iterator<String> iterator = services.keySet().iterator();
			while (iterator.hasNext()) {
				String baseClass = services.get(iterator.next());

				if (baseClass.toLowerCase(Locale.getDefault()).contains("parser")) {
					return new ParserValidator(services);
				} else if (baseClass.toLowerCase(Locale.getDefault()).contains("processor")) {
					return new ProcessorValidator(services);
				} else if (baseClass.toLowerCase(Locale.getDefault()).contains("transport")) {
					return new TransportValidator(services);
				} else if (baseClass.toLowerCase(Locale.getDefault()).contains("datasink")) {
					return new DatasinkValidator(services);
				} else if (baseClass.toLowerCase(Locale.getDefault()).contains("translator")) {
					return new TranslatorValidator(services);
				} else if (baseClass.toLowerCase(Locale.getDefault()).contains("splitter")) {
					return new SplitterValidator(services);
				}
			}
		}
		return validator;
	}

	private static Map<String, String> getServicesClass(File artifact) {
		Map<String, String> serviceMap = new HashMap<String, String>();
		InputStream is;
		try {
			is = new FileInputStream(artifact);
			try {
				JarInputStream jar = new JarInputStream(is);
				JarEntry entry;
				while ((entry = jar.getNextJarEntry()) != null) {
					if (entry.getName().matches(JAR_SERVICES_FILE_PATTERN)) {

						BufferedReader br = new BufferedReader(
								new InputStreamReader(jar));
						String serviceClassItf = entry.getName().replace(
								"META-INF/services/", "");
						String serviceClassImpl = null;

						while ((serviceClassImpl = br.readLine()) != null) {
							if (serviceClassImpl.trim().length() > 0) {
								serviceMap.put(serviceClassImpl,
										serviceClassItf);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return serviceMap;
	}
}
