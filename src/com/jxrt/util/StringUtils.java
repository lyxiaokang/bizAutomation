package com.jxrt.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtils {

	/**
	 * 字符串拼接处理
	 * 
	 * @param str
	 * @param path
	 * @return
	 */
	public static String concatStr(String str, String path) {
		int idx1 = path.indexOf("'");
		while (idx1 != -1) {
			if (path.substring(idx1 + 1, idx1 + 2).equals("'")) {
				path = path.substring(0, idx1 + 1) + str + path.substring(idx1 + 1);
			}
			idx1 = path.indexOf("'", idx1 + str.length() + 1);
		}
		return path;
	}

	/**
	 * 自定义随机字符串(其中可包含汉字，数字,符号,大小写字母)的生成方法. intLength需要位数 , booWord是否生成汉字，booNumber是否生成数字 ,booSign是否生成符号, booSmallword是否生成小写字母, booBigword是否生成大写字母
	 * 
	 * @param intLength
	 * @param booWord
	 * @param booNumber
	 * @param booSign
	 * @param booSmallword
	 * @param booBigword
	 * @return
	 */
	public static String getRandomizer(int intLength, boolean booWord, boolean booNumber, boolean booSign, boolean booSmallword, boolean booBigword) {
		Random ranA = new Random();
		int intResultRound = 0;
		int intA = 0;
		String strB = "";
		while (intResultRound < intLength) {
			// 生成随机数A，表示生成类型
			// 0=汉字，1=数字，2=符号，3=小写字母，4=大写字母
			// 如果随机数A=0，则运行生成汉字
			intA = ranA.nextInt(5);
			if (intA == 0 && booWord) {

				strB = String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)))) + strB;
				intResultRound = intResultRound + 1;
				continue;
			}
			// 如果随机数A=1，则运行生成数字
			// 生成随机数A，范围在0-10
			// 把随机数A，转成字符
			// 生成完，位数+1，字符串累加，结束本次循环
			if (intA == 1 && booNumber == true) {
				intA = ranA.nextInt(10);
				strB = String.valueOf(intA) + strB;
				intResultRound = intResultRound + 1;
				continue;
			}
			// 如果随机数A=2，则运行生成符号
			// 生成随机数A，表示生成值域
			// 1：33-47值域，2：58-64值域，3：91-96值域，4：123-126值域
			if (intA == 2 && booSign == true) {
				intA = 1 + ranA.nextInt(4);

				// 如果A=1
				// 生成随机数A，33-47的Ascii码
				// 把随机数A，转成字符
				// 生成完，位数+1，字符串累加，结束本次循环
				if (intA == 1) {
					intA = 33 + ranA.nextInt(15);
					strB = String.valueOf((char) intA) + strB;
					intResultRound = intResultRound + 1;
					continue;
				}

				// 如果A=2
				// 生成随机数A，58-64的Ascii码
				// 把随机数A，转成字符
				// 生成完，位数+1，字符串累加，结束本次循环
				if (intA == 2) {
					intA = 58 + ranA.nextInt(7);
					strB = String.valueOf((char) intA) + strB;
					intResultRound = intResultRound + 1;
					continue;
				}

				// 如果A=3
				// 生成随机数A，91-96的Ascii码
				// 把随机数A，转成字符
				// 生成完，位数+1，字符串累加，结束本次循环
				if (intA == 3) {
					intA = 91 + ranA.nextInt(6);
					strB = String.valueOf((char) intA) + strB;
					intResultRound = intResultRound + 1;
					continue;
				}

				// 如果A=4
				// 生成随机数A，123-126的Ascii码
				// 把随机数A，转成字符
				// 生成完，位数+1，字符串累加，结束本次循环
				if (intA == 4) {
					intA = 123 + ranA.nextInt(4);
					strB = String.valueOf((char) intA) + strB;
					intResultRound = intResultRound + 1;
					continue;
				}
			}
			// 如果随机数A=3，则运行生成小写字母
			// 生成随机数A，范围在97-122
			// 把随机数A，转成字符
			// 生成完，位数+1，字符串累加，结束本次循环
			if (intA == 3 && booSmallword == true) {
				intA = 97 + ranA.nextInt(26);
				strB = String.valueOf((char) intA) + strB;
				intResultRound = intResultRound + 1;
				continue;
			}

			// 如果随机数A=4，则运行生成大写字母
			// 生成随机数A，范围在65-90
			// 把随机数A，转成字符
			// 生成完，位数+1，字符串累加，结束本次循环
			if (intA == 4 && booBigword == true) {
				intA = 65 + ranA.nextInt(26);
				strB = String.valueOf((char) intA) + strB;
				intResultRound = intResultRound + 1;
				continue;
			}
		}
		return strB;
	}

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else {
			return str.length() > 0 ? false : true;
		}
	}
	
	public static long strToMilles(String dateStr){
		Calendar c = Calendar.getInstance();  
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return c.getTimeInMillis();
	}
	
	public static String getTimeString(int i){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date().getTime() + i * 24 * 60 *60 *1000);
	}
	
	public static String timeStringSimple() {
		Date currentTime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}
	
	/**
	* 验证ip是否合法
	* 
	* @param text
	*		  ip地址
	* @return 验证信息
	*/
	public static boolean isValidIp(String text) {
		if (text != null && !text.isEmpty()) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			// 判断ip地址是否与正则表达式匹配
			if (text.matches(regex)) {
				// 返回判断信息
				return true;
			} else {
				// 返回判断信息
				return false;
		  }
	   }
	   // 返回判断信息
	   return false;
    }
	
	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/** 
	 * 将字符串复制到剪切板。 
	 */  
	public static void setSysClipboardText(String writeMe) {  
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
		Transferable tText = new StringSelection(writeMe);  
		clip.setContents(tText, null);  
	}
	
}
