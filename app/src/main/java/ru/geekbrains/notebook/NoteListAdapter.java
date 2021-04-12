package ru.geekbrains.notebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private CardSource data;
    private OnItemClickListener listener;

    public NoteListAdapter(CardSource data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView linearLayout = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(data.getCardData(position));

        holder.getTitleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getTitleView(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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


        public void setData(CardData cardData) {
            title.setText(cardData.getTitle());
            date.setText(cardData.getDateOfCreation().toString());
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
