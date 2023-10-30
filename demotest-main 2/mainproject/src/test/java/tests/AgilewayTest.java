package tests;

import ddt.LogPassXLSProvider;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import poms.CardInfoPageFactory;
import poms.LoginPageFactory;
import poms.PersInfoPageFactory;
import poms.TicketPageFactory;


public class AgilewayTest {
    private static WebDriver driver;
    private static String ADDRESS = "https://travel.agileway.net/";
    private static String SHEET = "logpass";
    private static String ORIGIN = "Sydney";
    private static String DESTINATION = "New York";
    private static String FIRST_NAME = "Adam";
    private static String LAST_NAME = "Nano";
    private static String CARD_NUMBER = "123";
    private static LoginPageFactory loginPage;
    private static TicketPageFactory ticketPage;
    private static PersInfoPageFactory persInfoPage;
    private static CardInfoPageFactory cardInfoPage;
    
    public AgilewayTest() {

    }

    
    
    @Test (dataProvider = "testdata")
    public void loginAndPass(String login, String pass) {  
        loginPage.sendLogin(login);
        loginPage.sendPassword(pass);
        loginPage.clickSignIn();
        Assert.assertTrue(driver.getPageSource().contains("Signed in"));
    }
    
    @Test (dependsOnMethods = "loginAndPass")
    public void ticketPage(){
        ticketPage.clickOneway();
        ticketPage.selectorigin(ORIGIN);
        ticketPage.selectdestination(DESTINATION);
        ticketPage.clickSubmit();
        Assert.assertNotNull(driver.findElement(By.xpath("//b[contains(text(),'Sydney')]")));
        Assert.assertNotNull(driver.findElement(By.xpath("//b[contains(text(),'New York')]")));
    }

    @Test (dependsOnMethods = "ticketPage")
    public void persInfo(){
        persInfoPage.sendFirstName(FIRST_NAME);
        persInfoPage.sendLastName(LAST_NAME);
        persInfoPage.clickSubmit();
        Assert.assertEquals(FIRST_NAME + " " + LAST_NAME, driver.findElement(By.name("holder_name")).getAttribute("value"));
    }

    @Test(dependsOnMethods = "persInfo")
    public void cardInfo(){
        cardInfoPage.sendCardNumber(CARD_NUMBER);
        cardInfoPage.clickPayNow();
        Assert.assertNotNull(driver.findElement(By.id("confirmation")));
    }
    
    


    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", ".//chromedriver");
        driver = new ChromeDriver();
        loginPage = new LoginPageFactory(driver);
        ticketPage = new TicketPageFactory(driver);
        persInfoPage = new PersInfoPageFactory(driver);
        cardInfoPage = new CardInfoPageFactory(driver);
        driver.navigate().to(ADDRESS);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        PageFactory.initElements(driver,loginPage);
        PageFactory.initElements(driver,ticketPage);
        PageFactory.initElements(driver,persInfoPage);
        PageFactory.initElements(driver,cardInfoPage);
    }


    @AfterClass
    public void tearDownClass() throws Exception {
        driver.findElement(By.xpath("//a[contains(text(),'Sign off')]")).click();
        driver.quit();
    }
    
    
    @DataProvider(name = "testdata")
    public Object[][] testData() throws IOException {

        LogPassXLSProvider provider = new LogPassXLSProvider("data.xlsx");
        int rows = provider.getRowCount(SHEET);
        Object[][] data = new Object[rows][2];
        for (int i = 0; i < rows; i++) {
            data[i][0] = provider.getCellData(SHEET, i, 0);
            data[i][1] = provider.getCellData(SHEET, i, 1);
        }
        return data;
    }
}
