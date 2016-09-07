package com.hanbing.mytest.activity.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanbing.library.android.view.ExpandableTextView;
import com.hanbing.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestExpandableTextView extends AppCompatActivity {

    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.arrow)
    ImageView mArrow;
    @BindView(R.id.expandableTextView)
    ExpandableTextView mExpandableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expandable_text_view);
        ButterKnife.bind(this);


        mExpandableTextView.setOnExpandStateChangedListener(new ExpandableTextView.OnExpandStateChangedListener() {
            @Override
            public void onChanged(TextView textView, boolean isExpanded) {

                float fromDegrees = isExpanded ? 0: 180;
                float toDegrees = isExpanded ? 180 : 0;

                RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                animation.setDuration(500);

                if (null != mArrow.getAnimation())
                    mArrow.getAnimation().cancel();

                mArrow.startAnimation(animation);
            }
        });

    }

    @OnClick({R.id.text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text:
                break;
            case R.id.arrow:
                break;
        }
    }

    public void longText(View view) {
        mText.setText(R.string.hz);
    }

    public void shortText(View view) {
        mText.setText("hello 你好吗\n232\n2dfajdflajflajfjalfja\nljfal32\n2323212312111111111111111111111111");
    }
}
