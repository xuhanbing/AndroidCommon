/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��26�� 
 * Time : ����11:17:33
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.view.TurnPlate;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

/**
 * TestDraw.java 
 * @author hanbing 
 * @date 2014��6��26�� 
 * @time ����11:17:33
 */
public class TestDraw extends BaseActivity {

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        TurnPlate tp = new TurnPlate(this);
        tp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(tp);
    }

}
