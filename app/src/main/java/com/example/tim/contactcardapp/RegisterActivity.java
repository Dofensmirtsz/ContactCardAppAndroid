package com.example.tim.contactcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tim.contactcardapp.data.local.UsersDBHelper;
import com.example.tim.contactcardapp.data.model.User;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    String username;
    String password;

    EditText usernameEditText;
    EditText passwordEditText;
    Button register;
    TextView errorMessageTextView;

    private UsersDBHelper usersDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usersDBHelper = new UsersDBHelper(getApplicationContext(), null);

        usernameEditText = (EditText) findViewById(R.id.usernameRegister);
        passwordEditText = (EditText) findViewById(R.id.passwordRegister);
        register = (Button)findViewById(R.id.registerRegister);
        errorMessageTextView = (TextView) findViewById(R.id.errorMessageRegister);

        username = "";
        password = "";


        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkValidRegistration()){
                    usersDBHelper.addUser(new User(username,password));
                    usersDBHelper.printUsers();
                    Intent intent = new Intent(RegisterActivity.super.getApplication(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private boolean checkValidRegistration(){
        boolean valid = true;

        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if(username.length() < 3 || password.length() < 3){
            valid = false;
            errorMessageTextView.setText("Username and password must both be at least 3 characters");
        }

        return valid;
    }
}
