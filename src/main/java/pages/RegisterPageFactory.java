package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPageFactory {
    // Attributes
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//*[@class='form-fields']//*[@class='gender']")
    private List<WebElement> genders;

    @FindBy(id = "FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "LastName")
    private WebElement lastNameInput;

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(id = "register-button")
    private WebElement confirmRegisterButton;

    @FindBy(xpath = "//*[@class='page registration-result-page']")
    private WebElement successRegisterDiv;

    Faker faker = new Faker();
    String firstName;
    String lastName;
    String gender;
    String email;
    String password;

    // Constructor
    public RegisterPageFactory(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(90));
        PageFactory.initElements(this.driver, this);
    }

    // Methods
    public void fillFirstName() {

        this.firstName = faker.name().firstName();
        this.firstNameInput.sendKeys(firstName);
    }

    public void fillLastName() {
        this.lastName = faker.name().lastName();
        this.lastNameInput.sendKeys(lastName);
    }

    public void selectGender() {
        int randomGender = (int) Math.round(Math.random());
        this.genders.get(randomGender).click();
        this.gender = genders.get(randomGender).getText();
    }

    public void fillEmail() {
        assert firstName != null;
        assert lastName != null;
        this.emailInput.sendKeys(String.format("%s.%s@test.com", firstName, lastName));
    }

    public void fillPassword() {
        this.password = faker.internet().password();
        this.passwordInput.sendKeys(password);
        this.confirmPasswordInput.sendKeys(password);
    }

    public void clickOnConfirmationButton() {
        this.confirmRegisterButton.click();
    }

    public boolean wasRegisterSuccessfully() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successRegisterDiv));
            return true;
        } catch (TimeoutException timeoutException) {
            throw new RuntimeException(timeoutException.getMessage());
        }
    }
}
