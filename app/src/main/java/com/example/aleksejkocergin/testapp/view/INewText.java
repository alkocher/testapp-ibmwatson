package com.example.aleksejkocergin.testapp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface INewText extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showErrorDialog(String errorMessage);

    @StateStrategyType(SingleStateStrategy.class)
    void showLangDialog(String infoMessage);

    void showProgressBar();

    void hideProgressBar();

    void hideDialog();
}
