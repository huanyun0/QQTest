package com.example.qqtest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.qqtest.R;
import com.example.qqtest.adapter.TabAdapter;
import com.example.qqtest.fragment.ContactFragment;
import com.example.qqtest.fragment.MessageFragment;
import com.example.qqtest.fragment.MyFragment;
import com.example.qqtest.utils.UserUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private final String TAG = "MainActivity";
    private final int MESSAGE = 0;
    private final int CONTACT = 1;
    private final int MY = 2;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserUtil userUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        userUtil = new UserUtil(this);
        tabLayout.addOnTabSelectedListener(this);
        final MessageFragment messageFragment = new MessageFragment();
        final ContactFragment contactFragment = new ContactFragment();
        final MyFragment myFragment = new MyFragment(this);
        List<Fragment> fragments = new ArrayList<Fragment>() {
            {
                add(messageFragment);
                add(contactFragment);
                add(myFragment);
            }
        };
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        try {
            Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.bell_active);
            Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.tab_home);
            Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.tab_my);
        } catch (NullPointerException e) {
            Log.e(TAG, "initialize tab item icon exception: " + e);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d(TAG, "onTabSelected tab: " + tab.getPosition());
        switch (tab.getPosition()) {
            case MESSAGE:
                tab.setIcon(R.drawable.bell_active);
                break;
            case CONTACT:
                tab.setIcon(R.drawable.tab_home_active);
                break;
            case MY:
                tab.setIcon(R.drawable.tab_my_active);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.d(TAG, "onTabUnselected tab: " + tab.getPosition());
        switch (tab.getPosition()) {
            case MESSAGE:
                tab.setIcon(R.drawable.bell);
                break;
            case CONTACT:
                tab.setIcon(R.drawable.tab_home);
                break;
            case MY:
                tab.setIcon(R.drawable.tab_my);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.d(TAG, "onTabReselected tab: " + tab.getPosition());
    }

}
