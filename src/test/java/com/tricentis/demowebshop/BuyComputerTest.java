package com.tricentis.demowebshop;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DeskComputerPage;
import pages.HomePage;
import pages.RegisterPageFactory;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BuyComputerTest {

    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/web-drivers/" + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void addSimpleComputerToCart() {
        HomePage homePage = new HomePage(driver);
        RegisterPageFactory registerPage = new RegisterPageFactory(driver);
        DeskComputerPage deskComputerPage = new DeskComputerPage(driver);

        homePage.clickOnRegisterButton();
        registerPage.selectGender();
        registerPage.fillFirstName();
        registerPage.fillLastName();
        registerPage.fillEmail();
        registerPage.fillPassword();
        registerPage.clickOnConfirmationButton();

        Assert.assertTrue(registerPage.wasRegisterSuccessfully(), "Register was unsuccessful.");

        homePage.hoverOverComputerheader();
        homePage.clickOverDeskOption();
        deskComputerPage.selectSimpleComputer();
        deskComputerPage.fillMandatoryFields();
        deskComputerPage.clickOnAddToCartButton();

        Assert.assertTrue(deskComputerPage.wasElementAddedToCart(), "Element was not added to cart");
    }
}
