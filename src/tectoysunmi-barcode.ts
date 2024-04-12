import { NativeModules } from 'react-native';

import type { TectoyBarcodeGerarType } from './types/tectoysunmi-types';

const { TectoySunmiSdk } = NativeModules;

const Gerar = async (data: TectoyBarcodeGerarType) => {
  try {
    if (!data.cor_fundo) {
      data.cor_fundo = '#fff';
    }
    if (!data.cor) {
      data.cor = '#333';
    }

    const result = await TectoySunmiSdk.Barcode_Generate(
      data.conteudo,
      data.formato,
      data.largura,
      data.altura,

      data.cor,
      data.cor_fundo,
      data.margem ?? null
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
