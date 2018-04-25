package com.VP.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.VP.Framework.Adviser.Helper.*;
import com.VP.Framework.Adviser.ReadExcel.*;
public class Login_TestSuite extends WebdriverHelper{
 @Test(description = "Login Second Test Case")
 public static void Login_002() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.002");
}
 @Test(description = "Login First Test Case")
 public static void Login_001() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.001");
}
 @Test(description = "Login Third Test Case")
 public static void Login_003() throws Exception{
TestcaseFlow.runSingleTest("Login_TestSuite", "Login.003");
}
}
