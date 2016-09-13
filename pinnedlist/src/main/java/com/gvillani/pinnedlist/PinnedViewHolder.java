package com.gvillani.pinnedlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PinnedViewHolder extends RecyclerView.ViewHolder {
    protected View viewPin;

    public PinnedViewHolder(View itemView) {
        super(itemView);
        viewPin = itemView.findViewById(R.id.fixed_pin_id);
    }
}