import { NativeModules } from 'react-native';

const { TectoySunmiSdk } = NativeModules;

const FecharApp = async () => {
  try {
    return await TectoySunmiSdk.Utilidades_FecharApp();
  } catch (error) {
    throw error;
  }
};

const ModoFullScreen = async (ativar: boolean = true) => {
  try {
    return await TectoySunmiSdk.Utilidades_ModoFullScreen(ativar);
  } catch (error) {
    throw error;
  }
};

const NativeModuleTectToySunmiUtilsSDK = {
  FecharApp,
  ModoFullScreen,
};

export default NativeModuleTectToySunmiUtilsSDK;
