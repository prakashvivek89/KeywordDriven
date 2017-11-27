package com.VP.Framework.Adviser.Helper;

public enum Constants {

	/* Path for different browser drivers */
	FIREFOXDRIVER(System.getProperty("user.dir") + "/drivers/Firefox/geckodriver.exe"),

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
	DYNAMICCLASSDIR(System.getProperty("user.dir") + "\\src/main\\java/com\\VP\\Framework\\Adviser\\Testcases"),

	DYNAMICXMLDIR(System.getProperty("user.dir") + "\\src\\main\\resources\\dynamicXML"),

	/* Text to write on dynamic XMLs and classes */
	PACKAGENAME("com.VP.Framework.Adviser.Testcases."),

	CLASSTEXT("package com.VP.Framework.Adviser.Testcases;\r\n" + "\r\n" + "import org.testng.annotations.Test;\r\n"
			+ "import com.VP.Framework.Adviser.Helper.*;\r\n" + "import com.VP.Framework.Adviser.ReadExcel.*;");

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
