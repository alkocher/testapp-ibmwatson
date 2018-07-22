package com.example.aleksejkocergin.testapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.aleksejkocergin.testapp.R;
import com.example.aleksejkocergin.testapp.presenter.NewTextPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragNewText extends MvpAppCompatFragment implements INewText {

    private static final String TAG = FragNewText.class.getSimpleName();

    private Context context;
    private AlertDialog alertDialog;

    @InjectPresenter
    NewTextPresenter presenter;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.et_write_text)
    EditText writeText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_text, container, false);
        ButterKnife.bind(this, view);

        presenter.showKeyboard();

        writeText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (!writeText.getText().toString().equals("")) {
                presenter.getLanguage(writeText.getText().toString());
            } else {
                Toast.makeText(context, "Введите текст", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        fab.setOnClickListener(view1 -> {
            if (!writeText.getText().toString().equals("")) {
                presenter.getLanguage(writeText.getText().toString());
            } else {
                Toast.makeText(context, "Введите текст", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.setOnCancelListener(null);
            alertDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void showErrorDialog(String errorMessage) {
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(errorMessage)
                .setOnCancelListener(dialog -> presenter.hideDialog())
                .show();
    }

    @Override
    public void showLangDialog(String infoMessage) {
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(infoMessage)
                .setOnCancelListener(dialog -> presenter.hideDialog())
                .show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.cancel();
        }
    }

    @Override
    public void showKeyboard() {
        ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void hideKeyboard() {
        try {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((((Activity) context).getCurrentFocus() != null) && (((Activity) context).getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}