package com.hanbing.demo;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by hanbing on 2016/9/26
 */
public class BaseFragment extends Fragment {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //遍历所有子fragment
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (null != fragments && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (null != fragment)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (null == getActivity())
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");

        Fragment root = getRootParentFragment();

        if (null == root)
            root = this;

       getActivity().startActivityFromFragment(root, intent, requestCode);
    }

    /**
     *     获取最上层的fragment，即直接在activity中的fragment
     *     这样在activity调用onActivityResult时能够找到正确的fragment
     */
    private Fragment getRootParentFragment() {

        Fragment parent = getParentFragment();

        while (null != parent) {
            Fragment fragment = parent.getParentFragment();
            if (null == fragment) {
                //上层已经没有fragment，表明parent是最底层的fragment
                break;
            }

            parent = fragment;
        }
        return parent;
    }
}
