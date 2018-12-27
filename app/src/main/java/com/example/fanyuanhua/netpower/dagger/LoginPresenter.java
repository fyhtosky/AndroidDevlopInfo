package com.example.fanyuanhua.netpower.dagger;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class LoginPresenter {
    ICommonView iView;

    @Inject
    public LoginPresenter(ICommonView iView){

        this.iView = iView;
    }

    public void login(User user){

        Context mContext = iView.getContext();
        Toast.makeText(mContext,"login......",Toast.LENGTH_SHORT).show();
    }


}
