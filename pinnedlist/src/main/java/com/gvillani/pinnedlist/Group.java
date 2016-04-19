package com.gvillani.pinnedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giuseppe on 08/04/2016.
 */
public class Group<T> {
    protected List<ItemPinned<T>> items;

    public Group(){
        items = new ArrayList<>();
    }

    public Group(List<ItemPinned<T>> items, String label) {
        this.items = items;
        if(this.items!=null){
            for(int i = 0; i < this.items.size(); i++){
                ItemPinned itemPinned = this.items.get(i);
                initItemPosition(itemPinned, i);
                if(this.items.get(i) instanceof TextItemPinned)
                    ((TextItemPinned)this.items.get(i)).setLabel(label);
            }
        }
    }

    private void initItemPosition(ItemPinned item, int position){
        item.setGroupSize(getGroupSize());
        item.setRelativePosition(position);
    }

    public Group(List<ItemPinned<T>> items, int resourceId) {
        this.items = items;
        if(this.items!=null){
            for(int i = 0; i < this.items.size(); i++){
                ItemPinned itemPinned = this.items.get(i);
                initItemPosition(itemPinned, i);
                if(this.items.get(i) instanceof ImageItemPinned)
                    ((ImageItemPinned)this.items.get(i)).setImage(resourceId);
            }
        }
    }

    private int getGroupSize(){
        if(this.items!=null){
            return this.items.size();
        }

        return 0;
    }

    public List<ItemPinned<T>> getItems() {
        return items;
    }

}
