package com.example.check.Header_Footer;

import android.os.Bundle;

import com.example.check.R;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager

    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        CustomAdapter.ViewPagerAdapter adapter = new CustomAdapter.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ListView_Fragment(), "List View");
        adapter.addFrag(new RecyclerView_Fragment(), "Recycler View");
        viewPager.setAdapter(adapter);
    }


}