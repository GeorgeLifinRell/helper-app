package com.example.helperapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.helperapp.adapter.HelperAdapter;
import com.example.helperapp.models.Helper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DriverSelectionActivity extends AppCompatActivity {
    TextView hoursNeededValueTV;
    TextView totalFareValueTV;
    TextView platformFeeValueTV;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selection_new);
        db = FirebaseFirestore.getInstance();

//        AppCompatImageButton decreaseHoursNeededBtn = findViewById(R.id.decrease_hours_needed_img_btn);
//        AppCompatImageButton increaseHoursNeededBtn = findViewById(R.id.increase_hours_needed_img_btn);
//        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
//        totalFareValueTV = findViewById(R.id.total_fare_value_tv);
//        platformFeeValueTV = findViewById(R.id.platform_fee_value_tv);
//
//        decreaseHoursNeededBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateHoursValueAndTotalFare(v);
//            }
//        });
//        increaseHoursNeededBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateHoursValueAndTotalFare(v);
//            }
//        });
        ListView listView = findViewById(R.id.driver_list_view);
        List<Helper> driverList = populateDriverListFromDB();
        HelperAdapter driverAdapter = new HelperAdapter(this, driverList);
        listView.setAdapter(driverAdapter);
    }

//    private void updateHoursValueAndTotalFare(View view) {
//        hoursNeededValueTV = findViewById(R.id.hours_needed_value_tv);
//        totalFareValueTV = findViewById(R.id.total_fare_value_tv);
//        platformFeeValueTV = findViewById(R.id.platform_fee_value_tv);
//        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
//        if (view.getId() == R.id.increase_hours_needed_img_btn) {
//            hoursNeeded += 1;
//        } else if (view.getId() == R.id.decrease_hours_needed_img_btn) {
//            hoursNeeded = (hoursNeeded > 0) ? hoursNeeded - 1 : hoursNeeded;
//        }
//        hoursNeededValueTV.setText(String.valueOf(hoursNeeded));
//        double PLATFORM_FEE = 0.01;
//        double totalFare = 250 * hoursNeeded * (1 + PLATFORM_FEE);
//        double platformFee = totalFare - (250 * hoursNeeded);
//        platformFeeValueTV.setText(String.valueOf(platformFee));
//        totalFareValueTV.setText(String.valueOf(totalFare));
//    }

    private List<Helper> populateDriverListFromDB() {
        List<Helper> helperList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("/helper-list")
                .whereEqualTo("jobTitle", "driver")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots) {
                            Helper driver = queryDocumentSnapshot.toObject(Helper.class);
                            helperList.add(driver);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        return helperList;
    }
}