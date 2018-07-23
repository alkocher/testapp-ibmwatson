package com.example.aleksejkocergin.testapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aleksejkocergin.testapp.App;
import com.example.aleksejkocergin.testapp.room.AppDatabase;
import com.example.aleksejkocergin.testapp.room.Text;
import com.example.aleksejkocergin.testapp.view.IHistoryView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class HistoryPresenter extends MvpPresenter<IHistoryView> {

    private CompositeDisposable mDisposable;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mDisposable = new CompositeDisposable();
        loadData();
    }

    private void loadData() {
        AppDatabase db = App.getInstance().getDatabase();

        mDisposable.add(db.textDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onLoadingSuccess));
    }

    private void onLoadingSuccess(List<Text> textList) {
        getViewState().setHistoryList(textList);
        mDisposable.clear();
    }
}