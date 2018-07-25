package com.deleidos.maven.plugins;

import java.io.File;

public interface ValidationCondition {

	public boolean check(String className, File artifact);

}
