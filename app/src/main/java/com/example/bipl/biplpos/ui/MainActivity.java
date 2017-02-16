package com.example.bipl.biplpos.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bipl.biplpos.R;
import com.example.bipl.biplpos.boc.MainActivityBOC;

public class MainActivity extends AppCompatActivity {
    private EditText user,pass;
    private Button btn_login;
    private ProgressDialog dialog;
    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiManager mng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(!wifi.isConnected()){
            mng.setWifiEnabled(true);
        }
        user=(EditText)findViewById(R.id.editTextUser);
        pass=(EditText)findViewById(R.id.editTextPass);
        btn_login=(Button)findViewById(R.id.buttonLogin);
        dialog=new ProgressDialog(MainActivity.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=user.getText().toString();
                password=pass.getText().toString();
                new loginTask().execute();
            }
        });
    }

    private class loginTask extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            MainActivityBOC mainActivityBOC=new MainActivityBOC(username,password);
            return mainActivityBOC.logIn();
        }

           @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(MainActivity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,SelectionActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(MainActivity.this, "Login failed!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
