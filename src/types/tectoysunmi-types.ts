import type { TectoyLCDFuncaoEnum } from '../enums/tectoysunmisdk-enum';

export type ImprimirQRCodeType = {
  data: string;
  size:
    | 0
    | 1
    | 2
    | 3
    | 4
    | 5
    | 6
    | 7
    | 8
    | 9
    | 10
    | 11
    | 12
    | 13
    | 14
    | 15
    | 16;
  error: 0 | 1 | 2 | 3;
};

export type TectoyLCDMultiTextoType = {
  texto1: string;
  texto1_peso_tamanho: number;

  texto2: string;
  texto2_peso_tamanho: number;

  texto3: string;
  texto3_peso_tamanho: number;
};

export type TectoyLCDFuncaoType =
  | TectoyLCDFuncaoEnum.INICIALIZAR
  | TectoyLCDFuncaoEnum.LIGAR_LCD
  | TectoyLCDFuncaoEnum.DESLIGAR_LCD
  | TectoyLCDFuncaoEnum.LIMPAR_TELA;
