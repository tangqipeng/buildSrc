package com.aograph.androidtest;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aograph.androidtest.gradualradiobar.GradualRadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangqipeng
 * 2020-01-15
 * email: tangqipeng@aograph.com
 */
public class TabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
//        requestPermissions();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        GradualRadioGroup gradualRadioGroup = (GradualRadioGroup) findViewById(R.id.radiobar);
//        List<Fragment> list = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            DemoFragment fragment = new DemoFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("type",i);
//            fragment.setArguments(bundle);
//            list.add(fragment);
//        }
        List<Fragment> list = new ArrayList<>();
        DemoFragment demoFragment = new DemoFragment();
        list.add(demoFragment);
        Demo2Fragment demo2Fragment = new Demo2Fragment();
        list.add(demo2Fragment);
        Demo3Fragment demo3Fragment = new Demo3Fragment();
        list.add(demo3Fragment);
        Demo4Fragment demo4Fragment = new Demo4Fragment();
        list.add(demo4Fragment);

        viewPager.setAdapter(new DemoPagerAdapter(getSupportFragmentManager(), list));
        gradualRadioGroup.setViewPager(viewPager);
    }

    static class DemoPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mData;

        public DemoPagerAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }

    /**
     * 申请获取报文数据需要的相关权限
     */
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_SECURE_SETTINGS,
                    Manifest.permission.READ_CALL_LOG}, 1);
        }
    }

}
