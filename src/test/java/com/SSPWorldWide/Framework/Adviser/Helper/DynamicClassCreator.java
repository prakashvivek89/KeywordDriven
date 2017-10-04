package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.apache.commons.io.FilenameUtils;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.Read_Testcase_File;

public class DynamicClassCreator {
	private static final String excelTestcasePath = System.getProperty("user.dir") + "/src/test/resources/testcases";
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
			for (String methodName : Read_Testcase_File.testcases_persuite.keySet()) {
				bufferedWriter.newLine();
				bufferedWriter.write(" @Test");
				bufferedWriter.newLine();
				bufferedWriter.write(" public static void " + methodName + "() throws Exception{");
				bufferedWriter.newLine();
				bufferedWriter.write("TestcaseFlow.runSingleTest(\"" + className + "\", \"" + methodName + "\");");
				bufferedWriter.newLine();
				bufferedWriter.write("}");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("}");
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
	}

	public static void createSingleTestCaseClass(String suiteName, String tcID) throws Exception {
		String fileName = suiteName + ".java";
		File myFIle = new File(dynamicClassDir, fileName);
		FileWriter fileWriter = new FileWriter(myFIle);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(classText);
		bufferedWriter.newLine();
		bufferedWriter.write("public class " + suiteName + " extends WebdriverHelper{");
		Read_Testcase_File.testcases_persuite.clear();
		Read_Testcase_File.readTestcaseFile(FilenameUtils.removeExtension(suiteName));
		for (String methodName : Read_Testcase_File.testcases_persuite.keySet()) {
			bufferedWriter.newLine();
			bufferedWriter.write(" @Test");
			bufferedWriter.newLine();
			bufferedWriter.write(" public static void " + methodName + "() throws Exception{");
			bufferedWriter.newLine();
			bufferedWriter.write("TestcaseFlow.runSingleTest(\"" + suiteName + "\", \"" + methodName + "\");");
			bufferedWriter.newLine();
			bufferedWriter.write("}");
		}
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.close();
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

}
