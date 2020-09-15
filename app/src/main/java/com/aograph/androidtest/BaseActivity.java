package com.aograph.androidtest;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by tangqipeng
 * 2018/7/19
 * email: tangqipeng@aograph.com
 */
public class BaseActivity extends AppCompatActivity {

//    @Override
//    protected void onStart() {
//        super.onStart();
//        AoGraph.activityStarted(this);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); //统计时长
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        AoGraph.activityStopped(this);
//    }


    //Only one sim card
    private static final int PHONE_TYPE_CMCC = 0;
    private static final int PHONE_TYPE_UNICOM = 1;
    private static final int PHONE_TYPE_CDMA = 2;

    private int getPhoneType() {
        int phoneType = -1;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mccMnc = tm.getSimOperator();
        if ("46000".equals(mccMnc) || "46002".equals(mccMnc)
                || "46007".equals(mccMnc) || "46008".equals(mccMnc)
                || "45412".equals(mccMnc)) {
            phoneType = PHONE_TYPE_CMCC;
        } else if ("46001".equals(mccMnc) || "46006".equals(mccMnc)
                || "46009".equals(mccMnc)) {
            phoneType = PHONE_TYPE_UNICOM;
        } else if ("46003".equals(mccMnc) || "46005".equals(mccMnc)
                || "46011".equals(mccMnc) || "45502".equals(mccMnc)
                || "45507".equals(mccMnc)) {
            phoneType = PHONE_TYPE_CDMA;
        }
        return phoneType;
    }

    /**
     * 是否支持摄像头
     *
     * @return
     */
    private boolean hasCamera() {
        boolean hasCamera = false;
        PackageManager pm = this.getPackageManager();
        hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
                Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD || Camera.getNumberOfCameras() > 0;
        return hasCamera;
    }

    private void isBluetooth() {
        // 获取本地蓝牙适配器
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean isHook() {
        try {
            throw new Exception("Deteck hook");
        } catch (Exception e) {
            int zygoteInitCallCount = 0;
            for (StackTraceElement item : e.getStackTrace()) {
                // 检测"com.android.internal.os.ZygoteInit"是否出现两次，如果出现两次，则表明Substrate框架已经安装
                if (item.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    zygoteInitCallCount++;
                    if (zygoteInitCallCount == 2) {
                        Log.wtf("HookDetection", "Substrate is active on the device.");
                        return true;
                    }
                }

                if (item.getClassName().equals("com.saurik.substrate.MS$2") && item.getMethodName().equals("invoke")) {
                    Log.wtf("HookDetection", "A method on the stack trace has been hooked using Substrate.");
                    return true;
                }

                if (item.getClassName().equals("de.robv.android.xposed.XposedBridge")
                        && item.getMethodName().equals("main")) {
                    Log.wtf("HookDetection", "Xposed is active on the device.");
                    return true;
                }
                if (item.getClassName().equals("de.robv.android.xposed.XposedBridge")
                        && item.getMethodName().equals("handleHookedMethod")) {
                    Log.wtf("HookDetection", "A method on the stack trace has been hooked using Xposed.");
                    return true;
                }

            }

        }
        return false;
    }

}
