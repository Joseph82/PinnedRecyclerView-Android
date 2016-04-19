package com.gvillani.pinnedlist;

/**
 * Created by Giuseppe on 08/04/2016.
 */
public class ImageItemPinned<T> extends ItemPinned<T>{
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
