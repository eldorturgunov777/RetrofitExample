package com.example.retrofitexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitexample.CreateActivity;
import com.example.retrofitexample.MainActivity;
import com.example.retrofitexample.R;
import com.example.retrofitexample.data.Note;

import java.util.List;

/**
 * Created by Eldor Turgunov on 11.07.2022.
 * Retrofit Example
 * eldorturgunov777@gmail.com
 */
public class RetrofitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Note> list;
    MainActivity activity;

    public RetrofitAdapter(Context context, List<Note> list, MainActivity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Note note = list.get(position);
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).title.setText(note.getTitle());
            ((ViewHolder) holder).body.setText(note.getBody());

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, CreateActivity.class);
                intent.putExtra("IdExtra", note.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
            ((ViewHolder) holder).longClick.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    activity.dialogPoster(note);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;
        ConstraintLayout longClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            longClick = itemView.findViewById(R.id.longClick);
        }
    }
}
