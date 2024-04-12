import { NativeModules } from 'react-native';

import type { TectoyBarcodeGerarType } from './types/tectoysunmi-types';

const { TectoySunmiSdk } = NativeModules;

const Gerar = async (data: TectoyBarcodeGerarType) => {
  try {
    if (!data.cor_fundo) {
      data.cor_fundo = '#FFFFFF';
    }
    if (!data.cor) {
      data.cor = '#333333';
    }

    if (!data.margem) {
      data.margem = 0;
    }

    const result = await TectoySunmiSdk.Barcode_Generate(
      data.conteudo,
      +data.formato, // int
      +data.largura, // int
      +data.altura, // int
      +data.margem, // int

      data.cor,
      data.cor_fundo
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
