package com.github.magiepooh.mockrecycleritemparallax.binder;

import jp.satorufujiwara.binder.ViewType;

public enum MainViewType implements ViewType {
    PARALLAX_ITEM;

    @Override
    public int viewType() {
        return ordinal();
    }
}
