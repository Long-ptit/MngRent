package com.restaurant.exam.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.restaurant.exam.data.model.News;

import java.util.ArrayList;
import java.util.List;

import restaurant.exam.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> list;
    Context context;
    private ItemListener itemListener;
    public void setClickListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }
    public NewsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }
    public void add(News n){
        list.add(n);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        News item = list.get(position);
        if(item==null)
            return;
        holder.title.setText(item.getTitle());
        Glide
                .with(context)
                .load(item.getImg())
                .centerCrop()
                .into(holder.imgNews);
        //Picasso.with(context).load(item.getImg()).into(holder.imgNews);

    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        return 0;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private ImageView imgNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            imgNews = itemView.findViewById(R.id.imgNews);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public News getItem(int position){
        return list.get(position);
    }
    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
