import React from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';

// NATIVE MODULES
import NativeModuleTectToySunmiSDK, {
  TectoyLCDFuncaoEnum,
} from '@linvix-sistemas/react-native-tectoysunmisdk';

const HomeScreen = () => {
  const onRequestPrintQrCode = async () => {
    try {
      const status = await NativeModuleTectToySunmiSDK.impressora.ObterStatus();
      console.log(status);

      NativeModuleTectToySunmiSDK.impressora.ImprimirQRCode({
        data: 'linvix.com.br',
        size: 10,
        error: 3,
      });

      NativeModuleTectToySunmiSDK.impressora.AvancarLinha(3);

      NativeModuleTectToySunmiSDK.impressora.CortarPapel();
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestPrintText = async () => {
    try {
      const status = await NativeModuleTectToySunmiSDK.impressora.ObterStatus();
      console.log(status);

      NativeModuleTectToySunmiSDK.impressora.ImprimirTexto(
        'LINVIX SISTEMAS\nwww.linvix.com.br'
      );

      NativeModuleTectToySunmiSDK.impressora.AvancarLinha(8);

      NativeModuleTectToySunmiSDK.impressora.CortarPapel();
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestPrintRaw = async () => {
    // PALAVRA: TESTE (HEX)
    const bytes = [0x54, 0x45, 0x53, 0x54, 0x45];

    try {
      const status = await NativeModuleTectToySunmiSDK.impressora.ObterStatus();
      console.log(status);

      NativeModuleTectToySunmiSDK.impressora.ImprimirRAW(bytes);

      NativeModuleTectToySunmiSDK.impressora.CortarPapel();
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestAbrirGaveta = async () => {
    try {
      const result = await NativeModuleTectToySunmiSDK.gaveta.AbrirGaveta();
      console.log('onRequestAbrirGaveta', result);
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestTextoLCD = async () => {
    try {
      const result = await NativeModuleTectToySunmiSDK.lcd.EnviarTexto(
        'LINVIX SISTEMAS',
        16,
        true
      );
      console.log('onRequestTextoLCD', result);
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestTextosLCD = async () => {
    try {
      const result = await NativeModuleTectToySunmiSDK.lcd.EnviarTextos({
        texto1: 'LINVIX',
        texto1_peso_tamanho: 40,

        texto2: 'DESENVOLVIMENTO',
        texto2_peso_tamanho: 30,

        texto3: 'DE SISTEMAS',
        texto3_peso_tamanho: 30,
      });
      console.log('onRequestTextosLCD', result);
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestDesligarLCD = async () => {
    try {
      const result = await NativeModuleTectToySunmiSDK.lcd.ControlarLCD(
        TectoyLCDFuncaoEnum.DESLIGAR_LCD
      );
      console.log('onRequestDesligarLCD', result);
    } catch (error: Error | any) {
      JSON.stringify(error);
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestLigarLCD = async () => {
    try {
      const result = await NativeModuleTectToySunmiSDK.lcd.ControlarLCD(
        TectoyLCDFuncaoEnum.LIGAR_LCD
      );
      console.log('onRequestLigarLCD', result);
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  const onRequestLimparLCD = async () => {
    try {
      const result = await NativeModuleTectToySunmiSDK.lcd.ControlarLCD(
        TectoyLCDFuncaoEnum.LIMPAR_TELA
      );
      console.log('onRequestLimparLCD', result);
    } catch (error: Error | any) {
      console.log(error.code);
      console.log(error.message);
      console.log(JSON.stringify(error));
    }
  };

  return (
    <View style={Styles.main}>
      <View style={Styles.container}>
        <View
          style={{ display: 'flex', flexDirection: 'row', marginBottom: 30 }}
        >
          <TouchableOpacity
            style={[Styles.buttonHorizontal, { marginLeft: 0 }]}
            onPress={onRequestPrintQrCode}
          >
            <Text style={{ color: '#fff' }}>Imprimir QRCode</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestPrintText}
          >
            <Text style={{ color: '#fff' }}>Imprimir Texto</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestPrintRaw}
          >
            <Text style={{ color: '#fff' }}>Imprimir TEXTO - RAW</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestAbrirGaveta}
          >
            <Text style={{ color: '#fff' }}>Abrir Gaveta de Dinheiro</Text>
          </TouchableOpacity>
        </View>

        <View
          style={{ display: 'flex', flexDirection: 'row', marginBottom: 30 }}
        >
          <TouchableOpacity
            style={[Styles.buttonHorizontal, { marginLeft: 0 }]}
            onPress={onRequestTextoLCD}
          >
            <Text style={{ color: '#fff' }}>LDC = Enviar TEXTO</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestTextosLCD}
          >
            <Text style={{ color: '#fff' }}>LDC = Enviar TEXTOS</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestDesligarLCD}
          >
            <Text style={{ color: '#fff' }}>LDC -&gt; DESLIGAR</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestLigarLCD}
          >
            <Text style={{ color: '#fff' }}>LDC -&gt; LIGAR</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={Styles.buttonHorizontal}
            onPress={onRequestLimparLCD}
          >
            <Text style={{ color: '#fff' }}>LDC -&gt; LIMPAR</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

const Styles = StyleSheet.create({
  main: {
    display: 'flex',
    flex: 1,
  },

  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    alignContent: 'center',
  },

  button: {
    marginBottom: 30,
    backgroundColor: 'blue',
    padding: 10,
    borderRadius: 3,
    minWidth: 400,
    justifyContent: 'center',
    alignItems: 'center',
  },

  buttonHorizontal: {
    marginLeft: 30,
    backgroundColor: 'blue',
    padding: 10,
    borderRadius: 3,
    minWidth: 180,
    justifyContent: 'center',
    alignItems: 'center',
  },

  buttonText: {
    color: '#fff',
  },
});

export default HomeScreen;
