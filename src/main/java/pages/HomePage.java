package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
    // Attributes
    private WebDriver driver;

    // Elements
    private By registerButtonBy = By.className("ico-register");

    private By ComputerHeaderBy = By.xpath("//*[@class='top-menu']//*[contains(@href,'computers')]");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void hoverOverComputerheader() {
        Actions actions = new Actions(driver);
        WebElement computersHeader = driver.findElement(ComputerHeaderBy);
        actions.moveToElement(computersHeader).build().perform();
    }

    public void clickOverDeskOption() {
        WebElement computersHeader = driver.findElement(ComputerHeaderBy);
        WebElement deskComputers = computersHeader.findElement(By.xpath("./following-sibling::ul//*[contains(@href,'desktops')]"));
        deskComputers.click();
    }
}
