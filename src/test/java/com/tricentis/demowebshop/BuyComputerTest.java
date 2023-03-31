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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

        WebElement registerButton = driver.findElement(By.className("ico-register"));
        wait.until(ExpectedConditions.visibilityOf(registerButton));
        wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButton.click();

        List<WebElement> genders = driver.findElements(By.xpath("//*[@class='gender']"));
        wait.until(ExpectedConditions.visibilityOfAllElements(genders));

        int randomGender = (int) Math.round(Math.random());
        genders.get(randomGender).click();

        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String email = String.format("%s.%s@test.com", firstname, lastname).toLowerCase();
        String randomPassword = faker.internet().password();

        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstname);

        WebElement lastNameInput = driver.findElement(By.id("LastName"));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastname);

        WebElement emailInput = driver.findElement(By.id("Email"));
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(By.id("Password"));
        passwordInput.clear();
        passwordInput.sendKeys(randomPassword);
        WebElement confirmPasswordInput = driver.findElement(By.id("ConfirmPassword"));
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(randomPassword);

        WebElement confirmRegisterButton = driver.findElement(By.id("register-button"));
        confirmRegisterButton.click();

        WebElement successRegisterDiv = driver.findElement(By.xpath("//*[@class='page registration-result-page']"));
        try {
            wait.until(ExpectedConditions.visibilityOf(successRegisterDiv));
        } catch (TimeoutException timeoutException) {
            throw new RuntimeException(timeoutException.getMessage());
        }

        HomePage homePage = new HomePage(driver);
        homePage.hoverOverComputerheader();
        homePage.clickOverDeskOption();

        DeskComputerPage deskComputerPage = new DeskComputerPage(driver);
        deskComputerPage.selectSimpleComputer();

        List<WebElement> mandatoryFields = driver.findElements(By.xpath("//*[@class='attributes']//*[contains(@class,'required')]/parent::dt"));
        for (WebElement mandatoryField : mandatoryFields) {
            Random random = new Random();
            List<WebElement> options = mandatoryField.findElements(By.xpath("./following-sibling::dd[1]//input"));
            int selectOption = (int) Math.round(Math.random() * options.size());
            selectOption = Math.min(selectOption, options.size() - 1);
            options.get(selectOption).click();
            System.out.println(String.format("Selecting option: %s: %s",
                    (mandatoryField.findElement(By.xpath("./label"))).getText(),
                    (options.get(selectOption).findElement(By.xpath("./following-sibling::label"))).getText()
            ));
        }

        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button-75"));
        addToCartButton.click();

        WebElement loadingElement = driver.findElement(By.xpath("//*[@class='ajax-loading-block-window']"));
        try {
         loadingElement.isDisplayed();
         wait.until(ExpectedConditions.invisibilityOf(loadingElement));
        } catch (NoSuchElementException noSuchElementException) {
            // Do nothing
        }

        WebElement barNotification = driver.findElement(By.id("bar-notification"));
        Assert.assertTrue(barNotification.getAttribute("class").contains("success"), "Computer was not added to cart");
    }
}
