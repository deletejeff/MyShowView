package com.waakka.myshowview.jpush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.waakka.myshowview.LocalBroadcastManager;
import com.waakka.myshowview.MainActivity;
import com.waakka.myshowview.TagAliasOperatorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JPushReceiver extends JPushMessageReceiver {
    private static final String TAG = "TAG";

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG,"[onMessage] "+customMessage);

        processCustomMessage(context,customMessage.message);
    }




    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageArrived] "+message);
        processCustomMessage(context,message.notificationContent);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageDismiss] "+message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG,"[onRegister] "+registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG,"[onConnected] "+isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG,"[onCommandResult] "+cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, String message) {
        if (MainActivity.isForeground) {
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }


}
