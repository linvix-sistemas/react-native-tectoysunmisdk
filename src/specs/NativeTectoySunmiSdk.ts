import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  // Printer methods
  printText(texto: string): void;
  printQr(txt: string, size: number, error: number): void;
  printBarcode(text: string, type: number, weight: number, height: number, text_position: number): void;
  printRaw(message: number[], lines: number): void;
  cutpaper(): void;
  feed3lines(): void;
  feedAdvancesLines(av: number): void;
  getStatus(): Promise<string>;
  
  // Cash box methods
  openCashBox(): Promise<boolean>;
  
  // Lamp methods
  Lampada_ControlarLampada(status: number, lamp: string): Promise<boolean>;
  Lampada_ControlarLampadaLoop(status: number, onTime: number, offTime: number, lamp: string): Promise<boolean>;
  Lampada_Desligar(): Promise<boolean>;
  
  // LCD methods
  LCD_ControlarLCD(flag: number): Promise<boolean>;
  LCD_EnviarTexto(text: string, size: number, fill: boolean): Promise<boolean>;
  LCD_EnviarTextos(
    text1: string,
    text1_align: number,
    text2: string,
    text2_align: number,
    text3: string,
    text3_align: number
  ): Promise<boolean>;
  
  // Barcode methods
  Barcode_Generate(
    content: string,
    format: number,
    width: number,
    height: number,
    margin: number,
    color: string,
    backgroundColor: string
  ): Promise<string>;
  
  // Utility methods
  Utilidades_FecharApp(): Promise<boolean>;
  Utilidades_ModoFullScreen(enable: boolean): Promise<boolean>;
  Utilidades_ReiniciarDispositivo(reason: string): Promise<boolean>;
  
  // Constants
  getConstants(): {
    DocumentDirectoryPath: string;
    CachesDirectoryPath: string;
    TemporaryDirectoryPath: string;
    PicturesDirectoryPath: string;
    DownloadDirectoryPath: string;
    ExternalStorageDirectoryPath: string | null;
    AppExternalFilesDirectoryPath: string | null;
    AppExternalCachesDirectoryPath: string | null;
  };
}

export default TurboModuleRegistry.getEnforcing<Spec>('TectoySunmiSdk');