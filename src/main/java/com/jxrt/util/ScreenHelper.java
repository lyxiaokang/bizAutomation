package com.jxrt.util;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScreenHelper {
	public static void main(String[] args) {
		int w = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		System.out.println(w);
		System.out.println(h);
	}
	
	public static void getScreenshot(String path) {
		int w = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		try {
			Robot robot = new Robot();
			BufferedImage bi = robot
					.createScreenCapture(new Rectangle(w, h));
			ImageIO.write(bi, "jpg", new File(path));
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
