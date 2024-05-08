package com.example.helperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.helperapp.models.Helper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DriverSelectionFragment extends Fragment {
    private FirebaseFirestore db;
    private List<Helper> driverDataList;
    private ProgressBar progressBar;
    public DriverSelectionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_driver_selection, container, false);

        db = FirebaseFirestore.getInstance();
        driverDataList = new ArrayList<>();

        // Fetching driver data from the firestore
        db.collection("/helper-profile")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            if (Objects.requireNonNull(document.get("jobTitle")).toString().equals("driver")) {
                                Helper driver = document.toObject(Helper.class);
                                driverDataList.add(driver);
                            }
                        }
                        System.out.println(driverDataList); //TODO: Remove after debugging
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        return  view;
    }
}