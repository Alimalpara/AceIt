package com.android.aceit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView maintv;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
         maintv = itemView.findViewById(R.id.tvMainRv);
    }
}
