import { DeviceEventEmitter } from 'react-native';

import type { TectoyScannerOnBarcodeReadType } from './types/tectoysunmi-types';

const onBarcodeRead = (
  callback: (ev: TectoyScannerOnBarcodeReadType) => void
) => {
  const listener = DeviceEventEmitter.addListener(
    'SUNMI_TECTOY_BARCODE_READED',
    callback
  );
  return () => {
    listener?.remove();
  };
};

const NativeModuleTectToySunmiScannerSDK = {
  onBarcodeRead,
};

export default NativeModuleTectToySunmiScannerSDK;
