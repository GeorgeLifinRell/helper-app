package com.example.helperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperapp.utils.GenieStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.util.HashMap;

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
    private final int MIN_HOURS_NEEDED = 1;
    private final int MAX_HOURS_NEEDED = 12;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String currentBookingId;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

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
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String currentUserId = currentUser.getUid();
        currentBookingId = "";

        helperFarePerHourValueTV = findViewById(R.id.genie_fare_value_tv);
        helperNameValueTV = findViewById(R.id.genie_name_value_tv);
        hoursNeededValueTV = findViewById(R.id.genie_hours_needed_value_tv);
        totalFareValueTV = findViewById(R.id.genie_total_fare_value_tv);
        platformFeeValueTV = findViewById(R.id.genie_platform_fee_value_tv);
        decreaseHoursNeededBtn = findViewById(R.id.decrease_hours_needed_img_btn);
        increaseHoursNeededBtn = findViewById(R.id.increase_hours_needed_img_btn);
        checkoutBtn = findViewById(R.id.genie_checkout_btn);

        // Extract all the data from the previous activity
        Intent previousIntent = getIntent();
        String helperId = previousIntent.getStringExtra("helperId");
        String helperName = previousIntent.getStringExtra("helperName");
        String helperFarePerHour = previousIntent.getStringExtra("helperFarePerHour");
        String jobTitle = previousIntent.getStringExtra("jobTitle");

        helperNameValueTV.setText(helperName);
        helperFarePerHourValueTV.setText(helperFarePerHour);
        hoursNeededValueTV.setText("1");
        updateInitialUI();
        decreaseHoursNeededBtn.setOnClickListener(this::updateHoursNeededAndTotalFareValue);
        increaseHoursNeededBtn.setOnClickListener(this::updateHoursNeededAndTotalFareValue);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert helperId != null;
                assert helperName != null;
                assert helperFarePerHour != null;
                assert jobTitle != null;
                assert hoursNeededValueTV != null;
                if (helperId.isEmpty() && helperName.isEmpty() && helperFarePerHour.isEmpty() && jobTitle.isEmpty() && hoursNeededValueTV.toString().isEmpty()) {
                    Toast.makeText(HelperCheckoutActivity.this, "Error getting Genie info!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> helperBookingDetails = new HashMap<>();
                helperBookingDetails.put("userId", currentUserId);
                helperBookingDetails.put("helperId", helperId);
                helperBookingDetails.put("jobTitle", jobTitle);
                helperBookingDetails.put("hoursNeeded", hoursNeededValueTV.getText().toString());
                helperBookingDetails.put("bookingTimestamp", Instant.now().toString());
                helperBookingDetails.put("totalFare", totalFareValueTV.getText().toString());
                helperBookingDetails.put("serviceStatus", GenieStatusCodes.SERVICE_PENDING.toString());
                addBookingToDB(helperBookingDetails);
                Toast.makeText(HelperCheckoutActivity.this, "Booking Successful with ID: " + currentBookingId, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HelperCheckoutActivity.this, HomeActivity.class);
                intent.putExtra("lastBookingId", currentBookingId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateHoursNeededAndTotalFareValue(View view) {
        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
        double helperFarePerHour = Double.parseDouble(helperFarePerHourValueTV.getText().toString());

        if (view.getId() == R.id.decrease_hours_needed_img_btn) {
            hoursNeeded = (hoursNeeded > MIN_HOURS_NEEDED) ? hoursNeeded - 1 : hoursNeeded;
        } else if (view.getId() == R.id.increase_hours_needed_img_btn) {
            hoursNeeded = (hoursNeeded < MAX_HOURS_NEEDED) ? hoursNeeded + 1 : hoursNeeded;
        }
        hoursNeededValueTV.setText(String.valueOf(hoursNeeded));
        platformFeeValueTV.setText(String.valueOf(helperFarePerHour * hoursNeeded * PLATFORM_FEE));
        totalFareValueTV.setText(String.valueOf(hoursNeeded * helperFarePerHour * (1 + PLATFORM_FEE)));
    }

    private void updateInitialUI() {
        int hoursNeeded = Integer.parseInt(hoursNeededValueTV.getText().toString());
        double helperFarePerHour = Double.parseDouble(helperFarePerHourValueTV.getText().toString());
        platformFeeValueTV.setText(String.valueOf(helperFarePerHour * hoursNeeded * PLATFORM_FEE));
        totalFareValueTV.setText(String.valueOf(hoursNeeded * helperFarePerHour * (1 + PLATFORM_FEE)));
    }

    private void addBookingToDB(HashMap<String, String> helperBookingDetails) {
        db = FirebaseFirestore.getInstance();
        db.collection("/helper-bookings")
                .add(helperBookingDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        currentBookingId = documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HelperCheckoutActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}