package com.example.android.therearethesethings;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoAdapterViewHolder> {

    List<String> toDoList;
    Context context;
    MainActivity mainActivity;
    CompletedToDo completedToDo;


    public ToDoAdapter(List<String> toDoList, Context context) {
        this.toDoList = toDoList;
        this.context = context;
        mainActivity = (MainActivity) context;
    }

    @Override
    public ToDoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForTextItem = R.layout.custom_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForTextItem, parent, false);

        return new ToDoAdapterViewHolder(view, mainActivity);
    }

    @Override
    public void onBindViewHolder(final ToDoAdapterViewHolder holder, final int position) {

        String toDoPosition = toDoList.get(position);
        holder.toDoTextView.setText(toDoPosition.toString());

        if (!mainActivity.is_in_action_mode) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class ToDoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        public final TextView toDoTextView;
        MainActivity mainActivity;
        CheckBox checkBox;
        CheckBox completedCheckBox;

        public ToDoAdapterViewHolder(View itemView, MainActivity mainActivity) {

            super(itemView);
            toDoTextView = (TextView) itemView.findViewById(R.id.to_do_text_view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnLongClickListener(mainActivity);
            checkBox = (CheckBox) itemView.findViewById(R.id.context_select_checkbox);
            this.mainActivity = mainActivity;
            completedCheckBox = (CheckBox) itemView.findViewById(R.id.completed_check_box);
            checkBox.setOnClickListener(this);
            checkBox.setChecked(false);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.context_select_checkbox) {
                mainActivity.prepareSelection(v, getAdapterPosition());
            }
            if (v.getId() == R.id.completed_check_box) {
                mainActivity.addToCompleted(v, getAdapterPosition());
            }

        }

    }
    public void updateAdapter(ArrayList<String> list) {

        for(String s : list){
            toDoList.remove(s);
        }
        notifyDataSetChanged();
    }

    public void addCompleted(ArrayList<String> list){
        for(String s: list){
            toDoList.add(s);
        }
        notifyDataSetChanged();
    }

}