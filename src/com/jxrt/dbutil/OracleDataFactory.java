package com.jxrt.dbutil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OracleDataFactory implements DataFactory {
    
	public int getUserIdByUserName(String userName) {
		int deviceId = 0;
		Connection conn = DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select id from t_user ");

		sqlBuffer.append("where status=1 and name = ?");
		try {
			PreparedStatement ps = conn.prepareStatement(sqlBuffer.toString());
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				deviceId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return deviceId;
	}
	
	/**
	 * 通过数据库查询签发金额统计
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static BigDecimal getIssuedMoneyByDB(String time,String department) throws SQLException{
		String issueTimeBegin=null;
		String issueTimeEnd=null;
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select sum(maturity_amount) from "+DBUtil.schema+"t_credit");
		//签发条
		sqlBuffer.append(" where  pk_credit = fk_credit_root");
		//签发时间不为空，且状态不为已撤回、已拒收、已回收、已废弃
		sqlBuffer.append(" and issue_time is not null");
		sqlBuffer.append(" and credit_state not in ('IS4', 'IS6','CAN','DIS')");
		//时间范围
		LocalDate today=LocalDate.now();
		LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
		DayOfWeek dayOfWeek=today.getDayOfWeek();
		LocalDate firstDayOfThisWeek=today.minusDays(dayOfWeek.getValue()-1);
		LocalDate lastDayOfThisWeek=firstDayOfThisWeek.plusDays(6);
		switch(time){
		case "Today":
			issueTimeBegin=today.toString();
			issueTimeEnd=today.plusDays(1).toString();
			break;
		case "Week":
			issueTimeBegin=firstDayOfThisWeek.toString();
			issueTimeEnd=lastDayOfThisWeek.plusDays(1).toString();
			break;
		case "Month":
			issueTimeBegin=firstDayOfThisMonth.toString();
			issueTimeEnd=lastDayOfThisMonth.plusDays(1).toString();
			break;
		case "Now":
			//七一是我党成立日
			issueTimeBegin="1921-07-01";
			issueTimeEnd=today.plusDays(1).toString();
			break;
			
		}
		sqlBuffer.append(" and issue_time >= to_date('"+issueTimeBegin+"', 'yyyy-mm-dd')");
		sqlBuffer.append(" and issue_time <= to_date('"+issueTimeEnd+"', 'yyyy-mm-dd')");
		//业务一部二部等部门过滤
		// TODO
		if(!department.equals("All")){
			sqlBuffer.append(" and fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		}
		//删除标识位为0
		sqlBuffer.append(" and delete_flag = '0'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			BigDecimal issuedMoney=rs.getBigDecimal(1);
			if(!(issuedMoney!= null)){
				issuedMoney=BigDecimal.valueOf(0);
			}
			System.out.println(issuedMoney);
			return issuedMoney;
		}
		return null;
	}
	/**
	 * 通过数据库查询部门下方企业
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */

	public static String getFkCorpByDepartment(String department) throws SQLException{
		StringBuffer fkCorpCoreListString = new StringBuffer();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		if(department.equals("All")){

			sqlBuffer.append("select pk_core from "+DBUtil.schema+"T_ci_core");
		}else{
			sqlBuffer.append("select distinct d.fk_corp from "+DBUtil.schema+"T_ORG_STAFF_DEPT a");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_org_staff b");
			sqlBuffer.append(" on a.fk_staff = b.PK_STAFF");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_cr_team_ccbscf c");
			sqlBuffer.append(" on b.pk_staff = c.fk_staff_cm");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_cr_corp_team d");
			sqlBuffer.append(" on c.fk_team = d.fk_team");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_ci_corp e");
			sqlBuffer.append(" on d.fk_corp = e.pk_corp");
			switch(department){
			case "Mine":
				// TODO 姓名待参数化
				sqlBuffer.append(" where a.STAFF_NAME = '"+"邱刚"+"'");
				break;
			case "DepartmentOne":
				sqlBuffer.append(" where a.DEPT_NAME = '"+"公司业务一部"+"'");
				break;
			case "DepartmentTwo":
				sqlBuffer.append(" where a.DEPT_NAME = '"+"公司业务二部"+"'");
				break;
			}
			sqlBuffer.append(" and c.leave_time > sysdate");
		}

		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
	    
		while(rs.next()){
			String fkCorpCore=rs.getString(1);
			fkCorpCoreListString.append("'");
			fkCorpCoreListString.append(fkCorpCore.toString());
			fkCorpCoreListString.append("',");
		}
		return fkCorpCoreListString.toString().substring(0, fkCorpCoreListString.toString().length()-1);
	}
	
	/**
	 * 通过数据库查询已融资未放款核心企业选择list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getFNLCoreSelectFkcorpByDepartment(String department) throws SQLException{
		StringBuffer fkCorpCoreListString = new StringBuffer();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
	
		sqlBuffer.append("select distinct fk_corp_core from "+DBUtil.schema+"t_credit c");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_APPLY_TEMP t");
		sqlBuffer.append(" on c.PK_CREDIT = t.FK_CREDIT");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_FINANCE_TEMP f");
		sqlBuffer.append(" on (c.PK_CREDIT = f.FK_CREDIT and c.FK_WORKFLOW = f.FK_WORKFLOW and f.DELETE_FLAG ='0')");
		sqlBuffer.append(" where c.credit_state_main in ('FI1') and c.delete_flag='0'");
		sqlBuffer.append(" and  fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
	    
		while(rs.next()){
			String fkCorpCore=rs.getString(1);
			fkCorpCoreListString.append("'");
			fkCorpCoreListString.append(fkCorpCore.toString());
			fkCorpCoreListString.append("',");
		}
		return fkCorpCoreListString.toString().substring(0, fkCorpCoreListString.toString().length()-1);
	}

	/**
	 * 通过数据库查询已签收未融资核心企业选择list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getSNFCoreSelectFkcorpByDepartment(String department) throws SQLException{
		StringBuffer fkCorpCoreListString = new StringBuffer();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
	
		sqlBuffer.append("select distinct fk_corp_core from "+DBUtil.schema+"t_credit c");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_APPLY_TEMP t");
		sqlBuffer.append(" on c.PK_CREDIT = t.FK_CREDIT");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_FINANCE_TEMP f");
		sqlBuffer.append(" on (c.PK_CREDIT = f.FK_CREDIT and c.FK_WORKFLOW = f.FK_WORKFLOW and f.DELETE_FLAG ='0')");
		sqlBuffer.append(" where c.credit_state_main in ('ACD') and c.delete_flag='0'");
		sqlBuffer.append(" and  fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
	    
		while(rs.next()){
			String fkCorpCore=rs.getString(1);
			fkCorpCoreListString.append("'");
			fkCorpCoreListString.append(fkCorpCore.toString());
			fkCorpCoreListString.append("',");
		}
		return fkCorpCoreListString.toString().substring(0, fkCorpCoreListString.toString().length()-1);
	}
	
	
	/**
	 * 通过数据库查询首页各部门的已融资未放款统计
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */

	private static List<Map<String, String>> getFNLListByDB(String department) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select c.fk_corp_core,c.corp_name_hold,c.pk_credit,c.maturity_amount,c.finance_time from "+DBUtil.schema+"t_credit c");
		sqlBuffer.append(" join "+DBUtil.schema+"t_credit_finance_temp f");
		
		//表关联
		sqlBuffer.append(" on  c.pk_credit = f.fk_credit and f.delete_flag = '0'");
		
		//状态为融资中
		sqlBuffer.append(" where  c.credit_state_main in ('FI0','FI1')");
		
		//部门过滤
		if(department.equals("Mine")||department.equals("DepartmentOne")||department.equals("DepartmentTwo")){
			//如果对业务一部，业务二部，我的，使用getFkcorpByDepartment()结果

			sqlBuffer.append(" and c.fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		}else if(department.equals("All")){
			//如果是所有企业，则不带入此查询条件
			sqlBuffer.append("");
		}else{
			//如果不是上述类型，直接将department当做企业名称带入进行查询

			sqlBuffer.append(" and c.fk_corp_core in ('"+department+"')");
		}
		//删除标识位为0
		sqlBuffer.append(" and c.delete_flag = '0'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			Map<String, String> map=new HashMap<String, String>();
			map.put("fkCorpCore",rs.getString(1));
			map.put("corpNameHold",rs.getString(2));
			map.put("pkCredit",rs.getString(3));
			map.put("maturityAmount",rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			if(rs.getString(5)!=null){
				map.put("financeTime",rs.getString(5).replaceAll("-",".").substring(0, 10));
			}else{
				map.put("financeTime","");
			}
			
			list.add(map);
		}
		return list;
	}
	/**
	 * 通过白条号查询元素的creditList的其它信息
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	
	public static Map<String, String> getFNLListByPkCredit(String pkCredit,String department) throws SQLException{
		List<Map<String, String>> list=getFNLListByDB(department);
		for(Map<String, String> map:list){
			if(map.get("pkCredit").equals(pkCredit)){
				return map;
			}
		}
		return null;
	}
	
	
	/**
	 * 通过数据库查询首页各部门的已融资未放款统计
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */

	private static List<Map<String, String>> getSNFListByDB(String department) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select fk_corp_core,corp_name_hold,pk_credit,maturity_amount,accept_time from "+DBUtil.schema+"t_credit");
		//状态为融资中
		sqlBuffer.append(" where  credit_state_main in ('ACD')");

		//部门过滤
		if(department.equals("Mine")||department.equals("DepartmentOne")||department.equals("DepartmentTwo")){
			//如果对业务一部，业务二部，我的，使用getFkcorpByDepartment()结果
			sqlBuffer.append(" and fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		}else if(department.equals("All")){
			//如果是所有企业，则不带入此查询条件
			sqlBuffer.append("");
		}else{
			//如果不是上述类型，直接将department当做企业名称带入进行查询
			sqlBuffer.append(" and fk_corp_core in ('"+department+"')");
		}
		//删除标识位为0
		sqlBuffer.append(" and delete_flag = '0'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			Map<String, String> map=new HashMap<String, String>();
			map.put("fkCorpCore",rs.getString(1));
			map.put("corpNameHold",rs.getString(2));
			map.put("pkCredit",rs.getString(3));
			map.put("maturityAmount",rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			if(rs.getString(5)!=null){
				map.put("acceptTime",rs.getString(5).replaceAll("-",".").substring(0, 10));
			}else{
				map.put("acceptTime","");
			}
			
			list.add(map);
		}
		return list;
	}
	/**
	 * 通过白条号查询元素的creditList的其它信息
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	
	public static Map<String, String> getSNFListByPkCredit(String pkCredit,String department) throws SQLException{
		List<Map<String, String>> list=getSNFListByDB(department);
		for(Map<String, String> map:list){
			if(map.get("pkCredit").equals(pkCredit)){
				return map;
			}
		}
		return null;
	}
	

	/**
	 * 通过企业名称查询企业编号
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getPkCorpByCorpName(String corpName) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_corp from "+DBUtil.schema+"t_ci_corp");
		sqlBuffer.append(" where  corp_name = '"+corpName+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			String pkCorp=rs.getString(1);
			return pkCorp;
		}
		return null;
	}
	
	/**
	 * 通过企业简称查询企业编号
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getPkCorpByCorpShortName(String corpShortname) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_corp from "+DBUtil.schema+"t_ci_corp");
		sqlBuffer.append(" where  corp_shortname = '"+corpShortname+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			String pkCorp=rs.getString(1);
			return pkCorp;
		}
		return null;
	}
	
	/**
	 * 通过企业编号查询企业名称
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getCorpNameByPkCorp(String pkCorp) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select corp_name from "+DBUtil.schema+"t_ci_corp");
		sqlBuffer.append(" where  pk_corp = '"+pkCorp+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			String corpName=rs.getString(1);
			return corpName;
		}
		return null;
	}
	
	
	/**
	 * 查询给定状态的白条数量
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static int countCredit(List<String> creditStateList,String corpNameCore,
			String corpName,String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) from "+DBUtil.schema+"t_credit");
		sqlBuffer.append(" where  credit_state in (");
		for (String creditState:creditStateList){
			sqlBuffer.append("'"+creditState+"',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		if(corpNameCore!=null){
			sqlBuffer.append(" and corp_name_core ='"+corpNameCore+"'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and corp_name_accept ='"+corpName+"'");
		}
		if(productTypeCcbscf!=null){
			sqlBuffer.append(" and PRODUCT_TYPE_CCBSCF ='"+productTypeCcbscf+"'");
		}
		if(reddemDateBegin!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) >=date'"+reddemDateBegin+"'");
		}
		if(reddemDateEnd!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) <=date'"+reddemDateEnd+"'");
		}
		sqlBuffer.append(" and delete_flag !=1");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			int count=rs.getInt(1);
			return count;
		}
		return 0 ;
	}

	/**
	 * 查询付款通知书的白条list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, String>> listRedeemCredit(List<String> creditStateList,String corpNameCore,
			String corpName,String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_credit,corp_name_core,corp_name_accept,product_type_ccbscf,issue_time,maturity_amount,(maturity_date+GRACE_PERIOD) as redeem_date,"
				+ "REDEEM_AMOUNT_SUM,(maturity_amount-REDEEM_AMOUNT_SUM) as redeem_amount,credit_state from "+DBUtil.schema+"t_credit");
		sqlBuffer.append(" where  credit_state in (");
		for (String creditState:creditStateList){
			sqlBuffer.append("'"+creditState+"',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		if(corpNameCore!=null){
			sqlBuffer.append(" and corp_name_core ='"+corpNameCore+"'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and corp_name_accept ='"+corpName+"'");
		}
		if(productTypeCcbscf!=null){
			sqlBuffer.append(" and PRODUCT_TYPE_CCBSCF ='"+productTypeCcbscf+"'");
		}
		if(reddemDateBegin!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) >=date'"+reddemDateBegin+"'");
		}
		if(reddemDateEnd!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) <=date'"+reddemDateEnd+"'");
		}
		sqlBuffer.append(" and delete_flag !=1 order by maturity_date nulls last,sequence_no desc");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			Map<String, String> map=new HashMap<String, String>();
			map.put("pkCredit",rs.getString("pk_credit"));
			map.put("corpNameCore",rs.getString("corp_name_core"));
			map.put("corpName",rs.getString("corp_name_accept"));
			map.put("productTypeCcbscf",rs.getString("product_type_ccbscf"));
			map.put("issueTime",rs.getString("issue_time").substring(0,10));
			map.put("maturityAmount",rs.getBigDecimal("maturity_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			map.put("redeemDate",rs.getString("redeem_date").substring(0,10));
			map.put("redeemedAmount",rs.getBigDecimal("REDEEM_AMOUNT_SUM").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			map.put("redeemAmount",rs.getBigDecimal("redeem_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			map.put("creditState",rs.getString("credit_state"));
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 查询付款通知书的白条list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, String>> listRedeemCreditFinanced(List<String> creditStateList,String corpNameCore,
			String corpName,String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_credit,corp_name_core,corp_name_accept,product_type_ccbscf,issue_time,maturity_amount,(maturity_date+GRACE_PERIOD) as redeem_date,"
				+ "REDEEM_AMOUNT_SUM,(maturity_amount-REDEEM_AMOUNT_SUM) as redeem_amount,credit_state from "+DBUtil.schema+"t_credit");
		sqlBuffer.append(" where  credit_state in (");
		for (String creditState:creditStateList){
			sqlBuffer.append("'"+creditState+"',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		if(corpNameCore!=null){
			sqlBuffer.append(" and corp_name_core ='"+corpNameCore+"'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and corp_name_accept ='"+corpName+"'");
		}
		if(productTypeCcbscf!=null){
			sqlBuffer.append(" and PRODUCT_TYPE_CCBSCF ='"+productTypeCcbscf+"'");
		}
		if(reddemDateBegin!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) >=date'"+reddemDateBegin+"'");
		}
		if(reddemDateEnd!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) <=date'"+reddemDateEnd+"'");
		}
		//第一级子条必须是已融资状态
		sqlBuffer.append(" and pk_credit in(select fk_credit_parent from "+DBUtil.schema+"t_credit where credit_state ='FID')");
		sqlBuffer.append(" and delete_flag !=1 order by maturity_date nulls last,sequence_no desc");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		PreparedStatement pstm=connection.prepareStatement(sql);
	    ResultSet rs=pstm.executeQuery(sql);
		while(rs.next()){
			Map<String, String> map=new HashMap<String, String>();
			map.put("pkCredit",rs.getString("pk_credit"));
			map.put("corpNameCore",rs.getString("corp_name_core"));
			map.put("corpName",rs.getString("corp_name_accept"));
			map.put("productTypeCcbscf",rs.getString("product_type_ccbscf"));
			map.put("issueTime",rs.getString("issue_time").substring(0,10));
			map.put("maturityAmount",rs.getBigDecimal("maturity_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			map.put("redeemDate",rs.getString("redeem_date").substring(0,10));
			map.put("redeemedAmount",rs.getBigDecimal("REDEEM_AMOUNT_SUM").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			map.put("redeemAmount",rs.getBigDecimal("redeem_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			map.put("creditState",rs.getString("credit_state"));
			list.add(map);
		}
		return list;
	}
	public static void main(String[] args) throws SQLException {
		OracleDataFactory oracleDataFactory = new OracleDataFactory();
		List creditList=new ArrayList<String>();
		creditList.add("ISD");
		creditList.add("RD0");
		creditList.add("RD1");
		creditList.add("RD9");
		LocalDate today=LocalDate.now();
		LocalDate reddemDateBegin=today.minusDays(20);
		LocalDate reddemDateEnd=today.plusDays(20);
//		LocalDate reddemDateBegin=LocalDate.of(2018,11,22);
//		LocalDate reddemDateEnd=LocalDate.of(2018,11,22);
		System.out.println(oracleDataFactory.listRedeemCredit(creditList,"盛世集团成员二","太平链条企业一","CREDIT",null, null));
		}
		

}
