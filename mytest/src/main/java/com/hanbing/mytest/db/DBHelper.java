/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��4��3�� 
 * Time : ����4:04:25
 */
package com.hanbing.mytest.db;

import com.hanbing.mytest.db.DBCommon.TbUserInfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


/**
 * DatabaseHelper.java 
 * @author hanbing 
 * @date 2014��4��3�� 
 * @time ����4:04:25
 */
public class DBHelper extends SDSQLiteOpenHelper {

    private static final int DB_VERSION = 7;
    private static final String DB_NAME = "mytest.db";
    
    
    private static final String DB_PATH = "/mytest/";
    
    static DBHelper mSQLiteOpenHelper = null;
	
	/**
	 * ����Ψһʵ��
	 * @param context
	 */
	public static synchronized void getSQLiteOpenHelper (Context context)
	{
		if (null == mSQLiteOpenHelper)
		{
			mSQLiteOpenHelper = new DBHelper(context);
		}
	}
	
	/**
	 * ��ȡ����
	 * @param context
	 * @return
	 */
	public static DBHelper getInstance(Context context)
	{
		
		if (null == mSQLiteOpenHelper)
		{
			getSQLiteOpenHelper(context);
		}
		
		return mSQLiteOpenHelper;
	}
	
    
    
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @param context
//     * @param name
//     * @param factory
//     * @param version
//     * @param errorHandler
//     */
//    public DBHelper(Context context, String name, CursorFactory factory, int version,
//                          DatabaseErrorHandler errorHandler) {
//        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
//        // TODO Auto-generated constructor stub
//    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    	
    	System.out.println("dbtest onCreate");

        db.execSQL(TbUserInfo.getCreateSql());
        db.execSQL("create table test (date text)");
        
       
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (newVersion > oldVersion)
        {
        	TbUserInfo.upgrade(db, oldVersion, newVersion);
        }
    }
    
    

}
