package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class Lr1 {
    private WebDriver driver;
    private String url = "https://zooalliance.ua/";

    @BeforeSuite
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "F:\\Work\\Java projects\\Lr1\\.idea\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        System.out.println("The setup process is completed");
    }

    @BeforeTest
    public void profileSetup() {
        driver.manage().window().maximize();
        System.out.println("The profile setup process is completed");
    }

    @BeforeClass
    public void appSetup() {
        driver.get(url);
        System.out.println("The app setup process is completed");
    }

    @Test
    public void buttonClick() throws InterruptedException {
        WebElement button = driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[1]/a"));
        button.click();
        Thread.sleep(5000);
        Assert.assertNotEquals(driver.getCurrentUrl(), url);
    }

    @Test
    public void findLogo(){
        driver.findElement(By.className("logo"));
    }

    @Test
    public void checkInput() {
        WebElement input = driver.findElement(By.className("search_query"));
        input.sendKeys("Royal Canin");
        input.sendKeys(Keys.ENTER);
    }

    @Test
    public void getInput() {
        WebElement input = driver.findElement(By.className("search_query"));
        String finder = "Royal Canin";
        String getterText = input.getAttribute("value");
        if (getterText.equals(finder)) {
            System.out.println(getterText);
            System.out.println("The variables are the same");
        } else {
            System.out.println(getterText);
            System.out.println("The variables are not the same.");
        }
    }

    @Test
    public void logicTest() {
        WebElement listElement = driver.findElement(By.xpath("//*[@id=\"selectProductSort\"]/option[4]"));
        listElement.click();
    }

    @AfterClass
    public void closeUp() {
        driver.quit();
    }
}
