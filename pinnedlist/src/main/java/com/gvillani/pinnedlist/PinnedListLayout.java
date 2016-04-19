package com.gvillani.pinnedlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Giuseppe on 09/04/2016.
 */
public class PinnedListLayout extends CoordinatorLayout {
    private final static int TYPE_TEXTVIEW = 0;
    private final static int TYPE_IMAGEVIEW = 1;

    private final static int PIN_GRAVITY_TOP = 0;
    private final static int PIN_GRAVITY_CENTER = 1;

    private final static int DEFAULT_PIN_LAYOUT_WIDTH = 36;
    private final static int DEFAULT_PIN_LAYOUT_HEIGHT = 36;
    private final static int DEFAULT_PADDING = 3;

    private RecyclerView mRecyclerView;
    private FrameLayout mPinLayout;

    private int mPinLayoutHeight;
    private int mPinLayoutWidth;

    private int mPinVerticalPosition, mPinMarginLeft, mPinMarginRight, mPinMarginTop, mPinMarginBottom;

    private float mPinTextSize;
    private int mPinTextColor;

    private boolean mFadeEffect;
    private Behavior mBehavior;

    private int mType;

    public PinnedListLayout(Context context) {
        super(context);
        initializeViews(context);
    }

    public PinnedListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PinnedListLayout,
                0, 0);

        try {

            mType = a.getInteger(R.styleable.PinnedListLayout_type, TYPE_TEXTVIEW);

            if(a.hasValue(R.styleable.PinnedListLayout_layout_pin_width)){
                try{
                    mPinLayoutWidth = a.getInt(R.styleable.PinnedListLayout_layout_pin_width, ViewGroup.LayoutParams.WRAP_CONTENT);
                }catch (NumberFormatException e){
                    mPinLayoutWidth = a.getDimensionPixelSize(R.styleable.PinnedListLayout_layout_pin_width,
                            PinUtils.dpToPx(context, DEFAULT_PIN_LAYOUT_WIDTH));
                }
            }else{
                if(mType == TYPE_TEXTVIEW)
                    mPinLayoutWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
                else if (mType == TYPE_IMAGEVIEW)
                    mPinLayoutWidth = PinUtils.dpToPx(context, DEFAULT_PIN_LAYOUT_WIDTH);
            }

            if(a.hasValue(R.styleable.PinnedListLayout_layout_pin_height)){
                try{
                    mPinLayoutHeight = a.getInt(R.styleable.PinnedListLayout_layout_pin_height, ViewGroup.LayoutParams.WRAP_CONTENT);
                }catch (NumberFormatException e){
                    mPinLayoutHeight = a.getDimensionPixelSize(R.styleable.PinnedListLayout_layout_pin_height,
                            PinUtils.dpToPx(context, DEFAULT_PIN_LAYOUT_HEIGHT));
                }
            }else{
                if(mType == TYPE_TEXTVIEW)
                    mPinLayoutHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
                else if (mType == TYPE_IMAGEVIEW)
                    mPinLayoutHeight = PinUtils.dpToPx(context, DEFAULT_PIN_LAYOUT_WIDTH);
            }

            if(a.hasValue(R.styleable.PinnedListLayout_pin_margin)){
                mPinMarginLeft = mPinMarginRight = mPinMarginTop = mPinMarginBottom =
                        a.getDimensionPixelSize(R.styleable.PinnedListLayout_pin_margin,
                                PinUtils.dpToPx(context, DEFAULT_PADDING));
            }else{
                mPinMarginLeft = a.getDimensionPixelSize(R.styleable.PinnedListLayout_pin_margin_left,
                        PinUtils.dpToPx(context, DEFAULT_PADDING));
                mPinMarginRight = a.getDimensionPixelSize(R.styleable.PinnedListLayout_pin_margin_right,
                        PinUtils.dpToPx(context, DEFAULT_PADDING));
                mPinMarginTop = a.getDimensionPixelSize(R.styleable.PinnedListLayout_pin_margin_top,
                        PinUtils.dpToPx(context, DEFAULT_PADDING));
                mPinMarginBottom = a.getDimensionPixelSize(R.styleable.PinnedListLayout_pin_margin_bottom,
                        PinUtils.dpToPx(context, DEFAULT_PADDING));
            }

            mPinTextSize = a.getDimensionPixelSize(R.styleable.PinnedListLayout_pin_text_size,
                    PinUtils.dpToPx(context, -1));

            mFadeEffect = a.getBoolean(R.styleable.PinnedListLayout_fade_effect, false);

            mPinTextColor = a.getColor(R.styleable.PinnedListLayout_pin_text_color, -1);

            mPinVerticalPosition = a.getInt(R.styleable.PinnedListLayout_pin_vertical_position, PIN_GRAVITY_TOP);

        } finally {
            a.recycle();
        }

        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(mType == TYPE_TEXTVIEW)
            inflater.inflate(R.layout.tv_indicator, this);
        else
            inflater.inflate(R.layout.iv_indicator, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRecyclerView = (RecyclerView)this.findViewById(R.id.custom_list);
        mRecyclerView.setHasFixedSize(true);

        mPinLayout = (FrameLayout)this.findViewById(R.id.layout_pin);


        int colorDrawable;
        try{
            colorDrawable = ((ColorDrawable)this.getBackground()).getColor();
        }catch (NullPointerException e){
            // TODO: get the default background color
            colorDrawable = Color.parseColor("#EEEEEE");
        }

        int opaqueColor = Color.argb(255, Color.red(colorDrawable), Color.green(colorDrawable), Color.blue(colorDrawable));

        this.setBackgroundColor(opaqueColor);
        mPinLayout.setBackgroundColor(opaqueColor);

        View pin = mPinLayout.findViewById(R.id.pin);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mPinLayoutWidth, mPinLayoutHeight);
        pin.setLayoutParams(params);
        mPinLayout.setPadding(mPinMarginLeft, mPinMarginTop, mPinMarginRight, mPinMarginBottom);

        pin.setBackgroundColor(opaqueColor);

        if(mType == TYPE_TEXTVIEW){
            if(mPinTextSize != -1)
                ((TextView) pin).setTextSize(TypedValue.COMPLEX_UNIT_PX, mPinTextSize);
            if(mPinTextColor != -1)
                ((TextView) pin).setTextColor(mPinTextColor);
        }else if(mType == TYPE_IMAGEVIEW){
            // some operations for ImageView
        }

        setIndicatorAnimation();
    }

    private void setIndicatorAnimation(){
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams)mPinLayout.getLayoutParams();
        mBehavior = new PinBehavior(mFadeEffect, mPinVerticalPosition == PIN_GRAVITY_CENTER);
        params.setBehavior(mBehavior);
        mPinLayout.requestLayout();
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public LinearLayout getContainerRowLayout(){
        LinearLayout ll = new LinearLayout(this.getContext());
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(mPinVerticalPosition == PIN_GRAVITY_CENTER){
            ll.setGravity(Gravity.CENTER_VERTICAL);
        }
        ll.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout frameLayout = (FrameLayout)getPinLayout();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        frameLayout.setLayoutParams(params);
        ll.addView(frameLayout);

        return ll;
    }

    private View getPinLayout(){
        FrameLayout pinLayout = new FrameLayout(this.getContext());

        pinLayout.setPadding(mPinLayout.getPaddingLeft(), mPinLayout.getPaddingTop(), mPinLayout.getPaddingRight(), mPinLayout.getPaddingBottom());

        if(mType == TYPE_TEXTVIEW){
            TextView tvPin = ((TextView) mPinLayout.findViewById(R.id.pin));

            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            TextView tvLabel = (TextView)(inflater.inflate(R.layout.pin_tv, null));
            tvLabel.setLayoutParams(tvPin.getLayoutParams());

            tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvPin.getTextSize());

            if(mPinTextColor!= -1)
                tvLabel.setTextColor(mPinTextColor);

            tvLabel.setId(R.id.fixed_pin_id);

            pinLayout.addView(tvLabel);

        }else if(mType == TYPE_IMAGEVIEW){
            ImageView ivPin = ((ImageView) mPinLayout.findViewById(R.id.pin));

            ImageView ivLabel = new ImageView(this.getContext());

            ivLabel.setLayoutParams(ivPin.getLayoutParams());
            ivLabel.setId(R.id.fixed_pin_id);

            pinLayout.addView(ivLabel);
        }

        return pinLayout;
    }
}
