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

public class PlumberSelectionActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private HelperAdapter plumberAdapter;
    private List<Helper> plumberList = new ArrayList<>();
    private RecyclerView plumberSelectionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plumber_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        plumberSelectionRecyclerView = findViewById(R.id.plumber_selection_recycler_view);
        plumberSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        plumberAdapter = new HelperAdapter(plumberList, new HelperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helper driver) {
                Intent intent = new Intent(PlumberSelectionActivity.this, HelperCheckoutActivity.class);
                intent.putExtra("helperId", driver.getId());
                intent.putExtra("helperName", driver.getName());
                intent.putExtra("jobTitle", driver.getJobTitle());
                intent.putExtra("helperFarePerHour", driver.getFarePerHour());
                startActivity(intent);
            }
        });

        plumberSelectionRecyclerView.setAdapter(plumberAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.recycler_view_divider);
        plumberSelectionRecyclerView.addItemDecoration(dividerItemDecoration);
        db = FirebaseFirestore.getInstance();
        fetchPlumbers();
    }

    private void fetchPlumbers() {
        db.collection("/helper-profile").whereEqualTo("jobTitle", "plumber")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("MainActivity", "Error fetching data", error);
                        Toast.makeText(PlumberSelectionActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    plumberList.clear();
                    assert value != null;
                    for (DocumentSnapshot document : value.getDocuments()) {
                        Helper driver = document.toObject(Helper.class);
                        assert driver != null;
                        driver.setId(document.getId());
                        plumberList.add(driver);
                    }
                    plumberAdapter.notifyDataSetChanged();
                }
            });
    }
}