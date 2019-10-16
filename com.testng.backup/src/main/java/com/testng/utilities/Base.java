package com.testng.utilities;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Base {
	
	/*private static ThreadLocal<WebDriver> driver1=new ThreadLocal<WebDriver>();
	
	private static Base instance = new Base();
	
	public static Base getInstance() {
		return instance;
	}*/
	
	public static WebDriver driver =null;
			//Base.getInstance().getDriver();
	//public static ChromeDriverService service = null;
	
	
	public static ExtentHtmlReporter htmlreport;
	public static ExtentReports report;
	public static ExtentTest logger;
	
	
	
}
