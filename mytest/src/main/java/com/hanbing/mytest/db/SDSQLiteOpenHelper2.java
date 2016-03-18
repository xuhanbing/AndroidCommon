/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��9�� 
 * Time : ����11:19:44
 */
package com.hanbing.mytest.db;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

/**
 * aaa.java 
 * @author hanbing 
 * @date 2014��6��9�� 
 * @time ����11:19:44
 */
public abstract class SDSQLiteOpenHelper2 { 
    private static final String TAG = SDSQLiteOpenHelper.class.getSimpleName(); 
    @SuppressWarnings("unused") 
    private final Context mContext; 
    private final String mName; 
    private final CursorFactory mFactory; 
    private final int mNewVersion; 
    private SQLiteDatabase mDatabase = null; 
    private String mDirectory = null;
    private boolean mIsInitializing = false; 
    
    private final static String DEFAULT_DIR = "/.db/";
    
    public SDSQLiteOpenHelper2(Context context, String name, CursorFactory factory, int version) 
    { 
        this(context, DEFAULT_DIR, name, factory, version);
    } 
    
    public SDSQLiteOpenHelper2(Context context, String dir, String name, CursorFactory factory, int version) 
    { 
        if (version < 1) 
            throw new IllegalArgumentException("Version must be >= 1, was " + version); 
        mContext = context; 
        mName = name; 
        mFactory = factory; 
        mNewVersion = version; 
        mDirectory = dir;
    } 
    
    public synchronized SQLiteDatabase getWritableDatabase() 
    { 
        if (mDatabase != null && mDatabase.isOpen() && !mDatabase.isReadOnly()) 
        { 
            return mDatabase; // The database is already open for business 
        } 
        
        if (mIsInitializing) 
        { 
            throw new IllegalStateException( "getWritableDatabase called recursively"); 
        } 
        
        // If we have a read-only database open, someone could be using it 
        // (though they shouldn't), which would cause a lock to be held on 
        // the file, and our attempts to open the database read-write would 
        // fail waiting for the file lock. To prevent that, we acquire the 
        // lock on the read-only database, which shuts out other users. 
        boolean success = false; 
        SQLiteDatabase db = null; 
        try { 
            mIsInitializing = true; 
            if (mName == null) 
            { 
                db = SQLiteDatabase.create(null);
                
            } else { 
                String path = getDatabasePath(mName).getPath(); 
                db = SQLiteDatabase.openOrCreateDatabase(path, mFactory); 
            } 
            
            int version = db.getVersion(); 
            
            if (version != mNewVersion) { 
                db.beginTransaction(); 
                try { 
                    if (version == 0) { 
                        onCreate(db); 
                        
                    } else { 
                        onUpgrade(db, version, mNewVersion); 
                        
                    } 
                    
                    db.setVersion(mNewVersion); 
                    db.setTransactionSuccessful(); 
                    
                } finally { 
                    db.endTransaction(); 
                } 
            } 
            
            onOpen(db); 
            success = true; 
            return db; 
            
        } finally {
            mIsInitializing = false; 
            if (success) { 
                if (mDatabase != null) 
                { 
                    try { 
                        mDatabase.close(); 
                        } catch (Exception e) { 
                    } 
                } 
                mDatabase = db; 
            
            } else { 
                    if (db != null)
                    {
                        db.close();
                    }
                }
            } 
        } 
    
    
    public synchronized SQLiteDatabase getReadableDatabase() { 
        
        // The database is already open for business 
        if (mDatabase != null && mDatabase.isOpen())
        {
            return mDatabase; 
        }
        
        if (mIsInitializing) 
        { 
            throw new IllegalStateException( "getReadableDatabase called recursively"); 
        } 
        
        try { 
            return getWritableDatabase(); 
        } catch (SQLiteException e) { 
            if (mName == null) 
                throw e; // Can't open a temp database read-only! 
            Log.e(TAG, "Couldn't open " + mName + " for writing (will try read-only):", e); 
        } 
        
        SQLiteDatabase db = null; 
        
        try { 
            mIsInitializing = true;
            String path = getDatabasePath(mName).getPath(); 
            
            db = SQLiteDatabase.openDatabase(path, mFactory, SQLiteDatabase.OPEN_READWRITE); 
            
            if (db.getVersion() != mNewVersion) 
            { 
                throw new SQLiteException( "Can't upgrade read-only database from version " + db.getVersion() + " to " + mNewVersion + ": " + path); 
            } 
            
            onOpen(db); 
            
            Log.w(TAG, "Opened " + mName + " in read-only mode"); 
            mDatabase = db; 
            return mDatabase; 
            
            } finally { 
                mIsInitializing = false; 
                if (db != null && db != mDatabase) 
                    db.close(); 
            } 
       
    } 
    
    
    public synchronized void close() { 
        
        if (mIsInitializing) 
            throw new IllegalStateException("Closed during initialization"); 
        
        if (mDatabase != null && mDatabase.isOpen()) { 
            
            mDatabase.close(); 
            mDatabase = null; 
        } 
        
    } 
    
    
    public File getDatabasePath(String name) 
    { 
        String EXTERN_PATH = null; 
        
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)==true) 
        { 
            EXTERN_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + mDirectory; 
            
            File f=new File(EXTERN_PATH); 
            
            if(!f.exists()) {
                f.mkdirs(); 
            } 
            
        } 
        return new File(EXTERN_PATH+ name); 
    }
    
    
    public abstract void onCreate(SQLiteDatabase db); 
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion); 
    
    public void onOpen(SQLiteDatabase db) { 
    } 
    
    
}
