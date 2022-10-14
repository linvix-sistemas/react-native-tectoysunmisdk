import { NativeModules } from 'react-native';

import type { TectoyBarcodeGerarType } from './types/tectoysunmi-types';

const { TectoySunmiSdk } = NativeModules;

const Gerar = async (data: TectoyBarcodeGerarType) => {
  try {
    const result = await TectoySunmiSdk.Barcode_Generate(
      data.conteudo,
      data.formato,
      data.largura,
      data.altura
    );

    return JSON.parse(result);
  } catch (error) {
    throw error;
  }
};

const NativeModuleTectToySunmiBarcodeSDK = {
  Gerar,
};

export default NativeModuleTectToySunmiBarcodeSDK;
