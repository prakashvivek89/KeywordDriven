package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SSPWorldWide.Framework.Adviser.ReadExcel.ReadTestcaseFile;
import com.SSPWorldWide.Framework.Adviser.Helper.Constants;

/**
 * This class creates the dynamic class for each test case excel file. Methods
 * in the dynamic class are the test case id mentioned in the excel file.
 */

public class DynamicClassCreator {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(DynamicClassCreator.class);

	public static void createRegressionSuiteClasses() throws Exception {
		makeDirectory(Constants.DYNAMICCLASSDIR.toString());
		File ExcelFileToRead = new File(Constants.EXCELTESTCASEPATH.toString());
		File[] files = ExcelFileToRead.listFiles();
		for (File f : files) {
			dynamicMethodCreation(f);
		}
		compile();
		dynamicClassLoader();
	}

	public static void createSmokeSuiteClasses() throws Exception {
		makeDirectory(Constants.DYNAMICCLASSDIR.toString());
		File ExcelFileToRead = new File(Constants.EXCELTESTCASEPATH.toString());
		File[] files = ExcelFileToRead.listFiles();
		for (File f : files) {
			dynamicMethodCreation(f);
		}
		compile();
		dynamicClassLoader();
	}

	public static void createSingleSuiteClasse(String suiteName) throws Exception {
		makeDirectory(Constants.DYNAMICCLASSDIR.toString());
		if (suiteName.contains(",")) {
			for (String suite : suiteName.split(",")) {
				suite = suite.trim();
				dynamicMethodCreation(suite);
			}
		} else {
			suiteName = suiteName.trim();
			dynamicMethodCreation(suiteName);
		}
		compile();
		dynamicClassLoader();
	}

	public static void createSingleTestCaseClass(String suiteName, String tcID) throws Exception {
		makeDirectory(Constants.DYNAMICCLASSDIR.toString());
		String fileName = suiteName + ".java";
		File myFIle = new File(Constants.DYNAMICCLASSDIR.toString(), fileName);
		FileWriter fileWriter = new FileWriter(myFIle);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(Constants.CLASSTEXT.toString());
		bufferedWriter.newLine();
		bufferedWriter.write("public class " + suiteName + " extends TestcaseFlow{");
		if(tcID.contains(",")) {
			for(String id : tcID.split(",")) {
				id = id.trim();
				if (!(ReadTestcaseFile.testcases_persuite.get(id).isEmpty())) {
					dynamicMethodCreation(bufferedWriter, suiteName, id);
				} else {
					bufferedWriter.newLine();
					bufferedWriter.write(" @Test(description = \"" + ReadTestcaseFile.scenarioNames.get(id) + "\")");
					bufferedWriter.newLine();
					bufferedWriter.write(" public void " + id.replace(".", "_") + "() throws Exception{");
					bufferedWriter.newLine();
					bufferedWriter.write("throw new Exception(\" " + id
							+ "\"+ \" &ensp;:&ensp; testcaseID does not exist in the test suite\");");
					bufferedWriter.newLine();
					bufferedWriter.write("}");
				}
			}
		}
		else {
			if (!(ReadTestcaseFile.testcases_persuite.get(tcID.trim()).isEmpty())) {
				dynamicMethodCreation(bufferedWriter, suiteName, tcID);
			} else {
				bufferedWriter.newLine();
				bufferedWriter.write(" @Test(description = \"" + ReadTestcaseFile.scenarioNames.get(tcID) + "\")");
				bufferedWriter.newLine();
				bufferedWriter.write(" public void " + tcID.replace(".", "_") + "() throws Exception{");
				bufferedWriter.newLine();
				bufferedWriter.write("throw new Exception(\" " + tcID
						+ "\"+ \" &ensp;:&ensp; testcaseID does not exist in the test suite\");");
				bufferedWriter.newLine();
				bufferedWriter.write("}");
			}
		}
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.close();
		compile();
		dynamicClassLoader();
		
		
	}

	public static void makeDirectory(String path) {
		File theDir = new File(path);

		if (!theDir.exists()) {
			LOG.info("creating directory: " + theDir.getName());
			boolean result = false;
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
			}
			if (result) {
				LOG.info("DIR created");
			}
		}
	}

	public static void dynamicClassLoader() throws Exception {
		try {
			File file = new File(Constants.CLASSLOADER.toString());
			File[] files = file.listFiles();
			for (File f : files) {
				URL url = f.toURI().toURL();
				URL[] urls = new URL[] { url };
				URLClassLoader cl = URLClassLoader.newInstance(urls);
				Class cls = cl.loadClass(Constants.PACKAGENAME.toString() + f.getName().split("\\.")[0].trim());
				Object o = cls.newInstance();
				ProtectionDomain pDomain = cls.getProtectionDomain();
				CodeSource cSource = pDomain.getCodeSource();
				URL urlfrom = cSource.getLocation();
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	public static void compile() throws Exception {
		System.setProperty("java.home", Constants.JAVA_HOME.toString());
		File file = new File(Constants.DYNAMICCLASSDIR.toString());
		File[] files = file.listFiles();
		for (File f : files) {
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
					Arrays.asList(new File(System.getProperty("user.dir") + "/target/test-classes")));
			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(f));
			compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
			fileManager.close();
		}
	}

	public static void dynamicMethodCreation(File f) throws Exception {
		if (!(f.getName().contains("~"))) {
			String fileName = FilenameUtils.removeExtension(f.getName()) + ".java";
			String className = FilenameUtils.removeExtension(f.getName());
			File myFIle = new File(Constants.DYNAMICCLASSDIR.toString(), fileName);
			FileWriter fileWriter = new FileWriter(myFIle);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Constants.CLASSTEXT.toString());
			bufferedWriter.newLine();
			bufferedWriter.write("public class " + className + " extends TestcaseFlow{");
			for (String testCaseID : ReadTestcaseFile.testcases_persuite.keySet()) {
				if (className.equalsIgnoreCase(testCaseID.split("\\.")[0])) {
					dynamicMethodCreation(bufferedWriter, className, testCaseID);
				}
			}
			bufferedWriter.newLine();
			bufferedWriter.write("}");
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
	}

	public static void dynamicMethodCreation(String suiteName) throws Exception {
		if (!(suiteName.contains("~"))) {
			String fileName = suiteName + ".java";
			String className = suiteName;
			File myFIle = new File(Constants.DYNAMICCLASSDIR.toString(), fileName);
			FileWriter fileWriter = new FileWriter(myFIle);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Constants.CLASSTEXT.toString());
			bufferedWriter.newLine();
			bufferedWriter.write("public class " + className + " extends TestcaseFlow{");
			for (String testCaseID : ReadTestcaseFile.testcases_persuite.keySet()) {
				if (className.equalsIgnoreCase(testCaseID.split("\\.")[0])) {
					dynamicMethodCreation(bufferedWriter, className, testCaseID);
				}
			}
			bufferedWriter.newLine();
			bufferedWriter.write("}");
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
	}
	
	public static void dynamicMethodCreation(BufferedWriter bufferedWriter, String suiteName, String testCaseID) throws Exception {
		bufferedWriter.newLine();
		bufferedWriter
				.write(" @Test(description = \"" + ReadTestcaseFile.scenarioNames.get(testCaseID) + "\")");
		bufferedWriter.newLine();
		bufferedWriter.write(" public void " + testCaseID.replace(".", "_") + "() throws Exception{");
		bufferedWriter.newLine();
		bufferedWriter.write("runSingleTest(\"" + testCaseID + "\");");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
	}
}