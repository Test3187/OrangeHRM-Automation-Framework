package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    public static ExtentReports extent;
    public static ExtentTest test;

    public static ExtentReports getReportInstance() {
        if(extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter(
                    "reports/TestReport.html"
            );
            reporter.config().setReportName("OrangeHRM Test Report");
            reporter.config().setDocumentTitle("Automation Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Akshay");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}