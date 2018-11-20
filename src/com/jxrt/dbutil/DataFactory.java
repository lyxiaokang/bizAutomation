package com.jxrt.dbutil;

import java.util.List;


public interface DataFactory {
	/**
	 * 获得指定用户的userid
	 * @param userName
	 * @return
	 */
	public int getUserIdByUserName(String userName);
}
