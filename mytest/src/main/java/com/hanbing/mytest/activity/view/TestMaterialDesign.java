/**
 * 
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.activity.BaseActivity;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * @author hanbing
 * 
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class TestMaterialDesign extends BaseActivity implements OnClickListener{

	
	Scene scene1;
	Scene scene2;
	Scene scene3;
	TransitionManager transitionManager;
	/* (non-Javadoc)
	 * @see com.xhb.mytest.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_materialdesign);
		
		
		ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.layout_content);
		
		
		transitionManager = TransitionInflater.from(getApplicationContext())
				.inflateTransitionManager(R.transition.transition_mgr, sceneRoot);
				
		scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene1, this);
		scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene2, this);
		scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_scene3, this);
		
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.btn_scene1:
//			scene1.enter();
//			scene2.exit();
			
			transitionManager.transitionTo(scene1);
			break;
		case R.id.btn_scene2:
//			scene2.enter();
//			scene1.exit();
			transitionManager.transitionTo(scene2);
			break;
		case R.id.btn_scene3:
//			scene2.enter();
//			scene1.exit();
			transitionManager.transitionTo(scene3);
			break;
		}
	}
	
}
