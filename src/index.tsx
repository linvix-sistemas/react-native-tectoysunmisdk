import { NativeModules, Platform } from 'react-native';

import NativeModuleTectToySunmiPrinterSDK from './tectoysunmi-printer';
import NativeModuleTectToySunmiCashBoxSDK from './tectoysunmi-cashbox';
import NativeModuleTectToySunmiLCDSDK from './tectoysunmi-lcd';

const LINKING_ERROR =
  `The package 'react-native-TectoySunmiSdk' doesn't seem to be linked. Make sure: \n\n` +
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

const NativeModuleTectToySunmiSDK = {
  impressora: NativeModuleTectToySunmiPrinterSDK,
  gaveta: NativeModuleTectToySunmiCashBoxSDK,
  lcd: NativeModuleTectToySunmiLCDSDK,
};

export * from './types/tectoysunmi-types';
export * from './enums/tectoysunmisdk-enum';

export default NativeModuleTectToySunmiSDK;
