package com.github.magiepooh.mockrecycleritemparallax;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.magiepooh.mockrecycleritemparallax.binder.MainSection;
import com.github.magiepooh.mockrecycleritemparallax.binder.MainViewType;
import com.github.magiepooh.mockrecycleritemparallax.binder.ParallaxBinder;
import com.github.magiepooh.parallaxscrollcallback.ParallaxViewScrollCallbacks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter;

public class MainActivityFragment extends Fragment implements ObservableScrollViewCallbacks {

    private ObservableRecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (ObservableRecyclerView) view.findViewById(R.id.recycler_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerBinderAdapter<MainSection, MainViewType> adapter
                = new RecyclerBinderAdapter<>();

        for (int i = 0; i < 20; i++) {
            adapter.add(MainSection.NORMAL, new ParallaxBinder(getActivity()));
        }

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        mRecyclerView.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        final LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                .getLayoutManager();
        final int visibleNum = layoutManager.findLastVisibleItemPosition() - layoutManager
                .findFirstVisibleItemPosition();
        final int listHeight = mRecyclerView.getHeight();
        for (int i = 0; i <= visibleNum; i++) {
            final View child = mRecyclerView.getChildAt(i);
            if (child instanceof ParallaxViewScrollCallbacks) {
                int childTop = child.getTop();
                int childHeight = child.getHeight();

                float offset;
                if (childTop <= 0 - childHeight) {
                    offset = 0f;
                } else if (childTop >= listHeight) {
                    offset = 1f;
                } else {
                    offset = (childTop + childHeight) / (float) (listHeight + childHeight);
                }
                ((ParallaxViewScrollCallbacks) child).onScroll(offset, scrollY);
            }
        }
    }

    @Override
    public void onDownMotionEvent() {
        // no op
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        // no op
    }
}
