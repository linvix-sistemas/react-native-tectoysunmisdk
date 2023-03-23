// IStateLamp.aidl
package com.sunmi.statuslampmanager;

// Declare any non-default types here with import statements
import java.lang.String;

interface IStateLamp {

    /**
     * Função: controlar uma única luz de alarme
     * parâmetro：
     * [in] status      Estado，0 on，1 off
     * [in] lamp         Luz LED，parâmetro：
     *                      "Led-1"
     *                      "Led-2"
     *                      "Led-3"
     *                      "Led-4"
     *                      "Led-5"
     *                      "Led-6"
     * valor de retorno：null
     */
    void controlLamp(in int status, in String lamp);

    /**
     * Controlar uma única exibição de ciclo da lâmpada de alarme
     * [in] status       Estado, 0 on, 1 off
     * [in] lightTime    Timeout on, unidade: milissegundos (ms)
     * [in] putoutTime   Timeout off, unidade: milissegundos (ms)
     * [in] lamp         Luz LED，parâmetro
     *                      "Led-1"
     *                      "Led-2"
     *                      "Led-3"
     *                      "Led-4"
     *                      "Led-5"
     *                      "Led-6"
     */
    void controlLampForLoop(in int status, in long lightTime, in long putoutTime, in String lamp);

    /**
     * Apaga as lâmpadas
     */
    void closeAllLamp();

}