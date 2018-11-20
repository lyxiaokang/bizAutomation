package com.jxrt.dbOperate;

import org.testng.annotations.Test;

public class TestDBOperate{
	
	DBOperate dbOperate = new DBOperate();
	
	@Test
	public void createDBObject(){
//		t_app_store app = (t_app_store)dbOperate.selectObjectFromDB(t_app_store.class, "id=465");
//		System.out.println("********" + app.id);
		
		dbOperate.creatObjectFromDB("t_user_signin_log", true);
	}
	
}
