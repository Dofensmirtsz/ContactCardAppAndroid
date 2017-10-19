package com.example.tim.contactcardapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by tim on 5-9-2017.
 */

public class ProfilePageActivity extends AppCompatActivity {

    private final static String PERSON = "person";

    public static Intent getStartIntent(Context context, Person person) {
        Intent intent = new Intent(context, ProfilePageActivity.class);
        intent.putExtra(PERSON, person);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_profile);

        Person person = getIntent().getParcelableExtra(PERSON);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(person.firstName + " " + person.lastName);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView firstName = (TextView) findViewById(R.id.profile_page_first_name);
        TextView lastName = (TextView) findViewById(R.id.profile_page_last_name);
        TextView email = (TextView) findViewById(R.id.profile_page_email);
        ImageView profileImage = (ImageView) findViewById(R.id.profile_page_picture);

        if (person != null) {
            firstName.setText(person.firstName);
            lastName.setText(person.lastName);
            email.setText(person.email);
            Glide.with(this)
                    .load(person.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
