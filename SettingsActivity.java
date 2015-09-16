package com.tcl.account.activity.settings;

//import java.util.ArrayList;
//import java.util.List;

import android.content.Intent;
//import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.tcl.account.R;
import com.tcl.account.activity.userinfo.UpdatePwdActivity;
import com.tcl.account.base.ToolbarActivity;
import com.tcl.framework.log.NLog;
//import com.tcl.materialdesign.views.LayoutRipple;

/**
 * @Description: 设置页面
 * @author liyang.sun
 * @date 2015-3-25 下午8:25:15
 * @copyright TCL-MIE
 */

public class SettingsActivity extends ToolbarActivity implements View.OnClickListener {
    private RelativeLayout syncSettings, changePwd;
    public final static String TAG = "SettingsActivity";

    private void findView() {
        syncSettings = (RelativeLayout) findViewById(R.id.goto_sync_settings);
        syncSettings.setOnClickListener(this);
        changePwd = (RelativeLayout) findViewById(R.id.goto_change_pwd);
        changePwd.setOnClickListener(this);

//        int[] ids = {R.id.goto_sync_settings, R.id.goto_change_pwd};
//        LayoutRipple rippleBtn = null;
//        List<LayoutRipple> syncList = new ArrayList<LayoutRipple>();
//        for (int id : ids) {
//            rippleBtn = (LayoutRipple) findViewById(id);
//            if (rippleBtn != null) {
//                setOriginRiple(rippleBtn);
//                rippleBtn.setOnClickListener(this);
//                syncList.add(rippleBtn);
//            }
//        }
      }

//    private void setOriginRiple(LayoutRipple layoutRipple) {
//        layoutRipple.post(new RippleRunnable(layoutRipple));
//
//    }

//    private static class RippleRunnable implements Runnable {
//        LayoutRipple layoutRipple;
//
//        public RippleRunnable(LayoutRipple layoutRipple) {
//            this.layoutRipple = layoutRipple;
//        }
//
//        @Override
//        public void run() {
//            float startX = (float) layoutRipple.getWidth() / 2;
//            float startY = (float) layoutRipple.getHeight() / 2;
//            layoutRipple.setxRippleOrigin(startX);
//            layoutRipple.setyRippleOrigin(startY);
//            layoutRipple.setRippleColor(Color.parseColor("#dbecff"));
//            layoutRipple.setRippleSpeed(300);
//        }
//    }

    @Override
    protected void onContentCreate(Bundle savedInstanceState, View content) {
        initTitle();
        findView();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_settings;
    }

    long mLastClickTime;
    
    protected void initTitle() {
        setTitle(R.string.settings);
        enableBack();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        NLog.v(TAG, "onClick");

        long start = System.currentTimeMillis();
        if (start - mLastClickTime < 1000L) {
            return;
        }

        mLastClickTime = start;
        if (!isVisible()) return;
        switch (v.getId()) {
            case R.id.goto_sync_settings:
                // 跳转到同步设置页面
                Intent Syncintent = new Intent(SettingsActivity.this, SyncSettings.class);
                startActivity(Syncintent);
                break;
            case R.id.goto_change_pwd:
                // 跳转到修改密码页面
                Intent ChangePwdintent = new Intent(SettingsActivity.this, UpdatePwdActivity.class);
                startActivity(ChangePwdintent);
                break;

            default:
                break;
        }
    }
}
