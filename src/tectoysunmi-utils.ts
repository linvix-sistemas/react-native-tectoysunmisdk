import { NativeModules } from 'react-native';

const { TectoySunmiSdk } = NativeModules;

const FecharApp = async () => {
  try {
    return await TectoySunmiSdk.Utilidades_FecharApp();
  } catch (error) {
    throw error;
  }
};

const NativeModuleTectToySunmiUtilsSDK = {
  FecharApp,
};

export default NativeModuleTectToySunmiUtilsSDK;
