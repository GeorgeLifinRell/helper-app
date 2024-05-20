package com.example.helperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperapp.adapter.HelperAdapter;
import com.example.helperapp.models.Helper;
import com.example.helperapp.utils.DividerItemDecoration;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DriverSelectionActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private HelperAdapter driverAdapter;
    private List<Helper> driverList = new ArrayList<>();
    private RecyclerView driverSelectionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_selection);

        driverSelectionRecyclerView = findViewById(R.id.driver_selection_recycler_view);
        driverSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        driverAdapter = new HelperAdapter(driverList, new HelperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helper driver) {
                Intent intent = new Intent(DriverSelectionActivity.this, HelperCheckoutActivity.class);
                intent.putExtra("helperId", driver.getId());
                intent.putExtra("helperName", driver.getName());
                intent.putExtra("jobTitle", driver.getJobTitle());
                intent.putExtra("helperFarePerHour", driver.getFarePerHour());
                startActivity(intent);
            }
        });

        driverSelectionRecyclerView.setAdapter(driverAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.recycler_view_divider);
        driverSelectionRecyclerView.addItemDecoration(dividerItemDecoration);
        db = FirebaseFirestore.getInstance();
        fetchDrivers();
    }

    private void fetchDrivers() {
        db.collection("/helper-profile").whereEqualTo("jobTitle", "driver")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("MainActivity", "Error fetching data", error);
                            Toast.makeText(DriverSelectionActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        driverList.clear();
                        assert value != null;
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Helper driver = document.toObject(Helper.class);
                            assert driver != null;
                            driver.setId(document.getId());
                            driverList.add(driver);
                        }
                        driverAdapter.notifyDataSetChanged();
                    }
                });
    }
}