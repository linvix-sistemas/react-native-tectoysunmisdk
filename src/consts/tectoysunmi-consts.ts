import { NativeModules } from 'react-native';

const TSSDK = NativeModules.TectoySunmiSdk;

export const DocumentDirectoryPath = TSSDK.DocumentDirectoryPath;
export const CachesDirectoryPath = TSSDK.CachesDirectoryPath;
export const TemporaryDirectoryPath = TSSDK.TemporaryDirectoryPath;
export const PicturesDirectoryPath = TSSDK.PicturesDirectoryPath;
export const DownloadDirectoryPath = TSSDK.DownloadDirectoryPath;

export const ExternalStorageDirectoryPath = TSSDK.ExternalStorageDirectoryPath;
export const AppExternalFilesDirectoryPath =
  TSSDK.AppExternalFilesDirectoryPath;
export const AppExternalCachesDirectoryPath =
  TSSDK.AppExternalCachesDirectoryPath;
