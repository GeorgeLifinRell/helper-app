package com.example.helperapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

public class DriverSelectionActivity extends AppCompatActivity {
    TextView hoursNeededValueTV;
    TextView totalFareValueTV;
    TextView platformFeeValueTV;
    private final double PLATFORM_FEE = 0.01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selection);

        AppCompatImageButton decreaseHoursNeededBtn = findViewById(R.id.decrease_hours_needed_img_btn);
        AppCompatImageButton increaseHoursNeededBtn = findViewById(R.id.increase_hours_needed_img_btn);
        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.total_fare_value_tv);
        platformFeeValueTV = findViewById(R.id.platform_fee_value_tv);

        decreaseHoursNeededBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHoursValueAndTotalFare(v);
            }
        });
        increaseHoursNeededBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHoursValueAndTotalFare(v);
            }
        });
    }

    private void updateHoursValueAndTotalFare(View view) {
        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.total_fare_value_tv);
        platformFeeValueTV = findViewById(R.id.platform_fee_value_tv);
        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
        if (view.getId() == R.id.increase_hours_needed_img_btn) {
            hoursNeeded += 1;
        } else if (view.getId() == R.id.decrease_hours_needed_img_btn) {
            hoursNeeded = (hoursNeeded > 0) ? hoursNeeded - 1 : hoursNeeded;
        }
        hoursNeededValueTV.setText(String.valueOf(hoursNeeded));
        double totalFare = 250 * hoursNeeded * (1 + PLATFORM_FEE);
        double platformFee = totalFare - (250 * hoursNeeded);
        platformFeeValueTV.setText(String.valueOf(platformFee));
        totalFareValueTV.setText(String.valueOf(totalFare));
    }
}