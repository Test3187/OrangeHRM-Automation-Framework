package tests;

import base.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

 /*   @BeforeMethod
    public void setup(){
        // already in BaseTest — TestNG calls this automatically
    }

    @AfterMethod
     public void teardown(){
        // already in BaseTest — TestNG calls this automatically
    }*/

    @Test
    public void logintest(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserName("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        String actualTitle = driver.getTitle();
        System.out.println("Page Title after Login :" + actualTitle);
    }

   /* public static void main(String[] args) {
        LoginTest test = new LoginTest();
        test.logintest();
    }*/
}