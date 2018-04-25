package com.SSPWorldWide.Framework.Adviser.Helper;

public enum Constants {

	/* Path for different browser drivers */
	FIREFOXDRIVER(System.getProperty("user.dir") + "\\drivers\\Firefox\\geckodriver.exe"),

	CHROMEDRIVER(System.getProperty("user.dir") + "\\drivers\\Chrome\\chromedriver.exe"),

	INTERNETEXPLORERDRIVER(System.getProperty("user.dir") + "\\drivers\\IE11\\IEDriverServer.exe"),

	/* Path for different excels used */
	EXCELTESTCASEPATH(System.getProperty("user.dir") + "\\ProjectAutomationFiles\\testcases"),

	EXCELREUSABLEPATH(System.getProperty("user.dir") + "\\ProjectAutomationFiles\\projectResuables"),

	EXCELTESTDATAPATH(System.getProperty("user.dir") + "\\ProjectAutomationFiles\\testData"),

	EXCELOBJECTREPOPATH(System.getProperty("user.dir") + "\\ProjectAutomationFiles\\objectRepo"),

	REPROTINGFORMATDIR(System.getProperty("user.dir") + "\\src\\test\\resources\\Report Format\\Final Report.xlsx"),

	COPIEDFILESDIR(System.getProperty("user.dir") + "\\src\\test\\resources\\CopiedFiles"),

	/* Path for different dynamic class and XMLs created */
	DYNAMICCLASSDIR(System.getProperty("user.dir") + "\\src\\main\\java/com\\SSPWorldWide\\Framework\\Adviser\\Testcases"),

	JAVA_HOME("C:/Program Files/Java/jdk1.8.0_102"),
	
	DYNAMICXMLDIR(System.getProperty("user.dir") + "\\src\\main\\resources\\dynamicXML\\XMLs"),

	CLASSLOADER(System.getProperty("user.dir") + "\\target\\test-classes\\com\\SSPWorldWide\\Framework\\Adviser\\Testcases"),
	
	/* Text to write on dynamic XMLs and classes */
	PACKAGENAME("com.SSPWorldWide.Framework.Adviser.Testcases."),

	CLASSTEXT("package com.SSPWorldWide.Framework.Adviser.Testcases;\r\n" + "\r\n" + "import org.testng.annotations.Test;\r\n"
			+ "import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;"),
	
	SQLDIRECTORY(System.getProperty("user.dir") +"\\SQLQueries");

	private final String constants;

	private Constants(String constants) {
		this.constants = constants;
	}

	public String getConstants() {
		return this.constants;
	}

	public String toString() {
		return this.constants;
	}
}
