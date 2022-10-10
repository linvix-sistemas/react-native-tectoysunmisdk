package com.reactnativetectoysunmisdk;

import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.sunmi.scanner.*;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.sql.Array;

public class TectoySunmiScanner {

  private final ReactApplicationContext reactContext;
  private static final String ACTION_DATA_CODE_RECEIVED = "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";
  private static final String DATA = "data";
  private static final String SOURCE = "source_byte";

  private static final String JS_EVENT_NAME = "BARCODE_READED";

  private static IScanInterface scanInterface;

  public TectoySunmiScanner(ReactApplicationContext reactContext) {
    this.reactContext = reactContext;
    try {
      RegisterReceiver();
      BindScannerService();
    } catch (Exception e) {
    }
  }

  private static ServiceConnection conn = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      scanInterface = IScanInterface.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      scanInterface = null;
    }
  };

  private BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String code = intent.getStringExtra(DATA);
      byte[] arr = intent.getByteArrayExtra(SOURCE);

      // params enviados para o react native
      WritableMap params = new WritableNativeMap();

      try {
        if (code != null && !code.isEmpty()) {
          // monta o params
          params.putString("code", code);
          params.putString("bytes", arr.toString());

          // envia o evento para o react-native
          reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(JS_EVENT_NAME, params);
        }
      } catch (Exception e) {
      }
    }
  };

  public void BindScannerService() {
    Intent intent = new Intent();
    intent.setPackage("com.sunmi.scanner");
    intent.setAction("com.sunmi.scanner.IScanInterface");
    this.reactContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
  }

  private void RegisterReceiver() {
    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_DATA_CODE_RECEIVED);
    this.reactContext.registerReceiver(receiver, filter);
  }

}
