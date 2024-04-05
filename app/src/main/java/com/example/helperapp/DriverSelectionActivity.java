package com.example.helperapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class DriverSelectionActivity extends AppCompatActivity {
    TextView hoursNeededValueTV;
    TextView totalFareValueTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selection);

        AppCompatImageButton decreaseHoursNeededBtn = findViewById(R.id.decrease_hours_needed_img_btn);
        AppCompatImageButton increaseHoursNeededBtn = findViewById(R.id.increase_hours_needed_img_btn);
        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.total_fare_value_tv);

        decreaseHoursNeededBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseHoursAndUpdateHoursValue();
            }
        });
        increaseHoursNeededBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseHoursAndUpdateHoursValue();
            }
        });
    }

    private void increaseHoursAndUpdateHoursValue() {
        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.total_fare_value_tv);
        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
        hoursNeeded += 1;
        hoursNeededValueTV.setText(hoursNeeded);
        int totalFare = 250 * hoursNeeded;
        totalFareValueTV.setText(totalFare);
    }

    private void decreaseHoursAndUpdateHoursValue() {
        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.total_fare_value_tv);
        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
        hoursNeeded -= 1;
        hoursNeededValueTV.setText(hoursNeeded);
        int totalFare = 250 * hoursNeeded;
        totalFareValueTV.setText(totalFare);
    }
}