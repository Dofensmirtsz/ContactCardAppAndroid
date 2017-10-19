package com.example.tim.contactcardapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "LoginPreferences";
    String username;
    String password;

    EditText usernameEditText;
    EditText passwordEditText;
    CheckBox saveCredentials;
    Button login;
    TextView errorMessage;

    Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        saveCredentials = (CheckBox) findViewById(R.id.saveCredentials);
        login = (Button) findViewById(R.id.login);
        errorMessage = (TextView) findViewById(R.id.errorMessage);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        boolean rememberCredentials = settings.getBoolean("REMEMBER_CREDENTIALS", false);

        if(rememberCredentials){
            username = settings.getString("USERNAME", "");
            password = settings.getString("PASSWORD", "");
            usernameEditText.setText(username);
            passwordEditText.setText(password);
            saveCredentials.setChecked(rememberCredentials);
        } else{
            username = "";
            password = "";
        }

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkValidLogin()){
                    storeSettings();
                    goToMainActivity();
                }
            }
        });
    }

    private void storeSettings() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USERNAME", usernameEditText.getText().toString());
        editor.putString("PASSWORD", passwordEditText.getText().toString());
        editor.putBoolean("REMEMBER_CREDENTIALS", saveCredentials.isChecked());

        editor.apply();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", usernameEditText.getText().toString());
        startActivity(intent);
        finish();
    }

    private boolean checkValidLogin(){
        boolean valid = true;



        if(usernameEditText.getText().length() == 0 && passwordEditText.getText().length() == 0){
            errorMessage.setText("Please insert credentials");
            valid = false;
        }
        else if(usernameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0){
            errorMessage.setText("Invalid username or password.");
            valid = false;
        }

//        errorMessage.setText("Username '" + usernameEditText.getText() + "'");

        return valid;
    }
}
