package com.gvillani.pinnedlist;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Giuseppe on 09/04/2016.
 */
public class PinnedRecyclerView extends RecyclerView {
    private Paint paint = new Paint();
    private Path path = new Path();

    public PinnedRecyclerView(Context context) {
        super(context);
        setDefaultLayoutManager();
    }

    public PinnedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setDefaultLayoutManager();
    }

    public PinnedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDefaultLayoutManager();
    }

    public void setDefaultLayoutManager(){
        setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if(getLayoutManager() == null){
            super.setLayoutManager(layout);
        }
    }

    @Override
    public LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }
}
