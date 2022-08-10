package com.reactnativetectoysunmisdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.sunmi.peripheral.printer.ExceptionConst;
import com.sunmi.peripheral.printer.InnerLcdCallback;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.InnerResultCallback;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.sunmi.peripheral.printer.WoyouConsts;

/**
 * <pre>
 *      Esta classe é usada para demonstrar vários efeitos de impressão
 *      Developers need to repackage themselves, for details please refer to
 *  </pre>
 *
 * @author Geovani Nogueira
 * @since create at 2021-07-09
 */

public class TectoySunmiPrint {

    public static int NoSunmiPrinter = 0x00000000;
    public static int CheckSunmiPrinter = 0x00000001;
    public static int FoundSunmiPrinter = 0x00000002;
    public static int LostSunmiPrinter = 0x00000003;

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
    public static int BarCodeModels_CODE128A = 8;
    public static int BarCodeModels_CODE128B = 9;
    public static int BarCodeModels_CODE128C = 10;
    // Text Position
    public static int BarCodeTextPosition_INFORME_UM_TEXTO = 0;
    public static int BarCodeTextPosition_ACIMA_DO_CODIGO_DE_BARRAS_BARCODE = 1;
    public static int BarCodeTextPosition_ABAIXO_DO_CODIGO_DE_BARRAS = 2;
    public static int BarCodeTextPosition_ACIMA_E_ABAIXO_DO_CODIGO_DE_BARRAS = 3;

    /**
     * sunmiPrinter verificar o status de conexão da impressora
     */
    public int sunmiPrinter = CheckSunmiPrinter;
    /**
     * SunmiPrinterService for API
     */
    private SunmiPrinterService sunmiPrinterService;

    private static TectoySunmiPrint helper = new TectoySunmiPrint();

    private TectoySunmiPrint() {
    }

    public static TectoySunmiPrint getInstance() {
        return helper;
    }

    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            sunmiPrinterService = service;
            checkSunmiPrinterService(service);
        }

        @Override
        protected void onDisconnected() {
            sunmiPrinterService = null;
            sunmiPrinter = LostSunmiPrinter;
        }
    };

    /**
     * Serviço de impressão init sunmi
     */
    public void initSunmiPrinterService(Context context) {
        try {
            boolean ret = InnerPrinterManager.getInstance().bindService(context,
                    innerPrinterCallback);
            if (!ret) {
                sunmiPrinter = NoSunmiPrinter;
            }
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

    /**
     * deInit sunmi print service
     */
    public void deInitSunmiPrinterService(Context context) {
        try {
            if (sunmiPrinterService != null) {
                InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback);
                sunmiPrinterService = null;
                sunmiPrinter = LostSunmiPrinter;
            }
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se existe uma impressora conectada,
     * como alguns dispositivos não têm impressora, eles vão usar esse serviço para ter acesso a gaveta de dinheiro.
     */
    private void checkSunmiPrinterService(SunmiPrinterService service) {
        boolean ret = false;
        try {
            ret = InnerPrinterManager.getInstance().hasPrinter(service);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
        sunmiPrinter = ret ? FoundSunmiPrinter : NoSunmiPrinter;
    }

    /**
     * Algumas condições podem fazer com que as chamadas de interface falhem
     * Por exemplo: a versão é muito baixa ou dispositivo não é compatível
     * Você pode ver {@link ExceptionConst}
     * Então você tem que lidar com essas exceções
     */
    private void handleRemoteException(RemoteException e) {
        //TODO process when get one exception
    }

    /**
     * send esc cmd
     */
    public void sendRawData(byte[] data) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.sendRAWData(data, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Impressora corta papel e lança exceção em equipamentos sem essa função
     */
    public void cutPaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.cutPaper(null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Inicializa a impressora.
     * Todas as configurações de estilo serão restauradas ao padrão
     */
    public void initPrinter() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.printerInit(null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Alimentação de papel três linhas
     * Não desabilitado quando o espaçamento de linha é definido como 0
     */
    public void print3Line() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.lineWrap(3, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    public void printAdvanceLines(int linas) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.lineWrap(linas, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Obtenha o número de série da impressora
     */
    public String getPrinterSerialNo() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterSerialNo();
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Obter modelo do dispositivo
     */
    public String getDeviceModel() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterModal();
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Obtenha a versão do firmware
     */
    public String getPrinterVersion() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterVersion();
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Obtenha especificações de papel
     */
    public String getPrinterPaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterPaper() == 1 ? "58mm" : "80mm";
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Obtenha especificações de papel
     */
    public void getPrinterHead(InnerResultCallback callbcak) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.getPrinterFactory(callbcak);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Obtenha distanciamento de impressão desde o boot
     * Obtenha a distância de impressão através do retorno de chamada da interface desde 1.0.8 (biblioteca de impressoras)
     */
    public void getPrinterDistance(InnerResultCallback callback) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.getPrintedLength(callback);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Definir o alinhamento da impressora
     */
    public void setAlign(int align) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setAlignment(align, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    public void setSize(int align) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setFontSize(align, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Devido à distância entre a eclosão do papel e a cabeça de impressão,
     * o papel precisa ser alimentado automaticamente
     * Mas se a Api não suportar, ela será substituída pela impressão de três linhas
     */
    public void feedPaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.autoOutPaper(null);
        } catch (RemoteException e) {
            print3Line();
        }
    }

    /**
     * Imprimir texto
     * setPrinterStyle Api requer V4.2.22 ou posterior, então use esc cmd quando não for compatível
     * Mais documentação de referência de configurações {@link WoyouConsts}
     */


    public void printStyleBold(boolean isBold) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, isBold ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleUnderLine(boolean isUnderLine) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_UNDERLINE, isUnderLine ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleAntiWhite(boolean isAntiWhite) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_ANTI_WHITE, isAntiWhite ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleDoubleHeight(boolean isDoubleHeight) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_DOUBLE_HEIGHT, isDoubleHeight ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleDoubleWidth(boolean isDoubleWidth) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_DOUBLE_WIDTH, isDoubleWidth ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleItalic(boolean isItalic) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_ILALIC, isItalic ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleInvert(boolean isInvert) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_INVERT, isInvert ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void printStyleStrikethRough(boolean isStrikethRough) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_STRIKETHROUGH, isStrikethRough ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printStyleReset() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }
        try {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_DOUBLE_WIDTH, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_DOUBLE_HEIGHT, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_UNDERLINE, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_ANTI_WHITE, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_STRIKETHROUGH, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_ILALIC, WoyouConsts.DISABLE);
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_INVERT, WoyouConsts.DISABLE);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printText(String content) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            System.out.println("sunmiprinter null");
            return;
        }

        try {
            System.out.println("sunmiprinter deu certo");
            System.out.println("sunmiprinter deu certo : " + content);
            sunmiPrinterService.printText(content, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void printTextWithSize(String content, float size) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.printTextWithFont(content, null, size, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.printBarCode(data, symbology, height, width, textposition, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printQrCode(String data, int size, int errorlevel) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.printQRCode(data, size, errorlevel, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printDoubleQRCode(String data, String data2, int modulesize, int errorlevel) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing  getPrintDoubleQRCode
            return;
        }

        try {
            byte[] qrCode = ESCUtil.getPrintDoubleQRCode(data, data2, modulesize, errorlevel);
            sunmiPrinterService.sendRAWData(qrCode, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int printerStatusCode() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return -1;
        }

        Integer result = -1;

        try {
            result = sunmiPrinterService.updatePrinterState();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String printerStatus() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return "";
        }
        String result = "UNDEFINED";
        try {
            int res = sunmiPrinterService.updatePrinterState();
            switch (res) {
                case 1:
                    result = "OK";
                    break;
                case 2:
                    result = "STARTING_UP";
                    break;
                case 3:
                    result = "ABNORMAL_COMMUNICATION";
                    break;
                case 4:
                    result = "OUT_OF_PAPER";
                    break;
                case 5:
                    result = "OVERHEATING";
                    break;
                case 6:
                    result = "LID_OPEN";
                    break;
                case 7:
                    result = "PAPER_CUTTER_ABNORMAL";
                    break;
                case 8:
                    result = "PAPER_CUTTER_RECOVERED";
                    break;
                case 9:
                    result = "NO_BLACK_MARK_DETECTED";
                    break;
                case 505:
                    result = "NO_PRINTER_DETECTED";
                case 507:
                    result = "FAILED_TO_UPGRADE_FIRMWARE";
                    break;
                default:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Imprimir uma linha de uma tabela
     */
    public void printTable(String[] txts, int[] width, int[] align) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.printColumnsString(txts, width, align, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imprimir imagens e texto na ordem especificada
     * Depois que a imagem for impressa,
     * a saída de alimentação de linha precisa ser chamada,
     * caso contrário, será salvo no cache
     * Neste exemplo, a imagem será impressa porque o conteúdo do texto de impressão é adicionado
     */
    public void printBitmap(Bitmap bitmap) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.printBitmap(bitmap, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtém se a impressora atual está no modo de marca preta
     */
    public boolean isBlackLabelMode() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return false;
        }
        try {
            return sunmiPrinterService.getPrinterMode() == 1;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     * Obtém se a impressora atual está no modo de impressão de etiquetas
     */
    public boolean isLabelMode() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return false;
        }
        try {
            return sunmiPrinterService.getPrinterMode() == 2;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     * Abra a caixa de dinheiro
     * Este método pode ser usado em dispositivos Sunmi com interface de gaveta de dinheiro
     * Se não houver caixa registradora (como V1 、 P1) ou a chamada falhar, uma exceção será lançada
     */
    public void openCashBox(final Promise promise) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.openDrawer(new InnerResultCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                }

                @Override
                public void onReturnString(String s) throws RemoteException {

                }

                @Override
                public void onRaiseException(int code, String message) throws RemoteException {
                    promise.reject(String.valueOf(code), message);
                }

                @Override
                public void onPrintResult(int i, String s) throws RemoteException {

                }
            });

            promise.resolve(true);
        } catch (RemoteException e) {
            promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
        }
    }

    /**
     * Controle de tela LCD
     *
     * @param flag 1 —— Inicialização
     *             2 —— Tela iluminada
     *             3 —— Tela de extinção
     *             4 —— Limpar o conteúdo da tela
     */
    public void controlLcd(int flag, Promise promise) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.sendLCDCommand(flag);
            promise.resolve(true);
        } catch (RemoteException e) {
            promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
        }
    }

    /**
     * Exibir texto SUNMI, o tamanho da fonte é 16 e o formato é o preenchimento
     * sendLCDFillString (txt, size, fill, callback)
     * Como a altura do pixel da tela é 40, a fonte não deve exceder 40
     */
    public void sendTextToLcd(String text, int size, boolean fill, final Promise promise) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.sendLCDFillString(text, size, fill, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    promise.resolve(show);
                }
            });
        } catch (RemoteException e) {
            promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
        }

    }

    /**
     * Mostra duas linhas e uma linha vazia no meio
     */
    public void sendTextsToLcd(
            String text1,
            int text1_align,

            String text2,
            int text2_align,

            String text3,
            int text3_align,

            final Promise promise
    ) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            String[] texts = {text1, text2, text3};
            int[] align = {text1_align, text2_align, text3_align};

            sunmiPrinterService.sendLCDMultiString(texts, align, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });

            promise.resolve(true);
        } catch (RemoteException e) {
            promise.reject(e.getClass().getSimpleName(), e.getMessage(), e.fillInStackTrace());
        }

    }

    /**
     * Exibir um 128x40 pixels e imagem opaca
     */
    public void sendPicToLcd(Bitmap pic) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return;
        }

        try {
            sunmiPrinterService.sendLCDBitmap(pic, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
