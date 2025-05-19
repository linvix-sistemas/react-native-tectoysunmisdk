import { NativeModules, Platform } from 'react-native';

import TecToySunmiPrinterSDK from './tectoysunmi-printer';
import TecToySunmiCashBoxSDK from './tectoysunmi-cashbox';
import TecToySunmiScannerSDK from './tectoysunmi-scanner';
import TecToySunmiLCDSDK from './tectoysunmi-lcd';
import TecToySunmiBarcodeSDK from './tectoysunmi-barcode';
import TecToySunmiUtilsSDK from './tectoysunmi-utils';
import TecToySunmiLampadaSDK from './tectoysunmi-lampada';

const LINKING_ERROR =
  `The package '@linvix-sistemas/react-native-tectoysunmisdk' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

NativeModules.TectoySunmiSdk
  ? NativeModules.TectoySunmiSdk
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const TecToySunmiSDK = {
  impressora: TecToySunmiPrinterSDK,
  gaveta: TecToySunmiCashBoxSDK,
  scanner: TecToySunmiScannerSDK,
  lcd: TecToySunmiLCDSDK,
  lampada: TecToySunmiLampadaSDK,
  barcode: TecToySunmiBarcodeSDK,
  utils: TecToySunmiUtilsSDK,
};

export * from './types/tectoysunmi-types';
export * from './enums/tectoysunmisdk-enum';
export * from './consts/tectoysunmi-consts';

export default TecToySunmiSDK;
