package com.deleidos.maven.plugins.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.deleidos.maven.plugins.utils.PluginValidatorChooser;

@Ignore
public class PluginValidatorChooserTest {

	@Test
	public void test() {
		File plugin = new File(
				"C:/Users/bullockja/Documents/workspace-sts-2.8.1.RELEASE/parser-unstructured-file/target/parser-unstructured-file-1.0.0-SNAPSHOT.jar");
		PluginValidatorChooser chooser = new PluginValidatorChooser();
		assertNotNull(chooser.getValidator(plugin));
	}
}
