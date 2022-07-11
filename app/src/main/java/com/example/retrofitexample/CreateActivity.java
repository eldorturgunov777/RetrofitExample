package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.retrofitexample.data.Note;
import com.example.retrofitexample.retrofit.RetrofitInstance;
import com.example.retrofitexample.retrofit.ServiceApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {

    TextInputEditText title_edit_text, body_edit_text;
    Note note;
    int idExtra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initViews();
    }

    private void initViews() {
        title_edit_text = findViewById(R.id.title_edit_text);
        body_edit_text = findViewById(R.id.body_edit_text);
        Button createData = findViewById(R.id.bt_post);

        createData.setOnClickListener(view -> {

            Note note = new Note(
                    title_edit_text.getText().toString(),
                    body_edit_text.getText().toString()
            );
            if (idExtra == 0) {
                insertNote(note);
            } else {
                note.setId(idExtra);
                updateNote(idExtra, note);
            }
        });

        if (getIntent().hasExtra("IdExtra")) {
            idExtra = getIntent().getExtras().getInt("IdExtra");
            createData.setText("Update");
            getPostApi(idExtra);
        }
    }

    private void updateNote(int idExtra, Note note) {
        ServiceApi serviceApi = RetrofitInstance.getRetrofit().create(ServiceApi.class);
        Call<Note> call = serviceApi.updatePost(idExtra, note);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateActivity.this, "Successful Update", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPostApi(int idExtra) {
        ServiceApi serviceApi = RetrofitInstance.getRetrofit().create(ServiceApi.class);
        Call<Note> call = serviceApi.getNote(idExtra);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful()) {
                    note = response.body();
                    if (note != null) {
                        title_edit_text.setText(note.getTitle());
                        body_edit_text.setText(note.getBody());
                    }
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertNote(Note note) {
        ServiceApi serviceApi = RetrofitInstance.getRetrofit().create(ServiceApi.class);
        Call<Note> call = serviceApi.createPost(note);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateActivity.this, "Successful Create", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}