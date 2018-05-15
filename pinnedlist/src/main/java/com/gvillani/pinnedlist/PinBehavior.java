package com.gvillani.pinnedlist;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This class defines a Behavior for the floating indicator (image or text)
 */
public class PinBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = -1;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private int mCurrentDirection;
    private int mCurrentOffset;
    private int mRelativeOffset;

    private int mItemHeight;

    private int mBaseColor;
    private boolean mFadeEffect;
    private boolean mPinInizialized;
    private boolean mIsCenteredVertical;

    private int mCurrentImage;

    private boolean mFirstLayout;
    private int mVerticalScrollOffset;

    private boolean restoredInstance;

    /**
     * @param fadeEffect         TRUE if the floating pin should disappear with fade effect, FALSE otherwise
     * @param isCenteredVertical TRUE if the floating pin should be centered vertical respect to the RecyclerView item
     */
    public PinBehavior(boolean fadeEffect, boolean isCenteredVertical) {
        mFadeEffect = fadeEffect;
        mIsCenteredVertical = isCenteredVertical;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, View child) {
        View v = child.findViewById(R.id.pin);
        String text = null;
        if (v instanceof TextView) {
            text = ((TextView) v).getText() != null ? ((TextView) v).getText().toString() : null;
        }

        return new SavedState(super.onSaveInstanceState(parent, child), mCurrentOffset,
                text, mFadeEffect, mBaseColor, mPinInizialized, mItemHeight, mIsCenteredVertical,
                mRelativeOffset, mCurrentImage, mFirstLayout, mVerticalScrollOffset);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, View child, Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        restoredInstance = true;
        mCurrentOffset = ss.currentOffset;
        mPinInizialized = ss.labelInitialized;
        mCurrentImage = ss.currentImage;
        mFirstLayout = ss.isFirstLayout;
        mVerticalScrollOffset = ss.verticalScrollOffset;

        if (mPinInizialized) {
            child.setVisibility(View.VISIBLE);
            View v = child.findViewById(R.id.pin);
            if (v instanceof TextView) {
                ((TextView) v).setText(ss.currentText);
            } else if (v instanceof ImageView) {
                if (mCurrentImage != 0) {
                    ((ImageView) v).setImageResource(mCurrentImage);
                }
            }
        }

        mFadeEffect = ss.isFadeEnabled;
        mBaseColor = ss.baseColor;
        mItemHeight = ss.itemHeight;
        mIsCenteredVertical = ss.isCenteredVertical;
        mRelativeOffset = ss.relOffset;

        if (mIsCenteredVertical) {
            centerVerticalPin(child);
        }
    }

    private void centerVerticalPin(View container) {
        View pin = container.findViewById(R.id.pin);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) container.getLayoutParams();
        params.height = mItemHeight;
        container.setLayoutParams(params);

        FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) pin.getLayoutParams();
        params1.gravity = Gravity.CENTER_VERTICAL;
        pin.setLayoutParams(params1);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        final View c = child;

        parent.onLayoutChild(child, layoutDirection);

        child.offsetTopAndBottom(-mCurrentOffset);

        final List<View> dependencies = parent.getDependencies(child);

        if (dependencies.size() == 1 && dependencies.get(0) instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) dependencies.get(0);
            mAdapter = recyclerView.getAdapter();

            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    //Log.e("TAG","something has changed");
                    updateView(c, recyclerView, mVerticalScrollOffset - recyclerView.computeVerticalScrollOffset());
                }
            });

            if (mAdapter.getItemCount() > 0) {
                mItemHeight = recyclerView.getLayoutManager().getDecoratedMeasuredHeight((recyclerView).getChildAt(0));
            }

            mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (restoredInstance) {
                restoredInstance = false;
                updateView(child, recyclerView, mVerticalScrollOffset - recyclerView.computeVerticalScrollOffset());
                //updatePinContent(child);
            }

            if (mFirstLayout) {
                mFirstLayout = false;
                mRelativeOffset += recyclerView.getTop();
            }
        }

        child.offsetTopAndBottom(mRelativeOffset);

        if (mFadeEffect && mPinInizialized) {
            setAlpha(child);
        }

        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int dependencyOffset = dependency.getTop();
        child.offsetTopAndBottom(dependencyOffset - mRelativeOffset);
        mRelativeOffset = dependencyOffset;

        return true;
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  View child, View target,
                                  int dx, int dy,
                                  int[] consumed) {

        if (target instanceof RecyclerView) {
            if (!mPinInizialized)
                initPin(child);
            if (child.getVisibility() == View.INVISIBLE)
                child.setVisibility(View.VISIBLE);
        }

        mCurrentDirection = dy > 0 ? DIRECTION_UP : DIRECTION_DOWN;
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       View child, View directTargetChild, View target,
                                       int nestedScrollAxes) {

        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               View child, View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {

        if (target instanceof RecyclerView) {
            updateView(child, target, dyConsumed);
        }
    }

    private void updateView(View child, View target, int dy) {
        boolean isLastItem = isLastItemGroup();

        if (isLastItem) {
            mVerticalScrollOffset = ((RecyclerView) target).computeVerticalScrollOffset();
            int newOffset = mVerticalScrollOffset % mItemHeight;
            int increment = newOffset - mCurrentOffset;
            mCurrentOffset = newOffset;

            child.offsetTopAndBottom(-increment);

            if (mFadeEffect)
                setAlpha(child);

            if (mCurrentDirection == DIRECTION_DOWN ||
                    (mCurrentDirection == DIRECTION_UP && increment < 0)) {
                updatePinContent(child);
            }
        } else {

            if (mCurrentOffset != 0 || dy > mItemHeight) {
                if (mCurrentOffset != 0) {
                    child.offsetTopAndBottom(mCurrentOffset);

                    mCurrentOffset = 0;
                    if (mFadeEffect)
                        removeAlpha(child);

                }

                updatePinContent(child);
            }
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, final View child, View target, float velocityX, float velocityY) {
        if (target instanceof RecyclerView) {

            if (!mPinInizialized)
                initPin(child);
            if (child.getVisibility() == View.INVISIBLE)
                child.setVisibility(View.VISIBLE);

            mCurrentDirection = velocityY > 0 ? DIRECTION_UP : DIRECTION_DOWN;

            ((RecyclerView) target).clearOnScrollListeners();
            ((RecyclerView) target).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    updateView(child, recyclerView, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (recyclerView.computeVerticalScrollOffset() == 0) {
                            mVerticalScrollOffset = 0;
                            child.setVisibility(View.INVISIBLE);
                        }

                        updatePinContent(child);
                        recyclerView.clearOnScrollListeners();
                    }
                }
            });
        }

        return false;
    }

    private void updatePinContent(View container) {

        ItemPinned itemPinned = ((PinnedAdapter) mAdapter).getItem(mLayoutManager.findFirstVisibleItemPosition());

        View view = container.findViewById(R.id.pin);

        if (itemPinned == null) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
            if (view instanceof TextView)
                changeText((TextView) view, (TextItemPinned) itemPinned);
            else if (view instanceof ImageView)
                changeImage((ImageView) view, (ImageItemPinned) itemPinned);
        }
    }

    private void changeText(TextView view, TextItemPinned item) {
        view.setText(item.getLabel());
    }

    private void changeImage(ImageView view, ImageItemPinned item) {
        mCurrentImage = item.getImage();
        view.setImageResource(mCurrentImage);
    }

    private void initPin(View child) {
        mPinInizialized = true;

        View pin = child.findViewById(R.id.pin);
        if (mIsCenteredVertical) {
            centerVerticalPin(child);
        }

        updatePinContent(child);

        if (pin instanceof TextView)
            mBaseColor = ((TextView) pin).getCurrentTextColor();

        if (mFadeEffect) {
            setAlpha(child);
        }
    }

    private void setAlpha(View child) {
        View pin = child.findViewById(R.id.pin);

        if (pin instanceof TextView) {
            int currentAlpha = Color.alpha(mBaseColor);
            float alphaIncrement = (float) currentAlpha / (float) mItemHeight;
            int newAlpha = (int) (currentAlpha - mCurrentOffset * alphaIncrement);
            int newColor = Color.argb(newAlpha, Color.red(mBaseColor), Color.green(mBaseColor), Color.blue(mBaseColor));

            ((TextView) pin).setTextColor(newColor);
        }
    }

    private void removeAlpha(View child) {
        View pin = child.findViewById(R.id.pin);

        if (pin instanceof TextView) {
            ((TextView) pin).setTextColor(mBaseColor);
        }
    }

    private boolean isLastItemGroup() {
        ItemPinned item = ((PinnedAdapter) mAdapter).getItem(mLayoutManager.findFirstVisibleItemPosition());
        if (item == null) {
            return true;
        } else {
            return item.isLast();
        }
    }

    protected static class SavedState extends View.BaseSavedState {

        int currentOffset;
        String currentText;
        boolean isFadeEnabled;
        int baseColor;
        boolean labelInitialized;
        int itemHeight;
        boolean isCenteredVertical;
        int relOffset;
        int currentImage;
        boolean isFirstLayout;
        int verticalScrollOffset;

        public SavedState(Parcel source) {
            super(source);

            currentOffset = source.readInt();
            currentText = source.readString();
            isFadeEnabled = source.readInt() == 0 ? false : true;
        }

        public SavedState(Parcelable superState, int currentOffset, String currentText,
                          boolean isFadeEnabled, int baseColor, boolean labelInitialized,
                          int itemHeight, boolean isCenteredVertical, int relOffset,
                          int currentImage, boolean isFirstLayout, int verticalScrollOffset) {
            super(superState);
            this.currentOffset = currentOffset;
            this.currentText = currentText;
            this.isFadeEnabled = isFadeEnabled;
            this.baseColor = baseColor;
            this.labelInitialized = labelInitialized;
            this.itemHeight = itemHeight;
            this.isCenteredVertical = isCenteredVertical;
            this.relOffset = relOffset;
            this.currentImage = currentImage;
            this.isFirstLayout = isFirstLayout;
            this.verticalScrollOffset = verticalScrollOffset;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentOffset);
            out.writeString(currentText);
            out.writeInt(isFadeEnabled ? 1 : 0);
            out.writeInt(baseColor);
            out.writeInt(labelInitialized ? 1 : 0);
            out.writeInt(itemHeight);
            out.writeInt(isCenteredVertical ? 1 : 0);
            out.writeInt(relOffset);
            out.writeInt(isFirstLayout ? 1 : 0);
            out.writeInt(verticalScrollOffset);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}