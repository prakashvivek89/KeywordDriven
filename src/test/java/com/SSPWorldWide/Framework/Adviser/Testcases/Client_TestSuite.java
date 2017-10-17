package com.SSPWorldWide.Framework.Adviser.Testcases;

import org.testng.annotations.Test;
import com.SSPWorldWide.Framework.Adviser.Helper.*;
import com.SSPWorldWide.Framework.Adviser.ReadExcel.*;
public class Client_TestSuite extends WebdriverHelper{
 @Test(description = "Personal Client Creation")
 public static void Client_001() throws Exception{
TestcaseFlow.runSingleTest("Client_TestSuite", "Client.001");
}
}
