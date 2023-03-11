package com.jamillabltd.temperatureconverter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Spinner spinner;
    private TextView outputTextView, celsiusText, kelvinText, fahrenheitText, rankineText;
    private EditText valueEditText;
    String[] temperatureName;
    private LinearLayout celsiusArea, kelvinArea, fahrenheitArea, rankineArea;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        loadLocalLandLang();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        spinner = view.findViewById(R.id.spinnerId);
        Button convert = view.findViewById(R.id.convertButtonId);
        outputTextView = view.findViewById(R.id.outputTextViewId);
        temperatureName = getResources().getStringArray(R.array.temperatureName);
        valueEditText = view.findViewById(R.id.valueEditTextId);
        TextView refresh = view.findViewById(R.id.refreshId);

        celsiusArea = view.findViewById(R.id.celsiusAreaId);
        kelvinArea = view.findViewById(R.id.kelvinAreaId);
        fahrenheitArea = view.findViewById(R.id.fahrenheitAreaId);
        rankineArea = view.findViewById(R.id.rankineAreaId);

        celsiusText = view.findViewById(R.id.celsiusValueId);
        kelvinText = view.findViewById(R.id.kelvinValueId);
        fahrenheitText = view.findViewById(R.id.fahrenheitValueId);
        rankineText = view.findViewById(R.id.rankineValueId);

        convert.setOnClickListener(this);
        refresh.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinnerview, R.id.spinnerViewId, temperatureName);
        spinner.setAdapter(adapter);

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.refreshId) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            requireActivity().finish();
        }
        if (view.getId() == R.id.convertButtonId) {
            try {
                /*String value = spinner.getSelectedItem().toString();
                outputTextView.setText(value);
                String vName = spinner.getSelectedItem().toString();*/

                String vName = spinner.getSelectedItem().toString();
                outputTextView.setText(vName);

                String EditTextValue = valueEditText.getText().toString().trim();

                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                if (TextUtils.isEmpty(EditTextValue)) {
                    valueEditText.setError("Please Enter a value!");
                    valueEditText.requestFocus();
                } else {
                    String celsiusNameEn = "Celsius";
                    String celsiusNameAr = "درجةمئوية";
                    String celsiusNameBn = "সেলসিয়াস";
                    String celsiusNameHi = "सेल्सियस";
                    String celsiusNameUr = "سیلسیس";

                    if (vName.equals(celsiusNameEn) || vName.equals(celsiusNameAr) || vName.equals(celsiusNameBn) || vName.equals(celsiusNameHi) || vName.equals(celsiusNameUr)) {
                        celsiusText.setText(EditTextValue + "°C");
                        celsiusArea.setVisibility(View.VISIBLE);
                        kelvinArea.setVisibility(View.VISIBLE);
                        fahrenheitArea.setVisibility(View.VISIBLE);
                        rankineArea.setVisibility(View.VISIBLE);

                        double celsiusV = Double.parseDouble(EditTextValue);

                        /*Celsius To Kelvin Methods*/
                        double CKValue = (Math.round((celsiusV + 273.15) * 100.0) / 100.0);
                        String CelsiusToKelvin = String.valueOf(CKValue);
                        kelvinText.setText(CelsiusToKelvin + "°K");

                        /*Celsius To Fahrenheit Methods*/
                        double CFValue = (Math.round(((celsiusV * 9 / 5) + 32) * 100.0) / 100.0);
                        String CelsiusToFahrenheit = String.valueOf(CFValue);
                        fahrenheitText.setText(CelsiusToFahrenheit + "°F");

                        /*Celsius To Rankine Methods*/
                        double CRValue = (Math.round(((celsiusV + 273.15) * 1.8) * 100.0) / 100.0);
                        String CelsiusToRankine = String.valueOf(CRValue);
                        rankineText.setText(CelsiusToRankine + "°R");


                    }
                    String KelvinNameEn = "Kelvin";
                    String KelvinNameAr = "كلفن";
                    String KelvinNameBn = "কেলভিন";
                    String KelvinNameHi = "केल्विन";
                    String KelvinNameUr = "کیلون";
                    if (vName.equals(KelvinNameEn) || vName.equals(KelvinNameAr) || vName.equals(KelvinNameBn) || vName.equals(KelvinNameHi) || vName.equals(KelvinNameUr)) {
                        kelvinText.setText(EditTextValue + "°K");
                        celsiusArea.setVisibility(View.VISIBLE);
                        kelvinArea.setVisibility(View.VISIBLE);
                        fahrenheitArea.setVisibility(View.VISIBLE);
                        rankineArea.setVisibility(View.VISIBLE);


                        double kelvinV = Double.parseDouble(EditTextValue);

                        /*Kelvin To Celsius Methods*/
                        double KCValue = (Math.round((kelvinV - 273.15) * 100.0) / 100.0);
                        String CelsiusToKelvin = String.valueOf(KCValue);
                        celsiusText.setText(CelsiusToKelvin + "°C");

                        /*Kelvin To Fahrenheit Methods*/
                        double CFValue = (Math.round((kelvinV * 1.8 - 459.67) * 100.0) / 100.0);
                        String CelsiusToFahrenheit = String.valueOf(CFValue);
                        fahrenheitText.setText(CelsiusToFahrenheit + "°F");

                        /*Kelvin To Rankine Methods*/
                        double KRValue = (Math.round((kelvinV * 1.8) * 100.0) / 100.0);
                        String KelvinToRankine = String.valueOf(KRValue);
                        rankineText.setText(KelvinToRankine + "°R");

                    }
                    String FahrenheitNameEn = "Fahrenheit";
                    String FahrenheitNameAr = "فهرنهايت";
                    String FahrenheitNameBn = "ফারেনহাইট";
                    String FahrenheitNameHi = "फारेनहाइट";
                    String FahrenheitNameUr = "فارن ہائیٹ";
                    if (vName.equals(FahrenheitNameEn) || vName.equals(FahrenheitNameAr) || vName.equals(FahrenheitNameBn) || vName.equals(FahrenheitNameHi) || vName.equals(FahrenheitNameUr)) {
                        fahrenheitText.setText(EditTextValue + "°F");
                        celsiusArea.setVisibility(View.VISIBLE);
                        kelvinArea.setVisibility(View.VISIBLE);
                        fahrenheitArea.setVisibility(View.VISIBLE);
                        rankineArea.setVisibility(View.VISIBLE);

                        double fahrenheitV = Double.parseDouble(EditTextValue);

                        /*Fahrenheit to Celsius Methods*/
                        double CKValue = (Math.round(((fahrenheitV - 32) * 5 / 9) * 100.0) / 100.0);
                        String CelsiusToKelvin = String.valueOf(CKValue);
                        celsiusText.setText(CelsiusToKelvin + "°C");

                        /*Fahrenheit to kelvin Methods*/
                        double CFValue = (Math.round((((fahrenheitV - 32) / 1.8) + 273.15) * 100.0) / 100.0);
                        String CelsiusToFahrenheit = String.valueOf(CFValue);
                        kelvinText.setText(CelsiusToFahrenheit + "°K");

                        /*Fahrenheit To Rankine Methods*/
                        double FRValue = (Math.round((fahrenheitV + 459.67) * 100.0) / 100.0);
                        String FahrenheitToRankine = String.valueOf(FRValue);
                        rankineText.setText(FahrenheitToRankine + "°R");
                    }

                    String RankineNameEn = "Rankine";
                    String RankineNameAr = "رانكين";
                    String RankineNameBn = "রাঙ্কিন";
                    String RankineNameHi = "रैंकिन";
                    String RankineNameUr = "رینکائن";
                    if (vName.equals(RankineNameEn) || vName.equals(RankineNameAr) || vName.equals(RankineNameBn) || vName.equals(RankineNameHi) || vName.equals(RankineNameUr)) {
                        rankineText.setText(EditTextValue + "°R");
                        celsiusArea.setVisibility(View.VISIBLE);
                        kelvinArea.setVisibility(View.VISIBLE);
                        fahrenheitArea.setVisibility(View.VISIBLE);
                        rankineArea.setVisibility(View.VISIBLE);

                        double rankineV = Double.parseDouble(EditTextValue);


                        //Rankine to Celsius Methods
                        double RCValue = (Math.round(((rankineV - 491.67) / 1.8) * 100.0) / 100.0);
                        String RankineToCelsius = String.valueOf(RCValue);
                        celsiusText.setText(RankineToCelsius + "°C");

                        //Rankine to Kelvin Methods
                        double RKValue = (Math.round((rankineV / 1.8) * 100.0) / 100.0);
                        String RankineToKelvin = String.valueOf(RKValue);
                        kelvinText.setText(RankineToKelvin + "°K");


                        //Rankine to Fahrenheit Methods
                        double RFValue = (Math.round((rankineV - 459.67) * 100.0) / 100.0);
                        String RankineToFahrenheit = String.valueOf(RFValue);
                        fahrenheitText.setText(RankineToFahrenheit + "°F");

                    }
                }
            }catch (Exception e){
                Toast.makeText(getActivity(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }
}