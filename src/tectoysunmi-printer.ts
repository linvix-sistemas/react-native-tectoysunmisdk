import { NativeModules } from 'react-native';

import type { ImprimirQRCodeType } from './types/tectoysunmi-types';

const { TectoySunmiSdk } = NativeModules;

/**
 * Obtém o status da impressora
 */
const ObterStatus = async () => {
  try {
    const status = await TectoySunmiSdk.getStatus();
    return JSON.parse(status);
  } catch (error) {
    throw error;
  }
};

const ImprimirTexto = (texto = '') => {
  try {
    TectoySunmiSdk.printText(texto);
  } catch (error) {
    throw error;
  }
};

const AvancarLinha = (linhas = 0) => {
  try {
    TectoySunmiSdk.feedAdvancesLines(linhas);
  } catch (error) {
    throw error;
  }
};

const Avancar3Linhas = () => {
  try {
    TectoySunmiSdk.feed3lines();
  } catch (error) {
    throw error;
  }
};

const CortarPapel = () => {
  try {
    TectoySunmiSdk.cutpaper();
  } catch (error) {
    throw error;
  }
};

const ImprimirQRCode = (data: ImprimirQRCodeType) => {
  try {
    TectoySunmiSdk.printQr(data.data, data.size, data.error);
  } catch (error) {
    throw error;
  }
};

const NativeModuleTectToySunmiPrinterSDK = {
  ObterStatus,
  ImprimirTexto,
  ImprimirQRCode,
  CortarPapel,
  AvancarLinha,
  Avancar3Linhas,
};

export default NativeModuleTectToySunmiPrinterSDK;
