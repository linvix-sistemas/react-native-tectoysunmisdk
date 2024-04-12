package com.reactnativetectoysunmisdk;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

public class BitmapUtil {

  /**
   * Gerar código de barras em bitmap
   *
   * @param content
   * @param format
   * @param width
   * @param height
   * @return
   */
  public static Bitmap generateBitmap(
    String content,
    int format,
    int width,
    int height,
    int margin,

    String codeColor,
    String backgroundColor
  )
    throws WriterException, IllegalArgumentException {
    if (content == null || content.equals(""))
      return null;

    MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
    Map<EncodeHintType, Object> hints = new HashMap<>();

    BarcodeFormat barcodeFormat;

    switch (format) {
      case 0:
        barcodeFormat = BarcodeFormat.UPC_A;
        break;
      case 1:
        barcodeFormat = BarcodeFormat.UPC_E;
        break;
      case 2:
        barcodeFormat = BarcodeFormat.EAN_13;
        break;
      case 3:
        barcodeFormat = BarcodeFormat.EAN_8;
        break;
      case 4:
        barcodeFormat = BarcodeFormat.CODE_39;
        break;
      case 5:
        barcodeFormat = BarcodeFormat.ITF;
        break;
      case 6:
        barcodeFormat = BarcodeFormat.CODABAR;
        break;
      case 7:
        barcodeFormat = BarcodeFormat.CODE_93;
        break;
      case 8:
        barcodeFormat = BarcodeFormat.CODE_128;
        break;
      case 9:
        barcodeFormat = BarcodeFormat.QR_CODE;
        break;
      default:
        height = width;
        barcodeFormat = BarcodeFormat.QR_CODE;
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MAX_SIZE, width);
        break;
    }

    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    hints.put(EncodeHintType.MARGIN, margin);

    BitMatrix encode = qrCodeWriter.encode(content, barcodeFormat, width, height, hints);

    int[] pixels = new int[width * height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (encode.get(j, i)) {
          pixels[i * width + j] = Color.parseColor(codeColor);
        } else {
          pixels[i * width + j] = Color.parseColor(backgroundColor);
        }
      }
    }

    return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
  }
}
