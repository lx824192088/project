package com.shipment.common;


import java.util.UUID;


public class UidUtil {
    /**
     * get uuid
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
