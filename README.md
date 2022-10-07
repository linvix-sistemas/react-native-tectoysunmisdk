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
import NativeModuleTectToySunmiSDK, { TectoyLCDFuncaoEnum, TectoyLCDMultiTextoType } from '@linvix-sistemas/react-native-tectoysunmisdk';
```

Veja a pasta [example](example/src/App.tsx) para verificar como utilizar.

---
## [Types](src/types/tectoysunmi-types.ts) - [Enums](src/enums/tectoysunmisdk-enum.ts) 
---

## Metódos - Impressora
Lista de métodos expostos para utlização com a impressora.

### ObterStatus
```ts
// Lembre-se de que o status da impressora pode variar dependendo do dispositivo utilizado pela tectoy, sempre verifique o manual.
await NativeModuleTectToySunmiSDK.impressora.ObterStatus();
```
### ImprimirTexto
```ts
await NativeModuleTectToySunmiSDK.impressora.ImprimirTexto(texto: string);
```
### ImprimirRaw
Pode ser utilizado para enviar bytes gerados no lado do react-native diretamente para a impressora.
Olhar documentação ESC/POS ou alguma lib que possúa geração de comandos ESC/POS.
```ts
// palavra: teste
const bytes = [0x54, 0x45, 0x53, 0x54, 0x45];
await NativeModuleTectToySunmiSDK.impressora.ImprimirRAW(bytes);
```

### ImprimirQRCode - [ImprimirQRCodeType](src/types/tectoysunmi-types.ts#L26)
```ts
await NativeModuleTectToySunmiSDK.impressora.ImprimirQRCode(data: ImprimirQRCodeType);
```
### AvancarLinha
```ts
await NativeModuleTectToySunmiSDK.impressora.AvancarLinha(numero_linhas = 5);
```
### Avancar3Linhas
```ts
await NativeModuleTectToySunmiSDK.impressora.Avancar3Linhas();
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
await NativeModuleTectToySunmiSDK.lcd.ControlarLCD(funcao: TectoyLCDFuncaoEnum);
```
### EnviarTexto
```ts
await NativeModuleTectToySunmiSDK.lcd.EnviarTexto(texto: string);
```
### EnviarTextos
```ts
await NativeModuleTectToySunmiSDK.lcd.EnviarTextos(data: TectoyLCDMultiTextoType);
```
---

## Metódos - Gavega
Lista de métodos expostos para utlização com a gaveta.

### AbrirGaveta
```ts
await NativeModuleTectToySunmiSDK.gaveta.AbrirGaveta();
```
---

## Contribuindo
Fique a vontade para fazer contribuições no projeto, ele é um projeto que a Linvix Sistemas está utilizando em seus projetos e achou conveniente disponibilizar para a comunidade.

## License

MIT
