package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Login_Module extends TestcaseFlow{
 @Test(description = "Login with a valid user")
 public void Login_Module_001() throws Exception{
runSingleTest("Login_Module.001");
}
 @Test(description = "Login with a invalid user")
 public void Login_Module_002() throws Exception{
runSingleTest("Login_Module.002");
}
}
