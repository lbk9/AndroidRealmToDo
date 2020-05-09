package com.liamk.todorealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.liamk.todorealm.Adapters.EmptyRecyclerViewAdapter;
import com.liamk.todorealm.CustomElements.EmptyRecyclerView;
import com.liamk.todorealm.Helper.RealmHelper;
import com.liamk.todorealm.Model.ToDo;
import com.squareup.picasso.Picasso;

import java.util.Random;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements AddTodo.TodoListener{

    private Realm realmDB;
    private EmptyRecyclerView recyclerView;
    private FloatingActionButton fab;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.emptyImageView);
        String url = "https://i.pinimg.com/originals/ae/8a/c2/ae8ac2fa217d23aadcc913989fcc34a2.png";
        Picasso.get().load(url).into(imageView);

        realmDB = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.emptyRecyclerView);
        recyclerView.setEmptyView(findViewById(R.id.emptyImageView));
        fab = findViewById(R.id.floatingActionButton);
        populateRecyclerView();
        final DialogFragment newTodoView = new AddTodo();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTodoView.show(getSupportFragmentManager(), "New ToDo");
            }
        });
    }

    @Override
    public void onTaskCreated(String task) {
        Random random = new Random();
        int id = random.nextInt();
        RealmHelper.addTodo(realmDB, id, task);
    }

    @Override
    public void onTaskCancel(DialogFragment dialogFragment) {
        Snackbar.make(findViewById(R.id.constraintLayout), "Cancelled", Snackbar.LENGTH_SHORT).show();
    }

    private void populateRecyclerView() {
        EmptyRecyclerViewAdapter adapter = new EmptyRecyclerViewAdapter(realmDB.where(ToDo.class).findAll(), true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SwipeHelper swipeHelper = new SwipeHelper();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class SwipeHelper extends ItemTouchHelper.SimpleCallback{
        SwipeHelper(){
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull EmptyRecyclerView.ViewHolder viewHolder, int direction) {
            RealmHelper.deleteTodo(realmDB, viewHolder.getItemId());
        }
    }
}
