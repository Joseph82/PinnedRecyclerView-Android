package com.gvillani.pinnedlist;

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

    /**
     * Sets the size of the group. It should not be called explicitally
     *
     * @param groupSize
     */
    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public T getItem() {
        return item;
    }

    /**
     * Sets the position of the item inside the group. It should not be called explicitally
     *
     * @param relativePosition
     */
    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    /**
     * Checks if the pin should appear in the current item. This happens when it is in position 0
     * @return TRUE if is visible. FALSE otherwise
     */
    public boolean isVisible() {
        if (relativePosition == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the item is the last of the group
     * @return TRUE if is last. FALSE otherwise
     */
    public boolean isLast() {
        if (relativePosition == groupSize - 1) {
            return true;
        } else {
            return false;
        }
    }
}


