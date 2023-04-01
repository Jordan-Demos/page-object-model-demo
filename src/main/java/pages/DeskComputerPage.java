package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeskComputerPage {

    // Attributes
    private WebDriver driver;
    private WebDriverWait wait;
    private By simpleComputerBy = By.xpath("//*[@data-productid='75']//*[@class='product-title']");
    private By mandatoryFieldsBy = By.xpath("//*[@class='attributes']//*[contains(@class,'required')]/parent::dt");
    private By addToCartButtonBy = By.id("add-to-cart-button-75");
    private By barNotificationBy = By.id("bar-notification");
    private By loadingElementBy = By.xpath("//*[@class='ajax-loading-block-window']");

    // Constructor
    public DeskComputerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(90));
    }

    // Methods
    public void selectSimpleComputer() {
        WebElement simpleComputer = driver.findElement(simpleComputerBy);
        simpleComputer.click();
    }

    public void fillMandatoryFields() {
        List<WebElement> mandatoryFields = driver.findElements(mandatoryFieldsBy);
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
    }

    public void clickOnAddToCartButton() {
        WebElement addToCartButton = driver.findElement(addToCartButtonBy);
        addToCartButton.click();
    }

    public boolean wasElementAddedToCart() {
        WebElement loadingElement = driver.findElement(loadingElementBy);
        try {
            loadingElement.isDisplayed();
            wait.until(ExpectedConditions.invisibilityOf(loadingElement));
        } catch (NoSuchElementException noSuchElementException) {
            // Do nothing
        }
        WebElement barNotification = driver.findElement(barNotificationBy);
        return barNotification.getAttribute("class").contains("success");
    }
}