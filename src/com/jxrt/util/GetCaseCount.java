package com.jxrt.util;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.jxrt.test.TestBase;


public class GetCaseCount {
	
	public static int total = 0;
	public static int P0 = 0;
	public static int P1 = 0;
	public static int P2 = 0;
	public static int P3 = 0;
	
	public static void getCount() throws BiffException, IOException {
		String dir = TestBase.baseDir + "\\data";
		File folder = new File(dir);
		for(File file : folder.listFiles()) {
			Workbook wb = Workbook.getWorkbook(file);
			int sheets = wb.getNumberOfSheets();
			for (int i = 0; i < sheets; i++) {
				Sheet sheet = wb.getSheet(i);
				String name = sheet.getName();
				int rows = sheet.getRows();
				//System.out.println("name:" + name + " rows:" + rows);
				for (int j = 1; j < rows; j++) {
					Cell cell = sheet.getCell(1, j);
					String str = cell.getContents().trim();
					if (str.contains("P0")||str.contains("p0"))
						P0++;
					else if(str.contains("P1")||str.contains("p1"))
						P1++;
					else if(str.contains("P2")||str.contains("p2"))
						P2++;
					else if(str.contains("P3")||str.contains("p3"))
						P3++;
					else 
						System.out.println("error:" + file.getName() + " " + name + " " + str);
					if (str.contains("F") || str.contains("f")) 
						System.out.println("not run:" + file.getName() + " " + name + " " + str);
				}
			}
		}
		total = P0 + P1 + P2 + P3;
		System.out.println("TOTAL:" + total);
		System.out.println("P0:" + P0);
		System.out.println("P1:" + P1);
		System.out.println("P2:" + P2);
		System.out.println("P3:" + P3);
	}
	
	public static void main(String[] args) throws BiffException, IOException {
		getCount();
	}

}
