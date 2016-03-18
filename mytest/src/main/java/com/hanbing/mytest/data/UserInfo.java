/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��4��3�� 
 * Time : ����4:10:36
 */
package com.hanbing.mytest.data;

import com.hanbing.mytest.db.DBCommon.TbUserInfo;

import android.content.ContentValues;

/**
 * UserInfo.java 
 * @author hanbing 
 * @date 2014��4��3�� 
 * @time ����4:10:36
 */
public class UserInfo {

    
    private int id;
    private String name;
    private int age;
    /**
     * 
     */
    public UserInfo() {
        // TODO Auto-generated constructor stub
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        
        values.put(TbUserInfo._ID, id);
        values.put(TbUserInfo.NAME, name);
        values.put(TbUserInfo.AGE, age);
        
        return  values;
    }

    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

}
