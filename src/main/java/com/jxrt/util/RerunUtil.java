package com.jxrt.util;

import java.io.File;
import java.util.List;

import com.jxrt.test.TestBase;

public class RerunUtil {

	public static File rerunFile;
	public static FileHelper fileHelper = new FileHelper();

	public static void initRerunXml() {
		rerunFile = fileHelper.createFile("testng_rerun.xml");
		fileHelper.writeDataToFile(rerunFile, "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\" >");
		fileHelper.writeDataToFile(rerunFile, "<suite name=\"TestSuiteRerun\" verbose=\"2\">");
		fileHelper.writeDataToFile(rerunFile, "<listeners>");
		fileHelper.writeDataToFile(rerunFile, "		<listener class-name=\"com.mdm.listener.DotTestListener\" />");
		fileHelper.writeDataToFile(rerunFile, "		<listener class-name=\"org.uncommons.reportng.HTMLReporter\" />");
		fileHelper.writeDataToFile(rerunFile, "</listeners>");
	}

	public static void writeDataToFile(String testName, String className, List<String> methodName) {
		if (TestBase.writeTest&&!testName.equals("")) {
			System.out.println("2");
			fileHelper.writeDataToFile(rerunFile, "<test name=\"" + testName + "\" preserve-order=\"true\">");
			fileHelper.writeDataToFile(rerunFile, "	<classes>");
			TestBase.writeTest = false;
		}
		fileHelper.writeDataToFile(rerunFile, "		<class name=\"" + className + "\">");
		fileHelper.writeDataToFile(rerunFile, "			<methods>");
		for (int j = 0; j < methodName.size(); j++) {
			fileHelper.writeDataToFile(rerunFile, "				<include name=\"" + methodName.get(j) + "\" />");
		}
		fileHelper.writeDataToFile(rerunFile, "			</methods>");
		fileHelper.writeDataToFile(rerunFile, "		</class>");

	}

/*	public static void initTest(String testName) {
		fileHelper.writeDataToFile(rerunFile, "<test name=\"" + testName + "\" preserve-order=\"true\">");
		fileHelper.writeDataToFile(rerunFile, "<classes>");
	}*/

	public static void endTest() {
		if (!TestBase.writeTest) {
			fileHelper.writeDataToFile(rerunFile, "	</classes>");
			fileHelper.writeDataToFile(rerunFile, "</test>");
		}
	}

	public static void endSuite() {
		fileHelper.writeDataToFile(rerunFile, "</suite>");
	}

	public static void main(String[] args) {
		RerunUtil.initRerunXml();
	}

}
