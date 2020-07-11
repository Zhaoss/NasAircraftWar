package com.zhaoshuang.nasaircraftwar.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class BaseActivity extends Activity {

    protected BaseActivity mContext;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
    }

    protected void showProDialog(){

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        View view = View.inflate(mContext, R.layout.dialog_loading, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    protected View showEditNameDialog(){

        dismissDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        View view = View.inflate(mContext, R.layout.dialog_edit_name, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        return view;
    }

    protected void dismissDialog(){

        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
