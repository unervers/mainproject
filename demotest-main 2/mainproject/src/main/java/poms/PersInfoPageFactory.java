package poms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class PersInfoPageFactory {
    private WebDriver driver;
    @FindBy(name = "passengerFirstName")
    @CacheLookup
    WebElement firstName;

    @FindBy(name = "passengerLastName")
    @CacheLookup
    WebElement lastName;

    @FindBy(xpath = "//body[1]/div[1]/form[1]/input[3]")
    @CacheLookup
    WebElement submit;


    public PersInfoPageFactory(WebDriver driver) {
        this.driver = driver;
    }
    public void sendFirstName(String fName){
        firstName.sendKeys(fName);
    }
    public void sendLastName(String lName){
        lastName.sendKeys(lName);
    }
    public void clickSubmit(){
        submit.click();
    }
    }
