import NativeTectoySunmiSdk from './specs/NativeTectoySunmiSdk';

const AbrirGaveta = async () => {
  try {
    return await NativeTectoySunmiSdk.openCashBox();
  } catch (error) {
    throw error;
  }
};

const TecToySunmiCashBoxSDK = {
  AbrirGaveta,
};

export default TecToySunmiCashBoxSDK;
