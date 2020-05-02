package com.example.drzavnamatura_endgame.RecyclerViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drzavnamatura_endgame.R;

import java.util.List;

import javax.annotation.Nonnull;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterViewHolder> {

    private Context mcontext;
    private List<GradivoItem> Data;
    private List<CjelinaItem> listCjelina;
    private int kojiRecycler = 0;
    private OnItemListener onItemListener;

    public RecyclerAdapter(Context context, List<GradivoItem> mData, OnItemListener onItemListener) {
        this.mcontext = context;
        this.Data = mData;
        this.onItemListener = onItemListener;
    }

    public RecyclerAdapter(Context context, List<CjelinaItem> listCjelina, int kojiRecycler, OnItemListener onItemListener) {
        this.mcontext = context;
        this.listCjelina = listCjelina;
        this.kojiRecycler = kojiRecycler;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layout;
        switch (kojiRecycler) {
            case 1:
                layout = LayoutInflater.from(mcontext).inflate(R.layout.item_cjelina, viewGroup, false);
                break;
            default:
                layout = LayoutInflater.from(mcontext).inflate(R.layout.item_gradivo, viewGroup, false);
                break;
        }
        return new AdapterViewHolder(layout, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        switch (kojiRecycler) {
            case 0:
                holder.naslovGradiva.setText(Data.get(position).getNaslov());
                holder.slikaGradiva.setImageResource(Data.get(position).getSlikaGradiva());
                break;
            case 1:
                holder.naslovCjeline.setText(listCjelina.get(position).getNaslovCjelina());
                break;
        }
    }

    @Override
    public int getItemCount() {

        switch (kojiRecycler) {
            case 0:
                return Data.size();
            case 1:
                return listCjelina.size();
            default:
                return 0;
        }
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView naslovGradiva, naslovCjeline, naslovBuyIcone;
        ImageView slikaGradiva, slikaIkone;
        OnItemListener onItemListener;

        AdapterViewHolder(@Nonnull View itemview, OnItemListener onItemListener) {
            super(itemview);
            switch (kojiRecycler) {
                case 0:
                    naslovGradiva = itemview.findViewById(R.id.naslovGradiva);
                    slikaGradiva = itemview.findViewById(R.id.slikaGradiva);

                    break;
                case 1:
                    naslovCjeline = itemview.findViewById(R.id.naslovCjeline);
                    break;
            }
            this.onItemListener = onItemListener;
            itemview.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }


}
