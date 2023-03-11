package com.jamillabltd.temperatureconverter;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class SettingFragment extends Fragment implements View.OnClickListener {
    SwitchCompat switchCompat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loadLocalLandLang();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout language = view.findViewById(R.id.languageId);
        language.setOnClickListener(this);

        LinearLayout aboutUs = view.findViewById(R.id.aboutUsId);
        aboutUs.setOnClickListener(this);

        LinearLayout rateApp = view.findViewById(R.id.rateAppId);
        rateApp.setOnClickListener(this);

        LinearLayout moreApp = view.findViewById(R.id.moreAppId);
        moreApp.setOnClickListener(this);

        LinearLayout privacyPolicy = view.findViewById(R.id.privacyId);
        privacyPolicy.setOnClickListener(this);

        LinearLayout exitApp = view.findViewById(R.id.exitId);
        exitApp.setOnClickListener(this);

        switchCompat = view.findViewById(R.id.switchCompatId);

        /*Night Mode*/
        SharedPreferences sh = requireActivity().getSharedPreferences("night", 0);
        boolean booleanValue = sh.getBoolean("night_mode", false);
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchCompat.setChecked(true);
                SharedPreferences.Editor editor = sh.edit();
                editor.putBoolean("night_mode", true);
                requireActivity().recreate();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                editor.apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchCompat.setChecked(false);
                SharedPreferences.Editor editor = sh.edit();
                editor.putBoolean("night_mode", false);
                editor.apply();
                requireActivity().recreate();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        /*End Night Mode*/

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.languageId) {
            changeLanguage();
        }

        //About us
        if (view.getId() == R.id.aboutUsId) {
            startActivity(new Intent(getActivity(), AboutUs.class));
        }
        //rate app
        if (view.getId() == R.id.rateAppId) {
            String url = "https://play.google.com/store/apps/details?id=com.jamillabltd.temperatureconverter";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        //more app
        if (view.getId() == R.id.moreAppId) {
            String url = "https://play.google.com/store/apps/developer?id=Jamil+Lab+Ltd";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        //Privacy & Policy
        if (view.getId() == R.id.privacyId) {
            String url = "https://temperatureconverterjamillabltd.blogspot.com/2022/08/privacy-policy-temperature-converter.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        //exit
        if (view.getId() == R.id.exitId) {
            customExitDialog();
        }


    }

    //language setting
    private void changeLanguage() {
        final String[] languages = {"English", "عربى", "বাংলা", "हिन्दी", "اردو"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.language_setting);
        mBuilder.setSingleChoiceItems(languages, -1, (dialogInterface, i) -> {

            if (i == 0) {
                setLocalLandLang("");
                requireActivity().finish();
                requireActivity().recreate();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            } else if (i == 1) {
                setLocalLandLang("ar");
                requireActivity().finish();
                requireActivity().recreate();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            } else if (i == 2) {
                setLocalLandLang("bn");
                requireActivity().finish();
                requireActivity().recreate();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            } else if (i == 3) {
                setLocalLandLang("hi");
                requireActivity().finish();
                requireActivity().recreate();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            } else if (i == 4) {
                setLocalLandLang("ur");
                requireActivity().finish();
                requireActivity().recreate();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            }

        });
        mBuilder.create();
        mBuilder.show();
    }
    //language setting
    private void setLocalLandLang(String language) {

        Locale localeMS = new Locale(language);
        Locale.setDefault(localeMS);

        Configuration configuration = new Configuration();
        configuration.locale = localeMS;
        getResources().updateConfiguration(configuration, requireActivity().getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("SettingMS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_langMS", language);
        editor.apply();
    }

    private void loadLocalLandLang() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("SettingMS", MODE_PRIVATE);
        String language = sharedPreferences.getString("app_langMS", "0");
        setLocalLandLang(language);
    }


    //custom exit dialog
    private void customExitDialog() {
        // creating custom dialog
        final Dialog dialog = new Dialog(getActivity());

        // setting content view to dialog
        dialog.setContentView(R.layout.custom_exit_dialog);

        // getting reference of TextView
        TextView dialogButtonYes = dialog.findViewById(R.id.textViewYes);
        TextView dialogButtonNo = dialog.findViewById(R.id.textViewNo);

        // click listener for No
        dialogButtonNo.setOnClickListener(v -> {
            // dismiss the dialog
            dialog.dismiss();

        });
        // click listener for Yes
        dialogButtonYes.setOnClickListener(v -> {
            // dismiss the dialog and exit the exit
            dialog.dismiss();
            requireActivity().finish();

        });

        // show the exit dialog
        dialog.show();
    }

}