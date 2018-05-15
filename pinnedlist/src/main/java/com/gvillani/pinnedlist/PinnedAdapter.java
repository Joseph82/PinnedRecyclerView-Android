package com.gvillani.pinnedlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Custom Adapter that wraps the floating pin on the left side of the RecyclerView
 */
public abstract class PinnedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected GroupListWrapper mListWrapper;
    private PinnedListLayout mLayout;

    public PinnedAdapter(GroupListWrapper listGroupWrapper, PinnedListLayout layout) {
        mListWrapper = listGroupWrapper;
        mLayout = layout;
    }

    /**
     * It returns the layout that has to be passed to the view holder.
     *
     * @param rowView the layout that has to be shown
     * @return a wrapped layout
     */
    public ViewGroup getRowLayout(View rowView) {
        LinearLayout rowLayout = mLayout.getContainerRowLayout();
        rowLayout.addView(rowView);
        return rowLayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PinnedViewHolder) {
            ItemPinned currentItem = mListWrapper.getItemPinned(position);

            if (currentItem != null && currentItem.isVisible()) {
                ((PinnedViewHolder) holder).viewPin.setVisibility(View.VISIBLE);
            } else {
                ((PinnedViewHolder) holder).viewPin.setVisibility(View.INVISIBLE);
            }

            if (currentItem instanceof TextItemPinned) {
                ((TextView) ((PinnedViewHolder) holder).viewPin)
                        .setText(((TextItemPinned) currentItem).getLabel());

            } else if (currentItem instanceof ImageItemPinned) {
                ((ImageView) ((PinnedViewHolder) holder).viewPin)
                        .setImageResource(((ImageItemPinned) currentItem).getImage());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mListWrapper == null || mListWrapper.size() == 0)
            return 0;
        else
            return mListWrapper.size();
    }

    public ItemPinned getItem(int position) {
        return mListWrapper.getItemPinned(position);
    }
}

