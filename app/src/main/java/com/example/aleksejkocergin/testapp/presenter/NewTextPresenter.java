package com.example.aleksejkocergin.testapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aleksejkocergin.testapp.App;
import com.example.aleksejkocergin.testapp.room.AppDatabase;
import com.example.aleksejkocergin.testapp.room.Text;
import com.example.aleksejkocergin.testapp.room.TextDao;
import com.example.aleksejkocergin.testapp.service.WatsonService;
import com.example.aleksejkocergin.testapp.service.IdentifyModel;
import com.example.aleksejkocergin.testapp.service.ServiceGenerator;
import com.example.aleksejkocergin.testapp.view.INewText;

import java.util.Locale;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewTextPresenter extends MvpPresenter<INewText> {

    private static final String username = "1af1bba3-3ece-4fd9-84c2-a408282c7cfe";
    private static final String password = "BH5ea3FBHpWF";
    private String language;
    private CompositeDisposable mDisposable;

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        mDisposable = new CompositeDisposable();
    }

    public void getLanguage(String userText) {

        getViewState().showProgressBar();

        WatsonService watsonService =
                ServiceGenerator.createService(WatsonService.class, username, password);

        mDisposable.add(watsonService.getData(userText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<IdentifyModel>() {
                    @Override
                    public void onNext(IdentifyModel languageData) {
                        language = new Locale(languageData.getLanguages().get(0).getLanguage()).getDisplayName();
                        getViewState().hideProgressBar();
                        getViewState().showLangDialog(language);
                        addInDatabase(language, userText);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideProgressBar();
                        getViewState().showErrorDialog(e.getMessage().toLowerCase());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
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

        mDisposable.clear();
    }

    public void hideDialog() {
        getViewState().hideDialog();
    }
}
