package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeskComputerPage {

    // Attributes
    private WebDriver driver;
    private By simpleComputerBy = By.xpath("//*[@data-productid='75']//*[@class='product-title']");

    // Constructor
    public DeskComputerPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void selectSimpleComputer() {
        WebElement simpleComputer = driver.findElement(simpleComputerBy);
        simpleComputer.click();
    }

}
