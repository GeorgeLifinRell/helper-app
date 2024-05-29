package com.example.helperapp;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.util.HashMap;

public class JoinGenieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_genie);

        MaterialCheckBox materialCheckBox = findViewById(R.id.material_checkbox);
        MaterialTextView emailMTV = findViewById(R.id.email_mtv);
        MaterialTextView phoneNumberMTV = findViewById(R.id.phone_number_mtv);
        MaterialButton joinNowMBtn = findViewById(R.id.join_now_mb);
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        String[] selectedJobTitle = {""};
        findViewById(R.id.driver_radio_btn).setActivated(true);

        joinNowMBtn.setVisibility(View.INVISIBLE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.driver_radio_btn){
                    selectedJobTitle[0] = "driver";
                } else if (checkedId == R.id.carpenter_radio_btn) {
                    selectedJobTitle[0] = "carpenter";
                } else if (checkedId == R.id.electrician_radio_btn) {
                    selectedJobTitle[0] = "electrician";
                } else if (checkedId == R.id.plumber_radio_btn) {
                    selectedJobTitle[0] = "plumber";
                } else {
                    selectedJobTitle[0] = "";
                }
            }
        });
        materialCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailMTV.getText().toString().isEmpty() || phoneNumberMTV.getText().toString().isEmpty()){
                    Toast.makeText(JoinGenieActivity.this,
                            "Enter valid details before accepting terms and conditions!", Toast.LENGTH_SHORT).show();
                    return;
                }
                joinNowMBtn.setVisibility(View.VISIBLE);
            }
        });

        joinNowMBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailMTV.getText().toString();
                String phoneNumber = phoneNumberMTV.getText().toString();
                String jobTitle = selectedJobTitle[0];
                String createdAt = Instant.now().toString();
                HashMap<String, String> joinRequest = new HashMap<>();

                addJoinRequestToDB(joinRequest);
            }
        });
    }

    private void addJoinRequestToDB(HashMap<String, String> joinRequest) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("joining-requests")
            .add(joinRequest)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(JoinGenieActivity.this, "Request created with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(JoinGenieActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}