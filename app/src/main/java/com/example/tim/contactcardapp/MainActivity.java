package com.example.tim.contactcardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Person> data = new ArrayList<>();
        data.add(new Person("Hoofd","Man","https://fi.intms.nl/fi_43a1c02c/images/news/carmignac-benoemt-nieuw-hoofd-aandelen-en-nieuwe-adjunct-algemeen-directeur_1_npzDSm.jpg", "hoopaaa@gapas.tapas"));
        data.add(new Person("Clown","Clown2","http://www.fmwconcepts.com/misc_tests/FFT_tests/clown/clown.jpg", "clown@clown.nl"));
        data.add(new Person("Brock","Pokemon","https://68.media.tumblr.com/avatar_a96e379d0fe1_128.png", "komdanmisty@gmail.com"));
        data.add(new Person("Bill","Cosby","http://i0.kym-cdn.com/photos/images/original/000/863/070/fbf.jpg", "hellothere@cometomebaby.co.uk"));
        data.add(new Person("Hoofd","Man","https://fi.intms.nl/fi_43a1c02c/images/news/carmignac-benoemt-nieuw-hoofd-aandelen-en-nieuwe-adjunct-algemeen-directeur_1_npzDSm.jpg", "hoopaaa@gapas.tapas"));
        data.add(new Person("Clown","Clown2","http://www.fmwconcepts.com/misc_tests/FFT_tests/clown/clown.jpg", "clown@clown.nl"));
        data.add(new Person("Brock","Pokemon","https://68.media.tumblr.com/avatar_a96e379d0fe1_128.png", "komdanmisty@gmail.com"));
        data.add(new Person("Bill","Cosby","http://i0.kym-cdn.com/photos/images/original/000/863/070/fbf.jpg", "hellothere@cometomebaby.co.uk"));
        data.add(new Person("Hoofd","Man","https://fi.intms.nl/fi_43a1c02c/images/news/carmignac-benoemt-nieuw-hoofd-aandelen-en-nieuwe-adjunct-algemeen-directeur_1_npzDSm.jpg", "hoopaaa@gapas.tapas"));
        data.add(new Person("Clown","Clown2","http://www.fmwconcepts.com/misc_tests/FFT_tests/clown/clown.jpg", "clown@clown.nl"));
        data.add(new Person("Brock","Pokemon","https://68.media.tumblr.com/avatar_a96e379d0fe1_128.png", "komdanmisty@gmail.com"));
        data.add(new Person("Bill","Cosby","http://i0.kym-cdn.com/photos/images/original/000/863/070/fbf.jpg", "hellothere@cometomebaby.co.uk"));
        data.add(new Person("Hoofd","Man","https://fi.intms.nl/fi_43a1c02c/images/news/carmignac-benoemt-nieuw-hoofd-aandelen-en-nieuwe-adjunct-algemeen-directeur_1_npzDSm.jpg", "hoopaaa@gapas.tapas"));
        data.add(new Person("Clown","Clown2","http://www.fmwconcepts.com/misc_tests/FFT_tests/clown/clown.jpg", "clown@clown.nl"));
        data.add(new Person("Brock","Pokemon","https://68.media.tumblr.com/avatar_a96e379d0fe1_128.png", "komdanmisty@gmail.com"));
        data.add(new Person("Bill","Cosby","http://i0.kym-cdn.com/photos/images/original/000/863/070/fbf.jpg", "hellothere@cometomebaby.co.uk"));

        recyclerView.setAdapter(new PersonAdapter(data));

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("People" + " (" + data.size() + ")");
    }
}
