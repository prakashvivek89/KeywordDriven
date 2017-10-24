package com.SSPWorldWide.Framework.Adviser.Helper;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.Read_Testcase_File;

public class DynamicClassCreator {
	private static final String excelTestcasePath = System.getProperty("user.dir")
			+ "/ProjectAutomationFiles/testcases";
	private static final String dynamicClassDir = System.getProperty("user.dir")
			+ "/src/test/java/com/SSPWorldWide/Framework/Adviser/Testcases";
	private static final String classText = "package com.SSPWorldWide.Framework.Adviser.Testcases;\r\n" + "\r\n"
			+ "import org.testng.annotations.Test;\r\n" + "import com.SSPWorldWide.Framework.Adviser.Helper.*;\r\n"
			+ "import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;";

	public static void createRegressionSuiteClasses() throws Exception {
		makeDirectory(dynamicClassDir);
		File ExcelFileToRead = new File(excelTestcasePath);
		File[] files = ExcelFileToRead.listFiles();
		for (File f : files) {
			Read_Testcase_File.testcases_persuite.clear();
			Read_Testcase_File.readTestcaseFile(FilenameUtils.removeExtension(f.getName()));
			String fileName = FilenameUtils.removeExtension(f.getName()) + ".java";
			String className = FilenameUtils.removeExtension(f.getName());
			File myFIle = new File(dynamicClassDir, fileName);
			FileWriter fileWriter = new FileWriter(myFIle);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(classText);
			bufferedWriter.newLine();
			bufferedWriter.write("public class " + className + " extends WebdriverHelper{");
			for (String testCaseID : Read_Testcase_File.testcases_persuite.keySet()) {
				String methodName = testCaseID.replace(".", "_");
				bufferedWriter.newLine();
				bufferedWriter
						.write(" @Test(description = \"" + Read_Testcase_File.scenarioNames.get(testCaseID) + "\")");
				bufferedWriter.newLine();
				bufferedWriter.write(" public static void " + methodName + "() throws Exception{");
				bufferedWriter.newLine();
				bufferedWriter.write("TestcaseFlow.runSingleTest(\"" + className + "\", \"" + testCaseID + "\");");
				bufferedWriter.newLine();
				bufferedWriter.write("}");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("}");
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		compile();
		dynamicClassLoader();
	}

	public static void createSingleTestCaseClass(String suiteName, String tcID) throws Exception {
		makeDirectory(dynamicClassDir);
		String fileName = suiteName + ".java";
		File myFIle = new File(dynamicClassDir, fileName);
		FileWriter fileWriter = new FileWriter(myFIle);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(classText);
		bufferedWriter.newLine();
		bufferedWriter.write("public class " + suiteName + " extends WebdriverHelper{");
		Read_Testcase_File.testcases_persuite.clear();
		Read_Testcase_File.readTestcaseFile(FilenameUtils.removeExtension(suiteName));
		if (!(Read_Testcase_File.testcases_persuite.get(tcID).isEmpty())) {
			bufferedWriter.newLine();
			bufferedWriter.write(" @Test(description = \"" + Read_Testcase_File.scenarioNames.get(tcID) + "\")");
			bufferedWriter.newLine();
			bufferedWriter.write(" public static void " + tcID.replace(".", "_") + "() throws Exception{");
			bufferedWriter.newLine();
			bufferedWriter.write("TestcaseFlow.runSingleTest(\"" + suiteName + "\", \"" + tcID + "\");");
			bufferedWriter.newLine();
			bufferedWriter.write("}");
		} else {
			bufferedWriter.newLine();
			bufferedWriter.write(" @Test(description = \"" + Read_Testcase_File.scenarioNames.get(tcID) + "\")");
			bufferedWriter.newLine();
			bufferedWriter.write(" public static void " + tcID + "() throws Exception{");
			bufferedWriter.newLine();
			bufferedWriter.write("throw new Exception(\" " + tcID
					+ "\"+ \" &ensp;:&ensp; testcaseID does not exist in the test suite\");");
			bufferedWriter.newLine();
			bufferedWriter.write("}");
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

	public static void dynamicClassLoader() throws IntrospectionException {
		try {
			File file = new File(System.getProperty("user.dir")
					+ "/target/test-classes/com/SSPWorldWide/Framework/Adviser/Testcases");
			File[] files = file.listFiles();
			for (File f : files) {
//				System.out.println(f.getName().split("\\.")[0]);
				URL url = f.toURI().toURL();
				URL[] urls = new URL[] { url };
				URLClassLoader cl = URLClassLoader.newInstance(urls);
				Class cls = cl.loadClass(
						"com.SSPWorldWide.Framework.Adviser.Testcases." + f.getName().split("\\.")[0].trim());
				Object o = cls.newInstance();
				ProtectionDomain pDomain = cls.getProtectionDomain();
				// System.out.println(pDomain);
				CodeSource cSource = pDomain.getCodeSource();
				URL urlfrom = cSource.getLocation();
				// System.out.println("Class Loaded: " + o.getClass().toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void compile() throws Exception {
		System.setProperty("java.home", "C:/Program Files/Java/jdk1.8.0_102");
		File file = new File(
				System.getProperty("user.dir") + "/src/test/java/com/SSPWorldWide/Framework/Adviser/Testcases");
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
}
