package com.aiyaa.swipeleft;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private MorphAnimation morphAnimationSetting;
    static public int selectedPlan;
    //0 = 19p, 1 = 19r, 2 = 14p, 3 = 14r, 4 = 11r

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnOnSubtractButton();
        turnOnAddButton();
        turnOnSettingButton();
        turnOnSaveButton();
        turnOnCancelButton();

        Spinner planDropdown = findViewById(R.id.planDropdown);
        planDropdown.setOnItemSelectedListener(new CustomOnItemSelectedListener());


        TextView swipes = findViewById(R.id.swipeCounter);

        //Get Preference
        SharedPreferences myPrefs = getSharedPreferences("preferences.xml", MODE_PRIVATE);
        String newCounter = myPrefs.getString("counter", "");
        int newPlan = myPrefs.getInt("plan",0);

        if (newCounter.equals(""))
            updateCounter();
        else
            swipes.setText(newCounter);

        planDropdown.setSelection(newPlan);


    }

    @Override
    protected void onPause() {
        super.onPause();

        savePreferences();
    }

    @Override
    protected void onStop() {
        super.onStop();

        savePreferences();
    }

    public void turnOnSubtractButton(){
        Button subtractButton = findViewById(R.id.subtract);
        subtractButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView swipes = findViewById(R.id.swipeCounter);
                String currentCounter = swipes.getText().toString();
                int count = Integer.parseInt(currentCounter);

                if(count > 0)
                    swipes.setText("" + --count);
                else
                    Toast.makeText(getApplicationContext(), "You don't have any swipes left already!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void turnOffSubtractButton() {
        Button subtractButton = findViewById(R.id.subtract);
        subtractButton.setOnClickListener(null);
    }

    public void turnOnAddButton() {
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView swipes = findViewById(R.id.swipeCounter);
                String currentCounter = swipes.getText().toString();
                int count = Integer.parseInt(currentCounter);

                swipes.setText("" + ++count);
                Toast.makeText(getApplicationContext(), "wHY DId u aDd A sWIpE ow0", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void turnOffAddButton() {
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(null);
    }

    public void turnOnSettingButton(){
        final FrameLayout rootView = findViewById(R.id.setting_view);
        View cardviewContainer = findViewById(R.id.form_btn);
        final Button settingButton =  findViewById(R.id.setting_button);
        ViewGroup verticalLinearView = findViewById(R.id.vert_linear_view);
        ViewGroup buttonView = findViewById(R.id.button_view);

        morphAnimationSetting = new MorphAnimation(cardviewContainer, rootView, verticalLinearView, buttonView);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!morphAnimationSetting.isPressed()) {
                    morphAnimationSetting.morphIntoForm();
                    turnOffSettingButton();
                    turnOffAddButton();
                    turnOffSubtractButton();
                } else {
                    morphAnimationSetting.morphIntoButton();
                }
            }
        });
    }

    public void turnOffSettingButton(){
        final Button settingButton =  findViewById(R.id.setting_button);
        settingButton.setOnClickListener(null);
    }

    public void turnOnSaveButton() {
        final FrameLayout rootView = findViewById(R.id.setting_view);
        View cardviewContainer = findViewById(R.id.form_btn);
        final Button saveButton =  findViewById(R.id.saveButton);
        ViewGroup verticalLinearView = findViewById(R.id.vert_linear_view);
        ViewGroup buttonView = findViewById(R.id.button_view);

        morphAnimationSetting = new MorphAnimation(cardviewContainer, rootView, verticalLinearView, buttonView);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morphAnimationSetting.morphIntoButton();
                turnOnSettingButton();
                turnOnAddButton();
                turnOnSubtractButton();
                updateCounter();

            }
        });
    }

    public void turnOffSaveButton() {
        final Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(null);
    }

    public void turnOnCancelButton() {
        final FrameLayout rootView = findViewById(R.id.setting_view);
        View cardviewContainer = findViewById(R.id.form_btn);
        final Button cancelButton =  findViewById(R.id.cancelButton);
        ViewGroup verticalLinearView = findViewById(R.id.vert_linear_view);
        ViewGroup buttonView = findViewById(R.id.button_view);

        morphAnimationSetting = new MorphAnimation(cardviewContainer, rootView, verticalLinearView, buttonView);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morphAnimationSetting.morphIntoButton();
                turnOnSettingButton();
                turnOnAddButton();
                turnOnSubtractButton();
            }
        });
    }

    public void turnOffCancelButton() {
        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(null);
    }

    public void updateCounter() {
        TextView swipes = findViewById(R.id.swipeCounter);

        switch (selectedPlan)
        {
            case 0: swipes.setText("214"); break;
            case 1: swipes.setText("19"); break;
            case 2: swipes.setText("158"); break;
            case 3: swipes.setText("14"); break;
            case 4: swipes.setText("11"); break;
            default: swipes.setText("0");
        }
    }

    public void savePreferences() {
        TextView swipes = findViewById(R.id.swipeCounter);
        String currentCount = swipes.getText().toString();

        Spinner planDropdown = findViewById(R.id.planDropdown);
        int plan = planDropdown.getSelectedItemPosition();

        SharedPreferences mPrefs = getSharedPreferences("preferences.xml", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("counter", currentCount);

        prefsEditor.putInt("plan", plan);
        prefsEditor.apply();
    }

}

