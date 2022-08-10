import { NativeModules } from 'react-native';

import type {
  TectoyLCDFuncaoType,
  TectoyLCDMultiTextoType,
} from './types/tectoysunmi-types';

const { TectoySunmiSdk } = NativeModules;

/**
 * Controla o LCD.
 * @param funcao TectoyLCDFuncaoType
 */
const ControlarLCD = async (funcao: TectoyLCDFuncaoType) => {
  try {
    return await TectoySunmiSdk.LCD_ControlarLCD(funcao);
  } catch (error) {
    throw error;
  }
};

const EnviarTexto = async (
  texto = '',
  tamanho_fonte = 16,
  preencher = false
) => {
  try {
    if (tamanho_fonte > 40) {
      throw new Error('Tamanho da fonte não pode ser maior do que 40');
    }

    return await TectoySunmiSdk.LCD_EnviarTexto(
      texto,
      tamanho_fonte,
      preencher
    );
  } catch (error) {
    throw error;
  }
};

const EnviarTextos = async (data: TectoyLCDMultiTextoType) => {
  try {
    return await TectoySunmiSdk.LCD_EnviarTextos(
      data.texto1,
      data.texto1_peso_tamanho,

      data.texto2,
      data.texto2_peso_tamanho,

      data.texto3,
      data.texto3_peso_tamanho
    );
  } catch (error) {
    throw error;
  }
};

const NativeModuleTectToySunmiLCDSDK = {
  ControlarLCD,
  EnviarTexto,
  EnviarTextos,
};

export default NativeModuleTectToySunmiLCDSDK;
