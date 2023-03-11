package com.jamillabltd.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private Dialog dialog;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadLocalLandLang();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        //Objects.requireNonNull(getSupportActionBar()).hide();

        setTitle(R.string.app_name_translate);

        //save dark mood
        SharedPreferences sh = this.getSharedPreferences("night", 0);
        boolean booleanValue = sh.getBoolean("night_mode", false);
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //first time open language dialog
        dialog = new Dialog(this);

        //Dialog will be not show when app updated :)
        SharedPreferences prefs = this.getSharedPreferences("prefsLandLang", MODE_PRIVATE);
        if (prefs.getBoolean("isFirstRunLandLang", true)) {
            languageDialog();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstRunLandLang", false);
            editor.apply();
        }


        //Default Set Fragment Home
        bottomNavigationView = findViewById(R.id.bottomViewId);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.containerId, new HomeFragment());
        transaction1.commit();

        //Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottomViewId);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //int id = item.getItemId();
            switch (item.getItemId()) {
                case R.id.home_bottom_nav_id:
                    Objects.requireNonNull(getSupportActionBar()).show();
                    setTitle(R.string.app_name_translate);
                    transaction.replace(R.id.containerId, new HomeFragment());
                    break;
                case R.id.method_bottom_nav_id:
                    //show action bar
                    Objects.requireNonNull(getSupportActionBar()).show();
                    setTitle(R.string.necessaryFormula);
                    transaction.replace(R.id.containerId, new MethodFragment());
                    break;
                case R.id.howTo_bottom_nav_id:
                    Objects.requireNonNull(getSupportActionBar()).show();
                    setTitle(R.string.howto2);
                    transaction.replace(R.id.containerId, new HowToFragment());
                    break;
                case R.id.profile_bottom_nav_id:
                    Objects.requireNonNull(getSupportActionBar()).hide();
                    transaction.replace(R.id.containerId, new SettingFragment());
                    break;
            }
            transaction.commit();
            return true;
        });

    }

    //open time dialog
    private void languageDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_select_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button LanguageEnglish = dialog.findViewById(R.id.englishLanguageId);
        Button LanguageBengali = dialog.findViewById(R.id.bengaliLanguageId);

        LanguageEnglish.setOnClickListener(view -> {
            setLocalLandLang("en");
            finish();
            recreate();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            dialog.dismiss();
        });

        LanguageBengali.setOnClickListener(view -> {
            setLocalLandLang("bn");
            finish();
            recreate();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            dialog.dismiss();
        });
    }

    //language setting
    private void setLocalLandLang(String language) {

        Locale localeMS = new Locale(language);
        Locale.setDefault(localeMS);

        Configuration configuration = new Configuration();
        configuration.locale = localeMS;
        getResources().updateConfiguration(configuration, this.getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences = this.getSharedPreferences("SettingMS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_langMS", language);
        editor.apply();
    }

    private void loadLocalLandLang() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("SettingMS", MODE_PRIVATE);
        String language = sharedPreferences.getString("app_langMS", "0");
        setLocalLandLang(language);
    }

    //backPressed to
    @Override
    public void onBackPressed() {

        if (bottomNavigationView.getSelectedItemId() == R.id.home_bottom_nav_id) {
            super.onBackPressed();
            finish();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.home_bottom_nav_id);
        }

    }


}