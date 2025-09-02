package com.example.baseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.model.ProfileItem;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private final List<ProfileItem> profileItems;
    private final Context context;
    private final OnProfileItemClickListener listener;

    public interface OnProfileItemClickListener {
        void onItemClick(ProfileItem item, int position);
    }

    public ProfileAdapter(Context context, List<ProfileItem> profileItems, OnProfileItemClickListener listener) {
        this.context = context;
        this.profileItems = profileItems;
        this.listener = listener;
    }

    public void updateItems(List<ProfileItem> newItems) {
        profileItems.clear();
        profileItems.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        ProfileItem item = profileItems.get(position);
        holder.title.setText(item.getTitle());
        holder.value.setText(item.getValue());
        holder.value.setVisibility(item.isAction() ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(item.isAction() ? v -> {
            if (listener != null) {
                listener.onItemClick(item, position);
            }
        } : null);
    }

    @Override
    public int getItemCount() {
        return profileItems.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView value;

        ProfileViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.profile_title);
            value = itemView.findViewById(R.id.profile_value);
        }
    }
}