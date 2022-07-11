package com.example.retrofitexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofitexample.adapter.RecyclerItemClick;
import com.example.retrofitexample.adapter.RetrofitAdapter;
import com.example.retrofitexample.data.Note;
import com.example.retrofitexample.retrofit.RetrofitInstance;
import com.example.retrofitexample.retrofit.ServiceApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RetrofitAdapter adapter;
    private List<Note> note = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        createData();
    }

    private void createData() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        progressBar = findViewById(R.id.progress_bar);
        getNotes();
    }

    private void getNotes() {
        ServiceApi serviceApi = RetrofitInstance.getRetrofit().create(ServiceApi.class);
        Call<List<Note>> call = serviceApi.getNotes();

        call.enqueue(new Callback<List<Note>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                progressBar.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    note = response.body();
                    adapter = new RetrofitAdapter(MainActivity.this, note, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Log.d("TAG", "onFailure" + t.getLocalizedMessage());
            }
        });
    }

    public void dialogPoster(Note note) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Poster")
                .setMessage("Are you sure you want to delete this poster?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ServiceApi serviceApi = RetrofitInstance.getRetrofit().create(ServiceApi.class);
                        Call<Note> call = serviceApi.deletePost(note.getId());
                        call.enqueue(new Callback<Note>() {
                            @SuppressLint({"NotifyDataSetChanged", "NewApi"})

                            @Override
                            public void onResponse(Call<Note> call, Response<Note> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Successful Delete", Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<Note> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
