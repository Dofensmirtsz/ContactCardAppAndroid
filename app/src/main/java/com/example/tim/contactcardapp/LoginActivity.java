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

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    private boolean checkValidLogin(){
        boolean valid = true;

        usersDBHelper.printUsers();

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
            for(User u : userList){
                Log.i("Login","Inserted username: " + usernameEditText.getText().toString() + "\tComparing username: " + u.getUsername());
                Log.i("Login","Inserted password: " + passwordEditText.getText().toString() + "\tComparing password: " + u.getPassword());

                if(usernameEditText.getText().toString().equals(u.getUsername()) && passwordEditText.getText().toString().equals(u.getPassword())){
                    return true;
                } else{
                    errorMessage.setText("Invalid credentials");
                    valid = false;
                }
            }
        }

//        errorMessage.setText("Username '" + usernameEditText.getText() + "'");

        return valid;
    }
}
