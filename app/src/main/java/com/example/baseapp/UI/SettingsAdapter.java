package com.example.baseapp.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private final List<SettingItem> settingsList;
    private final OnSettingChangedListener listener;

    public interface OnSettingChangedListener {
        void onSettingChanged(int position, boolean isChecked);
    }

    public SettingsAdapter(List<SettingItem> settingsList, OnSettingChangedListener listener) {
        this.settingsList = settingsList;
        this.listener = listener;
    }

    public List<SettingItem> getSettingsList() {
        return settingsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SettingItem item = settingsList.get(position);
        holder.title.setText(item.getTitle());
        holder.settingSwitch.setChecked(item.isEnabled());
        holder.settingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setEnabled(isChecked);
            if (listener != null) {
                listener.onSettingChanged(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        SwitchMaterial settingSwitch;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.setting_title);
            settingSwitch = itemView.findViewById(R.id.setting_switch);
        }
    }

    public static class SettingItem {
        private String title;
        private boolean isEnabled;

        public SettingItem(String title, boolean isEnabled) {
            this.title = title;
            this.isEnabled = isEnabled;
        }

        public String getTitle() {
            return title;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        public void setEnabled(boolean enabled) {
            isEnabled = enabled;
        }
    }
}