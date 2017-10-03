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
	private static final String dynamicXMLDir = System.getProperty("user.dir")
			+ "/src/test/resources/dynamicXML";

	public static void main(String[] args) throws Exception {
//		DynamicClassCreator.createRegressionSuiteClasses();
		for (String s : createWholeXML().keySet()) {
			System.out.println(s);
			System.out.println(createWholeXML().get(s).toXml());
			XmlSuite suite =createWholeXML().get(s);
			runTestNG(suite);
		}
	}
	public static XmlSuite createXMLSuite(String suiteName, String tcID) throws Exception {
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		XmlTest test = new XmlTest(suite);
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
		return suite;
	}

	public static Map<String, XmlSuite> createWholeXML() throws Exception {
		DynamicClassCreator.makeDirectory(dynamicXMLDir);
		String FilePath = System.getProperty("user.dir") + "/src/test/resources/testcases";
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

	public static void runTestNG(XmlSuite suite) {
		TestNG tng = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng.setXmlSuites(suites);
		tng.run();
	}
}