package com.jxrt.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	public static void writeExcel(String filePath, List<Label> labelList) throws Exception {
		try {
			File f = new File(filePath);
			f.createNewFile();
			WritableWorkbook wwb = Workbook.createWorkbook(new FileOutputStream(f));
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			for (Label label : labelList) {
				ws.addCell(label);
			}
			wwb.write();
			wwb.close();
			System.out.println("文件生成完成！");
		} catch (Exception e) {
			System.out.println(e.toString());

		}
	}
}
