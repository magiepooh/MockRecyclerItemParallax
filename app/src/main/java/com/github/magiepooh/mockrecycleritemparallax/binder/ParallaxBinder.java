package com.github.magiepooh.mockrecycleritemparallax.binder;

import com.github.magiepooh.mockrecycleritemparallax.ParallaxView;
import com.github.magiepooh.mockrecycleritemparallax.R;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

import jp.satorufujiwara.binder.recycler.RecyclerBinder;

public class ParallaxBinder extends RecyclerBinder<MainViewType> {

    private static final int[] mResArray = {
            R.drawable.picture1,
            R.drawable.picture2,
            R.drawable.picture3,
            R.drawable.picture4,
            R.drawable.picture5,
            R.drawable.picture6,
            R.drawable.picture7,
            R.drawable.picture8};

    private static final int[] mImageRatioArray = {
            640 / 360,
            640 / 426,
            640 / 480,
            700 / 467,
            800 / 534,
            990 / 742,
            740 / 334,
            740 / 334
    };

    public ParallaxBinder(Activity activity) {
        super(activity, MainViewType.PARALLAX_ITEM);
    }

    @Override
    public int layoutResId() {
        return R.layout.row_parallax;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
//        holder.mParallaxView.setImage(mResArray[position % mResArray.length]);
        holder.mParallaxView.setViewHeightRatio(0.85f);
        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        holder.mParallaxView.setHeight(
                metrics.widthPixels / mImageRatioArray[position % mImageRatioArray.length]);

        holder.mParallaxView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        holder.mParallaxView.getViewTreeObserver().removeOnPreDrawListener(this);
                        holder.mParallaxView.setImage(mResArray[position % mResArray.length]);
                        return false;
                    }
                });

    }

    private static final class ViewHolder extends RecyclerView.ViewHolder {

        private final ParallaxView mParallaxView;

        public ViewHolder(View itemView) {
            super(itemView);
            mParallaxView = (ParallaxView) itemView.findViewById(R.id.view_row_parallax_root);
        }
    }
}
