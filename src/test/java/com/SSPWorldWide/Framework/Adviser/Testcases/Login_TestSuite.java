package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.Helper.*;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Login_TestSuite extends WebdriverHelper{
 @Test(description = "Login with a valid user")
 public static void Login_001() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.001");
}
}
