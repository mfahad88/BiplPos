package com.example.bipl.biplpos.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bipl.biplpos.R;
import com.example.bipl.biplpos.dao.ws.PUVDebitCardStatusPortBinding;
import com.example.bipl.biplpos.dao.ws.PUVwsParam;
import com.example.bipl.biplpos.data.DebitCardBean;
import java.util.ArrayList;
import java.util.List;


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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
       
            adapter = new ViewPagerAdapter(getSupportFragmentManager());

            viewPager.setAdapter(adapter);

            Intent i = getIntent();

            if (i.getStringExtra("ReturnFinger") != null) {
                if (i.getStringExtra("ReturnFinger").equals("YES")) {
                    viewPager.setCurrentItem(1);
                    showpaymentDialog(this,"Ahmed Abbas","4628830000144406","10,000");

                } else {
                    viewPager.setCurrentItem(1);

                }
            }
            if (i.getStringExtra("ReturnPAN") != null && i.getStringExtra("ReturnAmount") != null) {
                    viewPager.setCurrentItem(1);

                    String panNo = (String)i.getStringExtra("ReturnPAN");
                    try {
                       /* Intent intent=new Intent(getApplicationContext(),KeyboardView.class);
                        Log.e("Intent>>>>>",String.valueOf(intent));
                        startActivity(intent);*/
                        Dialog dialog=new KeyboardView(this);
                        dialog.setTitle("PinPad");
                        dialog.show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //pinpadDialog(this);
                   //DbHelper dbHelper = new DbHelper(this);
                 //   new HttpAsyncTask().execute(panNo,(String)i.getStringExtra("ReturnAmount"));

            }
       
    }


    public void succesfulDialog(Context context,String amount){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.confirmation_dialog);
        TextView tvAmount=(TextView)dialog.findViewById(R.id.textViewMessage);
        TextView tvRef=(TextView)dialog.findViewById(R.id.textViewRef);
        dialog.setTitle("Payment Successful");
        tvAmount.setText(tvAmount.getText().toString().replace(":PAMOUNT","10,000"));
        tvRef.setText(tvRef.getText().toString().replace(":PREF","mr140013"));
        dialog.show();
    }

    public void showpaymentDialog(final Context context, String name, String panNo, final String amount){

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
                    succesfulDialog(context,amount);
                }
            });

            dialog.show();
    }
        //  PinPad
        public void pinpadDialog(final Context context){

            final Dialog dialog=new KeyboardView(context);

        /*final EditText pinCode=(EditText)dialog.findViewById(R.id.editText2);
        Button btn_ok=(Button)dialog.findViewById(R.id.button4);
        Button btn_cancel=(Button)dialog.findViewById(R.id.button5);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(context, String.valueOf(pinCode.getText()), Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
            dialog.show();
        }

    class HttpAsyncTask extends AsyncTask<String,PUVDebitCardStatusPortBinding,List<DebitCardBean>> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(getApplicationContext());
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected List<DebitCardBean> doInBackground(String... params) {
            PUVDebitCardStatusPortBinding binding=new PUVDebitCardStatusPortBinding();
            PUVwsParam param=new PUVwsParam();
            PUVwsParam param1=new PUVwsParam();
            List<DebitCardBean> list=new ArrayList<>();
            try {
                DebitCardBean bean=new DebitCardBean();
                param.username = "admin";
                param.password = "admin";
                param.panNo = params[0];
                param.STAN = "662453";
                param.requestCode = "003";
                param1.username=param.username;
                param1.password=param.password;
                param1.panNo=param.panNo;
                param1.STAN=param.STAN;
                param1.requestCode="002";

                bean.setIbUser(binding.getDebitCardStatus(param).statusDescription);
                bean.setPanNo(binding.getDebitCardStatus(param).panNo);
                bean.setSTAN(binding.getDebitCardStatus(param).STAN);
                bean.setResponseCode(binding.getDebitCardStatus(param).responseCode);
                bean.setResponseDescription(binding.getDebitCardStatus(param).responseDescription);
                bean.setStatusCode(binding.getDebitCardStatus(param).statusCode);
                bean.setStatusDescription(binding.getDebitCardStatus(param1).statusDescription);
                bean.setExpiryDate(binding.getDebitCardStatus(param1).expiryDate);
                bean.setAmount(params[1]);
                list.add(bean);
            }catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DebitCardBean> beanList) {
            super.onPostExecute(beanList);
            if(beanList.get(0)!=null) {
                dialog.dismiss();
                for (int i = 0; i < beanList.size(); i++) {
                    showpaymentDialog(SelectionActivity.this,beanList.get(i).getIbUser(),beanList.get(i).getPanNo(),beanList.get(i).getAmount());

                }
            }
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
