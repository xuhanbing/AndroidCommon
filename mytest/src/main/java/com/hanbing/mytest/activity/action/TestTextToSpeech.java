/**
 * 
 */
package com.hanbing.mytest.activity.action;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-9-24
 */
public class TestTextToSpeech extends BaseActivity implements OnInitListener{

    TextToSpeech mSpeech;
    EditText edit;
    @Override
    protected void onCreate(Bundle arg0) {
	// TODO Auto-generated method stub
	super.onCreate(arg0);

	checkTts();

	LinearLayout layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);

	edit = new EditText(this);
	final Button btn = new Button(this);
	btn.setText("speech");

	btn.setOnClickListener(new OnClickListener() {

	    @SuppressWarnings("deprecation")
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		mSpeech.speak(edit.getText().toString(),
			TextToSpeech.QUEUE_FLUSH, null);
	    }
	});

	layout.addView(edit);
	layout.addView(btn);

	setContentView(layout);

    }

    private final static int CHECK_CODE = 1;

    public void checkTts() {
	Intent checkIntent = new Intent();
	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);

	startActivityForResult(checkIntent, CHECK_CODE);

    }

    @SuppressLint("NewApi") protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == CHECK_CODE) {
	    if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
		// 成功创建一个TTS
//		String engine = "com.miui.voicerecognizer.xunfei";
		String engine = "iflytek.speechcloud";
		
		mSpeech = new TextToSpeech(this, this);
	    } else {
		// 否则安装一个
		Intent installIntent = new Intent();
		installIntent
			.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		startActivity(installIntent);
	    }
	}
    }

    @Override
    protected void onDestroy() {
	// TODO Auto-generated method stub

	if (null != mSpeech) {
	    mSpeech.stop();
	    mSpeech.shutdown();
	}
	super.onDestroy();
    }

    @SuppressLint("NewApi") @Override
    public void onInit(int status) {
	// TODO Auto-generated method stub
	if (status == TextToSpeech.SUCCESS) {
	    
//	    List<EngineInfo> engines = mSpeech.getEngines();
//	    int result = mSpeech.setLanguage(mSpeech.getDefaultLanguage());
//	    if (result == TextToSpeech.LANG_MISSING_DATA
//		    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//		Log.e("lanageTag", "not use");
//	    } else {
//		mSpeech.speak("i love you", TextToSpeech.QUEUE_FLUSH,
//			null);
//	    }
	}
    }
}
