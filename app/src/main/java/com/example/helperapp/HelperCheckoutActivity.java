package com.example.helperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class HelperCheckoutActivity extends AppCompatActivity {
    private TextView helperNameValueTV;
    private TextView helperFarePerHourValueTV;
    private TextView hoursNeededValueTV;
    private TextView totalFareValueTV;
    private TextView platformFeeValueTV;
    private AppCompatImageButton decreaseHoursNeededBtn;
    private AppCompatImageButton increaseHoursNeededBtn;
    private MaterialButton checkoutBtn;
    private final double PLATFORM_FEE = 0.05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_helper_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        helperFarePerHourValueTV = findViewById(R.id.genie_fare_value_tv);
        helperNameValueTV = findViewById(R.id.genie_name_value_tv);
        hoursNeededValueTV = findViewById(R.id.genie_hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.genie_total_fare_value_tv);
        platformFeeValueTV = findViewById(R.id.genie_platform_fee_value_tv);
        decreaseHoursNeededBtn = findViewById(R.id.decrease_hours_needed_img_btn);
        increaseHoursNeededBtn = findViewById(R.id.increase_hours_needed_img_btn);
        checkoutBtn = findViewById(R.id.genie_checkout_btn);

        Intent intent = getIntent();
//        String helperId = intent.getStringExtra("helperId");
        String helperName = intent.getStringExtra("helperName");
        String helperFarePerHour = intent.getStringExtra("helperFarePerHour");
//        String jobTitle = intent.getStringExtra("jobTitle");

        helperNameValueTV.setText(helperName);
        helperFarePerHourValueTV.setText(helperFarePerHour);
        hoursNeededValueTV.setText("1");

        decreaseHoursNeededBtn.setOnClickListener(this::updateHoursNeededAndTotalFareValue);
        increaseHoursNeededBtn.setOnClickListener(this::updateHoursNeededAndTotalFareValue);
    }

    private void updateHoursNeededAndTotalFareValue(View view) {
        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
        double helperFarePerHour = Double.parseDouble(helperFarePerHourValueTV.getText().toString());

        if (view.getId() == R.id.decrease_hours_needed_img_btn) {
            hoursNeeded = (hoursNeeded > 1) ? hoursNeeded - 1 : hoursNeeded;
        } else if (view.getId() == R.id.increase_hours_needed_img_btn) {
            hoursNeeded += 1;
        }
        hoursNeededValueTV.setText(String.valueOf(hoursNeeded));
        platformFeeValueTV.setText(String.valueOf(helperFarePerHour * hoursNeeded * PLATFORM_FEE));
        totalFareValueTV.setText(String.valueOf(hoursNeeded * helperFarePerHour * (1 + PLATFORM_FEE)));
    }
}