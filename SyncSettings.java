package com.tcl.account.activity.settings;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tcl.account.AccountContext;
import com.tcl.account.R;
import com.tcl.account.base.ToolbarActivity;
import com.tcl.base.session.AccountInfo;
import com.tcl.base.session.AccountManager;
import com.tcl.base.session.SyncSetting;
import com.tcl.base.sync.SyncManager;
import com.tcl.base.sync.SyncStatusEvent;
import com.tcl.base.sync.SyncTrackInfo;
import com.tcl.base.utils.ContextUtils;
import com.tcl.framework.log.NLog;
import com.tcl.framework.notification.Subscriber;

/**
 * @author liyang.sun
 * @Description: 自动同步设置页面
 * @date 2015-3-25 上午10:05:44
 * @copyright TCL-MIE
 */

public class SyncSettings extends ToolbarActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private Switch apps_sw, contact_sw, callrecoder_sw, sms_sw;
    private AccountManager mAccountManager;
    private AccountInfo mAccountInfo;
    private SyncSetting mSyncSetting;


    @Override
    protected void onContentCreate(Bundle savedInstanceState, View content) {
        initTitle();
        ContextUtils.detectUINetwork(getApplicationContext());
        mAccountManager = AccountContext.getAccountManager();
        if (mAccountManager == null || !mAccountManager.isAuthorized() ||
                mAccountManager.getAccountInfo() == null) {
            finish();
            return;
        }

        mAccountInfo = mAccountManager.getAccountInfo();
        mSyncSetting = mAccountInfo.syncSetting;

        findView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAccountManager == null || !mAccountManager.isAuthorized()) {
            return;
        }

        ((AccountContext) AccountContext.getInstance()).syncAccountSetting();
        refreshViews();
    }

    private void refreshViews() {
        contact_sw.setOnCheckedChangeListener(null);
        callrecoder_sw.setOnCheckedChangeListener(null);
        sms_sw.setOnCheckedChangeListener(null);

        contact_sw.setChecked(mSyncSetting.enableContactsSyncing);
        callrecoder_sw.setChecked(mSyncSetting.enableCallRecordsSyncing);
        sms_sw.setChecked(mSyncSetting.enableSMSSyncing);

        contact_sw.setOnCheckedChangeListener(this);
        callrecoder_sw.setOnCheckedChangeListener(this);
        sms_sw.setOnCheckedChangeListener(this);
    }

    private void findView() {
        contact_sw = (Switch) findViewById(R.id.account_manage_contact_sw);
        callrecoder_sw = (Switch) findViewById(R.id.account_manage_callrecoder_sw);
        sms_sw = (Switch) findViewById(R.id.account_manage_sms_sw);

        contact_sw.setOnClickListener(this);
        callrecoder_sw.setOnClickListener(this);
        sms_sw.setOnClickListener(this);

        contact_sw.setOnCheckedChangeListener(this);
        callrecoder_sw.setOnCheckedChangeListener(this);
        sms_sw.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_settings_syncsettings;
    }

    protected void initTitle() {
        setTitle(R.string.sync_settings);
        enableBack();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.account_manage_contact_sw) {
            // 同步联系人
        } else if (id == R.id.account_manage_callrecoder_sw) {
            // 同步通话记录
        } else if (id == R.id.account_manage_sms_sw) {
            // 同步短信
        }

    }

    private Runnable mDelayedAction = new Runnable() {
        @Override
        public void run() {
            AccountContext.ACCOUNT_SYNC_ENABLED = true;
        }
    };

    private void enableSync(int type, boolean checked) {
        AccountContext.ACCOUNT_SYNC_ENABLED = false;
        ((AccountContext) AccountContext.getInstance()).enableSync(type, checked);
        postDelayed(mDelayedAction, 200);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.account_manage_apps_sw) {

        } else if (id == R.id.account_manage_contact_sw) {
            enableSync(SyncTrackInfo.CONTACT_TYPE, isChecked);
        } else if (id == R.id.account_manage_callrecoder_sw) {
            enableSync(SyncTrackInfo.CALLLOG_TYPE, isChecked);
        } else if (id == R.id.account_manage_sms_sw) {
            enableSync(SyncTrackInfo.SMS_TYPE, isChecked);
        }
    }

}
