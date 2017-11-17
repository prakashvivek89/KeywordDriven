package com.SSPWorldWide.Framework.Adviser.Helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class SuiteFileCreator {

    public static void main(String[] args) throws IOException {
        XmlSuite suite = new XmlSuite();
        suite.setName(WebdriverHelper.testSuiteName);
        XmlTest test = new XmlTest(suite);
        test.setName(WebdriverHelper.testcaseID);
        List<XmlClass> classes = new ArrayList<XmlClass>();
        classes.add(new XmlClass("com.SSPWorldWide.Framework.Adviser.Helper.WebdriverHelper"));
        test.setXmlClasses(classes) ;
        System.out.println(suite.toXml());
        FileWriter writer = new FileWriter(new File("MyMasterSuite.xml"));
        writer.write(suite.toXml());
        writer.flush();
        writer.close();
        System.out.println(new File("MyMasterSuite.xml").getAbsolutePath());
        TestNG tng = new TestNG();
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        tng.setXmlSuites(suites);
        tng.run();
    }
}