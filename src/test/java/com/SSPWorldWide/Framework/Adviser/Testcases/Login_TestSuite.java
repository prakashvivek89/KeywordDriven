package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.Helper.*;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Login_TestSuite extends WebdriverHelper{
 @Test(description = "Login with a invalid user")
 public static void Login_002() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.002");
}
 @Test(description = "Login with a valid user")
 public static void Login_001() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.001");
}
 @Test(description = "Login")
 public static void Login_003() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.003");
}
}
