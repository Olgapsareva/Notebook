package ru.geekbrains.notebook.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.notebook.R;
import ru.geekbrains.notebook.data.CardData;
import ru.geekbrains.notebook.data.CardSource;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private CardSource data;
    private final OnItemClickListener listener;
    private static Fragment fragment;
    private static int selectedContextMenuItem;


    public NoteListAdapter(OnItemClickListener listener, Fragment fragment) {
        this.listener = listener;
        this.fragment = fragment;
    }

    public void setData(CardSource data) {
        this.data = data;
        notifyDataSetChanged();
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

    public int getSelectedContextMenuItem() {
        return selectedContextMenuItem;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView title;
        private final TextView date;

        public ViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.list_item_title);
            this.date = itemView.findViewById(R.id.list_item_date);

            //регистрируем контекстное меню
            registerContextMenu(itemView);

            //чтобы меню отображалось нужно повесить листнер на пользовательский элемент
            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedContextMenuItem = getLayoutPosition();
                    itemView.showContextMenu();
                    return true;
                }
            });
        }

        //регистрируем контекстное меню во фрагменте
        private void registerContextMenu(ViewGroup itemView) {
            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
            }
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
