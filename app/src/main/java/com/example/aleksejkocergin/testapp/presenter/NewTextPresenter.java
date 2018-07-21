package com.example.aleksejkocergin.testapp.presenter;


import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aleksejkocergin.testapp.App;
import com.example.aleksejkocergin.testapp.room.AppDatabase;
import com.example.aleksejkocergin.testapp.room.Text;
import com.example.aleksejkocergin.testapp.room.TextDao;
import com.example.aleksejkocergin.testapp.service.WatsonService;
import com.example.aleksejkocergin.testapp.service.LanguageTranslatorModel;
import com.example.aleksejkocergin.testapp.service.ServiceGenerator;
import com.example.aleksejkocergin.testapp.view.INewText;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewTextPresenter extends MvpPresenter<INewText> {

    private static final String TAG = NewTextPresenter.class.getSimpleName();

    private static final String username = "1af1bba3-3ece-4fd9-84c2-a408282c7cfe";
    private static final String password = "BH5ea3FBHpWF";
    private String language;

    public void getLanguage(String newText) {

        getViewState().showProgressBar();

        WatsonService watsonService =
                ServiceGenerator.createService(WatsonService.class, username, password);

        final Observable<LanguageTranslatorModel> observable = watsonService.getData(newText);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(watsonData -> {
                    language = watsonData.getLanguages().get(0).getLanguage();
                    getViewState().hideProgressBar();
                    getViewState().showLangDialog(language);
                    addInDatabase(language, newText);
                }).isDisposed();
    }

    private void addInDatabase(String languageMarker, String userText) {

        AppDatabase db = App.getInstance().getDatabase();
        TextDao textDao = db.textDao();

        Text text = new Text();
        text.setLanguageMarker(languageMarker);
        text.setUserText(userText);

        Callable<Void> clb = () -> {
            textDao.insert(text);
            return null;
        };
        Completable.fromCallable(clb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void hideDialog() {
        getViewState().hideDialog();
    }
}
