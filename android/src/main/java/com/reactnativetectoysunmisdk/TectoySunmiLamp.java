package com.reactnativetectoysunmisdk;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.facebook.react.bridge.*;
import com.sunmi.statuslampmanager.IStateLamp;

public class TectoySunmiLamp {

  private final ReactApplicationContext reactContext;

  private static IStateLamp lampInterface;

  public TectoySunmiLamp(ReactApplicationContext reactContext) {
    this.reactContext = reactContext;
    try {
      BindLampService();
    } catch (Exception e) {
    }
  }

  public void ControlarLuz(int status, String lamp) throws RemoteException {
    lampInterface.closeAllLamp();
    lampInterface.controlLamp(status, lamp);
  }

  public void ControlarLuzLoop(int status, long lightTime, long putoutTime, String lamp) throws RemoteException {
    lampInterface.closeAllLamp();
    lampInterface.controlLampForLoop(status, lightTime, putoutTime, lamp);
  }

  public void DesligarLuz() throws RemoteException {
    lampInterface.closeAllLamp();
  }

  private static ServiceConnection conn = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.d("TECTOY-LAMP-STATUS", "service-connected");
      lampInterface = IStateLamp.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Log.d("TECTOY-LAMP-STATUS", "service-disconnected");
      lampInterface = null;
    }
  };

  private void BindLampService() {
    Intent intent = new Intent();
    intent.setPackage("com.sunmi.statuslampmanager");
    intent.setAction("com.sunmi.statuslamp.service");
    this.reactContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
  }

}
