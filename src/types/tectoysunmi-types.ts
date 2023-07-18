import type {
  TectoyBarcodeFormatEnum,
  TectoyLampLedEnum,
  TectoyLampStatusEnum,
  TectoyLCDFuncaoEnum,
} from '../enums/tectoysunmisdk-enum';

export type StatusImpressoraType = {
  code: number;
  device_name: string;
  status: string;
};

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

export type TectoyLCDFuncaoType = TectoyLCDFuncaoEnum;

export type TectoyLampStatusType = TectoyLampStatusEnum;
export type TectoyLampLedType = TectoyLampLedEnum;

export type TectoyScannerOnBarcodeReadType = {
  code: string;
  // bytes: string;
};

export type TectoyBarcodeGerarType = {
  conteudo: string;
  formato: TectoyBarcodeFormatEnum;
  largura: number;
  altura: number;

  cor: string;
  cor_fundo: string;
};
