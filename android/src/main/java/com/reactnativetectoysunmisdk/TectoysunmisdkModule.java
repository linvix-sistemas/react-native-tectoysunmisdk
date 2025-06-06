package com.reactnativetectoysunmisdk;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.zxing.WriterException;
import com.sunmi.extprinterservice.ExtPrinterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = TectoySunmiSdkModule.NAME)
public class TectoySunmiSdkModule extends ReactContextBaseJavaModule {
  public static final String NAME = "TectoySunmiSdk";

  private final Context appContext;

  private final TectoySunmiScanner scannerHelper;
  private final TectoySunmiLamp lampHelper;
  private ExtPrinterService extPrinterService = null;


  @SuppressLint("StaticFieldLeak")
  public static KTectoySunmiPrinter kPrinterPresenter;

  public TectoySunmiSdkModule(ReactApplicationContext context) {
    super(context);

    // pega o contexto da aplicação
    this.appContext = context.getApplicationContext();

    if (getDeviceName().equals("SUNMI K2")) {
      connectKPrintService();
    } else {
      TectoySunmiPrint.getInstance().initSunmiPrinterService(context);
    }

    // cria o scanner helper
    scannerHelper = new TectoySunmiScanner(context);

    // cria o lamp helper
    lampHelper = new TectoySunmiLamp(context);

    // cria pasta das imagens
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "/MeuApp/imagens/");
      directory.mkdirs();
    }
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // -----------------------------------------------------------------------------------------------------------------------
  // -------------- IMPRESSORA
  // -----------------------------------------------------------------------------------------------------------------------


  @ReactMethod
  public void cutpaper() {
    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.cutPaper(1, 2);
    } else {
      TectoySunmiPrint.getInstance().cutPaper();
    }
  }

  @ReactMethod
  public void printQr(String txt, int size, int error) {
    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.printQrCode(txt, size, error);
    } else {
      TectoySunmiPrint.getInstance().printQrCode(txt, size, error);
    }
  }

  @ReactMethod
  public void printBarcode(String text, int type, int weight, int height, int text_position) {
    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.barcode(text, type, weight, height, text_position);
    } else {
      TectoySunmiPrint.getInstance().initPrinter();
      TectoySunmiPrint.getInstance().printBarCode(text, type, weight, height, text_position);
      TectoySunmiPrint.getInstance().feedPaper();
    }
  }

  @ReactMethod
  public void openCashBox(final Promise promise) {
    if (getDeviceName().equals("SUNMI K2")) {

    } else {
      TectoySunmiPrint.getInstance().openCashBox(promise);
    }
  }

  @ReactMethod
  public void printText(String texto) {
    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.printText(texto);
    } else {
      TectoySunmiPrint.getInstance().printText(texto);
    }
  }

  @ReactMethod
  public void printRaw(ReadableArray message, int lines) {

    byte[] decoded = new byte[message.size()];

    for (int i = 0; i < message.size(); i++) {
      decoded[i] = new Integer(message.getInt(i)).byteValue();
    }

    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.sendRawData(decoded);
    } else {
      TectoySunmiPrint.getInstance().initPrinter();
      TectoySunmiPrint.getInstance().sendRawData(decoded);
      TectoySunmiPrint.getInstance().printAdvanceLines(lines);
    }
  }

  @ReactMethod
  public void feed3lines() {
    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.print3Line();
    } else {
      TectoySunmiPrint.getInstance().print3Line();
    }
  }

  @ReactMethod
  public void feedAdvancesLines(int av) {
    if (getDeviceName().equals("SUNMI K2")) {
      kPrinterPresenter.printAdvanceLines(av);
    } else {
      TectoySunmiPrint.getInstance().printAdvanceLines(av);
    }
  }

  @ReactMethod
  public void getStatus(final Promise promise) {
    JSONObject json = new JSONObject();

    String result = "UNDEFINED";
    Integer resultCode = -1;

    if (getDeviceName().equals("SUNMI K2")) {
      result = kPrinterPresenter.getStatus();
      resultCode = kPrinterPresenter.getStatusCode();
    } else {
      result = TectoySunmiPrint.getInstance().printerStatus();
      resultCode = TectoySunmiPrint.getInstance().printerStatusCode();
    }

    try {
      json.put("status", result);
      json.put("code", resultCode);
      json.put("device_name", getDeviceName());
    } catch (JSONException e) {
      promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
    }

    promise.resolve(json.toString());
  }


//  @ReactMethod
//  public void printStyleAntiWhite() {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleAntiWhite(true);
//    }
//  }

//  @ReactMethod
//  public void resetStyle() {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleReset();
//    }
//  }

//  @ReactMethod
//  public void styleUnderline(Boolean underline) {
//    if (getDeviceName().equals("SUNMI K2")) {
//      kPrinterPresenter.printStyleUnderLine();
//    } else {
//      TectoySunmiPrint.getInstance().printStyleUnderLine(underline);
//    }
//  }

//  @ReactMethod
//  public void aling(int aling) {
//    if (getDeviceName().equals("SUNMI K2")) {
//      kPrinterPresenter.setAlign(aling);
//    } else {
//      TectoySunmiPrint.getInstance().setAlign(aling);
//    }
//  }

//  @ReactMethod
//  public void printTable(String[] txt, int[] width, int[] aling) {
//    if (getDeviceName().equals("SUNMI K2")) {
//      kPrinterPresenter.printTable(txt, width, aling);
//    } else {
//      TectoySunmiPrint.getInstance().printTable(txt, width, aling);
//    }
//  }

//  @ReactMethod
//  public void styleDoubleHeight(Boolean bold) {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleDoubleHeight(bold);
//    }
//  }

//  @ReactMethod
//  public void styleDoubleWidth(Boolean bold) {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleDoubleWidth(bold);
//    }
//  }

//  @ReactMethod
//  public void printSytlebold(Boolean bold) {
//    if (getDeviceName().equals("SUNMI K2")) {
//      kPrinterPresenter.printStyleBold(bold);
//    } else {
//      TectoySunmiPrint.getInstance().printStyleBold(bold);
//    }
//  }

//  @ReactMethod
//  public void styleInvert(Boolean invert) {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleInvert(invert);
//    }
//  }

//  @ReactMethod
//  public void styleStrike(Boolean bool) {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleStrikethRough(bool);
//    }
//  }

//  @ReactMethod
//  public void printTextWithSize(String texto, int size) {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printTextWithSize(texto, size);
//    }
//  }

//  @ReactMethod
//  public void styleItalic(Boolean italic) {
//    if (getDeviceName().equals("SUNMI K2")) {
//
//    } else {
//      TectoySunmiPrint.getInstance().printStyleItalic(italic);
//    }
//  }


//  @ReactMethod
//  public void printDoubleQr(String txt, String txt1, int mode, int error) {
//    if (getDeviceName().equals("SUNMI K2")) {
//      kPrinterPresenter.printDoubleQRCode(txt, txt1, mode, error);
//    } else {
//      TectoySunmiPrint.getInstance().printDoubleQRCode(txt, txt1, mode, error);
//    }
//  }


  // -----------------------------------------------------------------------------------------------------------------------
  // -------------- LAMPADA
  // -----------------------------------------------------------------------------------------------------------------------

  @ReactMethod
  private void Lampada_ControlarLampada(int status, String lamp, final Promise promise) {
    if (getDeviceName().contains("K2")) {
      try {
        lampHelper.ControlarLuz(status, lamp);
        promise.resolve(true);
      } catch (Throwable e) {
        promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
      }
    } else {
      promise.resolve(false);
    }
  }

  @ReactMethod
  private void Lampada_ControlarLampadaLoop(int status, double onTime, double offTime, String lamp, final Promise promise) {
    if (getDeviceName().contains("K2")) {
      try {
        lampHelper.ControlarLuzLoop(status, (long) onTime, (long) offTime, lamp);
        promise.resolve(true);
      } catch (Throwable e) {
        promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
      }
    } else {
      promise.resolve(false);
    }
  }

  @ReactMethod
  private void Lampada_Desligar(final Promise promise) {
    if (getDeviceName().contains("K2")) {
      try {
        lampHelper.DesligarLuz();
        promise.resolve(true);
      } catch (Throwable e) {
        promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
      }
    } else {
      promise.resolve(false);
    }
  }

  // -----------------------------------------------------------------------------------------------------------------------
  // -------------- LCD
  // -----------------------------------------------------------------------------------------------------------------------

  @ReactMethod
  private void LCD_ControlarLCD(int flag, final Promise promise) {
    if (getDeviceName().equals("SUNMI K2")) {

    } else {
      TectoySunmiPrint.getInstance().controlLcd(flag, promise);
    }
  }

  @ReactMethod
  private void LCD_EnviarTexto(String text, int size, boolean fill, final Promise promise) {
    if (getDeviceName().equals("SUNMI K2")) {

    } else {
      TectoySunmiPrint.getInstance().sendTextToLcd(text, size, fill, promise);
    }
  }

  @ReactMethod
  private void LCD_EnviarTextos(String text1, int text1_align,

                                String text2, int text2_align,

                                String text3, int text3_align,

                                final Promise promise) {
    if (getDeviceName().equals("SUNMI K2")) {

    } else {
      TectoySunmiPrint.getInstance().sendTextsToLcd(text1, text1_align,

        text2, text2_align,

        text3, text3_align,

        promise);
    }
  }

  //    @ReactMethod
  //    private void LCD_ControlarLCD(int flag){
  //        if (getDeviceName().equals("SUNMI K2")) {
  //
  //        } else {
  //            TectoySunmiPrint.getInstance().controlLcd(flag);
  //        }
  //    }


  // -----------------------------------------------------------------------------------------------------------------------
  // -------------- CÓDIGOS DE BARRAS
  // -----------------------------------------------------------------------------------------------------------------------

  @ReactMethod
  public void Barcode_Generate(String content, int format, int width, int height, int margin,

                               String color, String backgroundColor,

                               final Promise promise

  ) {
    JSONObject json = new JSONObject();

    try {

      /**
       * Gera o bitmap
       */
      Bitmap bitmap = BitmapUtil.generateBitmap(content, format, width, height, margin, color, backgroundColor);

      /**
       * Converte o bitmap para BASE64
       */
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
      String code = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

      /**
       * Coloca o base64 no response
       */
      json.put("code", code);

      /**
       * Resolve a promise
       */
      promise.resolve(json.toString());

    } catch (IllegalArgumentException | WriterException | JSONException e) {
      promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
    }

  }


  // -----------------------------------------------------------------------------------------------------------------------
  // -------------- UTILIDADES
  // -----------------------------------------------------------------------------------------------------------------------

  @ReactMethod
  public void Utilidades_FecharApp(final Promise promise) {
    android.os.Process.killProcess(android.os.Process.myPid());
    promise.resolve(true);
  }

  @ReactMethod
  public void Utilidades_ModoFullScreen(boolean enable, final Promise promise) {

    UiThreadUtil.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        // pega a view atual
        View view = getCurrentActivity().getWindow().getDecorView();

        int flags;

        if (enable) {
          flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // hide nav bar
            | View.SYSTEM_UI_FLAG_FULLSCREEN  // hide status bar
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        } else {
          flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }

        // define as flags
        view.setSystemUiVisibility(flags);

        // resolve a promessa
        promise.resolve(true);

      }
    });
  }

  @ReactMethod
  public void Utilidades_ReiniciarDispositivo(String reason, final Promise promise) {
    PowerManager powerManager = (PowerManager) getReactApplicationContext().getSystemService(Context.POWER_SERVICE);

    // força o reinício
    powerManager.reboot(reason);

    // resolve a promessa
    promise.resolve(true);
  }


  // -----------------------------------------------------------------------------------------------------------------------
  // -------------- OUTRAS FUNÇÕES
  // -----------------------------------------------------------------------------------------------------------------------

  public static String getDeviceName() {
    String manufacturer = Build.MANUFACTURER;
    String model = Build.MODEL;
    if (model.startsWith(manufacturer)) {
      return capitalize(model);
    }
    return capitalize(manufacturer) + " " + model;
  }

  private static String capitalize(String str) {
    if (TextUtils.isEmpty(str)) {
      return str;
    }
    char[] arr = str.toCharArray();
    boolean capitalizeNext = true;

    StringBuilder phrase = new StringBuilder();
    for (char c : arr) {
      if (capitalizeNext && Character.isLetter(c)) {
        phrase.append(Character.toUpperCase(c));
        capitalizeNext = false;
        continue;
      } else if (Character.isWhitespace(c)) {
        capitalizeNext = true;
      }
      phrase.append(c);
    }

    return phrase.toString();
  }

  private void connectKPrintService() {
    Intent intent = new Intent();
    intent.setPackage("com.sunmi.extprinterservice");
    intent.setAction("com.sunmi.extprinterservice.PrinterService");
    getReactApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
  }

  private final ServiceConnection connService = new ServiceConnection() {

    @Override
    public void onServiceDisconnected(ComponentName name) {
      extPrinterService = null;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      extPrinterService = ExtPrinterService.Stub.asInterface(service);
      kPrinterPresenter = new KTectoySunmiPrinter(getReactApplicationContext(), extPrinterService);
    }
  };

  private Bitmap scaleImage(Bitmap bitmap1) {
    int width = bitmap1.getWidth();
    int height = bitmap1.getHeight();
    int newWidth = (width / 8 + 1) * 8;
    float scaleWidth = ((float) newWidth) / width;
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, 1);
    return Bitmap.createBitmap(bitmap1, 0, 0, width, height, matrix, true);
  }


  private Bitmap getBitmapFromURL(String url) {
    try {
      URL src = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) src.openConnection();
      connection.setDoInput(true);
      connection.connect();
      InputStream input = connection.getInputStream();
      Bitmap myBitmap = BitmapFactory.decodeStream(input);
      return myBitmap;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void salvando(Bitmap abmp) {

    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeuApp/imagens/";
    File dir = new File(file_path);
    Log.d("Geovai", String.valueOf(abmp));
    if (!dir.exists()) dir.mkdirs();
    File file = new File(dir, "nomedaImagembaixada");
    FileOutputStream fOut;
    try {
      fOut = new FileOutputStream(file);
      abmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
      fOut.flush();
      fOut.close();

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();

    constants.put("DocumentDirectoryPath", getReactApplicationContext().getFilesDir().getAbsolutePath());
    constants.put("CachesDirectoryPath", getReactApplicationContext().getCacheDir().getAbsolutePath());
    constants.put("TemporaryDirectoryPath", getReactApplicationContext().getCacheDir().getAbsolutePath());
    constants.put("PicturesDirectoryPath", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
    constants.put("DownloadDirectoryPath", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

    File externalStorageDir = Environment.getExternalStorageDirectory();
    constants.put("ExternalStorageDirectoryPath", externalStorageDir != null ? externalStorageDir.getAbsolutePath() : null);

    File appExternalFilesDir = getReactApplicationContext().getExternalFilesDir(null);
    constants.put("AppExternalFilesDirectoryPath", appExternalFilesDir != null ? appExternalFilesDir.getAbsolutePath() : null);

    File appExternalCachesDir = getReactApplicationContext().getExternalCacheDir();
    constants.put("AppExternalCachesDirectoryPath", appExternalCachesDir != null ? appExternalCachesDir.getAbsolutePath() : null);

    return constants;
  }

}
