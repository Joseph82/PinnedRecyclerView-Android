package com.gvillani.pinnedlist;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Giuseppe on 08/04/2016.
 */
public class GroupListWrapper {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ASCENDING, DESCENDING})
    public @interface ItemOrder {}
    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;

    List<ItemPinned> mItems;

    public GroupListWrapper() {
        mItems = new ArrayList<>();
    }

    public void addGroup(Group group){
        if(group!=null && group.getItems()!=null){
            mItems.addAll(group.getItems());
        }
    }

    public ItemPinned getItemPinned(int position) {
        if(mItems != null){
            try{
                return mItems.get(position);
            }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                throw indexOutOfBoundsException;
            }

        }else{
            return null;
        }
    }

    public Object getItem(int position){
        if(mItems != null){
                return getItemPinned(position).getItem();
        }else{
            return null;
        }
    }

    public int size(){
        if(mItems != null)
            return mItems.size();
        else
            return 0;
    }

    public static GroupListWrapper createAlphabeticList(List<Selector> items){
        return createAlphabeticList(items, ASCENDING);
    }

    public static GroupListWrapper createAlphabeticList(List<Selector> items, @ItemOrder int order){
        Collections.sort(items, new CompareItem());

        if(order == DESCENDING){
            Collections.reverse(items);
        }

        HashMap<String, List<ItemPinned>> hashMap = new HashMap<>();

        for(Selector item : items) {
            if (!hashMap.containsKey(item.select().substring(0,1).toUpperCase())){
                List<ItemPinned> list = new ArrayList<>();
                list.add(new TextItemPinned(item));

                hashMap.put(item.select().substring(0, 1).toUpperCase(), list);
            }else {
                hashMap.get(item.select().substring(0, 1).toUpperCase()).add(new TextItemPinned(item));
            }
        }

        GroupListWrapper groups = new GroupListWrapper();

        SortedSet<String> keys = new TreeSet<>(new CompareItem());

        if(order == DESCENDING){
            keys = ((TreeSet)keys).descendingSet();
        }

        keys.addAll(hashMap.keySet());

        for (String key : keys) {
            List<ItemPinned> values = hashMap.get(key);
            groups.addGroup(new Group(values, key));
        }

        return groups;
    }

    private static class CompareItem implements Comparator {
        private Collator collator;

        public CompareItem(){
            this(Locale.getDefault());
        }

        public CompareItem(Locale locale){
            this.collator = Collator.getInstance(locale);
        }

        @Override
        public int compare(Object obj1, Object obj2) {
            if(obj1 instanceof String && obj2 instanceof String){
                return collator.compare((String)obj1, (String) obj2);
            }else if(obj1 instanceof Selector && obj2 instanceof Selector){
                return collator.compare(((Selector) obj1).select(), ((Selector) obj2).select());
            }else
                return 0;

        }
    }

    public interface Selector{
        String select();
    }
}
