package utilities;

import logger.LOG;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class CommonUtils {

	public static Properties readPropertyFile(String filePath) {
		try (FileInputStream input = new FileInputStream(filePath)) {
			Properties prop = new Properties();
			prop.load(input);
			return prop;
		} catch (IOException ex) {
			throw new RuntimeException("Exception occurred for filePath" + filePath + ":" + ex.getMessage());
		}
	}

	public static String getCellphoneNumber() {
		String ran = RandomStringUtils.randomNumeric(9).toString();
		LOG.INFO("Random mobilenumber is " + ran);
		return '9' + ran;
	}

	public static String getRandomNumber(int range) {
		String ran = RandomStringUtils.randomNumeric(range).toString();
		LOG.INFO("Random number is " + ran);
		return ran;
	}

	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");
		String strDate = formatter.format(date);
		LOG.INFO(strDate.toUpperCase());
		return strDate.toUpperCase();
	}

	public static String getDatePlusHours(int hours) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);

		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");
		String strDate = formatter.format(calendar.getTime());
		LOG.INFO(strDate.toUpperCase());
		return strDate.toUpperCase();
	}

	public static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (Exception ex) {
			LOG.INFO(ex.getMessage());
		}
	}

}
