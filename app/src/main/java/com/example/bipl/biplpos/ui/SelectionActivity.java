package com.example.bipl.biplpos.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

        viewPager.setAdapter(adapter);
        if(getIntent().getBooleanExtra("ReturnFinger",false)){
            viewPager.setCurrentItem(1);
        }
    }




    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private UpdatableFragment[] fragments;
        private String[] fragmentNames;


        public ViewPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);

            UpdatableFragment reportFragment = new ReportFragment();
            UpdatableFragment saleFragment = new SalesFragment(reportFragment);
            UpdatableFragment inventoryFragment = new InventoryFragment(saleFragment);
            UpdatableFragment paymentFragment = new PaymentFragment();

            fragments = new UpdatableFragment[] { inventoryFragment, saleFragment,
                    reportFragment,paymentFragment };
            fragmentNames = new String[] { "Inventory", "Sales", "Report","Payment Fragment" };

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
