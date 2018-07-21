package com.example.aleksejkocergin.testapp.view;

import com.arellomobile.mvp.MvpView;
import com.example.aleksejkocergin.testapp.room.Text;

import java.util.List;

public interface IHistoryView extends MvpView {

    void setHistoryList(List<Text> userTexts);
}
