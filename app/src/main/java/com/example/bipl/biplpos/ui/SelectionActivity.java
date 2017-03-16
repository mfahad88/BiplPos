package com.example.bipl.biplpos.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.R;
import com.example.bipl.biplpos.dao.DbHelper;
import com.square.MagRead;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.bipl.biplpos.R.id.radio;

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

            Intent i = getIntent();

            if (i.getStringExtra("ReturnFinger") != null) {
                if (i.getStringExtra("ReturnFinger").equals("YES")) {
                    viewPager.setCurrentItem(1);
                    showpaymentDialog(this,"","","");

                } else {
                    viewPager.setCurrentItem(1);

                }
            }
            if (i.getStringExtra("ReturnPAN") != null && i.getStringExtra("ReturnAmount") != null) {
                try {
                    viewPager.setCurrentItem(1);
                    Context context = getApplicationContext();
                    String panNo = i.getStringExtra("ReturnPAN");
                    Toast.makeText(context, "PAN: " + panNo + " Amount: " + i.getStringExtra("ReturnAmount"), Toast.LENGTH_SHORT).show();
                    DbHelper dbHelper = new DbHelper(context);
                    showpaymentDialog(this, dbHelper.getDebitCard(panNo).get(0), dbHelper.getDebitCard(panNo).get(1),
                            i.getStringExtra("ReturnAmount"));
                }catch (Exception e){e.printStackTrace();}


            }
       
    }

    public void succesfulDialog(Context context){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.confirmation_dialog);
        TextView tvAmount=(TextView)dialog.findViewById(R.id.textViewMessage);
        TextView tvRef=(TextView)dialog.findViewById(R.id.textViewRef);
        dialog.setTitle("Payment Successful");
        tvAmount.setText(tvAmount.getText().toString().replace(":PAMOUNT","10,000"));
        tvRef.setText(tvRef.getText().toString().replace(":PREF","mr140013"));
        dialog.show();
    }
    public void showpaymentDialog(final Context context,String name,String panNo,String amount){
        final Dialog dialog=new Dialog(context);

        dialog.setContentView(R.layout.fragment_payment);
        EditText paymentAmount=(EditText)dialog.findViewById(R.id.editTextAmount);
        EditText cardName=(EditText)dialog.findViewById(R.id.editTextName);
        EditText cardNo=(EditText)dialog.findViewById(R.id.editTextCardno);
        EditText loyalityPoints=(EditText)dialog.findViewById(R.id.editTextLoyality);
        EditText worth=(EditText)dialog.findViewById(R.id.editTextWorth);
        EditText otp=(EditText)dialog.findViewById(R.id.editTextOTP);

            final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
            dialog.setTitle("Payment...");
            paymentAmount.setText(paymentAmount.getText()+amount);
            cardName.setText(name);
            cardNo.setText(panNo);
            loyalityPoints.setText("10");
            worth.setText("PKR: 100");

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radiopaymentType = (RadioButton) dialog.findViewById(checkedId);
                    Toast.makeText(dialog.getContext(), radiopaymentType.getText(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    succesfulDialog(context);
                }
            });

            dialog.show();

    }




    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private UpdatableFragment[] fragments;
        private String[] fragmentNames;


        public ViewPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);

            UpdatableFragment reportFragment = new ReportFragment();
            UpdatableFragment saleFragment = new SalesFragment(reportFragment);
            UpdatableFragment inventoryFragment = new InventoryFragment(saleFragment);


            fragments = new UpdatableFragment[] { inventoryFragment, saleFragment,
                    reportFragment};
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
