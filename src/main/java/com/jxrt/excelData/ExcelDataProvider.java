package com.jxrt.excelData;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {
	@DataProvider(name = "Team1Data")
	public static Object[][] team1Data(Method m) throws FileNotFoundException {
		return new BaseExcelData().getData(m.getName(), "Team1.xls");
	}
	@DataProvider(name = "Team2Data")
	public static Object[][] team2Data(Method m) throws FileNotFoundException {
		return new BaseExcelData().getData(m.getName(), "Team2.xls");
	}
	@DataProvider(name = "Team3Data")
	public static Object[][] team3Data(Method m) throws FileNotFoundException {
		return new BaseExcelData().getData(m.getName(), "Team3.xls");
	}
	@DataProvider(name = "Team4Data")
	public static Object[][] team4Data(Method m) throws FileNotFoundException {
		return new BaseExcelData().getData(m.getName(), "Team4.xls");
	}
	
}
