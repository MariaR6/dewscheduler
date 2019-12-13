package com.example.dewscheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlantAdapter extends FirestoreRecyclerAdapter<Note, PlantAdapter.NoteHolder> {

    public PlantAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    private AdapterClickListener clickListener;

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewNumber.setText(String.valueOf(model.getNumber()));
        holder.imageViewIcon.setImageResource(IconResourceFinder.getIconResIdByIndex(model.getIcon()));
        holder.model = model;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item,
                parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null)
                    clickListener.OnClickAdapterItem((NoteHolder)v.getTag());
            }
        });
        NoteHolder holder = new NoteHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public void setOnItemClickListener(AdapterClickListener listener)
    {
        clickListener = listener;
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        Note model;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewNumber;
        ImageView imageViewIcon;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewNumber = itemView.findViewById(R.id.text_view_number);
            imageViewIcon = itemView.findViewById(R.id.image_view_icon);
        }
    }

    interface AdapterClickListener
    {
        void OnClickAdapterItem(NoteHolder item);
    }
}
