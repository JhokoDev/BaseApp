package com.example.baseapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baseapp.R;
import com.example.baseapp.model.SettingItem;
import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingViewHolder> {

    private List<SettingItem> settingItems;
    private Context context;
    private SharedPreferences prefs;

    public SettingsAdapter(Context context, List<SettingItem> settingItems) {
        this.context = context;
        this.settingItems = settingItems;
        this.prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        SettingItem item = settingItems.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());

        if (item.isToggle()) {
            holder.switchCompat.setVisibility(View.VISIBLE);
            holder.switchCompat.setChecked(item.getToggleValue());
            holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setToggleValue(isChecked);
                prefs.edit().putBoolean(item.getKey(), isChecked).apply();
                Toast.makeText(context, item.getTitle() + ": " + (isChecked ? "Ativado" : "Desativado"), Toast.LENGTH_SHORT).show();
            });
        } else {
            holder.switchCompat.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Clicado em " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // Adicione funcionalidade futura aqui
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