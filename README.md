# @linvix-sistemas/react-native-tectoysunmisdk
Wrapper para comunicação com a SDK da Sunmi/Tectoy para React Native.

## Atenção
Este pacote foi desenvolvido para facilitar a integração com o SDK da Sunmi/Tectoy para comunicar com os produtos/equipamentos da empresa.

Verifique a documentação da Sunmi para maior endentimento das funções e possíveis implementações adicionais.

Esta biblioteca não está completa com todos os recursos disponíveis da sunmi/tectoy, caso você consiga implementar novas funções, contribua com o pacote e torne ele mais útil para todos os que utilizam.

---

## Testado nos equipamentos
#### [D2S Combo](https://tectoyautomacao.com.br/produtos/terminais-pdv/pos-desktop-d2s-combo) - Possuí gaveta
#### [T2 Mini](https://tectoyautomacao.com.br/produtos/terminais-pdv/pdv-desktop-t2-mini) - Sem leitor câmera integrada
#### [T2s](https://tectoyautomacao.com.br/produtos/terminais-pdv/pdv-desktop-t2s) - Impressora 80mm (48col)

#### [L2s](https://tectoyautomacao.com.br/produtos/terminais-moveis/computador-de-mao-l2s) - Coletor de dados SEM teclado físico
#### [L2K](https://tectoyautomacao.com.br/produtos/terminais-moveis/computador-de-mao-l2k) - Coletor de dados COM teclado físico
---

## Instalação

```sh
npm install @linvix-sistemas/react-native-tectoysunmisdk
```

```sh
yarn add @linvix-sistemas/react-native-tectoysunmisdk
```
## Uso

```js
import TecToySunmiSDK, { TectoyLCDFuncaoEnum, TectoyLCDMultiTextoType } from '@linvix-sistemas/react-native-tectoysunmisdk';
```

Veja a pasta [example](example/src/App.tsx) para verificar como utilizar.

---
## [Types](src/types/tectoysunmi-types.ts) - [Enums](src/enums/tectoysunmisdk-enum.ts) 
---

## Metódos - Impressora
Lista de métodos expostos para utlização com a impressora.

### ObterStatus - [StatusImpressoraType](src/types/tectoysunmi-types.ts#L08)
```ts
// Lembre-se de que o status da impressora pode variar dependendo do dispositivo utilizado pela tectoy, sempre verifique o manual.
await TecToySunmiSDK.impressora.ObterStatus();
```
### ImprimirTexto
```ts
await TecToySunmiSDK.impressora.ImprimirTexto(texto: string);
```
### ImprimirRaw
Pode ser utilizado para enviar bytes gerados no lado do react-native diretamente para a impressora.
Olhar documentação ESC/POS ou alguma lib que possúa geração de comandos ESC/POS.
```ts
// palavra: teste
const bytes = [0x54, 0x45, 0x53, 0x54, 0x45];
await TecToySunmiSDK.impressora.ImprimirRAW(bytes);
```

### ImprimirQRCode - [ImprimirQRCodeType](src/types/tectoysunmi-types.ts#L14)
```ts
await TecToySunmiSDK.impressora.ImprimirQRCode(data: ImprimirQRCodeType);
```
### AvancarLinha
```ts
await TecToySunmiSDK.impressora.AvancarLinha(numero_linhas = 5);
```
### Avancar3Linhas
```ts
await TecToySunmiSDK.impressora.Avancar3Linhas();
```
---

## Metódos - LCD
Lista de métodos expostos para utlização com o LCD.

### ControlarLCD - [TectoyLCDFuncaoEnum](src/enums/tectoysunmisdk-enum.ts#L1)
```ts
// Função utilizada para controlar o display lcd disponível em algúns modelos da tectoy/sunmi.
// TectoyLCDFuncaoEnum.INICIALIZAR
// TectoyLCDFuncaoEnum.LIGAR_LCD
// TectoyLCDFuncaoEnum.DESLIGAR_LCD
// TectoyLCDFuncaoEnum.LIMPAR_TELA
await TecToySunmiSDK.lcd.ControlarLCD(funcao: TectoyLCDFuncaoEnum);
```
### EnviarTexto
```ts
await TecToySunmiSDK.lcd.EnviarTexto(texto: string);
```
### EnviarTextos
```ts
await TecToySunmiSDK.lcd.EnviarTextos(data: TectoyLCDMultiTextoType);
```
---

## Metódos - Lampada (K2/K2 MINI)
Lista de métodos expostos para utlização com o LED.

### ControlarLampada - [TectoyLampStatusEnum](src/enums/tectoysunmisdk-enum.ts) | [TectoyLampLedEnum](src/enums/tectoysunmisdk-enum.ts) 
```ts
// Função utilizada para controlar o LED superior nos dispotivos K2 e K2 Mini.
// TectoyLampStatusEnum.LIGAR
// TectoyLampStatusEnum.DESLIGAR
//
// TectoyLampLedEnum.LED_1
// TectoyLampLedEnum.LED_2
// TectoyLampLedEnum.LED_3
// TectoyLampLedEnum.LED_4
// TectoyLampLedEnum.LED_5
// TectoyLampLedEnum.LED_6
await TecToySunmiSDK.lampada.ControlarLampada(status: TectoyLampStatusEnum, led: TectoyLampLedEnum);
```

### ControlarLampadaLoop - [TectoyLampStatusEnum](src/enums/tectoysunmisdk-enum.ts) | [TectoyLampLedEnum](src/enums/tectoysunmisdk-enum.ts)
```ts
// Função utilizada para controlar o LED superior nos dispotivos K2 e K2 Mini.
// TectoyLampStatusEnum.LIGAR
// TectoyLampStatusEnum.DESLIGAR
//
// TectoyLampLedEnum.LED_1
// TectoyLampLedEnum.LED_2
// TectoyLampLedEnum.LED_3
// TectoyLampLedEnum.LED_4
// TectoyLampLedEnum.LED_5
// TectoyLampLedEnum.LED_6
await TecToySunmiSDK.lampada.ControlarLampadaLoop(status: TectoyLampStatusEnum, onTime: number, offTime: number, led: TectoyLampLedEnum);
```

### Desligar
```ts
await TecToySunmiSDK.lampada.Desligar();
```
---

## Metódos - Gavega
Lista de métodos expostos para utlização com a gaveta.

### AbrirGaveta
```ts
await TecToySunmiSDK.gaveta.AbrirGaveta();
```
---

## Métodos - Scanner
Métodos para interação com leitor de código de barras

### onBarcodeRead (broadcast)
Este método é um "listener" ou seja, ele aguarda que o evento ocorra, e dispara a função de callback quando o evento acontece.
```ts
useEffect(() => {
   // Em dispositivos que possúem leitor de código de barras ou o suporte via USB.
   // Ex: D2Mini, D2SCombo, T2s com leitor USB externo (precisa configurar broadcast).
   // Para funcionar a leitura do código, precisa configurar o dispositivo para fazer broadcast dos dados e desabilitar TextInput para saída de texto.
   //
   // L2s/L2ks também funciona aqui com leitura de código de barras.
   //
   // Importante chamar cleanup para remover o listener da função quando quiser parar de receber o código de barras lido.
   //
  const cleanup = TecToySunmiSDK.scanner.onBarcodeRead((ev) => {
    console.log(ev);
  });
  
  return () => cleanup();
}, []);
  ```
  ---
## Métodos - Utilidades
Métodos auxiliares que podem ser úteis.

### FecharApp
```ts
await TecToySunmiSDK.utils.FecharApp();
```

### Reiniciar Dispositivo
```ts
await TecToySunmiSDK.utils.ReiniciarDispositivo(motivo: string);
```

### Modo Full Screen
```ts
await TecToySunmiSDK.utils.ModoFullScreen(ativar: boolean = true);
```
---

## Contribuindo
Fique a vontade para fazer contribuições no projeto, ele é um projeto que a Linvix Sistemas está utilizando em seus projetos e achou conveniente disponibilizar para a comunidade.

## License

MIT
