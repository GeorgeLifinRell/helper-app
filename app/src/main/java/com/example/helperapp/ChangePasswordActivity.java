package com.example.helperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView changePasswordErrorMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();

        TextInputEditText currentPasswordET = findViewById(R.id.existing_password_material_et);
        TextInputEditText newPasswordET = findViewById(R.id.new_password_material_et);
        TextInputEditText confirmNewPasswordET = findViewById(R.id.confirm_password_material_et);
        MaterialButton submitChangePasswordRequestBtn = findViewById(R.id.submit_change_password_req_btn);
        changePasswordErrorMessageTV = findViewById(R.id.change_password_error_msg_tv);

        submitChangePasswordRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = Objects.requireNonNull(currentPasswordET.getText()).toString();
                String newPassword = Objects.requireNonNull(newPasswordET.getText()).toString();
                String confirmPassword = Objects.requireNonNull(confirmNewPasswordET.getText()).toString();

                if (newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "New Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser!= null) {
                    AuthCredential authCredential = EmailAuthProvider.getCredential(Objects.requireNonNull(currentUser.getEmail()), currentPassword);
                    currentUser.reauthenticate(authCredential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    submitChangePasswordRequest(newPassword);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangePasswordActivity.this, "User authentication failed!", Toast.LENGTH_SHORT).show();
                                    changePasswordErrorMessageTV.setText("User Authentication failed!");
                                    changePasswordErrorMessageTV.setTextColor(getResources().getColor(R.color.ruby_red));
                                }
                            });
                }
            }
        });
    }

    private void submitChangePasswordRequest(@NotNull String newPassword) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            changePasswordErrorMessageTV.setText(e.getMessage());
                            changePasswordErrorMessageTV.setTextColor(getResources().getColor(R.color.ruby_red));
                        }
                    });
        }
    }
}