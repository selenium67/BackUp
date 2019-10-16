package com.aviall.authentication;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aviall.dataProvider.ReadTestData;
import com.aviall.utilities.GenericMethods;
import com.aviall.webPages.Login_POM;

public class LoginAsInternalUser extends GenericMethods {

	Map<String, String> testData = ReadTestData.retrieveData("Authentication", "LoginAsInternalUser");

	/*
	 * BE-816 - Add to Cart_Restrictions Based on User Roles_Internal Users
	 * author - srilekha
	 */
	@Test(priority = 1)
	public void loginAsInternalUser() {
		logger.assignCategory("User Management");
		try {
			// User Login
			login("Authentication", "LoginAsInternalUser");
			// Verify Checkout Now option

			if (home.getShopForParts().isDisplayed())

			{
				logger.log(Status.PASS, "CheckOut is Disabled as Expected");
			} else {
				Assert.fail( "login not successful");
			}
		} catch (Exception e) {

			Assert.fail("User - InternalUser is Not-Logged In Successfully");
		}
	}

	/*
	 * BE-653 - Authentication_Login as Internal User_Admin author - smiryala
	 */
	@Test(priority = 2)
	public void authLoginAsInternalUser() {
		logger.assignCategory("User Management");
		// Verify Checkout Now option
		domClick(cart.getMiniCart(), "Mini cart");

		if (!getText(checkout.getMessages()).contains(getFlagData("checkout.error.invalid.accountType"))) {
			Assert.fail("User - InternalUser is Not-Logged In Successfully");
		}
	}

	@Test(priority = 3)
	public void viewDetailsInternalUser() {
		logger.assignCategory("User Management");
		domClick(account.getProfile(), "My Profile");
		List<WebElement> profileData = driver
				.findElements(By.xpath("//form[@id='updateProfileForm']/div[2]/div/table/tbody/tr/td/div[2]/input"));
		for (WebElement webElement : profileData) {
			if (webElement.isEnabled()) {
				Assert.fail("Internal user should not have permission to edit user details");
			} else {
				logger.log(Status.INFO, "Internal user permissions are working as intended");
			}
		}
	}

	/*
	 * BE-826 - Authentication_Login as_FailedInternalUser author - srilekha
	 */
	@Test(priority = 4)
	public void internalUserCheckout() {
		logger.assignCategory("User Management");
		addProductFromPLP("Authentication", "LoginAsInternalUser");
		domClick(home.getMiniViewCart(), "Mini Cart");
		if (getText(cart.getErrorMessage())
				.equals(repositoryData().getProperty("checkout.error.invalid.accountType"))) {
			if (cart.getChceckout().getCssValue("color").equals("rgba(255, 255, 255, 1)")) {
				logger.log(Status.INFO, "Internal User will not have permission to checkout");
			} else {
				Assert.fail("User logged in is not internal user");
			}
		}
		removeProductsFromCart();
		userLogout();
	}

	/*
	 * BE-825 - Authentication_Login as_InternalSuccessfully Aviall User author
	 * - srilekha
	 */
	@Test(priority = 5)
	public void invalidInternaluser() {
		logger.assignCategory("User Management");
		try {

			// User Login
			Login_POM login = PageFactory.initElements(driver, Login_POM.class);

			click(login.getLoginButton(), "Login");
			input(login.getUserName(), testData.get("UserName"), "User");
			input(login.getPassWord(), testData.get("InvalidPassword"), "Password");
			click(login.getSubmit(), "Submit Login");

			// Verify error on page

			if (getInnerText(login.getErrorMessage())
					.contains(repositoryData().getProperty("login.error.incorrect.password"))) {
				logger.log(Status.PASS, "Invalid login details are used");
				driver.navigate().refresh();
			} else {
				Assert.fail("Invalid functionality is not working");
			}

		} catch (Exception e) {
			Assert.fail();
		}
	}

}
