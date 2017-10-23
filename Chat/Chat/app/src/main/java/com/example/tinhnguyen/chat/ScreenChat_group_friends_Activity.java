package com.example.tinhnguyen.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TextView;

import static android.R.id.tabhost;

public class ScreenChat_group_friends_Activity extends AppCompatActivity implements  TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{
    TabHost mTabHost;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_chat_group_friends_);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager) ;
        initialiseTabHost();
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); // unselected
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected
        TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#000000"));

        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOnPageChangeListener(ScreenChat_group_friends_Activity.this);

    }

    private void initialiseTabHost() {
        mTabHost = (TabHost) findViewById(tabhost);
        mTabHost.setup();
        // TODO Put here your Tabs
        ScreenChat_group_friends_Activity.AddTab(ScreenChat_group_friends_Activity.this, this.mTabHost, this.mTabHost.newTabSpec("Chat Firends").setIndicator("Chat Friends").setContent(new Intent(this,Tabs_Friend.class)));
        ScreenChat_group_friends_Activity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Chat Group ").setIndicator("Chat Group").setContent(new Intent(this,Tabs_Group.class)));

        mTabHost.setOnTabChangedListener(this);
    }

    private static void AddTab(Activity activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int pos  = this.viewPager.getCurrentItem();
        this.mTabHost.setCurrentTab(pos);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int position  = this.mTabHost.getCurrentTab();
        this.viewPager.setCurrentItem(position);
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); // unselected
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#0000FF")); // selected
        TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#000000"));

    }
}
class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: Fragment t1 =new Tabs_Friend();
                return t1;
            case 1: Fragment t2 = new Tabs_Group();
                return t2;

        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2 ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

}

