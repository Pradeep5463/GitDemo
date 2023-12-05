package driverFactory;

import java.io.ObjectInputFilter.Status;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
public static WebDriver driver;
String TestCases = "MasterTestCases";
String inputpath = "./FileInput/Controller.xlsx";
String outputpath = "./FileOutput/HybridResults.xlsx";
ExtentReports report;
ExtentTest logger;
public void StartTest() throws Throwable
{
	String Module_Status = "";
	//create object for ExcelFileUtil class
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in TestCases sheet
	for(int i=1; i<=xl.rowCount(TestCases);i++)
	{
		String Execution_Status = xl.getCellData(TestCases, i, 2);
		if(Execution_Status.equalsIgnoreCase("Y"))
		{
			//store corresponding Sheet
			String TCModule = xl.getCellData(TestCases, i, 1);
			//define path of html
			report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+"____"+".html");
			//Start test cases
			logger = report.startTest(TCModule);
			//iterate all rows in TCModule sheet
			for(int j=1; j<=xl.rowCount(TCModule);j++)
			{
				//read all cells from TCModule
				
				String Description = xl.getCellData(TCModule, j, 0);
				String Object_Type = xl.getCellData(TCModule, j, 1);
				String Locator_Type = xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibrary.mouseClick(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("categoryTable"))
					{
						FunctionLibrary.categoryTable(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureStocknum"))
					{
						FunctionLibrary.captureStocknum(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("stockItemTable"))
					{
						FunctionLibrary.stockItemTable(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("capturesuppnum"))
					{
						FunctionLibrary.capturesuppnum(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("suppliertable"))
					{
						FunctionLibrary.supplierTable(driver);
						logger.log(LogStatus.INFO, Description);
					}
					//write as pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);
					Module_Status = "True";
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//write as fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_Status = "False";
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					//Write as Pass into Testcases Sheet
					xl.setCellData(TestCases, i, 3, "Pass", outputpath);
				}
				else
				{
					//Write as Fail into Testcases Sheet
					xl.setCellData(TestCases, i, 3, "Fail", outputpath);
				}
				report.endTest(logger);
				report.flush();
			}
		}
		else
		{
			//Write as blocked into TestCases sheet
			xl.setCellData(Execution_Status, i, 3, "Blocked", outputpath);
		}
	}
	
}








}
