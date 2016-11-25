package com.domain.securitysharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        //常规方式保存用户名密码
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInCommonPreference();
            }
        });

        //加密方式保存用户名密码
        Button mEmailSignInButtonSecurity = (Button) findViewById(R.id.email_sign_in_button_secure);
        mEmailSignInButtonSecurity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInSecurityPreference();
            }
        });
    }

    /**
     * 以常规的SharedPreference保存数据
     */
    private void saveInCommonPreference(){
        SharedPreferences sharedPreferences = getSharedPreferences("common_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", mEmailView.getText().toString());
        editor.putString("password", mPasswordView.getText().toString());
        editor.apply();
    }

    /**
     * 以加密的SharedPreference保存数据
     */
    private void saveInSecurityPreference(){
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), "security_prefs", Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.putString("username", mEmailView.getText().toString());
        securityEditor.putString("password", mPasswordView.getText().toString());
        securityEditor.apply();
    }
}

