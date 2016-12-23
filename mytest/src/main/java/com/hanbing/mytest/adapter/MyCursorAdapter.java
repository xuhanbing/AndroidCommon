/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��4��3�� 
 * Time : ����3:49:23
 */
package com.hanbing.mytest.adapter;

import com.hanbing.mytest.db.DBCommon.TbUserInfo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * MyCursorAdapter.java 
 * @author hanbing 
 * @date 2014��4��3�� 
 * @time ����3:49:23
 */
public class MyCursorAdapter extends CursorAdapter {

    
    private float downX;  //����ʱ���ȡ��x����
    private float upX;   //��ָ�뿪ʱ���x����
    /**
     * @param context
     * @param c
     * @param autoRequery
     */
    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param c
     * @param flags
     */
    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO Auto-generated method stub
        
        ViewHolder holder = (ViewHolder) view.getTag();
        
        holder.name.setText(cursor.getString(cursor.getColumnIndex(TbUserInfo.NAME)));
        holder.age.setText(cursor.getString(cursor.getColumnIndex(TbUserInfo.AGE)));
        
        String infoText = "null";
        int index = -1;
        if ((index = cursor.getColumnIndex(TbUserInfo.INFO)) > 0)
        {
            infoText = cursor.getString(index);
        }
        holder.info.setText(infoText);
        
        holder.age.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
        
        view.setOnTouchListener(new OnTouchListener() {  //Ϊÿ��item����setOnTouchListener�¼�

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ViewHolder holder = (ViewHolder) v.getTag();  //��ȡ����ʱ����Ӧ��ViewHolder���Ա��ȡbutton��ť
                System.out.println("ontouch  " + event.getAction());
                switch (event.getAction()) {
                    
                case MotionEvent.ACTION_DOWN:  //��ָ����
                    downX = event.getX(); //��ȡ��ָx����
                    break;
                case MotionEvent.ACTION_UP:  //��ָ�뿪
                    upX = event.getX(); //��ȡx����ֵ
                    break;
                }
                
//                if (holder.button != null) {  
                    if (Math.abs(downX - upX) > 35) {  //2������ľ���ֵ�������35������Ϊ�����һ���
                        System.out.println("delete");
                        return true; //��ֹ�¼�
                    }
                    return false;  //�ͷ��¼���ʹonitemClick����ִ��
//                }
//                return false;   
            }

        });
    }

    /* (non-Javadoc)
     * @see android.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // TODO Auto-generated method stub
        
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_simple, null, false);
        
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView age = (TextView) view.findViewById(R.id.tv_age);
        TextView info = (TextView) view.findViewById(R.id.tv_info);
        
        ViewHolder holder = new ViewHolder();
        holder.name = name;
        holder.age = age;
        holder.info = info;
        
                
        view.setTag(holder);
        
        return view;
    }
    
    class ViewHolder
    {
        TextView name;
        TextView age;
        TextView info;
    }

}
