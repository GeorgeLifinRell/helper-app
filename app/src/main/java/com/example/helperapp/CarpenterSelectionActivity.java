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

public class CarpenterSelectionActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private HelperAdapter carpenterAdapter;
    private List<Helper> carpenterList = new ArrayList<>();
    private RecyclerView carpenterSelectionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carpenter_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carpenterSelectionRecyclerView = findViewById(R.id.carpenter_selection_recycler_view);
        carpenterSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        carpenterAdapter = new HelperAdapter(carpenterList, new HelperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helper driver) {
                Intent intent = new Intent(CarpenterSelectionActivity.this, HelperCheckoutActivity.class);
                intent.putExtra("helperId", driver.getId());
                intent.putExtra("helperName", driver.getName());
                intent.putExtra("jobTitle", driver.getJobTitle());
                intent.putExtra("helperFarePerHour", driver.getFarePerHour());
                startActivity(intent);
            }
        });

        carpenterSelectionRecyclerView.setAdapter(carpenterAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.recycler_view_divider);
        carpenterSelectionRecyclerView.addItemDecoration(dividerItemDecoration);
        db = FirebaseFirestore.getInstance();
        fetchCarpenters();
    }

    private void fetchCarpenters() {
        db.collection("/helper-profile").whereEqualTo("jobTitle", "carpenter")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("MainActivity", "Error fetching data", error);
                            Toast.makeText(CarpenterSelectionActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        carpenterList.clear();
                        assert value != null;
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Helper driver = document.toObject(Helper.class);
                            assert driver != null;
                            driver.setId(document.getId());
                            carpenterList.add(driver);
                        }
                        carpenterAdapter.notifyDataSetChanged();
                    }
                });
    }
}