package com.sec.cryptohdslibrary.util;

import java.io.*;

public class Util {

    public static byte[] objectToByte(Object object2convert) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object2convert);
            out.flush();
            return bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }

    public static Object byteToObject(byte[] byte2convert) {
        ByteArrayInputStream bis = new ByteArrayInputStream(byte2convert);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }
}
