package com.example.aleksejkocergin.testapp.view.adapters;

import android.widget.BaseAdapter;

import com.arellomobile.mvp.MvpDelegate;

public abstract class MvpBaseAdapter extends BaseAdapter {
    private MvpDelegate<? extends MvpBaseAdapter> mMvpDelegate;
    private MvpDelegate<?> mParentDelegate;
    private String mChildId;

    public MvpBaseAdapter(MvpDelegate<?> parentDelegate) {
        mParentDelegate = parentDelegate;
        //mChildId = childId;

        getMvpDelegate().onCreate();
    }

    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
            mMvpDelegate.setParentDelegate(mParentDelegate, mChildId);
        }
        return mMvpDelegate;
    }
}
