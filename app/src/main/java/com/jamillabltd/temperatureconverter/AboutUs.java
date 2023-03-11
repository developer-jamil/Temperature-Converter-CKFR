package com.jamillabltd.temperatureconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AboutUs extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        this.setTitle(R.string.about_us);

        //back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView profileImageView = findViewById(R.id.profileImageViewId);
        Picasso.with(this)
                .load("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgp_kNU3-IutJZH_6QxkbclLKhS9NDQtijZGtj-idTeAgMNEO8EkNVsR3Be2oFJcO02iKhaQljLuzIg8ko_swtfGWSx1RX9EFxMCY1YWFC6UY1PgMKYE0iar2HKn0HRy-PIL9PzBe7-Rizg4ZxwIpW_fOM0-h9NHt2cDhuTLi89BPDosLccSY3h4qXD/s320/lrjamil%20jamil%20lab%20ltd.png")
                .into(profileImageView);


    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}