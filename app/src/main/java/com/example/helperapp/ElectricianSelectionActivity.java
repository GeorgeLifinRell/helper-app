package com.example.helperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class ElectricianSelectionActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private HelperAdapter electricianAdapter;
    private List<Helper> electricianList = new ArrayList<>();
    private RecyclerView electricianSelectionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_electrician_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        electricianSelectionRecyclerView = findViewById(R.id.electrician_selection_recycler_view);
        electricianSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        electricianAdapter = new HelperAdapter(electricianList, new HelperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helper driver) {
                Intent intent = new Intent(ElectricianSelectionActivity.this, HelperCheckoutActivity.class);
                intent.putExtra("helperId", driver.getId());
                intent.putExtra("helperName", driver.getName());
                intent.putExtra("jobTitle", driver.getJobTitle());
                intent.putExtra("helperFarePerHour", driver.getFarePerHour());
                startActivity(intent);
            }
        });

        electricianSelectionRecyclerView.setAdapter(electricianAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.recycler_view_divider);
        electricianSelectionRecyclerView.addItemDecoration(dividerItemDecoration);
        db = FirebaseFirestore.getInstance();
        fetchCarpenters();
    }

    private void fetchCarpenters() {
        db.collection("/helper-profile").whereEqualTo("jobTitle", "electrician")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("MainActivity", "Error fetching data", error);
                        Toast.makeText(ElectricianSelectionActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    electricianList.clear();
                    assert value != null;
                    for (DocumentSnapshot document : value.getDocuments()) {
                        Helper driver = document.toObject(Helper.class);
                        assert driver != null;
                        driver.setId(document.getId());
                        electricianList.add(driver);
                    }
                    electricianAdapter.notifyDataSetChanged();
                }
            });
    }
}