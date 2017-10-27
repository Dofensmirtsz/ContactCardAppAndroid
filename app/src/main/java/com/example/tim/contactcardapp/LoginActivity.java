package com.example.tim.contactcardapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tim.contactcardapp.data.local.UsersDBHelper;
import com.example.tim.contactcardapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    String username;
    String password;

    EditText usernameEditText;
    EditText passwordEditText;
    CheckBox saveCredentials;
    Button login;
    Button register;
    TextView errorMessage;

    private UsersDBHelper usersDBHelper;

    private static final String PREFS_NAME = "LoginPreferences";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        usersDBHelper = new UsersDBHelper(getApplicationContext(), null);

        //Pre-made users.
        //usersDBHelper.addUser(new User("tim","tim"));
        //usersDBHelper.addUser(new User("hoi","123"));
        //usersDBHelper.addUser(new User("username","password"));

        //usersDBHelper.printUsers();


        boolean rememberCredentials = settings.getBoolean("REMEMBER_CREDENTIALS", false);

        if(rememberCredentials){
            autoLogin();
        } else{
            initialiseLoginScreen();
            login.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(checkValidLogin()){
                        storeSettings();
                        goToMainActivity();
                    }
                }
            });
            register.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(LoginActivity.super.getApplication(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void autoLogin(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        username = settings.getString("USERNAME", "");
        password = settings.getString("PASSWORD", "");
        goToMainActivity();
    }

    private void initialiseLoginScreen(){
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        saveCredentials = (CheckBox) findViewById(R.id.saveCredentials);
        login = (Button) findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        errorMessage = (TextView) findViewById(R.id.errorMessage);

        username = "";
        password = "";
    }

    private void storeSettings() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USERNAME", usernameEditText.getText().toString());
        editor.putString("PASSWORD", passwordEditText.getText().toString());
        editor.putBoolean("REMEMBER_CREDENTIALS", saveCredentials.isChecked());

        editor.apply();

        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }

    private boolean checkValidLogin(){
        boolean valid = true;

        List<User> userList = new ArrayList<User>();
        userList = usersDBHelper.getUserList();

        if(usernameEditText.getText().length() == 0 && passwordEditText.getText().length() == 0){
            errorMessage.setText("Please insert credentials");
            valid = false;
        }
        else if(passwordEditText.getText().length() == 0){
            errorMessage.setText("Please insert a password");
            valid = false;
        }
        else if(usernameEditText.getText().length() == 0){
            errorMessage.setText("Please insert a username");
        }
        else {
            username = usernameEditText.getText().toString();
            password = usersDBHelper.getDBValue("password","username", username);
            if(password.equals(passwordEditText.getText().toString())){
                return true;
            } else {
                errorMessage.setText("Invalid credentials");
                valid = false;
            }
        }

        return valid;
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}
