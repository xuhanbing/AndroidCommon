/**
 *
 */
package com.hanbing.library.android.adapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hanbing.library.android.util.ReflectUtils;

/**
 * @author hanbing
 */
public class BaseFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    List<? extends Fragment> mFragments;
    String[] mTitles;

    /**
     * @param fm
     */
    public BaseFragmentPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments, String[] titles) {
        super(fm);
        // TODO Auto-generated constructor stub

        if (null != fragments && null != titles && fragments.size() != titles.length) {
            throw new IllegalArgumentException("fragments.size() must equal titles.length");
        }

        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return null == mFragments ? null : mFragments.get(arg0);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return null == mFragments ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO Auto-generated method stub
        return null == mTitles ? null : mTitles[position];
    }

    public interface FragmentCreator {
        public Fragment onCreate(int index);
    }

    /**
     * 恢复fragment
     *
     * @param fragmentManager
     * @param containerId     viewpager的id
     * @param size            个数
     * @param creator         如果未找到，创建
     * @return
     */
    public static List<Fragment> restoreFragments(FragmentManager fragmentManager, int containerId, int size, FragmentCreator creator) {

        List<Fragment> fragments = new ArrayList<>();

        Method method = ReflectUtils.getMethod(FragmentPagerAdapter.class, "makeFragmentName", int.class, long.class);
        for (int i = 0; i < size; i++) {
            String tag = ReflectUtils.invokeMethod(null, method, containerId, i);
            Fragment fragment = fragmentManager.findFragmentByTag(tag);

            if (null == fragment && null != creator) {
                fragment = creator.onCreate(i);
            }

            fragments.add(fragment);
        }

        return fragments;
    }

    /**
     * 恢复fragment
     *
     * @param fragmentManager
     * @param containerId     ViewPager的id
     * @param fragments       默认的fragments
     * @return
     */
    public static List<Fragment> restoreFragments(FragmentManager fragmentManager, int containerId, List<Fragment> fragments) {

        if (null == fragments || 0 == fragments.size())
            return fragments;

        int size = fragments.size();

        Method method = ReflectUtils.getMethod(FragmentPagerAdapter.class, "makeFragmentName", int.class, long.class);
        for (int i = 0; i < size; i++) {
            String tag = ReflectUtils.invokeMethod(null, method, containerId, i);
            Fragment fragment = fragmentManager.findFragmentByTag(tag);

            if (null != fragment) {
                fragments.set(i, fragment);
            }

        }

        return fragments;
    }
}
