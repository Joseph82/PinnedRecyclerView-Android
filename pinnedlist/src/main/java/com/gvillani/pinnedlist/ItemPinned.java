package com.gvillani.pinnedlist;

/**
 * Created by Giuseppe on 08/04/2016.
 */
public class ItemPinned<T> {
    private int groupSize;
    private int relativePosition;
    private T item;

    public ItemPinned(T item) {
        this.item = item;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public T getItem() {
        return item;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public boolean isVisible() {
        if (relativePosition == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLast() {
        if (relativePosition == groupSize - 1) {
            return true;
        } else {
            return false;
        }
    }
}


