package com.reactnativetectoysunmisdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.RemoteException;

import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.extprinterservice.ExtPrinterService;
import com.sunmi.peripheral.printer.SunmiPrinterService;


public class KTectoySunmiPrinter extends AppCompatActivity {

    public static int NoSunmiPrinter = 0x00000000;
    public static int CheckSunmiPrinter = 0x00000001;
    public static int FoundSunmiPrinter = 0x00000002;
    public static int LostSunmiPrinter = 0x00000003;
    private Context context;
    private static final String TAG = "KPrinterPresenter";
    private ExtPrinterService mPrinter;
    String unic = "GBK";
    private static final byte ESC = 0x1B;
    // Alinhamento
    public static int Alignment_LEFT = 0;
    public static int Alignment_CENTER = 1;
    public static int Alignment_RIGTH = 2;
    // BarCode
    public static int BarCodeModels_UPC_A = 0;
    public static int BarCodeModels_UPC_E = 1;
    public static int BarCodeModels_EAN13 = 2;
    public static int BarCodeModels_EAN8 = 3;
    public static int BarCodeModels_CODE39 = 4;
    public static int BarCodeModels_ITF = 5;
    public static int BarCodeModels_CODABAR = 6;
    public static int BarCodeModels_CODE93 = 7;
    public static int BarCodeModels_CODE128 = 8;

    // Text Position
    public static int BarCodeTextPosition_NAO_IMPRIMIR = 0;
    public static int BarCodeTextPosition_ACIMA_DO_CODIGO_DE_BARRAS_BARCODE = 1;
    public static int BarCodeTextPosition_ABAIXO_DO_CODIGO_DE_BARRAS = 2;
    public static int BarCodeTextPosition_ACIMA_E_ABAIXO_DO_CODIGO_DE_BARRAS = 3;

    // Cutter’s paper cutting
    public static int FULL_CUTTING = 0;
    public static int HALF_CUTTING = 1;
    public static int CUTTING_PAPER_FEED = 2;


    public int sunmiPrinter = CheckSunmiPrinter;
    private SunmiPrinterService sunmiPrinterService;
    private ExtPrinterService extPrinterService = null;

    public KTectoySunmiPrinter(Context context, ExtPrinterService printerService) {
        this.context = context;
        this.mPrinter = printerService;
    }

    public String getStatus() {
        String result = "UNDEFINED";
        try {
            int res = mPrinter.getPrinterStatus();
            switch (res) {
                case 0:
                    result = "OK";
                    break;
                case 1:
                    result = "UNCAPPED";
                    break;
                case 2:
                    result = "OUT_OF_PAPER";
                    break;
                case 3:
                    result = "IS_GOING_OUT_OF_PAPER";
                    break;
                case 4:
                    result = "OVERHEATING";
                    break;
                default:
                    result = "OFFLINE_OR_PRINT_SERVICE_NOT_CONNECTED_TO_PRINTER";
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Status
    public int getStatusCode() {

        int res = -1;
        try {
            res = mPrinter.getPrinterStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return res;

    }

    public void setSize() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BarCode
    public void barcode(String text, int type, int weight, int height, int hripos) {
        try {
            mPrinter.printBarCode(text, type, weight, height, hripos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlign(int aling) {
        try {
            mPrinter.setAlignMode(aling);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBitmap(Bitmap bitmap, int mode) {
        try {
            mPrinter.printBitmap(bitmap, mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Texto
    public void printText(String texto) {
        try {
            mPrinter.printText(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBarcode(String code, int type, int width, int height, int hriPos) {
        try {
            byte[] barcode = ESCUtil.getPrintBarCode(code, type, width, height, hriPos);
            mPrinter.sendRawData(barcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStyleUnderLine() {
        try {
            byte[] underline = ESCUtil.underlineWithOneDotWidthOn();
            mPrinter.sendRawData(underline);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAdvanceLines(int av) {
        try {
            mPrinter.lineWrap(av);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print3Line() {
        try {
            mPrinter.lineWrap(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cortar Papel
    public void cutPaper(int cutter_mode, int advance_lines) {
        try {
            mPrinter.cutPaper(cutter_mode, advance_lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Negrito
    public void printStyleBold(boolean boo) {
        try {
            if (boo) {
                mPrinter.sendRawData(boldOn());
            } else {
                mPrinter.sendRawData(boldOff());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printQrCode(String data, int size, int error) {
        try {
            mPrinter.printQrCode(data, size, error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDoubleQRCode(String data, String data1, int modulesize, int errorlevel) {
        try {
            byte[] qrCode = ESCUtil.getPrintDoubleQRCode(data, data1, modulesize, errorlevel);
            mPrinter.sendRawData(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printTable(String[] colsTextArr, int[] colsWidthArr, int[] colsAlign) {
        try {
            mPrinter.printColumnsText(colsTextArr, colsWidthArr, colsAlign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetStyle() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap scaleImage(Bitmap bitmap1) {
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();

        int newWidth = (width / 8 + 1) * 8;
        float scaleWidth = ((float) newWidth) / width;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, 1);

        return Bitmap.createBitmap(bitmap1, 0, 0, width, height, matrix, true);
    }

    private byte[] boldOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0xF;
        return result;
    }

    private byte[] boldOff() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0;
        return result;
    }

}
