package com.gvillani.pinnedlist;

import java.util.List;

/**
 * This class wraps the list of items united by the same String label or image
 */
public class Group {
    protected List<ItemPinned> items;

    /**
     * Creates a Group using a label for grouping items
     *
     * @param items the list of items
     * @param label the String that rapresent the label to be shown
     */
    public Group(List<ItemPinned> items, String label) {
        this.items = items;
        if (this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                ItemPinned itemPinned = this.items.get(i);
                initItemPosition(itemPinned, i);
                if (this.items.get(i) instanceof TextItemPinned)
                    ((TextItemPinned) this.items.get(i)).setLabel(label);
            }
        }
    }

    private void initItemPosition(ItemPinned item, int position) {
        item.setGroupSize(getGroupSize());
        item.setRelativePosition(position);
    }

    /**
     * Creates a Group using an image for grouping items
     *
     * @param items      the list of items
     * @param resourceId resource id associated with the image.
     */
    public Group(List<ItemPinned> items, int resourceId) {
        this.items = items;
        if (this.items != null) {
            for (int i = 0; i < this.items.size(); i++) {
                ItemPinned itemPinned = this.items.get(i);
                initItemPosition(itemPinned, i);
                if (this.items.get(i) instanceof ImageItemPinned)
                    ((ImageItemPinned) this.items.get(i)).setImage(resourceId);
            }
        }
    }

    private int getGroupSize() {
        if (this.items != null) {
            return this.items.size();
        }

        return 0;
    }

    public List<ItemPinned> getItems() {
        return items;
    }
}
