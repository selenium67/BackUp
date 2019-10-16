/**
* Company		:	Accenture
* Project		:	Aviall
* FileName		:	GlobalConstants
* Author		:	Suresh Kurry
* Description	:	Initializing Required File Paths 
**/
package com.testng.utilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Suresh Kurry
 *
 */
public class GlobalConstants extends Reports {
	/*  API Automation Parameters*/
	public static final String QA_TOKEN = "https://testh.aviall.com/rest";
	public static final String D2_TOKEN = "https://devhybris.aviallinc.com/rest";
	public static final String D2_HOST = D2_TOKEN +"/v2";
	public static final String QA_HOST = QA_TOKEN +"/v2";
	public static final String PRXY = "mwsg-vip.aviallinc.com";

	public static String RESULT_SCREENSHOTS = "Yes";
	public static String QA_2_STOREFRONT = "https://testh.aviall.com/aviallstorefront/";
	public static String QA_3_STOREFRONT = "https://q3-testh.aviall.com/aviallstorefront/";
	public static String QA_4_STOREFRONT = "https://q4-testh.aviall.com/aviallstorefront/";

	public static String D_2_STOREFRONT = "http://devhybris.aviallinc.com/aviallstorefront/";
	public static String D_3_STOREFRONT = "http://d3hybris.aviallinc.com/aviallstorefront/";
	public static String EPUBS_Q4_STOREFRONT = "https://q4-testh.aviall.com/aviallstorefront/login?isepubs=true";
	public static String EPUBS_Q3_STOREFRONT = "https://q3-testh.aviall.com/aviallstorefront/login?isepubs=true";
	public static String EPUBS_Q2_STOREFRONT = "https://testh.aviall.com/aviallstorefront/login?isepubs=true";
	public static String D_4_STOREFRONT = "https://d4hybris.aviallinc.com/aviallstorefront/";
	public static String P2_STOREFRONT = "https://aviall.com/aviallstorefront/";

	public static String JEPP_Q_3_STOREFRONT = "https://q3-testh-jeppuniv.aviall.com/aviallstorefront/jepp";
	public static String JEPP_Q_2_STOREFRONT = "https://testh-jeppuniv.aviall.com/aviallstorefront/jepp";
	public static String JEPP_Q_4_STOREFRONT = "https://q4-testh-jeppuniv.aviall.com/aviallstorefront/jepp";
	public static String JEPP_D_2_STOREFRONT = "https://devhybris-jeppuniv.aviallinc.com/aviallstorefront/jepp";

	// Drivers Path
	public static String CHROMEPATH = System.getProperty("user.dir")
			+ "/BrowserDrivers/chromedriver_win/chromedriver.exe";
	public static String CHROMEPATH_LINUX = "./BrowserDrivers/chromedriver_linux/chromedriver";
	public static String CHROMEPATH_MAC = "./BrowserDrivers/chromedriver_mac/chromedriver";

	public static String FIREFOXPATH = "./BrowserDrivers/geckodriver-win/geckodriver.exe";
	public static String FIREFOXPATH_LINUX = "./BrowserDrivers/geckodriver-linux/geckodriver";
	public static String FIREFOXPATH_MAC = "./BrowserDrivers/geckodriver-linux/geckodriver";
	public static String IEPATH = "./BrowserDrivers/IEDriver/IEDriverServer.exe";

	// Properties File
	public static String propertiesFile = System.getProperty("user.dir")
			+ "/Validations_Document/Validations.properties";
	// API TestData
	public static  final String apiTestDataFile = System.getProperty("user.dir")
			+ "/AviallTestData/apiTestData.json";
	public static  final String payload = System.getProperty("user.dir")
			+ "/AviallTestData/";
	// Driver System Set Property Syntax
	public static String CHROME = "webdriver.chrome.driver";
	public static String FIREFOX = "webdriver.gecko.driver";
	public static String IE = "webdriver.ie.driver";

	// Excel Data Path

	public static final String TESTDATAFILE = retrieveDataFile();
	//public static final String JEPPTESTDATAFILE = getTestDataFile();

	// ReadTestData Constants

	public static Map<String, Map<String, String>> Aviall_Login;
	public static Iterator<Entry<String, Map<String, String>>> entries;
	public static Map.Entry<String, Map<String, String>> entry;
	public static Map<String, String> values;

	// browser details
	public static final String BROWSER = retrieveBrowser();
	public static final String APPURL = getApplicationUrl();
	//public static final String JEPPESENURL = getJeppesenUrl();

	// Firefox Configuration

	public static String JQueryURL = "https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js";
	public static String env;
	
	public static String time = System.getProperty("time");
	
	public static String retrieveBrowser() {

		String browser = System.getProperty("browser");

		if (null == browser || browser.isEmpty()) {

			browser = "chrome";
		}
		
		switch (browser) {
		case "firefox":
			browser =  "firefox";
			break;
		case "ie":
			browser = "ie";
			break;
		default:
			browser = "chrome";
			break;
		}

		return browser;
	}

	public static String retrieveDataFile() {

		String filePath = "./AviallTestData/";
		String fileName = "";
		String envName = System.getProperty("env");

		if (null == envName || envName.isEmpty()) {
			return "./AviallTestData/Aviall_TestData_Q2.xlsx";
		}

		switch (envName) {
		case "D2":
			fileName = "D2.xlsx";
			break;
		case "D3":
			fileName = "Aviall_TestData_DEV.xlsx";
			break;
		case "D4":
			fileName = "Aviall_TestData_DEV.xlsx";
			break;
		case "Q2":
			fileName = "Q2.xlsx";
			break;
		case "Q3":
			fileName = "Aviall_TestData_QA.xlsx";
			break;
		case "Q4":
			fileName = "Aviall_TestData_QA.xlsx";
			break;
		case "S1":
			fileName = "Aviall_TestData_S1.xlsx";
			break;
		case "S4":
			fileName = "Aviall_TestData_S4.xlsx";
			break;
		case "J4":
			fileName = "Jepp_TestData_QA.xlsx";
			break;
		case "J2":
			fileName = "Jepp_TestData_QA.xlsx";
			break;
		case "P2":
			fileName = "Prod_TestData.xlsx";
			break;
		default:
			fileName = "Aviall_TestData_QA.xlsx";
			break;
		}
		return new StringBuffer(filePath).append(fileName).toString();
	}

	public static String getApplicationUrl() {

		String appUrl = "";
		String envName = System.getProperty("env");

		if (null == envName || envName.isEmpty()) {

			return JEPP_Q_4_STOREFRONT;
		}

		switch (envName) {
		case "D2":
			appUrl = D_2_STOREFRONT;
			break;
		case "D3":
			appUrl = D_3_STOREFRONT;
			break;
		case "D4":
			appUrl = D_4_STOREFRONT;
			break;
		case "Q2":
			appUrl = QA_2_STOREFRONT;
			break;
		case "Q3":
			appUrl = QA_3_STOREFRONT;
			break;
		case "Q4":
			appUrl = QA_4_STOREFRONT;
			break;
		case "J2":
			appUrl = JEPP_D_2_STOREFRONT;
			break;
		case "J4":
			appUrl = JEPP_Q_2_STOREFRONT;
			break;
		case "S1":
			appUrl = "https://stageh.aviall.com/aviallstorefront/";
			break;
		case "S4":
			appUrl = "https://s4-stageh.aviall.com/aviallstorefront/";
			break;
		case "P2":
			appUrl = P2_STOREFRONT;
			break;
		default:
			appUrl = QA_4_STOREFRONT;
			break;
		}
		return appUrl;
	}

	/*public static String getTestDataFile() {

		String filePath = "./AviallTestData/";
		String fileName = "";
		String envName = System.getProperty("env");

		if (null == envName || envName.isEmpty()) {
			return "./AviallTestData/Jepp_TestData_QA.xlsx";
		}
		setenvironment(envName);
		switch (envName) {
		
		case "Q2":
			fileName = "Jepp_TestData_QA.xlsx";
			break;
		case "Q3":
			fileName = "Jepp_TestData_QA.xlsx";
			break;
		case "Q4":
			fileName = "Jepp_TestData_QA.xlsx";
			break;					
		default:
			fileName = "Jepp_TestData_QA.xlsx";
			break;
		}
		return new StringBuffer(filePath).append(fileName).toString();
	}*/
	
	/*public static String getJeppesenUrl() {
		
		String appUrl = "";
		String envName = System.getProperty("env");
		if (null == envName || envName.isEmpty()) {
			return JEPP_Q_4_STOREFRONT;
		}

		switch (envName) {
		case "Q3":
			appUrl = JEPP_Q_3_STOREFRONT;
			break;
		case "Q4":
			appUrl = JEPP_Q_4_STOREFRONT;
			break;
		default:
			appUrl = JEPP_Q_4_STOREFRONT;
			break;
		}
		return appUrl;
	}
	
	public static String setenvironment (String env) {
		return GlobalConstants.env = env;
	}
	public static String getenvironment (String env) {
		return GlobalConstants.env;
	}*/
}
