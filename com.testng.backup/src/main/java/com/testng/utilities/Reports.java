/**
* Company		:	Accenture
* Project		:	Aviall
* FileName		:	Extent Reports
* Author		:	Suresh Kurry
* Description	:	Initializing Extent Reports 
**/
package com.testng.utilities;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * @author Suresh Kurry
 *
 */
public class Reports extends Base {
	public static final String LOGS = System.getProperty("user.dir")+File.separator+"ExtentReports"+File.separator+"Screenshots";
	

	@BeforeSuite(alwaysRun = true)
	public static void startReport() {
		String dir = "user.dir";
		File extentReportPath = new File(System.getProperty(dir) + "//ExtentReports//extentReport.html");
		
		if(!extentReportPath.exists()){
			
			try {
				extentReportPath.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}

		htmlreport = new ExtentHtmlReporter(extentReportPath);
		htmlreport.loadXMLConfig(System.getProperty(dir) + "//extent-config.xml");
		
		
		htmlreport.config().setTheme(Theme.STANDARD);
		htmlreport.config().setDocumentTitle("Aviall");
		htmlreport.config().setEncoding("utf-8");
		htmlreport.config().setReportName("Automation Report");
		htmlreport.config().setCSS("css-string");
		htmlreport.config().setJS("js-string");
		report = new ExtentReports();
		report.setSystemInfo("Current User", System.getProperty(dir));
		report.attachReporter(htmlreport);
		
	}

	@AfterSuite(alwaysRun = true)
	public void endReport() {

		report.flush();
		
		try {
			driver.quit();
		} catch (Exception e) {
			e.getMessage();
		}
		
		try {
			GenericMethods.killBrowserInstances();
		} catch (Exception e) {
			e.getMessage();
		}

	}

}