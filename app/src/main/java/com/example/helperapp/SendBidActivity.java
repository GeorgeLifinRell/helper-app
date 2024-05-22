package com.example.helperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import android.net.Uri;

public class SendBidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_bid);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent previousIntent = getIntent();
        String farePerHour = previousIntent.getStringExtra("helperFarePerHour");

        MaterialTextView askAmountMTV = findViewById(R.id.ask_amount_mtv);
        EditText bidAmountET = findViewById(R.id.bid_amount_et);
        EditText helperPhoneNumberET = findViewById(R.id.helper_phone_number_et);
        MaterialButton sendBidBtn = findViewById(R.id.send_bid_btn);

        askAmountMTV.setText(farePerHour);
        sendBidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bidAmount = bidAmountET.getText().toString();
                String helperPhoneNumber = helperPhoneNumberET.getText().toString();

                if (bidAmount.isEmpty() || helperPhoneNumber.isEmpty()) {
                    Toast.makeText(SendBidActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String message = "I would like to bid " + bidAmount + " for you to complete my task.";
                    sendSms(helperPhoneNumber, message);
                } catch (Exception e) {
                    Toast.makeText(SendBidActivity.this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void sendSms(String phoneNumber, String message) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        }
    }
}