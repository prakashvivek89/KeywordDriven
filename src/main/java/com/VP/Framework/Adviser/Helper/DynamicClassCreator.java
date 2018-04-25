package com.VP.Framework.Adviser.Helper;

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

import com.VP.Framework.Adviser.Helper.Constants;
import com.VP.Framework.Adviser.ReadExcel.ReadTestcaseFile;

public class DynamicClassCreator {

	public static void createRegressionSuiteClasses() throws Exception {
		makeDirectory(Constants.DYNAMICCLASSDIR.toString());
		File ExcelFileToRead = new File(Constants.EXCELTESTCASEPATH.toString());
		File[] files = ExcelFileToRead.listFiles();
		for (File f : files) {
			ReadTestcaseFile.testcases_persuite.clear();
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
			ReadTestcaseFile.testcases_persuite.clear();
			ReadTestcaseFile.smokeTestCases.clear();
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
		bufferedWriter.write("public class " + suiteName + " extends WebdriverHelper{");
		ReadTestcaseFile.testcases_persuite.clear();
		ReadTestcaseFile.readTestcaseFile(FilenameUtils.removeExtension(suiteName));
		if(tcID.contains(",")) {
			for(String id : tcID.split(",")) {
				id = id.trim();
				if (!(ReadTestcaseFile.testcases_persuite.get(id).isEmpty())) {
					dynamicMethodCreation(bufferedWriter, suiteName, id);
				} else {
					bufferedWriter.newLine();
					bufferedWriter.write(" @Test(description = \"" + ReadTestcaseFile.scenarioNames.get(id) + "\")");
					bufferedWriter.newLine();
					bufferedWriter.write(" public static void " + id.replace(".", "_") + "() throws Exception{");
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
				bufferedWriter.write(" public static void " + tcID.replace(".", "_") + "() throws Exception{");
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
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}

	public static void dynamicClassLoader() throws Exception {
		try {
			File file = new File(
					System.getProperty("user.dir") + "/target/test-classes/com/VP/Framework/Adviser/Testcases");
			File[] files = file.listFiles();
			for (File f : files) {
				URL url = f.toURI().toURL();
				URL[] urls = new URL[] { url };
				URLClassLoader cl = URLClassLoader.newInstance(urls);
				Class cls = cl.loadClass("com.VP.Framework.Adviser.Testcases." + f.getName().split("\\.")[0].trim());
				Object o = cls.newInstance();
				ProtectionDomain pDomain = cls.getProtectionDomain();
				CodeSource cSource = pDomain.getCodeSource();
				URL urlfrom = cSource.getLocation();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void compile() throws Exception {
		System.setProperty("java.home", "C:/Program Files/Java/jdk1.8.0_102");
		File file = new File(System.getProperty("user.dir") + "/src/main/java/com/VP/Framework/Adviser/Testcases");
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
			ReadTestcaseFile.readTestcaseFile(FilenameUtils.removeExtension(f.getName()));
			String fileName = FilenameUtils.removeExtension(f.getName()) + ".java";
			String className = FilenameUtils.removeExtension(f.getName());
			File myFIle = new File(Constants.DYNAMICCLASSDIR.toString(), fileName);
			FileWriter fileWriter = new FileWriter(myFIle);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Constants.CLASSTEXT.toString());
			bufferedWriter.newLine();
			bufferedWriter.write("public class " + className + " extends WebdriverHelper{");
			for (String testCaseID : ReadTestcaseFile.testcases_persuite.keySet()) {
				dynamicMethodCreation(bufferedWriter, className, testCaseID);
			}
			bufferedWriter.newLine();
			bufferedWriter.write("}");
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
	}

	public static void dynamicMethodCreation(String suiteName) throws Exception {
		if (!(suiteName.contains("~"))) {
			ReadTestcaseFile.testcases_persuite.clear();
			ReadTestcaseFile.readTestcaseFile(suiteName);
			String fileName = suiteName + ".java";
			String className = suiteName;
			File myFIle = new File(Constants.DYNAMICCLASSDIR.toString(), fileName);
			FileWriter fileWriter = new FileWriter(myFIle);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Constants.CLASSTEXT.toString());
			bufferedWriter.newLine();
			bufferedWriter.write("public class " + className + " extends WebdriverHelper{");
			for (String testCaseID : ReadTestcaseFile.testcases_persuite.keySet()) {
				dynamicMethodCreation(bufferedWriter, className, testCaseID);
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
		bufferedWriter.write(" public static void " + testCaseID.replace(".", "_") + "() throws Exception{");
		bufferedWriter.newLine();
		bufferedWriter.write("TestcaseFlow.runSingleTest(\"" + suiteName + "\", \"" + testCaseID + "\");");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
	}
}