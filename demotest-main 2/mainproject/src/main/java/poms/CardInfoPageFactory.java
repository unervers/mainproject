package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class CardInfoPageFactory {
    private WebDriver driver;
    @FindBy(name = "card_number")
    @CacheLookup
    WebElement cardNumber;

    @FindBy(xpath = "//body[1]/div[1]/form[1]/p[1]/input[1]")
    @CacheLookup
    WebElement payNow;

    public CardInfoPageFactory(WebDriver driver) {
        this.driver = driver;
    }
    public void sendCardNumber(String card_number){
        cardNumber.sendKeys(card_number);
    }
    public void clickPayNow() {
        payNow.click();
    }
}
