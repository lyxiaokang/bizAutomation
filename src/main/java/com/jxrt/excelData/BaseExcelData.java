package com.jxrt.excelData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.jxrt.test.TestBase;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class BaseExcelData implements IData {

	String defaultPath = "data/";

	public Object[][] getData(String caseName, String dataFile) {
		return getData(caseName, dataFile, 0);
	}

	public Object[][] getData(String caseName, String dataFile, int rowNum) {
		Object[][] data = null;
		try {
			data = addList(caseName, dataFile, rowNum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public Object[][] getData(String caseName, String dataFile, int beginRowNum, int endRowNum) {
		Object[][] data = null;
		try {
			data = addList2(caseName, dataFile, beginRowNum, endRowNum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private Object[][] addList(String caseName, String dataFile, int rowNum) throws FileNotFoundException {
		int count = 0;
		// System.out.println("priority:" + priority);
		ArrayList<Object> list = new ArrayList<Object>();
		// 文件路径
		InputStream is = new FileInputStream(defaultPath + dataFile);
		Object[][] data = null;
		try {
			Workbook wb = Workbook.getWorkbook(is);
			Sheet rs = wb.getSheet(caseName);
			
			// 获取表格总行数
			int rsRows = rs.getRows();
			// 获取表格总列数
			int rsColumns = rs.getColumns();

			if (rs != null) {
				for (int i = 1; i <= rsRows - 1; i++) {
					String caseId = rs.getCell(0, i).getContents().trim();
					String isRun = "T";
					String priority = "";
					String content = rs.getCell(1, i).getContents();
					if (content.contains("|")) {
						priority = content.split("\\|")[0];
						isRun = content.split("\\|")[1];
					} else {
						priority = content;
					}
					if (isRun.equalsIgnoreCase("T")) {
						if (TestBase.isReRun) {
							if (TestBase.rerunCaseList.contains(caseId)) {
								count++;
								for (int j = 0; j <= rsColumns - 1; j++) {
									Cell c = rs.getCell(j, i);
									String cz = c.getContents();
									list.add(cz);
								}
							}
						} else {
							if (TestBase.priority.equalsIgnoreCase("all")) {
								if (priority.startsWith("P")) {
									count++;
									for (int j = 0; j <= rsColumns - 1; j++) {
										Cell c = rs.getCell(j, i);
										String cz = c.getContents();
										list.add(cz);
									}
								}
							} else {
								if (priority.equalsIgnoreCase(TestBase.priority)) {
									count++;
									for (int j = 0; j <= rsColumns - 1; j++) {
										Cell c = rs.getCell(j, i);
										String cz = c.getContents();
										list.add(cz);
									}
								}
							}
						}
					}
				}
				// System.out.println(list);
			}

			if (rowNum <= 0 || rowNum >= count) {
				data = new Object[count][rsColumns];
				int k = -1;
				for (int i = 0; i < count; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (k < list.size())
							k++;
						data[i][j] = list.get(k);
						// System.out.println("i="+i+","+"j="+j+","+data[i][j]);
					}
				}
			} else {
				int k = -1;
				data = new Object[rowNum][rsColumns];
				for (int i = 0; i < rowNum; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (k < list.size())
							k++;
						if (i <= (rowNum - 1)) {
							data[i][j] = list.get(k);
						}
					}
				}
			}
			wb.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private Object[][] addList2(String caseName, String dataFile, int beginNum, int endNum) throws FileNotFoundException {

		ArrayList<Object> list = new ArrayList<Object>();
		// 文件路径
		InputStream is = new FileInputStream(defaultPath + dataFile);
		Object[][] data = null;
		try {
			Workbook wb = Workbook.getWorkbook(is);
			Sheet rs = wb.getSheet(caseName);
			// 获取表格总行数
			int rsRows = rs.getRows();
			// 获取表格总列数
			int rsColumns = rs.getColumns();

			if (rs != null) {
				for (int i = 1; i <= rsRows - 1; i++) {
					for (int j = 0; j <= rsColumns - 1; j++) {
						Cell c = rs.getCell(j, i);
						String cz = c.getContents();
						list.add(cz);
					}
				}
				// System.out.println(list);
			}

			int sub = (endNum - beginNum) + 1;
			data = new Object[sub][rsColumns];
			if (beginNum <= 0 || endNum > rsRows) {
				if (beginNum <= 0 && endNum > rsRows) {
					beginNum = 0;
					endNum = rsRows;
					data = new Object[rsRows][rsColumns];
					for (int i = 0; i < sub; i++) {
						for (int j = 0; j < rsColumns; j++) {
							if (beginNum < rsRows * rsColumns)
								data[i][j] = list.get(beginNum);
							beginNum++;
						}
					}
				} else if (beginNum <= 0 && endNum <= rsRows) {
					beginNum = 0;
					sub = (endNum - beginNum);
					data = new Object[endNum][rsColumns];
					for (int i = 0; i < sub; i++) {
						for (int j = 0; j < rsColumns; j++) {
							if (beginNum < sub * rsColumns)
								data[i][j] = list.get(beginNum);
							beginNum++;
						}
					}
				} else {
					endNum = rsRows;
					sub = (endNum - beginNum) + 1;
					data = new Object[sub][rsColumns];
					for (int i = 0; i < sub; i++) {
						for (int j = 0; j < rsColumns; j++) {
							if (beginNum <= sub * rsColumns)
								data[i][j] = list.get(beginNum - 1);
							beginNum++;
						}
					}

				}
			} else {
				int k = 0;
				int a = beginNum * rsColumns;
				for (int i = 0; i < sub; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (k < sub * rsColumns)
							data[i][j] = list.get(a + k - 2);
						k++;

					}
				}
			}
			wb.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static void writeExcel(String excelName, String sheetName, String[] header, Object[][] objects) {
		if (objects == null)
			return;

		String[] title = null;
		if (header == null) {
			title = new String[objects[0].length];
			for (int i = 0; i < objects[0].length; i++) {
				title[i] = "Col_" + i;
			}
		} else {
			title = header;
		}
		try {
			// 获得开始时间
			long start = System.currentTimeMillis();
			// 输出的excel的路径
			String filePath = ".\\" + excelName + ".xls";
			// 创建Excel工作薄
			WritableWorkbook wwb;
			// 新建立一个jxl文件,即在d盘下生成testJXL.xls
			OutputStream os = new FileOutputStream(filePath);
			wwb = Workbook.createWorkbook(os);
			// 添加第一个工作表并设置第一个Sheet的名字
			WritableSheet sheet = wwb.createSheet(sheetName, 0);
			Label label;
			int row = 0;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, row, title[i]);
				label = new Label(i, row, title[i], getHeader());
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			row++;
			for (int i = 0; i < objects.length; i++) {
				int col = 0;
				for (Object value : objects[i]) {
					String valuetemp = String.valueOf(value);
					label = new Label(col++, row, valuetemp);
					sheet.addCell(label);
				}
				row++;
			}
			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();
			long end = System.currentTimeMillis();
			System.out.println("----完成该操作共用的时间是:" + (end - start) / 1000);
		} catch (Exception e) {
			System.out.println("---出现异常---");
			e.printStackTrace();
		}
	}

	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);// 定义字体
		try {
			font.setColour(Colour.BLUE);// 蓝色字体
		} catch (WriteException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			format.setBackground(Colour.YELLOW);// 黄色背景
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
}
