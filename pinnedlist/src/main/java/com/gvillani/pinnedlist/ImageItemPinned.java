package com.gvillani.pinnedlist;

public class ImageItemPinned<T> extends ItemPinned<T> {
    private int image;

    public ImageItemPinned(T item) {
        super(item);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
