package com.deleidos.maven.plugins.checks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.deleidos.maven.plugins.ValidationCondition;

public class ClassExistsCheck implements ValidationCondition {

	public boolean check(String className, File artifact) {
		boolean passed = false;

		InputStream is;
		try {
			is = new FileInputStream(artifact);
			JarInputStream jar = null;
			try {
				jar = new JarInputStream(is);
				JarEntry entry;

				while ((entry = jar.getNextJarEntry()) != null) {
					if (entry.getName().replace("/", ".").replace(".class", "")
							.equals(className))
						passed = true;

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (jar != null)
					try {
						jar.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {

		}
		return passed;
	}
}
