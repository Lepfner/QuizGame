package com.example.mtseminar;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserScoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserScoresAdapter adapter;
    private List<UserScore> userScoresList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_scores);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userScoresList = new ArrayList<>();
        adapter = new UserScoresAdapter(userScoresList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchUserScores();
            }
        });

        fetchUserScores();
    }

    private void fetchUserScores() {
        DatabaseReference userScoresRef = FirebaseDatabase.getInstance().getReference("user-scores");
        userScoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("UserScoresActivity", "onDataChange triggered");
                userScoresList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserScore userScore = snapshot.getValue(UserScore.class);
                    if (userScore != null) {
                        userScoresList.add(userScore);
                    }
                }
                adapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}