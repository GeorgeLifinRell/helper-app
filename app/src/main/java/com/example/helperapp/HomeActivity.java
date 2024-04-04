package com.example.helperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        ImageButton logOutBtn = findViewById(R.id.log_out_img_btn);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View logoutConfirmationDialogView = layoutInflater.inflate(R.layout.dialog_logout, null);
        builder.setView(logoutConfirmationDialogView);

        builder.setPositiveButton("Stay Back!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Leave now!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
    }

    private void logout() {
        mAuth.signOut();
    }
}