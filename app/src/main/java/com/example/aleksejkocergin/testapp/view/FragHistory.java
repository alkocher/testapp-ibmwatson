package com.example.aleksejkocergin.testapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.aleksejkocergin.testapp.R;
import com.example.aleksejkocergin.testapp.presenter.HistoryPresenter;
import com.example.aleksejkocergin.testapp.room.Text;
import com.example.aleksejkocergin.testapp.view.adapters.HistoryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragHistory extends MvpAppCompatFragment implements IHistoryView {

    private static final String TAG = FragHistory.class.getSimpleName();

    @InjectPresenter
    HistoryPresenter mHistoryPresenter;

    @BindView(R.id.fragment_list_view)
    ListView textsListView;

    private HistoryAdapter mHistoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        mHistoryAdapter = new HistoryAdapter(getMvpDelegate());
        textsListView.setAdapter(mHistoryAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setHistoryList(List<Text> userTexts) {
        mHistoryAdapter.setTexts(userTexts);
    }
}
