/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��7��8�� 
 * Time : ����10:20:51
 */
package com.hanbing.mytest.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.mytest.fragment.BaseFragment;
import com.hanbing.mytest.fragment.NearGridFragment;
import com.hanbing.mytest.fragment.NearListFragment;
import com.hanbing.mytest.fragment.NumFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * TestFragment.java 
 * @author hanbing 
 * @date 2014��7��8�� 
 * @time ����10:20:51
 */
public class TestFragment extends FragmentActivity {
    
    FragmentManager fm;

    ViewPager viewPager;
    class  MyPagerAdapter extends FragmentStatePagerAdapter
    {

	/**
	 * @param fm
	 */
	public MyPagerAdapter(FragmentManager fm) {
	    super(fm);
	    // TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
	    // TODO Auto-generated method stub
	    return fragments.get(arg0);
	}

	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return count;
	}
	
    }
    
    
    List<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_numfragment);
//        fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        if (null == savedInstanceState)
//        {
//            
//            Fragment fragment = new NumFragment();
//            ft.add(R.id.simple_fragment, fragment);
//            
//            ft.commit();
//        }
//        
//        fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {
//            
//            @Override
//            public void onBackStackChanged() {
//                // TODO Auto-generated method stub
//                System.out.println("count=" + fm.getBackStackEntryCount());
//            }
//        });
        
        for (int i = 0; i < count;i++)
        {
            fragments.add(NumFragment.newInstance(i));
        }
        
        viewPager = new ViewPager(this);
        viewPager.setBackgroundColor(Color.YELLOW);
        viewPager.setId(1);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));



        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                int item = viewPager.getCurrentItem();
                LogUtils.e(page.getTag() + ", position = " + position + ", item = " + item);


                int width = page.getMeasuredWidth();
                if (position <= -1.0) {


                } else if (position >= 1.0) {
                    page.setTranslationX(-width);
                } else if (position > -1.0 && position < 0) {


                } else if (position >= 0 && position < 1) {


                    page.setTranslationX((int) (-width * position));
                }


            }
        });


        setContentView(viewPager);

        viewPager.setCurrentItem(0);

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = viewPager.getCurrentItem() + 1;

                viewPager.setCurrentItem(item, true);
            }
        });




        
    }

    int count = 10;
    public void add(View view)
    {
        count++;
        FragmentTransaction ft = fm.beginTransaction();
        
        Fragment fragment = NumFragment.newInstance(count);
        
        ft.replace(R.id.simple_fragment, fragment);
        ft.addToBackStack("" + count);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        
        
    }
    
    BaseFragment baseFragment = null;
    List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    
    private void initFragment()
    {
    	BaseFragment f1 = new NearListFragment();
    	f1.setData(null);
    	
    	BaseFragment f2 = new NearGridFragment();
    	f2.setData(null);
    	
    	fragmentList.add(f1);
    	fragmentList.add(f2);
    	
    	f1.getView();
    	
    	
    	
    	android.app.FragmentManager fm = getFragmentManager();
    	android.app.FragmentTransaction ft = fm.beginTransaction();
    	
    	
    	ft.add(R.id.simple_fragment, f1, "list");
    	ft.add(f2, "grid");
    	
    	
    	ft.commit();
    	
    	baseFragment = f1;
    }
    
    private void changeView(boolean isList)
    {
    	android.app.Fragment f = null;
    	if (isList)
    	{
    		f = getFragmentManager().findFragmentByTag("list");
    	}
    	else
    	{
    		f = getFragmentManager().findFragmentByTag("grid");
    	}
    	
    	if (f != baseFragment)
    	{
    		android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        	
        	ft.replace(R.id.simple_fragment, f);
        	
        	ft.commit();
        	
        	baseFragment = (BaseFragment) f;
    	}
    	
    	
    }
    
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("num", count);
    }
    
    public void remove(View view)
    {
        fm.popBackStack();
    }
    
    int second = 1;
    public void goFirst(View view)
    {
//        fm.popBackStack("" + (second + 1), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        second = count;
        
        String path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.jpg";
        String html = "http://www.baidu.com";
        String text = "I have successfully share my message through my app (������city���˹�)";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent=new Intent(Intent.ACTION_SEND); 
//        intent.setType("text/plain"); 
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share"); 
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        startActivity(Intent.createChooser(intent, getTitle())); 
    }

}
