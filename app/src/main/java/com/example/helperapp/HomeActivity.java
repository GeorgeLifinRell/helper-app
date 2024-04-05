package com.example.helperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private static final int MENU_ITEM_CHANGE_PASSWORD = 1;
    private static final int MENU_ITEM_VERIFY_EMAIL = 2;
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
        ImageButton moreProfileMenuBtn = findViewById(R.id.more_profile_options_img_btn);

        MaterialCardView driverSelectionCard = findViewById(R.id.driver_home_card);

        driverSelectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DriverSelectionActivity.class));
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
        moreProfileMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreProfileOptionsMenu(v);
            }
        });
    }

    private void showMoreProfileOptionsMenu(View view) {
        PopupMenu moreProfileOptionsMenu = new PopupMenu(this, view);
        moreProfileOptionsMenu.getMenuInflater().inflate(R.menu.menu_profile, moreProfileOptionsMenu.getMenu());
        moreProfileOptionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case MENU_ITEM_CHANGE_PASSWORD:
                        changeUserPassword();
                        return true;
                    case MENU_ITEM_VERIFY_EMAIL:
                        verifyUserEmailId();
                        return true;
                    default:
                        return false;
                }
            }
        });
        moreProfileOptionsMenu.show();
    }

    private void changeUserPassword() {
        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
    }

    private void verifyUserEmailId() {
        startActivity(new Intent(getApplicationContext(), VerifyEmailActivity.class));
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
        builder.create().show();
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}