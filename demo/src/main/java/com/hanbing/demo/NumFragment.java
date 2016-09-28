package com.hanbing.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hanbing on 2016/9/26
 */
public class NumFragment extends BaseFragment {
    public static NumFragment newInstance(int num) {

        Bundle args = new Bundle();
        args.putInt("num", num);
        NumFragment fragment = new NumFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Button button = new Button(getContext());

        if (null != getArguments()) {
            button.setText(""+getArguments().getInt("num"));
        } else {
            button.setText("" + Integer.MAX_VALUE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EmptyActivity.class);

                int requestCode = 5;
                startActivityForResult(intent, requestCode);

            }
        });

        return button;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", this + " onActivityResult requestCode = " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
