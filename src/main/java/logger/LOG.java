package logger;

import com.aventstack.extentreports.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import reporter.ExtentTestManager;


public class LOG {

    private final static Logger logger = LoggerFactory.getLogger(LOG.class);

    public static void INFO(String message){
        logger.info(message);
    }

    public static void Reporter(String Message){
        INFO(Message);
        ExtentTestManager.getTest().log(Status.INFO,Message);
    }
    public static void failed(String Message){
        INFO(Message);
        ExtentTestManager.getTest().log(Status.FAIL,Message);
    }

    public static void ErrorMsgDetails(int count, ITestResult iTestResult){
        for(int i=0;i<count;i++) {
            Reporter("Error Msg in::" + iTestResult.getThrowable().getStackTrace()[i].getClassName()
                    + "  And methodName: " + iTestResult.getThrowable().getStackTrace()[i].getMethodName()
                    +"  And lineNumber: "+iTestResult.getThrowable().getStackTrace()[i].getLineNumber());
        }

    }



}
