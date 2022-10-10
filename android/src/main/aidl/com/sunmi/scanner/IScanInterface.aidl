// IScanInterface.aidl
package com.sunmi.scanner;

// Declare any non-default types here with import statements

interface IScanInterface {
    /**
     *
     * key.getAction()==KeyEvent.ACTION_UP
     * key.getAction()==KeyEvent.ACTION_DWON
     */
    void sendKeyEvent(in KeyEvent key);

    /**
     *
     */
    void scan();

    /**
     *
     */
    void stop();

    /**
     *
     * 100-->NONE
     * 101-->P2Lite
     * 102-->l2-newland
     * 103-->l2-zebra
     */
    int getScannerModel();
}
