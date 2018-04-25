package com.sec.cryptohdslibrary.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

public class Util {

    public static String bytesToString(byte[] bytes2convert) {
        return Base64.encodeBase64String(bytes2convert);
    }

    public static byte[] stringToBytes(String string2convert) {
        return Base64.decodeBase64(string2convert);
    }

    public static byte[] objectToByte(Object object2convert) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try (ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object2convert);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object byteToObject(byte[] byte2convert) {
        ByteArrayInputStream bis = new ByteArrayInputStream(byte2convert);

		try (ObjectInput in = new ObjectInputStream(bis)) {
			Object obj = in.readObject();
			return obj;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
