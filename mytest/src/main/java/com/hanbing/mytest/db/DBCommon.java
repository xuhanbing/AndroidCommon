/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��4��3�� 
 * Time : ����4:00:28
 */
package com.hanbing.mytest.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DBCommon.java 
 * @author hanbing 
 * @date 2014��4��3�� 
 * @time ����4:00:28
 */
public class DBCommon {

    /**
     * 
     */
    public DBCommon() {
        // TODO Auto-generated constructor stub
    }
    
    public static class TbUserInfo
    {
        public static final String TB_NAME = "tb_user_info";
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String INFO = "info";
        
        public static String getCreateSql()
        {
            return "create table if not exists " + TB_NAME + "("
                    + _ID + " integer,"
                    + NAME + " text,"
                    + AGE + " text"
                    + ")";
        }
        
        public static String getDelSql()
        {
            return "drop table if exsits " + TB_NAME;
        }
        
        public static void upgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            List<HashMap<String, String>> addList = new ArrayList<HashMap<String,String>>();
            
            HashMap<String, String> map = new HashMap<String, String>();
            
            map.put("key", INFO);
            map.put("type", "text");
            map.put("value", "wahwahahha");
            
            addList.add(map);
            
            updateTable(db, TB_NAME, addList);
//        	String tempTable = "_t_" + TB_NAME;
//        	
//        	String sql1 = "alter table " + TB_NAME + " rename to " + tempTable;
//        	String sql2 = "create table if not exists " + TB_NAME + "("
//                    + _ID + " integer,"
//                    + NAME + " text,"
//                    + AGE + " integer,"
//                    + INFO + " text"
//                    + ")";
//        	String sql3 = "insert into " + TB_NAME + " select *,'xuhanbing' from " + tempTable;
//        	String sql4 = "drop table if exists " + tempTable;
//        	
//        	db.execSQL(sql1);
//        	db.execSQL(sql2);
//        	db.execSQL(sql3);
//        	db.execSQL(sql4);
        }
    }

    
    
    
    public static void updateTable(SQLiteDatabase db,
                                   String tableName,
                                   List<HashMap<String, String>> addList
                                   )
    {
        String sql = "";
        
        if (null != addList && addList.size() > 0)
        {
            
            for (int i = 0; i < addList.size(); i++)
            {
                
                sql = "add column ";
                HashMap<String, String> map = addList.get(i);
                
                String key = map.get("key");
                String type = map.get("type");
                String value = map.get("value");
                
                Cursor cursor = db.rawQuery("select * from " + tableName + " where 1=0", null);
                
                //
                if (cursor.getColumnIndex(key) < 0)
                {
                	if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(type))
                    {
                        String string = key +" " + type + " default \'" + value + "\'";
                        sql += string;
                        
                        sql = "alter table " + tableName + " " + sql;
                        db.execSQL(sql);
                    }
                }
                else{
                	System.out.println("contains column " + key);
                }
                cursor.close();
                
            }
        }
        
    }
    
    /**
	 * �жϱ����Ƿ����ĳ��
	 * @param db
	 * @param tableName  ����
	 * @param columnName ����
	 * @return
	 */
	public static boolean isTableColumnExist(SQLiteDatabase db, String tableName, String columnName)
	{
		Cursor cursor = db.rawQuery("select * from " + tableName + " where 1=0", null);
		
		boolean isExist = true;
		if (cursor.getColumnIndex(columnName) < 0)
		{
			isExist = false;
		}
		
		cursor.close();
		
		return isExist;
	}
}
