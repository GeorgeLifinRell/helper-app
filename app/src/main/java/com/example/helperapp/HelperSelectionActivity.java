package com.example.helperapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

public class HelperSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_selection);

        FragmentContainerView helperSelectionFragmentContainerView = findViewById(R.id.helper_fragment_container_view);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(R.id.helper_fragment_container_view, fragment)
                .addToBackStack(null)
                .commit();
    }
}