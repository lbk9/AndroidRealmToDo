package com.liamk.todorealm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.liamk.todorealm.Helper.RealmHelper;
import com.liamk.todorealm.Model.ToDo;
import com.liamk.todorealm.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class EmptyRecyclerViewAdapter extends RealmRecyclerViewAdapter<ToDo, EmptyRecyclerViewAdapter.MyViewHolder> {

    public EmptyRecyclerViewAdapter(@Nullable OrderedRealmCollection<ToDo> data, boolean autoUpdate) {
        super(data, autoUpdate);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public EmptyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new MyViewHolder(todoRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmptyRecyclerViewAdapter.MyViewHolder holder, int position) {
        final ToDo todoObject = getItem(position);
        holder.taskTitleText.setText(todoObject.getTitle());
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitleText;
        ImageView deleteImageButton;
        MyViewHolder(View view){
            super(view);
            taskTitleText = view.findViewById(R.id.todoTitle);
        }
    }
}
