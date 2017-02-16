package com.example.bipl.biplpos.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.bipl.biplpos.R;

/**
 * Created by fahad on 2/9/2017.
 */

public class SelectionActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        /*adapter.addFragment(new InventoryFragment(), "Inventory");
        adapter.addFragment(new SalesFragment(), "Sales");
        adapter.addFragment(new ReportFragment(), "Report");*/
        viewPager.setAdapter(adapter);
    }


   /* class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private UpdatableFragment[] fragments;
        private String[] fragmentNames;


        public ViewPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);

            UpdatableFragment reportFragment = new ReportFragment();
            UpdatableFragment saleFragment = new SalesFragment(reportFragment);
            UpdatableFragment inventoryFragment = new InventoryFragment(saleFragment);

            fragments = new UpdatableFragment[] { inventoryFragment, saleFragment,
                    reportFragment };
            fragmentNames = new String[] { "Inventory", "Sales", "Report" };

        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return fragmentNames[i];
        }

        /**
         * Update
         * @param index
         */
        public void update(int index) {
            fragments[index].update();
        }

    }

}
