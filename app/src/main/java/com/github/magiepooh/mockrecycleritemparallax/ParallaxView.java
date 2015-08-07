package com.github.magiepooh.mockrecycleritemparallax;

import com.bumptech.glide.Glide;
import com.github.magiepooh.parallaxscrollcallback.ParallaxViewScrollCallbacks;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ParallaxView extends RelativeLayout implements ParallaxViewScrollCallbacks {

    private ImageView mImageView;

    private int imageHeight = 0;
    private float viewHeightRatio = 1f;

    public ParallaxView(Context context) {
        super(context);
        init(context);
    }

    public ParallaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParallaxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec((int) (imageHeight * viewHeightRatio),
                        View.MeasureSpec.EXACTLY));
        mImageView.measure(widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec(imageHeight, View.MeasureSpec.EXACTLY));
    }

    private void init(final Context context) {
        final View view = inflate(context, R.layout.view_parallax, this);
        mImageView = (ImageView) view.findViewById(R.id.img_view_parallax);
    }

    /**
     * @param ratio if set 0.85 ViewHeight is 85% of ImageView Height, and can scroll 15% of
     *              ImageView
     */
    public void setViewHeightRatio(float ratio) {
        viewHeightRatio = ratio;
    }

    public void setHeight(int height) {
        imageHeight = height;
        requestLayout();
    }

    public void setImage(@DrawableRes int resId) {
        Glide.with(getContext())
                .load(resId)
                .centerCrop()
                .into(mImageView);
    }

    @Override
    public void onScroll(float ratio, int scrollY) {
        int imageHeight = mImageView.getHeight();
        int canScrollSize = imageHeight - getHeight();
        float imageOffset = -(ratio * canScrollSize);
        mImageView.setTranslationY(imageOffset);
    }
}
