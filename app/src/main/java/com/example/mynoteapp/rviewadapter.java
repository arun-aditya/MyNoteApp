package com.example.mynoteapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class rviewadapter extends FirestoreRecyclerAdapter<modelclass,rviewadapter.vholder> {
    onNoteClick monNoteClick;

    public rviewadapter(@NonNull FirestoreRecyclerOptions<modelclass> options, rviewadapter.onNoteClick onNoteClick) {
        super(options);
        this.monNoteClick = onNoteClick;
    }

    public rviewadapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull vholder holder, int position, @NonNull modelclass model) {
        holder.heading.setText(model.getHead());
        holder.description.setText(model.getDesc());
        holder.date_.setText(model.getDate());
        holder.day_.setText(model.getDay());

        holder.colorbar.setBackgroundColor(Color.parseColor(model.getCol()));
    }

    @NonNull
    @Override
    public vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item1_recyclerview,parent,false);
        return new vholder(view,monNoteClick);
    }


    class vholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout colorbar;
        TextView heading,description,date_,day_;
        onNoteClick onNoteClick;

        public vholder(@NonNull View itemView,onNoteClick onNoteClick) {
            super(itemView);
            heading=itemView.findViewById(R.id.head_item);
            description=itemView.findViewById(R.id.body_item);
            date_=itemView.findViewById(R.id.date_item);
            day_=itemView.findViewById(R.id.day_item);
            colorbar= itemView.findViewById(R.id.colour_item);
            this.onNoteClick=onNoteClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteClick.onNoteItemSelected(getAdapterPosition(),getItem(getAdapterPosition()),itemView);
        }
    }
    public interface onNoteClick{
        void onNoteItemSelected(int position,modelclass modelclass,View view);
    }
}
