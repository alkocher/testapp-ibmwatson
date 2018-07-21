package com.example.aleksejkocergin.testapp.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpDelegate;
import com.example.aleksejkocergin.testapp.R;
import com.example.aleksejkocergin.testapp.room.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends MvpBaseAdapter {

    private List<Text> mTexts;

    public HistoryAdapter(MvpDelegate<?> parentDelegate) {
        super(parentDelegate);
        mTexts = new ArrayList<>();
    }

    public void setTexts(List<Text> textList) {
        mTexts.addAll(textList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTexts.size();
    }

    @Override
    public Text getItem(int position) {
        return mTexts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryHolder holder;
        if (convertView != null) {
            holder = (HistoryHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hitory_item_view,
                    parent, false);
            holder = new HistoryHolder(convertView);
            convertView.setTag(holder);
        }
        final Text item = getItem(position);
        holder.newText.setText(item.getUserText());
        holder.markerText.setText(item.getLanguageMarker());

        return convertView;
    }

    public class HistoryHolder {

        @BindView(R.id.laguage_marker_text_view)
        TextView markerText;
        @BindView(R.id.new_text_view)
        TextView newText;

        /*private MvpDelegate mMvpDelegate;
        private Text mText;*/
        View view;

        HistoryHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }

        /*void bind(int position, Text text) {
            if (getMvpDelegate() != null) {
                getMvpDelegate().onSaveInstanceState();
                getMvpDelegate().onDetach();
                getMvpDelegate().onDestroyView();
                mMvpDelegate = null;
            }
            mText = text;
            getMvpDelegate().onCreate();
            getMvpDelegate().onAttach();
        }

        MvpDelegate getMvpDelegate() {
            if (mText == null) {
                return null;
            }
            if (mMvpDelegate == null) {
                mMvpDelegate = new MvpDelegate<>(this);
                mMvpDelegate.setParentDelegate(HistoryAdapter.this.getMvpDelegate(), String.valueOf(mText.getId()));
            }
            return mMvpDelegate;
        }*/
    }
}
