package com.example.bipl.biplpos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.bipl.biplpos.R.string.password;
import static com.example.bipl.biplpos.R.string.username;

public class MainActivity extends AppCompatActivity {
    private EditText user,pass;
    private Button btn_login;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        user=(EditText)findViewById(R.id.editTextUser);
        pass=(EditText)findViewById(R.id.editTextPass);
        btn_login=(Button)findViewById(R.id.buttonLogin);
        dialog=new ProgressDialog(MainActivity.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new loginTask().execute();
            }
        });
    }

    private class loginTask extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {


            String username= user.getText().toString();
            String password= pass.getText().toString();
            boolean status=false;

            if(username.equals("admin") && password.equals("123")){
                status=true;
            }else{
                status=false;
            }

            return status;
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
