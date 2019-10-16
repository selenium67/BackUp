package webpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.testng.utilities.Reports;


public class LoginPage extends com.testng.utilities.GenericMethods {

	public LoginPage(WebDriver driver) {
		Reports.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "//a[@id='loginButton']")
	private WebElement loginButton;

	public WebElement getLoginButton() {
		highlight(driver, loginButton);
		return loginButton;
	}

	@FindBy(how = How.ID, using = "j_username")
	@CacheLookup
	private WebElement userName;

	public WebElement getUserName() {
		highlight(driver, userName);
		return userName;
	}

	@FindBy(how = How.XPATH, using = "//input[@id='loginpassword']")
	@CacheLookup
	private WebElement password;

	public WebElement getPassWord() {
		highlight(driver, password);
		return password;
	}

	@FindBy(how = How.ID, using = "submitbtn")
	@CacheLookup
	private WebElement submitLogin;

	public WebElement getSubmit() {
		highlight(driver, submitLogin);
		return submitLogin;
	}

	@FindBy(how = How.LINK_TEXT, using = "Log Out")
	private WebElement logOut;

	public WebElement getLogOut() {
		return logOut;
	}

	@FindBy(how = How.ID, using = "myAccountLink")
	private WebElement myAccount;

	public WebElement getMyAccount() {
		explicitWait(myAccount);
		return myAccount;
	}

	@FindBy(how = How.XPATH, using = "//form[@id='loginsubmitform']/div")
	private WebElement errorMessage;

	public WebElement getErrorMessage() {
		return errorMessage;
	}

	@FindBy(how = How.ID, using = "Iagreebtn")
	private WebElement agreeCookies;

	public WebElement getAgreeCookies() {
		explicitWait(agreeCookies);
		return agreeCookies;
	}

	@FindBy(how = How.XPATH, using = "//div[@id='select_account']/a")
	private List<WebElement> accountNumbers;

	public List<WebElement> getAccountNumber() {
		return accountNumbers;
	}

	@FindBy(how = How.XPATH, using = "//div[@id='select_account']/a")
	private List<WebElement> accountNumbers1;

	public List<WebElement> getAccountNum() {
		return accountNumbers1;
	}

	@FindBy(how = How.XPATH, using = "//a[text()='Reset password']")
	private WebElement resetPass;

	public WebElement getResetPassword() {
		return resetPass;
	}

	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	private WebElement resetPassEmail;

	public WebElement getResetEmail() {
		return resetPassEmail;
	}

	@FindBy(how = How.XPATH, using = "//button[@id='forg_password_submit']")
	private WebElement resetPasssubmit;

	public WebElement getResetSubmit() {
		return resetPasssubmit;
	}

	@FindBy(how = How.XPATH, using = "//div[@id='forg_password_success']")
	private WebElement resetPasSuccess;

	public WebElement getResetSuccess() {
		return resetPasSuccess;
	}

	@FindBy(how = How.XPATH, using = "//div[@id='forgottenPwd-error-message']")
	private WebElement resetPasError;

	public WebElement getResetError() {
		return resetPasError;
	}

	@FindBy(how = How.XPATH, using = "//a[@id='back_login1']")
	private WebElement back_login;

	public WebElement getBacklogin() {
		return back_login;
	}

	@FindBy(how = How.ID, using = "login-error-message")
	private WebElement loginerrormessage;

	public WebElement getLoginError() {

		return loginerrormessage;
	}

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Reset Your Password')]")
	private WebElement restpasswordpage;

	public WebElement getResetPasswordPage() {

		return restpasswordpage;
	}

	@FindBy(how = How.XPATH, using = "//a[@id='back_login']")
	private WebElement backtologin;

	public WebElement getBacktologinLink() {
		return backtologin;
	}
	
	@FindBy(how = How.ID, using = "loginpassword")
	@CacheLookup
	private WebElement passwordById;
	
	@FindBy(how = How.ID, using = "submitbtn")
	private WebElement loginButtonById;
	
	public MyAccount_POM logintoAccount(String username, String password) {
		input(userName, username, "userame");
		input(passwordById, password, "password");
		click(loginButtonById, "login Button in Login Page");
		return new MyAccount_POM(driver);
	}
	
	public Home_POM logintoHomeAccount(String username, String password) {
		click(loginButton, "Login button in Home Page");
		input(userName, username, "userame");
		input(passwordById, password, "password");
		click(loginButtonById, "login Button in Login Page");
		return new Home_POM(driver);
	}
}
