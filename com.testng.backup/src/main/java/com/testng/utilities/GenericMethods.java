/**
 * Company		:	Accenture
 * Project		:	Aviall
 * FileName		:	Generic Methods
 * Author		:	Suresh Kurry
 * Description	:	Implemented Utility Methods which will help in creating test scripts 
 **/

package com.testng.utilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.assertNotEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aviall.api.LoggingLink;
import com.aviall.api.Utils;
import com.aviall.dataProvider.ReadTestData;
import com.aviall.webPages.Aviall_Register_POM;
import com.aviall.webPages.CheckoutPreferences_POM;
import com.aviall.webPages.Customer_Registration_Search_POM;
import com.aviall.webPages.Home_POM;
import com.aviall.webPages.Invoice_History_POM;
import com.aviall.webPages.LTL_POM;
import com.aviall.webPages.Login_POM;
import com.aviall.webPages.MarketPlace_POM;
import com.aviall.webPages.MultiLine_POM;
import com.aviall.webPages.MyAccount_POM;
import com.aviall.webPages.OrderConfirmation_POM;
import com.aviall.webPages.OrderModifyPOM;
import com.aviall.webPages.Order_History_POM;
import com.aviall.webPages.PDP_POM;
import com.aviall.webPages.PLP_POM;
import com.aviall.webPages.Payment_Information_POM;
import com.aviall.webPages.PendingManagerApprovalPOM;
import com.aviall.webPages.PlaceOrder_RunAs_POM;
import com.aviall.webPages.Profile_POM;
import com.aviall.webPages.QuoteSearch_POM;
import com.aviall.webPages.Register_POM;
import com.aviall.webPages.ReportPage;
import com.aviall.webPages.SavedList_POM;
import com.aviall.webPages.ShiptoProfile_POM;
import com.aviall.webPages.Update_Password_POM;
import com.aviall.webPages.VanityURL_POM;
import com.aviall.webPages.ViewCart_POM;
import com.aviall.webPages.ViewCheckout_POM;
import com.jeppesen.pages.JepAvionicsPOM;
import com.jeppesen.pages.JepChartingPOM;
import com.jeppesen.pages.JepHomePOM;
import com.jeppesen.pages.JepOnlineTrainingPOM;
import com.jeppesen.pages.JepOverviewPOM;
import com.jeppesen.pages.JepTrainingMaterialsPOM;
import com.jeppesen.pages.JeppOrderConfirmationPOM;
import com.jeppesen.pages.PurchaseSummaryAndInfoPOM;
import com.jeppesen.pages.SelectProductPOM;

/**
 * @author skurry
 *
 */
public class GenericMethods extends GlobalConstants {
	public static ArrayList<Character> testStatus = null;
	public static int count = 0;
	public static String OS = null;
	static String runRimeBrowser = getParameter("browser");
	public static JavascriptExecutor js;
	public static String pageLoadStatus;
	public String parent;
	public static String currentEnv = null;
	public static String appUrl;
	public String recentAddedProduct = null;
	public static Home_POM home;
	public static ViewCheckout_POM checkout;
	public static Aviall_Register_POM locator;
	public static MyAccount_POM account;
	public static ShiptoProfile_POM shipTo;
	public static ViewCart_POM cart;
	public static CheckoutPreferences_POM checkPref;
	public static Payment_Information_POM payment;
	public static Login_POM login;
	public static PLP_POM plp;
	public static PDP_POM pdp;
	public static QuoteSearch_POM quote;
	public static PendingManagerApprovalPOM mgrApproval;
	public static OrderModifyPOM modify;
	public static Order_History_POM orderHistory;
	public static OrderConfirmation_POM orderConfirm;
	public static Invoice_History_POM invoice;
	public static Aviall_Register_POM register;
	public static OrderConfirmation_POM confirm;
	public static PlaceOrder_RunAs_POM runAs;
	public static MarketPlace_POM market;
	public static MultiLine_POM multiLine;
	public static SavedList_POM savedList;
	public static Profile_POM profile;
	public static Update_Password_POM updatePwd;
	public static VanityURL_POM vanity;
	public WebDriverWait wait;
	public static ReportPage myAccountreport;
	public static String flatfilepath;
	public static String tcName;
	public static String ProductID;
	public static HashMap<String, String> CompleteOrders = new HashMap<String, String>();
	public static Register_POM registration;
	public static Customer_Registration_Search_POM customerReg;
	public static LTL_POM lTL;
	public static String testCaseName;

	/* JEPPESEN:-> PARAMETERS */
	public static JepHomePOM jeppHome;
	public static JepAvionicsPOM JepAvionics;
	public static JepOnlineTrainingPOM JepOnlineTraining;
	public static JepChartingPOM JepCharting;
	public static SelectProductPOM partsList;
	public static PurchaseSummaryAndInfoPOM summary;
	public static JepOverviewPOM JepOverview;
	public static JepTrainingMaterialsPOM JepTrainingMaterials;
	public static JeppOrderConfirmationPOM Jepporderconf;

	public static int size = Integer.parseInt(GlobalConstants.time);

	@BeforeSuite(alwaysRun = true)
	public static void clearScreenshots() {
		try {
			File dir = new File("./ExtentReports/Screenshots/");
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					if (file.getName().equals("DoNotRemove.txt")) {
						continue;
					}
					file.delete();
				}

			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Description: This Method will Execute before each and every class -- This
	 * Method will take care of opening of browser and launching of Application
	 * -- Verify Whether the Application is working or Not
	 */

	@BeforeClass(alwaysRun = true)
	public static void launchBrowser() {
		try {
			getBrowser(BROWSER);
			driver.get(APPURL);
			maximizeBrowserWindow();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void maximizeBrowserWindow() {

		if (OSFinder.isWindows()) {
			driver.manage().window().maximize();
		} else {
			driver.manage().window().setSize(new Dimension(1600, 900));
		}
	}

	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		// Try if the logout is avaible
		try {
			if (APPURL.contains("jeppuniv")) {
				jeppLogout();
			} else {
				userLogout();
			}
		} catch (Exception e) {
		}
		//driver.close();
		driver.quit();
		//Base.getInstance().remoteDriver();
		//killBrowserInstances();
		//service.stop();

	}

	@BeforeMethod(alwaysRun = true)
	public static void setupExtentTest(Method result) {
		logger = report.createTest(result.getName(), result.getDeclaringClass().getName());
		testCaseName = result.getName();
		testStatus = new ArrayList<Character>();
		verifyAppStatus();
		/*
		 * Creating Objects For below classes
		 */
		home = new Home_POM(driver);
		checkout = new ViewCheckout_POM(driver);
		locator = new Aviall_Register_POM(driver);
		account = new MyAccount_POM(driver);
		shipTo = new ShiptoProfile_POM(driver);
		cart = new ViewCart_POM(driver);
		checkPref = new CheckoutPreferences_POM(driver);
		payment = new Payment_Information_POM(driver);
		login = new Login_POM(driver);
		plp = new PLP_POM(driver);
		pdp = new PDP_POM(driver);
		quote = new QuoteSearch_POM(driver);
		mgrApproval = new PendingManagerApprovalPOM(driver);
		orderHistory = new Order_History_POM(driver);
		orderConfirm = new OrderConfirmation_POM(driver);
		modify = new OrderModifyPOM(driver);
		invoice = new Invoice_History_POM(driver);
		register = new Aviall_Register_POM(driver);
		confirm = new OrderConfirmation_POM(driver);
		runAs = new PlaceOrder_RunAs_POM(driver);
		market = new MarketPlace_POM(driver);
		multiLine = new MultiLine_POM(driver);
		savedList = new SavedList_POM(driver);
		myAccountreport = new ReportPage(driver);
		vanity = new VanityURL_POM(driver);
		profile = new Profile_POM(driver);
		updatePwd = new Update_Password_POM(driver);
		registration = new Register_POM(driver);
		customerReg = new Customer_Registration_Search_POM(driver);
		lTL = new LTL_POM(driver);

		/* JEPPESEN:-> PAGE CLASSES */
		jeppHome = new JepHomePOM(driver);
		JepAvionics = new JepAvionicsPOM(driver);
		JepOnlineTraining = new JepOnlineTrainingPOM(driver);
		JepCharting = new JepChartingPOM(driver);
		partsList = new SelectProductPOM(driver);
		summary = new PurchaseSummaryAndInfoPOM(driver);
		JepOverview = new JepOverviewPOM(driver);
		JepTrainingMaterials = new JepTrainingMaterialsPOM(driver);
		Jepporderconf = new JeppOrderConfirmationPOM(driver);

		if(testCaseName.contains("API") ||result.getDeclaringClass().getName().contains("API") ) {
			Utils.logPrintStream(testCaseName);
			logger.assignCategory("API Tests");
			String env = System.getProperty("env");
			String baseURI;
			String tokenuri;
			ExtentTest log = logger.createNode("Environment:- "+ env).log(Status.INFO, "Environment:- "+ env);
			switch(env) {
			case "Q2":
				baseURI=GlobalConstants.QA_HOST;
				tokenuri = GlobalConstants.QA_TOKEN;
				break;
			case"D2":
				baseURI=GlobalConstants.D2_HOST;
				tokenuri = GlobalConstants.D2_TOKEN;
				break;
			default:
				baseURI=GlobalConstants.QA_HOST;
				tokenuri = GlobalConstants.QA_TOKEN;	
			}
			log.info("Environment URI:- "+ baseURI);
			log.info("Token URI:- "+ tokenuri);


		}

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {

		if((result.getName().contains("API") || result.getName().contains("api"))&&(result.getStatus() == ITestResult.SUCCESS)) {		
			logger.createNode("Detailed Logs").log(Status.PASS, new LoggingLink("Screenshots"+File.separator+testCaseName+".txt"));
		} else if ((result.getName().contains("API") || result.getName().contains("api"))&&(result.getStatus() == ITestResult.FAILURE)) {
			logger.createNode("Detailed Logs").log(Status.FAIL, new LoggingLink("Screenshots"+File.separator+testCaseName+".txt"));
		}else if (result.getStatus() == ITestResult.FAILURE) {

			logger.fail(MarkupHelper.createLabel(result.getName() + "  Test Case Failed", ExtentColor.RED));
			logger.fail(result.getThrowable());
			getScreen("./ExtentReports/Screenshots/" + result.getName() + "_" + ".png");
			String screenlocation = System.getProperty("user.dir") + "/ExtentReports/Screenshots/" + result.getName()
			+ "_" + ".png";
			logger.fail("Screenshot Reference: ",
					MediaEntityBuilder.createScreenCaptureFromPath(screenlocation).build());
		}else if(result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + "  Test Case Skipped", ExtentColor.YELLOW));
			logger.fail(result.getThrowable());
		}else if(result.getStatus() == ITestResult.SUCCESS){
			logger.log(Status.PASS, result.getName() + " Successfully Executed");
		}else{
			logger.log(Status.FAIL, "Application is not responding at the moment: " + driver.getTitle());
			logger.fail(result.getThrowable());
			Assert.fail(driver.getTitle());
		}

	}

	public void captureFailureScreenshot() {
		logger.fail(MarkupHelper.createLabel(testCaseName + "  Test Case Failed", ExtentColor.RED));
		getScreen("./ExtentReports/Screenshots/" + testCaseName + "_" + ".png");
		String screenlocation = System.getProperty("user.dir") + "/ExtentReports/Screenshots/" + testCaseName
				+ "_" + ".png";
		try {
			logger.fail("Screenshot Reference: ",
					MediaEntityBuilder.createScreenCaptureFromPath(screenlocation).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void takeScreen(String screenshotName) {
		try {
			File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destinationPath = new File(System.getProperty("user.dir") + "/Screenshots/" + screenshotName + ".png");
			FileHandler.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			logger.log(Status.INFO, "Failed to take screenshot " + e.getMessage());
		}
	}

	public static void getScreen(String location) {

		try {
			File destination = new File(location);
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			FileHandler.copy(src, destination.getAbsoluteFile());

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void login(String sheetName, String testCaseName) {

		try {
			logger.log(Status.INFO, "User navigated to "+APPURL);
			// Retrieve Test Data
			Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);
			Login_POM login = PageFactory.initElements(driver, Login_POM.class);
			acceptCookies();
			click(login.getLoginButton(), "Login");
			input(login.getUserName(), testData.get("UserName"), "User");
			input(login.getPassWord(), testData.get("Password"), "Password");
			click(login.getSubmit(), "Submit Login");
			verifyLogin();
			selectOneAccount(testData.get("MultiAccount"));
			removeProductsFromCart();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static String getChromeDriverPath() {

		try {
			OS = System.getProperty("os.name");

			if (OS.contains("Window")) {

				return GlobalConstants.CHROMEPATH;

			} else if (OS.contains("linux")) {

				return GlobalConstants.CHROMEPATH_LINUX;

			} else {

				return GlobalConstants.CHROMEPATH_LINUX;
			}
		} catch (Exception e) {

			Assert.fail("Unable to Get the Chrome File Path");
		}
		return OS;
	}

	public static String getFirefoxDriverPath() {

		try {

			if (OSFinder.isWindows()) {

				return GlobalConstants.FIREFOXPATH;

			} else if (OSFinder.isUnix()) {

				return GlobalConstants.FIREFOXPATH_LINUX;

			} else if (OSFinder.isMac()) {

				return GlobalConstants.FIREFOXPATH_MAC;
			} else {
				return GlobalConstants.FIREFOXPATH_LINUX;
			}
		} catch (Exception e) {

			Assert.fail("Unable to Get the Firefox File Path");
		}
		return OS;
	}

	public static FirefoxOptions addFirefoxOptions() {

		FirefoxOptions option = new FirefoxOptions();
		Proxy proxy = new Proxy();
		proxy.setProxyType(ProxyType.AUTODETECT);
		option.setProxy(proxy);
		option.setCapability("marionette", true);
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.cache.disk.enable", false);
		return option;
	}

	public static ChromeOptions addChromeOptions() {
		ChromeOptions chromeOptions = null;
		try {

			chromeOptions = new ChromeOptions();
			Proxy proxy = new Proxy();
			proxy.setProxyType(ProxyType.AUTODETECT);
			chromeOptions.setCapability("proxy", proxy);
			chromeOptions.setAcceptInsecureCerts(true);

		} catch (Exception e) {

		}
		return chromeOptions;
	}

	public static void getBrowser(String browserName) throws Exception {

		try {
			if (browserName.equalsIgnoreCase("firefox")) {

				System.setProperty(GlobalConstants.FIREFOX, getFirefoxDriverPath());

				try {
					driver = new FirefoxDriver(addFirefoxOptions());
				} catch (Exception e) {
					driver = null;
					driver = new FirefoxDriver(addFirefoxOptions());
				}

			} else if (browserName.equalsIgnoreCase("Chrome")) {

				System.setProperty(GlobalConstants.CHROME, getChromeDriverPath());


				try {
					driver = new ChromeDriver(addChromeOptions());
				} catch (Exception e) {
					driver = null;
					driver = new ChromeDriver(addChromeOptions());
				}

			} else if (browserName.equalsIgnoreCase("ie")) {

				System.setProperty(GlobalConstants.IE, GlobalConstants.IEPATH);
				driver = new InternetExplorerDriver();

			} else {

				Assert.fail("No Browser has been selected");
			}
			maximizeBrowserWindow();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		} catch (Exception e) {

			Assert.fail("Fail to Launch Browser " + e.getMessage());
		}
	}

	public static void jeppLogin(String sheetName, String testCaseName) {

		Map<String, String> testData = ReadTestData.getTestData(sheetName, testCaseName);
		try {
			input(jeppHome.getusername(), testData.get("UserName"), "Username");
			input(jeppHome.getpassword(), testData.get("Password"), "Password");
			click(jeppHome.getloginbtn(), "Login");
			Thread.sleep(5000);
			try {
				if (jeppHome.getokaybtn().isDisplayed()) {
					jeppHome.getokaybtn().click();
				}
			} catch (Exception e) {
			}
			jeppUserLoggodedOn(testData.get("UserName"));
		} catch (Exception e) {
			Assert.fail("Login failed");
		}
	}

	public static void jeppUserLoggodedOn(String userName) {
		try {
			jeppHome.getloggedon().isDisplayed();
			String loggeonmsg = jeppHome.getloggedon().getAttribute("innerText");
			if (loggeonmsg.contains(userName)) {
				logger.log(Status.PASS, "Successful User Logged on: " + loggeonmsg);
			} else {
				logger.log(Status.FAIL, "Not Successful User Logged on: " + loggeonmsg);
			}
		} catch (Exception e) {
			Assert.fail("Login failed");
		}

	}

	public static void jeppLogout() {
		try {
			jeppHome.getnotyoulnk().isDisplayed();
			jeppHome.getnotyoulnk().click();
			logger.log(Status.PASS, "Successfully User logged out");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void click(WebElement element, String buttonName) {
		try {
			waitForElementToDisplay(element);
			element.click();
			logger.log(Status.INFO, "Clicked on " + buttonName);
			urlRejected(buttonName);
			//isPageReadyJS(element);
		} catch (Exception e) {
			logger.log(Status.FAIL, "Fail to Click on "+ element + " " + e.getMessage());
			Assert.fail("Fail to Click on "+ element + " " + e.getMessage());
		}
	}

	public static void prodClick(WebElement element, String buttonName) {
		waitForElementToDisplay(element);
		element.click();
		logger.log(Status.INFO, "Clicked on " + buttonName);
		urlRejected(buttonName);
		try {
			if (driver.getTitle().equals("Request Rejected") || driver.getTitle().contains("Page not found")) {
				Assert.fail("Clicking on " + buttonName + " redirected to " + driver.getTitle());
			}
			acceptCookies();
			verifyAppStatus();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void click1(WebElement element, String buttonName) {
		waitForElementToDisplay(element);
		element.click();
		logger.log(Status.INFO, "Clicked on " + buttonName);
		urlRejected(buttonName);
	}

	public static void input(WebElement element, String value, String fieldName) {
		waitForElementToDisplay(element);
		try {
			if (element.isDisplayed() || element.isEnabled()) {
				// To clear the existed value
				element.clear();
				// To enter current value
				element.sendKeys(value);
				logger.log(Status.INFO, "Entered the value in " + fieldName + " as: " + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Fail to Enter " + value + " in  " + fieldName + " " + e.getMessage());
		}

	}

	public static void switchToNewTab() {

		ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());

		driver.switchTo().window(tab.get(1));
	}

	public static void switchToParentTab() {

		ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());

		driver.switchTo().window(tab.get(0));
	}

	public static String getText(WebElement element) {
		String text = null;
		if (element.isDisplayed()) {
			text = element.getText().trim();
		}

		return text;
	}

	public static String getInnerText(WebElement element) {
		String str_Text = null;
		try {
			if (element.isDisplayed()) {
				str_Text = element.getAttribute("innerHTML").trim();
				// logger.log(Status.INFO, "Inner HTML Value : " + str_Text);
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return str_Text;
	}

	public static String getattributeValue(WebElement element) {
		String str_Text = null;
		try {
			if (element.isDisplayed()) {
				str_Text = element.getAttribute("value").trim();
				// logger.log(Status.INFO, "Input Text Field Value is : " +
				// str_Text);
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return str_Text;
	}

	public static WebElement explicitWait(WebElement element) {

		try {
			// WebDriverWait Initialization

			WebDriverWait wait = new WebDriverWait(driver, size);
			wait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {

			logger.log(Status.INFO, "Waited For " +size+" Seconds, Unable to Locate" + element);
		}
		return element;
	}

	public static WebElement elementToBeClick(WebElement element) {

		try {
			// WebDriverWait Initialization
			WebDriverWait wait = new WebDriverWait(driver, size);
			wait.until(ExpectedConditions.elementToBeClickable(element));

		} catch (Exception e) {

			logger.log(Status.INFO, "Waited For " +size+" Seconds, Unable to Locate" + element);
		}

		return element;
	}

	public static void selectShippingMethod(WebElement element, String fieldName) {
		waitForElementToDisplay(element);
		try {
			Select select = new Select(element);
			List<WebElement> shippings = select.getOptions();
			for (int i = 0; i < shippings.size(); i++) {

				String name = shippings.get(i).getText();

				if (name.equalsIgnoreCase("US FedEx 2day") || name.equalsIgnoreCase("US FedEx Int'l Priority")
						|| name.equalsIgnoreCase("US FedEx Int'l Economy")
						|| name.equalsIgnoreCase("US FedEx Priority Overnight")
						|| name.equalsIgnoreCase("US FedEx Ground")
						|| name.equalsIgnoreCase("US FedEx Standard Overnight")
						|| name.equalsIgnoreCase("US Ceva Air")) {

					select.selectByVisibleText(name);
				}
			}

		} catch (Exception e) {

			Assert.fail("Faile to Select Value from " + fieldName + " Error: " + e.getMessage());
		}
	}

	public static void selectByVisibleText(WebElement ele, String text, String fieldName) {
		try {
			waitForElementToDisplay(ele);
			new Select(ele).selectByVisibleText(text);
			logger.log(Status.INFO, "Selected " + text + " From " + fieldName);
		} catch (Exception e) {
			Assert.fail("Faile to Select " + text + "  from " + fieldName + " Error: " + e.getMessage());
		}
	}

	public List<WebElement> getListOptions(WebElement ele) {
		List<WebElement> lists = null;
		try {
			lists = new Select(ele).getOptions();
		} catch (Exception e) {
			logger.log(Status.FAIL, "Unable to Fetch Value From List");
			Assert.fail("Failed due to " + e.getMessage());
		}
		return lists;
	}

	public static boolean selectValuesDrpDwn(WebElement element, String selType, int index, String value,
			String visibleText, String fieldName) {
		boolean isSelected = false;
		waitForElementToDisplay(element);
		try {
			Select objSelect = new Select(element);
			switch (selType.toUpperCase()) {
			case "INDEX":

				objSelect.selectByIndex(index);
				isSelected = true;
				logger.log(Status.INFO, "Selected " + index + " From " + fieldName);

				break;

			case "VALUE":

				objSelect.selectByValue(value);
				isSelected = true;
				logger.log(Status.INFO, "Selected " + value + " From " + fieldName);

				break;

			case "VISIBLETEXT":
				objSelect.selectByVisibleText(visibleText);
				isSelected = true;
				logger.log(Status.INFO, "Selected " + visibleText + " From " + fieldName);

				break;

			default:
				Assert.fail("Unable to Select " + fieldName);
				break;
			}
			if (isSelected) {

			} else {
				Assert.fail("Unable to Selected Given element from the Dropdown");
			}
		} catch (Exception e) {
			Assert.fail("Failed To Select Value From " + fieldName + e.getMessage());

		}
		return isSelected;
	}

	public static void domClick(WebElement element, String fieldName) {
		try {
			driver.switchTo().defaultContent();
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			logger.log(Status.INFO, "Clicked on " + fieldName);
			urlRejected(fieldName);
		} catch (Exception e) {

			Assert.fail("Fail to Click on " + fieldName + " Error: " + e.getMessage());
		}
	}

	public static void capturescreen(String screenShotName) {

		if (GlobalConstants.RESULT_SCREENSHOTS == "Yes" || GlobalConstants.RESULT_SCREENSHOTS == "YES") {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			String screenlocation = "./Screenshots/" + screenShotName + "_" + timestamp.getTime() + ".png";

			getScreen("./ExtentReports/Screenshots/" + screenShotName + "_" + timestamp.getTime() + ".png");
			try {
				logger.info("", MediaEntityBuilder.createScreenCaptureFromPath(screenlocation).build());
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}

	public static void capturescreen() {

		if (GlobalConstants.RESULT_SCREENSHOTS == "Yes" || GlobalConstants.RESULT_SCREENSHOTS == "YES") {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			String screenlocation = "./Screenshots/" + testCaseName + "_" + timestamp.getTime() + ".png";

			getScreen("./ExtentReports/Screenshots/" + testCaseName + "_" + timestamp.getTime() + ".png");
			try {
				logger.info("", MediaEntityBuilder.createScreenCaptureFromPath(screenlocation).build());
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}

	public static void signOut(WebElement element, String fieldName) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			if (driver.getTitle().equals("Request Rejected")) {
				logger.log(Status.WARNING,
						"Logout Failed: For security reasons, the requested URL was rejected. Please contact hybrisSupport@aviall.com for support.");
			}
		} catch (Exception e) {
			// logger.log(Status.WARNING, fieldName + " is Not Available");
			driver.get(APPURL);
		}
	}

	public static String getPageTitle() {
		String strWebPageTitle = null;
		try {
			strWebPageTitle = driver.getTitle().trim();
			// logger.log(Status.INFO, "Page Title : " + strWebPageTitle);
		} catch (Exception e) {

			Assert.fail("Unable to Fetch Page Title");
		}
		return strWebPageTitle;
	}

	public static void scrollDown(WebElement element) {

		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
		}
	}

	public static void scrollUp() {
		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,250)", "");
		} catch (Exception e) {
		}
	}

	public static void mouseHover(WebElement element) {
		waitForElementToDisplay(element);
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			delay(2000);
		} catch (Exception e) {

			Assert.fail("Fail to MouseHover on " + element);
		}
	}

	public static String toolTip(WebElement element) {
		waitForElementToDisplay(element);
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
			delay(2000);
		} catch (Exception e) {

			Assert.fail("Fail to MouseHover on " + element);
		}
		return getText(element);
	}

	public static String toolTipText(WebElement element) {
		waitForElementToDisplay(element);
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			delay(2000);
		} catch (Exception e) {

			Assert.fail("Fail to tool text on " + element);
		}
		return getText(element);
	}

	public static void actionClick(WebElement element) {
		waitForElementToDisplay(element);
		try {
			Actions action = new Actions(driver);
			action.clickAndHold(element).build().perform();
		} catch (Exception e) {

			Assert.fail("Fail to MouseHover on " + element + e.getMessage());
		}
	}

	public static Properties repositoryData() {
		Properties prop = null;
		try {

			String propPath = propertiesFile;
			File source = new File(propPath);
			FileInputStream fin = new FileInputStream(source);
			prop = new Properties();
			prop.load(fin);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return prop;
	}

	public static String getFlagData(String data) {
		Properties prop = null;
		try {

			String propPath = propertiesFile;
			File source = new File(propPath);
			FileInputStream fin = new FileInputStream(source);
			prop = new Properties();
			prop.load(fin);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return prop.getProperty(data);
	}

	public static String getTimeStamp() {

		return new SimpleDateFormat("dd-MM-yy.mm.ss").format(Calendar.getInstance().getTime()).replace('-', ' ')
				.replace('.', ' ').replaceAll("\\s+", "") + "@gmail.com";
	}

	public static String getTimeStamp1() {

		return new SimpleDateFormat("dd-MM-yy.mm.ss").format(Calendar.getInstance().getTime()).replace('-', ' ')
				.replace('.', ' ').replaceAll("\\s+", "");
	}

	public static void acceptAlert() {

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			logger.log(Status.INFO, "No Alert Present in this Window");
		}
	}

	public static void verifyFlagMessage(WebElement element, String statusMessage) {

		String expMessage = repositoryData().getProperty("statusMessage");
		String actualMsg = element.getText();
		if (expMessage.equals(actualMsg)) {

			logger.log(Status.INFO, "Verification of Status Message is Successful");
		} else {

			Assert.fail("Unable to Find " + statusMessage);
		}

	}

	public static void verifyAppStatus() {
		if (driver.getTitle().equalsIgnoreCase("Error")) {
			Assert.fail("Application is Not Working Please Contact Dev Tem");
		} else if (driver.getTitle().equalsIgnoreCase("504 Gateway Timeout")) {
			Assert.fail("The gateway did not receive a timely response from the upstream server or application.");
		} else if (driver.getTitle().equalsIgnoreCase("503 Service Unavailable")){
			Assert.fail("The server is temporarily unable to service your request due to maintenance downtime or capacity problems. Please try again later: " + driver.getTitle());
		}else if(driver.getTitle().equalsIgnoreCase("testh.aviall.com") || driver.getTitle().equalsIgnoreCase("devhybris.aviallinc.com")){
			Assert.fail("Application is Down");
		}
		testStatus.add('f');
	}

	public static void selectOneAccount(String accountNumber) {
		delay(1000);
		Login_POM userData = PageFactory.initElements(driver, Login_POM.class);
		if (driver.getTitle().contains(("AccountSelectionPage"))) {
			List<WebElement> account_num = userData.getAccountNumber();
			for (WebElement webElement : account_num) {
				if(getText(webElement).contains(accountNumber)){
					webElement.click();
					break;
				}
			}
		}
	}

	public static void userLogout() {

		try {
			MyAccount_POM profile = PageFactory.initElements(driver, MyAccount_POM.class);
			signOut(profile.getLogout(), "Logout");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void killBrowserInstances() {

		try {

			switch (runRimeBrowser.toUpperCase()) {

			case "CHROME":
				try {
					Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
				} catch (Exception e1) {
					Runtime.getRuntime().exec("pkill chrome");
					Runtime.getRuntime().exec("pkill chromedriver");
				}
				break;
			case "FIREFOX":
				try {
					Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				} catch (Exception e) {
					Runtime.getRuntime().exec("pkill firefox");
					Runtime.getRuntime().exec("pkill geckodriver");
				}
				break;
			default:
				driver.quit();
				break;
			}

		} catch (Exception e) {

			Assert.fail("Unable to Kill the Browser Process");
		}

	}

	public static void addCapabilities() {

		try {
			DesiredCapabilities capability = DesiredCapabilities.firefox();
			Proxy proxy = new Proxy();
			proxy.setProxyType(ProxyType.AUTODETECT);
			capability.setCapability(CapabilityType.PROXY, proxy);
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		} catch (Exception e) {

			Assert.fail("Failed To Load DesiredCapabilities");
		}

	}



	public static void waitForElementToDisplay(WebElement element){
		
		
		try {
			for (int i = 0; i <= size; i++) {
				delay(1000);
				if (element.isDisplayed()) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getParameter(String name) {
		String browser = System.getProperty(name);
		if (null == browser) {

			browser = "chrome";

		}

		if (browser.isEmpty()) {

			browser = "chrome";
		}

		return browser;
	}

	public static String envName(String environment) {

		if (environment.equalsIgnoreCase(repositoryData().getProperty("QA_2_Storefront"))) {

			return "Q2";
		} else if (environment.equalsIgnoreCase(repositoryData().getProperty("QA_4_Storefront"))) {

			return "Q4";
		} else if (environment.equalsIgnoreCase(repositoryData().getProperty("D_4_Storefront"))) {

			return "D4";
		} else if (environment.equalsIgnoreCase(repositoryData().getProperty("D_2_Storefront"))) {

			return "D2";
		}
		return null;
	}

	public static String getFirstSelected(WebElement ele) {
		String firstSelected = null;
		try {
			Select select = new Select(ele);
			firstSelected = select.getFirstSelectedOption().getText().trim();
		} catch (Exception e) {
			Assert.fail("Unable to Fetch First Selected Option due to " + e.getMessage());
		}
		return firstSelected;
	}

	public static void takeScreenShot(String name) {

		try {

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(name));

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void getAgreeTerms() {

		try {
			Home_POM home = PageFactory.initElements(driver, Home_POM.class);

			if (home.getAcceptCookie().isDisplayed()) {
				click(home.getAcceptCookie(), "Accept Cookies");
			}

		} catch (Exception e) {

		}
	}

	public static void maximize() {

		if (OS.contains("Windows")) {
			driver.manage().window().maximize();
		} else {

			driver.manage().window().setSize(new Dimension(1600, 900));
		}
	}

	public static void verifyLogin() {
		try {
			Login_POM userData = PageFactory.initElements(driver, Login_POM.class);
			 if (userData.getLoginError().isDisplayed()) {
				capturescreen("InvalidUserCredentials");
				Assert.fail("Login Failed Due to " + getText(userData.getLoginError()));
			}
		} catch (Exception e) {
			logger.log(Status.INFO, "User succesfully logged in");
		}
	}

	public static void keyEnter(WebElement ele) {

		try {

			ele.sendKeys(Keys.ENTER);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void addProductFromPDP(String shetName, String testCaseName) {

		try {
			// Loading Locators From POM File
			Home_POM home = PageFactory.initElements(driver, Home_POM.class);
			PLP_POM plp = PageFactory.initElements(driver, PLP_POM.class);
			PDP_POM pdp = PageFactory.initElements(driver, PDP_POM.class);

			Map<String, String> testData = ReadTestData.retrieveData(shetName, testCaseName);

			// Implementing Functionality
			input(home.getInputSearch(), testData.get("Search_Product"), "Search for a Product");
			click(home.getSearchProduct(), "LookUp Icon");
			waitForPanda();
			List<WebElement> productslist = plp.getProductList();

			try {
				if (productslist.size() == 0) {

					Assert.fail("Total no.of Products are " + productslist.size() + " Error Message is "
							+ getText(plp.getUnableFindProduct()));
				}
			} catch (Exception e1) {
				e1.getMessage();
			}
			for (int i = 1; i <= productslist.size(); i++) {

				try {

					WebElement product = driver.findElement(
							By.xpath("//div[@id='resultsList']/div[2]/div/div[" + i + "]/div/div[2]/a[1]"));
					//highlight(driver, product); 

					if ((getText(product).equalsIgnoreCase(testData.get("Search_Product")))) {
						// verifyShowFunctionality(pdp.getPdpCart());
						click(product, getText(product));
						break;
					}
				} catch (Exception e) {
					Assert.fail(e.getMessage());
				}
			}
			click(pdp.getAddToCartButton(), getText(pdp.getAddToCartButton()));

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
	// Highlight the Particular Element
	public static void highlight(WebDriver driver,WebElement element){
		try{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='3px solid red'", element);
			Thread.sleep(200);
			//js.executeScript("arguments[0].style.border=''", element);
		}catch(Exception e){
			e.printStackTrace();
		}		
	} 

	public static DesiredCapabilities downloadFileLocation() {

		String downloadFilepath = "./DownloadedFiles";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return cap;

	}

	public static void waitForPanda() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, size);
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='waitMsg']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void waitForTableData() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, size);
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='waitMsg']"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void addProductFromPLP(String sheetName, String testCaseName) {

		try {
			Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);
			setProductID(testData.get("Search_Product"));
			input(home.getInputSearch(), testData.get("Search_Product"), "Search for a Product");
			//recentAddedProduct = getattributeValue(home.getInputSearch());
			click(home.getSearchProduct(), "LookUp Icon");
			waitForPanda();
			List<WebElement> productslist = plp.getProductList();
			try {
				if (productslist.size() == 0) {

					Assert.fail("Total no.of Products are " + productslist.size() + " Error Message is "
							+ getText(plp.getUnableFindProduct()));
				}
			} catch (Exception e1) {
				logger.log(Status.INFO, "Product(s) are available as per the  " + testData.get("Search_Product"));
			}

			for (int i = 1; i <= productslist.size(); i++) {

				try {

					WebElement product = driver.findElement(
							By.xpath("//div[@id='resultsList']/div[2]/div/div[" + i + "]/div/div[2]/a[1]"));

					if ((getText(product).equalsIgnoreCase(testData.get("Search_Product")))) {


						String addToCart = getText(driver.findElement(By.xpath(
								"//div[@id='resultsList']/div[2]/div[" + i + "]/div[4]/div[2]/form/button[1]")));

						String InCart = getText(driver.findElement(By.xpath(
								"//div[@id='resultsList']/div[2]/div[" + i + "]/div[4]/div[2]/form/button[2]")));
						WebElement cart = driver.findElement(By.xpath(
								"//div[@id='resultsList']/div[2]/div[" + i + "]/div[4]/div[2]/form/button[1]"));

						if (addToCart != null && addToCart
								.equalsIgnoreCase(repositoryData().getProperty("save.list.add.to.cart"))) {
							click(cart, "Add Cart");
							try {
								if (checkout.getMessages().isDisplayed()
										&& getText(checkout.getMessages()).equalsIgnoreCase(
												repositoryData().getProperty("timeout.addtocart.error.msg"))) {

									Assert.fail("Unable to Add " + testData.get("Search_Product") + "due to "
											+ getText(checkout.getMessages()));

								}
							} catch (Exception e1) {

								logger.log(Status.INFO, testData.get("Search_Product") + "is added to the Cart");
							}
							break;

						} else if (InCart != null && InCart
								.equalsIgnoreCase(repositoryData().getProperty("multiOrder.text.inCart"))) {

							logger.log(Status.INFO, "Product " + getText(product) + " is Already in " + InCart);
							break;
						}
					}
				} catch (Exception e) {

					Assert.fail(e.getMessage());
				}
			}

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public static void acceptCookies() {

		try {

			domClick(driver.findElement(By.xpath("//button[@title='Accept Cookies']")), "Accept Cookies");
		} catch (Exception e) {

		}
	}

	public void removeProductsInCheckout() {

		ViewCheckout_POM checkout = PageFactory.initElements(driver, ViewCheckout_POM.class);
		ViewCart_POM cart = PageFactory.initElements(driver, ViewCart_POM.class);

		click(checkout.getBackToCartButton(), "Back To Cart");
		click(cart.getRemoveallitem(), "Remove All Link");
		driver.switchTo().activeElement();
		click(cart.getRemoveOkButton(), "Remove Pop-up Ok button");
	}

	public static void removeProductsFromCart() {
			if (!getText(cart.getMiniCart()).contains("0")) {
				domClick(home.getMiniViewCart(), "Mini View Cart");
				try {
					click(cart.getRemoveallitem(), "Remove All Link");
					driver.switchTo().activeElement();
					click(cart.getRemoveOkButton(), "Remove Pop-up Ok button");
				} catch (Exception e) {
					logger.log(Status.INFO, "Products are Not Available in the Cart");
				}
			}
	}

	public void searchAccount(String sheetName, String testCaseName) {
		// Importing POM Files
		PlaceOrder_RunAs_POM runAs = PageFactory.initElements(driver, PlaceOrder_RunAs_POM.class);

		Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);

		click(runAs.getAccountSearch(), "Account Search");
		input(runAs.getAccountNumber(), testData.get("AccountNumber"), "Account Number");
		click(runAs.getSearchButton(), "Search");
		List<WebElement> userlist = runAs.getUser();

		if (getText(runAs.getNoResultsMsg())
				.equalsIgnoreCase(repositoryData().getProperty("runAs.NoSearchResults.message"))) {

			Assert.fail("No Search Results found with Searched UsedID");

		} else if (userlist.size() != 0) {

			try {
				for (int i = 1; i <= userlist.size(); i++) {

					try {
						WebElement userAccount = driver
								.findElement(By.xpath("//table[@id='runas_search']/tbody/tr[" + i + "]/td[1]/span[1]"));

						if (getText(userAccount).equalsIgnoreCase(testData.get("AccountNumber"))) {

							click(userAccount, "Run As Button");
							break;
						}
					} catch (Exception e) {
						Assert.fail(e.getMessage());
					}
				}
			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		}

		parent = driver.getWindowHandle();
		// Switch to New Tab
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windowHandles.get(1));

	}

	public void searchAccountRunAs(String sheetName, String testCaseName) {

		// Importing POM Files
		PlaceOrder_RunAs_POM runAs = PageFactory.initElements(driver, PlaceOrder_RunAs_POM.class);

		Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);

		try {
			click(runAs.getAccountSearch(), "Account Search");
			input(runAs.getUserId(), testData.get("UserID"), "User ID");
			click(runAs.getSearchButton(), "Search");
			List<WebElement> userlist = runAs.getUser();

			if (getText(runAs.getNoResultsMsg())
					.equalsIgnoreCase(repositoryData().getProperty("runAs.NoSearchResults.message"))) {
				Assert.fail("No Search Results found with Searched UsedID");

			} else if (userlist.size() != 0) {

				for (int i = 1; i <= userlist.size(); i++) {

					try {
						WebElement userAccount = driver
								.findElement(By.xpath("//table[@id='runas_search']/tbody/tr[" + i + "]/td[1]/span[1]"));

						if (getText(userAccount).contains(testData.get("AccountNumber"))) {
							WebElement runASButton = driver.findElement(
									By.xpath("//table[@id='runas_search']/tbody/tr[" + i + "]/td[6]/input"));

							click(runASButton, "Run As Button");
							try {
								if (runAs.getAccountSearch().isDisplayed()) {
									Assert.fail(
											"Run As Functionality is not working as Account Search link is displayed even after clicked on RunAs");
								}
							} catch (Exception e) {
								break;
							}
						}
					} catch (Exception e) {
						Assert.fail(e.getMessage());
					}

				}
			}

		}

		catch (Exception e) {

			Assert.fail(e.getMessage());
		}
	}

	public String verifyOrderNumber() {

		String orderNO = null;
		try {
			// Import WebPage Locators
			Order_History_POM orderHistory = new Order_History_POM(driver);
			OrderConfirmation_POM orderConfirm = PageFactory.initElements(driver, OrderConfirmation_POM.class);
			MyAccount_POM profile = PageFactory.initElements(driver, MyAccount_POM.class);
			orderNO = getText(orderConfirm.getRetrieveOrderNumber());
			domClick(profile.getOrderHistory(), "My Profile_ OrderHistory");
			input(orderHistory.getOrderNumber(), orderNO, "Order Number");
			scrollDown(orderHistory.getOrderSearch());
			click(orderHistory.getOrderSearch(), "Search Order Number");
			waitForElementToDisplay(orderHistory.getResultOrder());
			if (orderHistory.getOrderHistoryCount().size() > 1) {
				Assert.fail("Morethan one record has been found");
			}

			Assert.assertEquals(orderNO, (orderHistory.getResultOrder().getText().replace('#', ' ').trim()));
			logger.log(Status.PASS, "Expected Order Number is displayed");

			if (orderNO != null) {
				CompleteOrders.put(gettcName(), gettcName() + " " + orderNO);
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return orderNO;
	}

	/*
	 * BE- 7858 Veriy PO Number Filed level validation on Checkout Page
	 */
	public void completeOrder(String sheetName, String testCaseName) {

		try {
			// Load Test Data
			Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);
			waitForElementToDisplay(checkout.getPurchaseOrderNumber());
			if (!driver.findElement(By.xpath("//input[@id='deliveryOptionCheck']")).isSelected()) {
				driver.findElement(By.xpath("//input[@id='deliveryOptionCheck']")).click();
			}
			selectByVisibleText(checkout.getShippingMethod(), testData.get("Shipping_Option"), "Shipping Method");
			input(checkout.getPurchaseOrderNumber(), testData.get("PONumber"), "PO Number");
			try{
				if (checkout.getInclude8130().isSelected()) {
					domClick(checkout.getInclude8130(), "Include 8130");
				}
			}catch(Exception e){

			}
			if(!checkout.getPlaceOrderButton().getAttribute("class").contains("btnGray")){
				domClick(checkout.getPlaceOrderButton(), "Place Order");
				explicitWait(confirm.getConfirmationMsg());
			}else{
				click(checkout.getCDICheckbox(), "CDI Checkbox");
				domClick(checkout.getPlaceOrderButton(), "Place Order");
				explicitWait(confirm.getConfirmationMsg());
			}

		} catch(Exception e){
			Assert.fail("Place order failed" + e.getMessage());
		}
	}

	public void validatePlaceorderWarningMSG(String sheetName, String testCaseName) {

		try {
			// Load Test Data
			Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);

			// Verify PO Number Error Msg When user enter credit card number
			input(checkout.getPurchaseOrderNumber(), testData.get("DummyCard"), "PO Number");
			checkout.getPurchaseOrderNumber().sendKeys(Keys.TAB);
			Assert.assertEquals(repositoryData().getProperty("checkout.invalid.ponumber"),
					getText(checkout.getPonumbervaliderror()));
			logger.log(Status.INFO, getText(checkout.getPonumbervaliderror()));
			input(checkout.getPurchaseOrderNumber(), testData.get("PONumber"), "PO Number");
			selectByVisibleText(checkout.getShippingMethod(), testData.get("Shipping_Option"), "Shipping Method");
			domClick(checkout.getPlaceOrderButton(), "Place Order");

			for (WebElement element : checkout.getGlobalMessages()) {
				if (getText(element).contains(repositoryData().getProperty("checkout.confirmation.invalidUser"))) {
					Assert.fail("This User is Not Allowed to Place An Order");
				} else if (getText(element).equals(repositoryData().getProperty("checkou.placeorder.cannotpurchase"))) {
					Assert.fail(getText(checkout.getMessages()));
				} else if (getText(element)
						.contains(repositoryData().getProperty("checkout.consignee.address.compare"))) {
					logger.log(Status.PASS, "Any Two Adddresses are Same from Billing, Ship To and Consignee");
				} else {
					logger.log(Status.INFO, getText(checkout.getMessages()));
					break;
				}
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void addConsigneeAddress(String sheetName, String testCaseName) {

		Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);

		try {
			// Import POM Files
			ViewCheckout_POM viewchkout = PageFactory.initElements(driver, ViewCheckout_POM.class);
			click(viewchkout.getChangeShippingAddressLink(), "Shipping Address");
			selectValuesDrpDwn(viewchkout.getCountry(), "Visibletext", 0, "", testData.get("Country"), "Country");
			input(driver.findElement(By.id("address.name")), "Aviall", "Name");
			selectByVisibleText(viewchkout.getState(), testData.get("State"), "State");
			input(viewchkout.getZipCode(), testData.get("PostalCode"), "ZipCode");
			click(viewchkout.getCountry(), "Country");
			click(viewchkout.getUseOneTimeAddressButton(), "OneTimeShiptoButton");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	public void addMulProductFromPLP(String sheetName, String testCaseName, String productName) {

		try {
			Home_POM home = PageFactory.initElements(driver, Home_POM.class);
			PLP_POM plp = PageFactory.initElements(driver, PLP_POM.class);

			Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);

			input(home.getInputSearch(), testData.get(productName), "Search for a Product");
			click(home.getSearchProduct(), "LookUp Icon");
			waitForPanda();
			List<WebElement> productslist = plp.getProductList();

			try {
				if (productslist.size() == 0) {

					Assert.fail("Total no.of Products are " + productslist.size() + " Error Message is "
							+ getText(plp.getUnableFindProduct()));
				}
			} catch (Exception e1) {
				Assert.fail(e1.getMessage());
			}

			for (int i = 1; i <= productslist.size(); i++) {

				try {

					WebElement product = driver.findElement(
							By.xpath("//div[@id='resultsList']/div[2]/div/div[" + i + "]/div/div[2]/a[1]"));

					if ((getText(product).equalsIgnoreCase(testData.get(productName)))) {

						String addToCart = getText(driver.findElement(By
								.xpath("//div[@id='resultsList']/div[2]/div[" + i + "]/div[4]/div[2]/form/button[1]")));

						String InCart = getText(driver.findElement(By
								.xpath("//div[@id='resultsList']/div[2]/div[" + i + "]/div[4]/div[2]/form/button[2]")));
						WebElement cart = driver.findElement(By
								.xpath("//div[@id='resultsList']/div[2]/div[" + i + "]/div[4]/div[2]/form/button[1]"));

						if (addToCart != null
								&& addToCart.equalsIgnoreCase(repositoryData().getProperty("save.list.add.to.cart"))) {

							click(cart, "Add Cart");
							break;

						} else if (InCart != null
								&& InCart.equalsIgnoreCase(repositoryData().getProperty("multiOrder.text.inCart"))) {

							logger.log(Status.INFO, "Product" + getText(product) + "is Already in " + InCart);
							break;
						}
					}
				} catch (Exception e) {

					Assert.fail(e.getMessage());
				}
			}

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void softAssert(String actual, String expected, String message) {
		try {
			SoftAssert soft = new SoftAssert();
			soft.assertEquals(actual, expected);
			soft.assertAll();
			logger.log(Status.INFO, message);
		} catch (Exception e) {
			logger.log(Status.INFO, e.getMessage());
		}
	}

	public static void closeParentWindow() {

		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			logger.log(Status.INFO, "Window is Not Available");
		}

	}

	public static void softAssertionEquals(String actual, String expected) {
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(actual, expected);
		soft.assertAll();

	}

	public static void delay(int time) {

		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}

	public static void editflatfile(String fpath, String ordernumbers) throws IOException {

		try (FileWriter file = new FileWriter(fpath, true);
				BufferedWriter bw = new BufferedWriter(file);
				PrintWriter pw = new PrintWriter(bw);) {
			pw.write("\r\n");
			pw.append(ordernumbers + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void verifyShowFunctionality(WebElement ele) {

		String beforeButtonColor = ele.getCssValue("background-color").replace("rgb", "").replace("(", "")
				.replace(")", "").replace("a", "").replaceAll(",", "").replaceAll("\\s", "");
		mouseHover(ele);
		String afterButtonColor = ele.getCssValue("background-color").replace("rgb", "").replace("(", "")
				.replace(")", "").replace("a", "").replaceAll(",", "").replaceAll("\\s", "");
		assertNotEquals(beforeButtonColor, afterButtonColor);
	}

	public static String settcName(String scriptName) {
		return tcName = scriptName;
	}

	public static String gettcName() {
		return GenericMethods.tcName;
	}

	public static String getTestCaseName() {
		return testCaseName;
	}
	public static String setProductID(String Product) {
		return ProductID = Product;
	}

	public static String getProductID() {
		return GenericMethods.ProductID;
	}

	public static String copyFile() {
		// String dt;
		String fpath = null;
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter datetime = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
		String dTime = now.format(datetime);
		/*
		 * if(String.valueOf(dTime.charAt(0)).equals("9")) { dt=
		 * dTime.replace(String.valueOf(dTime.charAt(0)), "0"); } else { dt = dTime; }
		 */
		if (CompleteOrders.size() > 0) {

			String currentDir = System.getProperty("user.dir");
			String srcpath = GlobalConstants.TESTDATAFILE; // currentDir+"\\AviallTestData\\";//Aviall_TestData_QA.xlsx";
			String despath = currentDir + "\\PlacedOrders\\";// "./PlacedOrders/";
			try {
				File to = new File(despath);
				File from = new File(srcpath);
				if (from.getName().equalsIgnoreCase("Aviall_TestData_QA.xlsx")) {
					FileUtils.copyFile(from, new File(despath + from.getName()));
					File[] content = to.listFiles();
					for (int i = 0; i < content.length; i++) {
						if (content[i].getName().equalsIgnoreCase("Aviall_TestData_QA.xlsx")) {
							if (!content[i].renameTo(new File(to, dTime + ".xlsx"))) {
								System.out.println("not moved");
							}
							break;
						}
					}
					fpath = despath + dTime + ".xlsx";
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println("Not Placed Any Orders");
		}
		return fpath;
	}

	public void pickUpSelection() {

		if (checkout.getPickupOptionCheck().isEnabled()) {
			if (!checkout.getPickupOptionCheck().isSelected()) {
				domClick(checkout.getPickupOptionCheck(), "Pick Up");
				input(checkout.getPickUpCarrierName(), "Aviall", "Pick Up Name");
			}
		} else {
			logger.log(Status.INFO, "User Does not have Pick up Option");
		}

		domClick(checkout.getPickupEmailCheck(), "Pick Up Email");
		input(checkout.getPickupEnterEmail(), "skurry@aviall.com", "Pick Up Email");

		if (!checkout.getPickupTextMessageCheck().isSelected()) {
			domClick(checkout.getPickupTextMessageCheck(), "Number");
			input(checkout.getPickUpEnterNumber(), "9999890198", "Mobile Number");
		}
	}

	public static void urlRejected(String buttonName) {
		try {
			if (driver.getTitle().equals("Request Rejected") || driver.getTitle().contains("Page not found")) {
				Assert.fail("Clicking on " + buttonName + " redirected to " + driver.getTitle());
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*public static void isPageReadyJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i < size; i++) {
			delay(1000);
			if (js.executeScript("return document.readyState").toString().equals("complete")) {

				break;
			}
			if (i == size) {
				logger.log(Status.ERROR, "Page load incomplete after clicking on " + element);
			}
		}
	}*/

	public void chooseDate(String date) {
		driver.findElement(By.linkText(date)).click();
	}

	public void verifyTextContains(String expected, String actual, String message) {
		try {
			assertThat(actual,containsString(expected));
			logger.log(Status.PASS, "Verification passed :"+message+", Actual: "+actual+", Expected: "+expected);
		} catch (AssertionError e) {
			testStatus.add('F');
			logger.fail("Verification failed :"+message+", Actual: "+actual+", Expected: "+expected);
			capturescreen();
		}
	}

	public void verifyTextNotContains(String expected, String actual, String message) {
		try {
			assertThat(actual,not(containsString(expected)));
			logger.log(Status.PASS, "Verification passed :"+message+", Actual: "+actual+", Expected: "+expected);
		} catch (AssertionError e) {
			testStatus.add('F');
			logger.fail("Verification failed :"+message+", Actual: "+actual+", Expected: "+expected);
			capturescreen();
		}
	}

	public String getPreviousMonth() {
		SimpleDateFormat format = new SimpleDateFormat("MMM");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return format.format(cal.getTime());
	}

	public <E> void clickElement(E element, String elementName) {
		if (element.getClass().getName().contains("By")) {
			click(driver.findElement((By)element), elementName);
		}else {
			click((WebElement)element, elementName);
		}

	}

	public void clickLinkText(String linkText) {
		clickElement(By.linkText(linkText), linkText+ " link");

	}

	public void verifyTextMatch(String expected, String actual, String message) {
		try {
			assertThat(actual,equalToIgnoringCase(expected));
			logger.log(Status.PASS, "Verification of Text Match passed :"+message+", Actual: "+actual+", Expected: "+expected);
		} catch (AssertionError e) {
			testStatus.add('F');
			logger.fail("Verification of Text Match failed :"+message+", Actual: "+actual+", Expected: "+expected);
			capturescreen();
		}
	}

}

