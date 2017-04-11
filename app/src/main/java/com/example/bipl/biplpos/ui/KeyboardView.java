package com.example.bipl.biplpos.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.R;

public class KeyboardView extends Dialog implements View.OnClickListener {

    private EditText mPasswordField;
    private ImageView i1, i2, i3, i4;
    Context context;
    public KeyboardView(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_pinpad);
        initViews();
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1){
                    i1.setImageResource(R.drawable.circle2);
                }if(s.length()==2){
                    i2.setImageResource(R.drawable.circle2);
                }if(s.length()==3){
                    i3.setImageResource(R.drawable.circle2);
                }if(s.length()==4){
                    i4.setImageResource(R.drawable.circle2);
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void initViews() {
        i1 = (ImageView) findViewById(R.id.imageview_circle1);
        i2 = (ImageView) findViewById(R.id.imageview_circle2);
        i3 = (ImageView) findViewById(R.id.imageview_circle3);
        i4 = (ImageView) findViewById(R.id.imageview_circle4);
        mPasswordField = $(R.id.password_field);
        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_clear).setOnClickListener(this);
        $(R.id.t9_key_backspace).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            if(mPasswordField.getText().length()<4) {
                mPasswordField.append(((TextView) v).getText());
            }
            return;
        }
        switch (v.getId()) {
            case R.id.t9_key_clear: { // handle clear button
                mPasswordField.setText(null);
                i1.setImageResource(R.drawable.circle);
                i2.setImageResource(R.drawable.circle);
                i3.setImageResource(R.drawable.circle);
                i4.setImageResource(R.drawable.circle);
            }
            break;
            case R.id.t9_key_backspace: { // handle backspace button
                // delete one character
                Editable editable = mPasswordField.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                    if(charCount==4){
                        i4.setImageResource(R.drawable.circle);
                    }if(charCount==3){
                        i3.setImageResource(R.drawable.circle);
                    }if(charCount==2){
                        i2.setImageResource(R.drawable.circle);
                    }if(charCount==1){
                        i1.setImageResource(R.drawable.circle);
                    }
                }
            }
            break;
        }
    }

    public String getInputText() {
        return mPasswordField.getText().toString();
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }
}