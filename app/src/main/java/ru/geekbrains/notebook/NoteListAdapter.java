package ru.geekbrains.notebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private Note[] data;
    private OnItemClickListener listener;

    public NoteListAdapter(Note[] data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleView().setText(data[position].getTitle());
        holder.getDateView().setText(data[position].getDateOfCreation().toString());

        holder.getTitleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getTitleView(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView date;

        public ViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.list_item_title);
            this.date = itemView.findViewById(R.id.list_item_date);
        }

        public TextView getTitleView() {
            return title;
        }

        public TextView getDateView() {
            return date;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
