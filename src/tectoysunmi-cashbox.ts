import { NativeModules } from 'react-native';

const { TectoySunmiSdk } = NativeModules;

const AbrirGaveta = async () => {
  try {
    return await TectoySunmiSdk.openCashBox();
  } catch (error) {
    throw error;
  }
};

const TecToySunmiCashBoxSDK = {
  AbrirGaveta,
};

export default TecToySunmiCashBoxSDK;
