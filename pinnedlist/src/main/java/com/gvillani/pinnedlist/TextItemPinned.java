package com.gvillani.pinnedlist;

/**
 * Created by Giuseppe on 08/04/2016.
 */
public class TextItemPinned<T> extends ItemPinned<T> {
    private String label;

    public TextItemPinned(T item) {
        super(item);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}