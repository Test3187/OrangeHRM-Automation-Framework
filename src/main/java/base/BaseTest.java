package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {

    public WebDriver driver;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeSuite
    public void initReport(){
        extent = ExtentReportManager.getReportInstance();
    }

    @BeforeMethod
    public void setup(Method method){
        WebDriverManager.chromedriver().setup();

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        if (!headless) {
            driver.manage().window().maximize();
        }
        driver.get("https://opensource-demo.orangehrmlive.com");

        // Create test entry in report with method name
        test = extent.createTest(method.getName());
    }

      @AfterMethod
      public void teardown(ITestResult result) {
          if(result.getStatus() == ITestResult.FAILURE) {
              // Log failure in report
              test.log(Status.FAIL, "Test Failed: " + result.getThrowable());

              // Take screenshot
              TakesScreenshot ts = (TakesScreenshot) driver;
              File screenshot = ts.getScreenshotAs(OutputType.FILE);
              String testName = result.getName();
              String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                      .format(new Date());
              try {
                  String screenshotPath = "screenshots/" + testName + "_" + timestamp + ".png";
                  FileUtils.copyFile(screenshot, new File(screenshotPath));
                  test.addScreenCaptureFromPath(screenshotPath);
              } catch(IOException e) {
                  e.printStackTrace();
              }
          } else if(result.getStatus() == ITestResult.SUCCESS) {
              // Log pass in report
              test.log(Status.PASS, "Test Passed");
          } else {
              // Log skip in report
              test.log(Status.SKIP, "Test Skipped");
          }
          driver.quit();
      }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }


}
