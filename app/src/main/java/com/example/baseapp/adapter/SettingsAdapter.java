package com.example.baseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.model.SettingItem;
import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingViewHolder> {

    private final List<SettingItem> settingItems;
    private final Context context;
    private final OnSettingChangeListener listener;

    public interface OnSettingChangeListener {
        void onSettingChanged(SettingItem item, boolean isChecked);
    }

    public SettingsAdapter(Context context, List<SettingItem> settingItems, OnSettingChangeListener listener) {
        this.context = context;
        this.settingItems = settingItems;
        this.listener = listener;
    }

    public List<SettingItem> getSettingItems() {
        return settingItems;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        SettingItem item = settingItems.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.switchCompat.setVisibility(item.isToggle() ? View.VISIBLE : View.GONE);
        holder.switchCompat.setChecked(item.getToggleValue());
        holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setToggleValue(isChecked);
            if (listener != null) {
                listener.onSettingChanged(item, isChecked);
            }
        });
        if (!item.isToggle()) {
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSettingChanged(item, false);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return settingItems.size();
    }

    static class SettingViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        SwitchCompat switchCompat;

        SettingViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.setting_title);
            description = itemView.findViewById(R.id.setting_description);
            switchCompat = itemView.findViewById(R.id.setting_switch);
        }
    }
}