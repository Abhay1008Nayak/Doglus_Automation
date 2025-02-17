package listeners;

import com.aventstack.extentreports.Status;
import logger.LOG;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reporter.ExtentManager;
import reporter.ExtentTestManager;
import utilities.DriverUtils;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {
	public static boolean verifyElementsFail = false;
	public static List<String> failedElementList = new ArrayList<>();

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		verifyElementsFail = false;
		failedElementList.clear();
		LOG.INFO(" onStart method " + iTestContext.getName());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {

		LOG.INFO(" onFinish method " + iTestContext.getName());
		ExtentManager.extentReports.flush();
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		verifyElementsFail = false;
		failedElementList.clear();
		LOG.INFO(getTestMethodName(iTestResult) + " Test is Starting.");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		callSoftErrors();
		LOG.INFO(getTestMethodName(iTestResult) + " Test is Succeed.");
		ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		callSoftErrors();
		LOG.INFO(getTestMethodName(iTestResult) + " Test is Failed.");
		ExtentTestManager.getTest().log(Status.FAIL, "Test is Failed.");	
		DriverUtils.attachScreenshot();
		LOG.Reporter("Error Msg ::" + iTestResult.getThrowable().getMessage().toString());
		LOG.Reporter("Error Msg ::" + iTestResult.getThrowable().getLocalizedMessage().toString());
		LOG.ErrorMsgDetails(5, iTestResult);

	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		callSoftErrors();
		LOG.INFO(getTestMethodName(iTestResult) + " test is skipped.");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		LOG.INFO("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

	public void addFailedElements() {
		ExtentTestManager.getTest().log(Status.INFO, "Soft Assertions Fails during this scenario Execution");
		int count = 0;
		for (String error : failedElementList) {

			count++;
			ExtentTestManager.getTest().log(Status.FAIL, "Error Details : " + count + "  - " + error);

		}
		ExtentTestManager.getTest().log(Status.INFO, "Total Soft Assertion failure for this scenario =" + count);

	}

	public void callSoftErrors() {
		if (verifyElementsFail) {
			addFailedElements();
		}

	}
}
