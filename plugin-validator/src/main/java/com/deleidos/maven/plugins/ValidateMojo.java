package com.deleidos.maven.plugins;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.deleidos.maven.plugins.utils.PluginValidatorChooser;

/**
 * Goal which validates that a plugin is correctly configured for use in the
 * system.
 * 
 * @goal validate-plugin
 * 
 * @phase verify
 */
public class ValidateMojo extends AbstractMojo {
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Artifact to validate.
	 * 
	 * @parameter expression="${project.build.finalName}"
	 * @required
	 */
	private String artifact;

	/**
	 * Artifact to validate.
	 * 
	 * @parameter expression="${project.packaging}"
	 * @required
	 */
	private String type;

	/**
	 * Checks to perform.
	 * 
	 * @parameter
	 * @required
	 */
	private List<String> validationRules;

	/**
	 * flag to skip validation for those plugins which are dependencies for
	 * others but not to be included
	 * 
	 * @parameter
	 * @required
	 */
	private boolean bypassValidation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */

	public void execute() throws MojoExecutionException, MojoFailureException {

		File artifactToValidate = new File(outputDirectory + File.separator
				+ artifact + "." + type);

		if (!artifactToValidate.getName().endsWith(".jar")) {
			getLog().info(
					"Skipping validation of artifact:"
							+ artifactToValidate.getAbsolutePath());
		} else {

			if (!artifactToValidate.exists()) {
				throw new MojoExecutionException(
						"Unable to valiate missing artifact: "
								+ artifactToValidate.getAbsolutePath());
			}

			PluginValidator validator = PluginValidatorChooser
					.getValidator(artifactToValidate);

			if (!bypassValidation) {
				if (validator == null) {
					throw new MojoExecutionException(
							"Unexpected services implementation found.");
				}

				getLog().info(
						"Validating: " + artifactToValidate.getAbsolutePath());
				validator.setChecks(validationRules);
				validator.setLogger(getLog());
				if (!validator.validate(artifactToValidate)) {
					throw new MojoFailureException(validator.getReason());

				}
			}
		}
	}
}
