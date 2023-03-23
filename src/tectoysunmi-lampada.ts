import { NativeModules } from 'react-native';

import type {
  TectoyLampLedType,
  TectoyLampStatusType,
} from './types/tectoysunmi-types';

const { TectoySunmiSdk } = NativeModules;

/**
 * Controla a lampada.
 * @param status TectoyLampStatusType Ligado ou Desligado
 * @param led TectoyLampLedType Cor do led
 */
const ControlarLampada = async (
  status: TectoyLampStatusType,
  led: TectoyLampLedType
) => {
  try {
    return await TectoySunmiSdk.Lampada_ControlarLampada(status, led);
  } catch (error) {
    throw error;
  }
};

/**
 * Controla a lampada.
 * @param status TectoyLampStatusType Ligado ou Desligado
 * @param onTime Number tempo em ms para ligar
 * @param offTime Number tempo em ms para desligar
 * @param led TectoyLampLedType Cor do led
 */
const ControlarLampadaLoop = async (
  status: TectoyLampStatusType,
  onTime: number,
  offTime: number,
  led: TectoyLampLedType
) => {
  try {
    return await TectoySunmiSdk.Lampada_ControlarLampadaLoop(
      status,
      onTime,
      offTime,
      led
    );
  } catch (error) {
    throw error;
  }
};

const Desligar = async () => {
  try {
    return await TectoySunmiSdk.Lampada_Desligar();
  } catch (error) {
    throw error;
  }
};

const NativeModuleTectToySunmiLampadaSDK = {
  ControlarLampada,
  ControlarLampadaLoop,
  Desligar,
};

export default NativeModuleTectToySunmiLampadaSDK;
