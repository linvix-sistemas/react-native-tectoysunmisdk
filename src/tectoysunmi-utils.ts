import NativeTectoySunmiSdk from './specs/NativeTectoySunmiSdk';

const FecharApp = async () => {
  try {
    return await NativeTectoySunmiSdk.Utilidades_FecharApp();
  } catch (error) {
    throw error;
  }
};

const ModoFullScreen = async (ativar: boolean = true) => {
  try {
    return await NativeTectoySunmiSdk.Utilidades_ModoFullScreen(ativar);
  } catch (error) {
    throw error;
  }
};

const ReiniciarDispositivo = async (motivo = '') => {
  try {
    return await NativeTectoySunmiSdk.Utilidades_ReiniciarDispositivo(motivo);
  } catch (error) {
    throw error;
  }
};

const TecToySunmiUtilsSDK = {
  FecharApp,
  ModoFullScreen,
  ReiniciarDispositivo,
};

export default TecToySunmiUtilsSDK;
