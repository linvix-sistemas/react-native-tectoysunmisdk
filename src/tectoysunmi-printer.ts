import type {
  ImprimirQRCodeType,
  StatusImpressoraType,
} from './types/tectoysunmi-types';
import NativeTectoySunmiSdk from './specs/NativeTectoySunmiSdk';

/**
 * Obtém o status da impressora
 */
const ObterStatus = async () => {
  try {
    const status = await NativeTectoySunmiSdk.getStatus();
    return JSON.parse(status) as StatusImpressoraType;
  } catch (error) {
    throw error;
  }
};

const ImprimirTexto = (texto = '') => {
  try {
    NativeTectoySunmiSdk.printText(texto);
  } catch (error) {
    throw error;
  }
};

const ImprimirRAW = (bytes: any, feed_lines: number = 0) => {
  try {
    NativeTectoySunmiSdk.printRaw(bytes, feed_lines);
  } catch (error) {
    throw error;
  }
};

const AvancarLinha = (linhas = 0) => {
  try {
    NativeTectoySunmiSdk.feedAdvancesLines(linhas);
  } catch (error) {
    throw error;
  }
};

const Avancar3Linhas = () => {
  try {
    NativeTectoySunmiSdk.feed3lines();
  } catch (error) {
    throw error;
  }
};

const CortarPapel = () => {
  try {
    NativeTectoySunmiSdk.cutpaper();
  } catch (error) {
    throw error;
  }
};

const ImprimirQRCode = (data: ImprimirQRCodeType) => {
  try {
    NativeTectoySunmiSdk.printQr(data.data, data.size, data.error);
  } catch (error) {
    throw error;
  }
};

const TecToySunmiPrinterSDK = {
  ObterStatus,
  ImprimirTexto,
  ImprimirQRCode,
  ImprimirRAW,
  CortarPapel,
  AvancarLinha,
  Avancar3Linhas,
};

export default TecToySunmiPrinterSDK;
