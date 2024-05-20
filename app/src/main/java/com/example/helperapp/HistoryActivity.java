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

import com.example.helperapp.adapter.HelperBookingAdapter;
import com.example.helperapp.models.HelperBooking;
import com.example.helperapp.utils.DividerItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private List<HelperBooking> helperBookingList = new ArrayList<>();
    private HelperBookingAdapter helperBookingAdapter;
    private RecyclerView bookingsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        if (currentUser == null) {
            finish();
        }
        String currentUserId = currentUser.getUid();

        bookingsRecyclerView = findViewById(R.id.recycler_view_history);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        helperBookingAdapter = new HelperBookingAdapter(helperBookingList, new HelperBookingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HelperBooking helperBooking) {
                String helperId = helperBooking.getHelperId();
                final String[] helperName = {""};
                final String[] helperFarePerHour = {""};

                db.collection("/helper-bookings").whereEqualTo("userId", currentUserId)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.e("HistoryActivity", "Error fetching data", error);
                            Toast.makeText(HistoryActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        } else if (value != null) {
                            for (DocumentSnapshot document : value.getDocuments()) {
                                helperName[0] = document.getString("name");
                                helperFarePerHour[0] = document.getString("farePerHour");
                            }
                        }
                    });

                Intent intent = new Intent(HistoryActivity.this, HelperCheckoutActivity.class);
                intent.putExtra("helperId", helperId);
                intent.putExtra("helperName", helperName[0]);
                intent.putExtra("jobTitle", helperBooking.getJobTitle());
                intent.putExtra("helperFarePerHour", helperFarePerHour[0]);
                startActivity(intent);
            }
        });
        bookingsRecyclerView.setAdapter(helperBookingAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.recycler_view_divider);
        bookingsRecyclerView.addItemDecoration(dividerItemDecoration);
        fetchBookings(currentUserId);
    }

    private void fetchBookings(String currentUserId) {
        db = FirebaseFirestore.getInstance();
        db.collection("/helper-bookings").whereEqualTo("userId", currentUserId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("HistoryActivity", "Error fetching data", error);
                            Toast.makeText(HistoryActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        helperBookingList.clear();
                        assert value != null;
                        for (DocumentSnapshot document : value.getDocuments()) {
                            HelperBooking booking = document.toObject(HelperBooking.class);
                            assert booking != null;
                            booking.setBookingId(document.getId());
                            helperBookingList.add(booking);
                        }
                        helperBookingAdapter.notifyDataSetChanged();
                    }
                });
    }
}