package com.example.aleksejkocergin.testapp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aleksejkocergin.testapp.App;
import com.example.aleksejkocergin.testapp.room.AppDatabase;
import com.example.aleksejkocergin.testapp.room.Text;
import com.example.aleksejkocergin.testapp.view.IHistoryView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class HistoryPresenter extends MvpPresenter<IHistoryView> {

    private static final String TAG = HistoryPresenter.class.getSimpleName();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    public void loadData() {
        AppDatabase db = App.getInstance().getDatabase();

        db.textDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(texts -> {
                    onLoadingSuccess(texts);
                    for (int i = 0; i < texts.size(); i++)
                    Log.d(TAG, texts.get(i).getUserText());
                });
    }

    private void onLoadingSuccess(List<Text> textList) {
        getViewState().setHistoryList(textList);
    }
}
