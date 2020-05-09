package com.liamk.todorealm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddTodo extends DialogFragment {
    TodoListener todoListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            todoListener = (TodoListener) context;
        } catch (Exception e){
            try {
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View popupView = inflater.inflate(R.layout.add_todo, null);
        final EditText addTodoInput = popupView.findViewById(R.id.addTodoTitle);
        builder.setView(popupView).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = addTodoInput.getText().toString();
                todoListener.onTaskCreated(title);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoListener.onTaskCancel(AddTodo.this);
                AddTodo.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public interface TodoListener {
        void onTaskCreated(String task);
        void onTaskCancel(DialogFragment dialogFragment);
    }
}
