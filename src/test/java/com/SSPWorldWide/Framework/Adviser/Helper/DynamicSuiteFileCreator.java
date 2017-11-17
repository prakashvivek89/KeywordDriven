package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.Read_Testcase_File;

public class DynamicSuiteFileCreator {

	String packageName = "com.SSPWorldWide.Framework.Adviser.Testcases";
	private static final String dynamicXMLDir = System.getProperty("user.dir") + "/src/test/resources/dynamicXML";

	public static XmlSuite createXMLSuite(String suiteName, String tcID) throws Exception {
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		String className = suiteName;
		if (tcID.contains(",")) {
			XmlClass packageName = null;
			for (String id : tcID.split(",")) {
				id = id.replace(".", "_").trim();
				XmlTest test = new XmlTest(suite);
				test.setName(id);
				List<XmlClass> classes = new ArrayList<XmlClass>();
				packageName = new XmlClass("com.SSPWorldWide.Framework.Adviser.Testcases." + className);
				XmlInclude method = null;
				List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
				method = new XmlInclude(id);
				inmethods.add(method);
				packageName.setIncludedMethods(inmethods);
				classes.add(packageName);
				test.setXmlClasses(classes);
				FileWriter writer = new FileWriter(new File(dynamicXMLDir, className + ".xml"));
				writer.write(suite.toXml());
				writer.flush();
				writer.close();
			}
			FileWriter writer = new FileWriter(new File(dynamicXMLDir, suiteName + ".xml"));
			writer.write(suite.toXml());
			writer.flush();
			writer.close();
		} else {
			XmlTest test = new XmlTest(suite);
			tcID = tcID.replace(".", "_").trim();
			test.setName(tcID);
			List<XmlClass> classes = new ArrayList<XmlClass>();
			XmlClass packageName = new XmlClass("com.SSPWorldWide.Framework.Adviser.Testcases." + suiteName);
			XmlInclude method = new XmlInclude(tcID);
			List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
			inmethods.add(method);
			packageName.setIncludedMethods(inmethods);
			classes.add(packageName);
			test.setXmlClasses(classes);
			FileWriter writer = new FileWriter(new File(dynamicXMLDir, suiteName + ".xml"));
			writer.write(suite.toXml());
			writer.flush();
			writer.close();
		}
		return suite;
	}

	public static Map<String, XmlSuite> createRegressionXML() throws Exception {
		String FilePath = System.getProperty("user.dir") + "/ProjectAutomationFiles/testcases";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
		Map<String, XmlSuite> regressionSuites = new HashMap<String, XmlSuite>();
		for (File f : files) {
			Read_Testcase_File.testcases_persuite.clear();
			Read_Testcase_File.readTestcaseFile(FilenameUtils.removeExtension(f.getName()));
			String className = FilenameUtils.removeExtension(f.getName());
			XmlSuite suite = new XmlSuite();
			suite.setName(className);
			XmlClass packageName = null;
			for (String TCid : Read_Testcase_File.testcases_persuite.keySet()) {
				XmlTest test = new XmlTest(suite);
				TCid = TCid.replace(".", "_").trim();
				test.setName(TCid);
				List<XmlClass> classes = new ArrayList<XmlClass>();
				packageName = new XmlClass("com.SSPWorldWide.Framework.Adviser.Testcases." + className);
				XmlInclude method = null;
				List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
				method = new XmlInclude(TCid);
				inmethods.add(method);
				packageName.setIncludedMethods(inmethods);
				classes.add(packageName);
				test.setXmlClasses(classes);
				regressionSuites.put(className, suite);
				FileWriter writer = new FileWriter(new File(dynamicXMLDir, className + ".xml"));
				writer.write(suite.toXml());
				writer.flush();
				writer.close();
			}
		}
		return regressionSuites;
	}

	public static Map<String, XmlSuite> createSmokeXML() throws Exception {
		String FilePath = System.getProperty("user.dir") + "/ProjectAutomationFiles/testcases";
		File ExcelFileToRead = new File(FilePath);
		File[] files = ExcelFileToRead.listFiles();
		Map<String, XmlSuite> regressionSuites = new HashMap<String, XmlSuite>();
		for (File f : files) {
			Read_Testcase_File.testcases_persuite.clear();
			Read_Testcase_File.readTestcaseFile(FilenameUtils.removeExtension(f.getName()));
			String className = FilenameUtils.removeExtension(f.getName());
			XmlSuite suite = new XmlSuite();
			suite.setName(className);
			XmlClass packageName = null;
			for (String TCid : Read_Testcase_File.testcases_persuite.keySet()) {
				if (Read_Testcase_File.smokeTestCases.get(TCid) != null
						&& Read_Testcase_File.smokeTestCases.get(TCid).equalsIgnoreCase("smoke")) {
					XmlTest test = new XmlTest(suite);
					TCid = TCid.replace(".", "_").trim();
					test.setName(TCid);
					List<XmlClass> classes = new ArrayList<XmlClass>();
					packageName = new XmlClass("com.SSPWorldWide.Framework.Adviser.Testcases." + className);
					XmlInclude method = null;
					List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
					method = new XmlInclude(TCid);
					inmethods.add(method);
					packageName.setIncludedMethods(inmethods);
					classes.add(packageName);
					test.setXmlClasses(classes);
					regressionSuites.put(className, suite);
					FileWriter writer = new FileWriter(new File(dynamicXMLDir, className + ".xml"));
					writer.write(suite.toXml());
					writer.flush();
					writer.close();
				}
			}
		}
		return regressionSuites;
	}

	public static Map<String, XmlSuite> createSingleTestSuiteRegressionXML(String testSuiteName) throws Exception {
		Map<String, XmlSuite> regressionSuites = new HashMap<String, XmlSuite>();
		XmlSuite suite = new XmlSuite();
		if (testSuiteName.contains(",")) {
			for (String suiteName : testSuiteName.split(",")) {
				Read_Testcase_File.testcases_persuite.clear();
				suiteName = suiteName.trim();
				Read_Testcase_File.readTestcaseFile(suiteName);
				String className = suiteName;
				System.out.println("size  :  " + Read_Testcase_File.testcases_persuite.keySet().size());
				suite.setName(className);
				XmlClass packageName = null;
				for (String TCid : Read_Testcase_File.testcases_persuite.keySet()) {
					XmlTest test = new XmlTest(suite);
					TCid = TCid.replace(".", "_").trim();
					test.setName(TCid);
					List<XmlClass> classes = new ArrayList<XmlClass>();
					packageName = new XmlClass("com.SSPWorldWide.Framework.Adviser.Testcases." + className);
					XmlInclude method = null;
					List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
					method = new XmlInclude(TCid);
					inmethods.add(method);
					packageName.setIncludedMethods(inmethods);
					classes.add(packageName);
					test.setXmlClasses(classes);
					regressionSuites.put(className, suite);
					FileWriter writer = new FileWriter(new File(dynamicXMLDir, suiteName + ".xml"));
					writer.write(suite.toXml());
					writer.flush();
					writer.close();
				}
			}
		} else {
			Read_Testcase_File.testcases_persuite.clear();
			Read_Testcase_File.readTestcaseFile(testSuiteName);
			String className = testSuiteName;
			suite.setName(className);
			XmlClass packageName = null;
			for (String TCid : Read_Testcase_File.testcases_persuite.keySet()) {
				XmlTest test = new XmlTest(suite);
				TCid = TCid.replace(".", "_").trim();
				test.setName(TCid);
				List<XmlClass> classes = new ArrayList<XmlClass>();
				packageName = new XmlClass("com.SSPWorldWide.Framework.Adviser.Testcases." + className);
				XmlInclude method = null;
				List<XmlInclude> inmethods = new ArrayList<XmlInclude>();
				method = new XmlInclude(TCid);
				inmethods.add(method);
				packageName.setIncludedMethods(inmethods);
				classes.add(packageName);
				test.setXmlClasses(classes);
				regressionSuites.put(className, suite);
				FileWriter writer = new FileWriter(new File(dynamicXMLDir, testSuiteName + ".xml"));
				writer.write(suite.toXml());
				writer.flush();
				writer.close();
			}
		}
		return regressionSuites;
	}

	public static void runTestNG(XmlSuite suite) {
		TestNG tng = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng.setXmlSuites(suites);
		tng.run();
	}

	public static void runSingleTestCase(String testSuiteName, String testcaseID) throws Exception {
		DynamicClassCreator.createSingleTestCaseClass(testSuiteName, testcaseID);
		DynamicClassCreator.makeDirectory(dynamicXMLDir);
		XmlSuite suite = createXMLSuite(testSuiteName, testcaseID);
		runTestNG(suite);
	}

	public static void runSingleSuite(String testSuiteName) throws Exception {
		DynamicClassCreator.createSingleSuiteClasse(testSuiteName);
		DynamicClassCreator.makeDirectory(dynamicXMLDir);
		for (String s : createRegressionXML().keySet()) {
			XmlSuite suite = createRegressionXML().get(s);
			runTestNG(suite);
		}
	}

	public static void runRegressionSuite() throws Exception {
		DynamicClassCreator.createRegressionSuiteClasses();
		DynamicClassCreator.makeDirectory(dynamicXMLDir);
		for (String s : createRegressionXML().keySet()) {
			XmlSuite suite = createRegressionXML().get(s);
			runTestNG(suite);
		}
	}

	public static void runSmokeSuite() throws Exception {
		DynamicClassCreator.createSmokeSuiteClasses();
		DynamicClassCreator.makeDirectory(dynamicXMLDir);
		for (String s : createSmokeXML().keySet()) {
			XmlSuite suite = createSmokeXML().get(s);
			runTestNG(suite);
		}
	}
}