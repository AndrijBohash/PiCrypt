package com.krizsak;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class Main {
    public static File f_i, f_i_2, f_p;

    public static void main(String[] args) {
	System.out.println("oh hai");

        f_i = new File("pic.jpg");
        try {
            byte[] data = Files.readAllBytes(f_i.toPath());
            System.out.println("size before: " + data.length);
            /*
            byte[] password = new byte[] {'j','e', 'l', 's', 'z', 'o',
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'j','e', 'l', 's', 'z', 'o',
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '8'};
                    */
            byte[] password = picToPassword("password.jpg");

            //encrypt(data, password);

            f_i_2 = new File("encoded_pic.pcr");
            byte[] encrypted_data_in = Files.readAllBytes(f_i_2.toPath());
            decrypt(encrypted_data_in, password);


        } catch (java.io.IOException e){
            e.printStackTrace();
        }

    } //main

    public static void encrypt(byte[] data, byte[] password){
        Key key = new SecretKeySpec(password, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] enc_data = cipher.doFinal(data);
            System.out.println("size after encrypt: " + enc_data.length);

            Files.write(Paths.get("encoded_pic.pcr"), enc_data);

        } catch (java.security.NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException f){
            f.printStackTrace();
        } catch (java.security.InvalidKeyException g){
            g.printStackTrace();
        } catch (javax.crypto.IllegalBlockSizeException h){
            h.printStackTrace();
        } catch (javax.crypto.BadPaddingException i){
            i.printStackTrace();
        } catch (java.io.IOException j){
            j.printStackTrace();
        }

    } //encrypt

    public static void decrypt(byte[] encrypted_data, byte[] password){
        Key key = new SecretKeySpec(password, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] dec_data = cipher.doFinal(encrypted_data);
            System.out.println("size after decrypt: " + dec_data.length);

            Files.write(Paths.get("decoded_pic.jpg"), dec_data);

        } catch (java.security.NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException f){
            f.printStackTrace();
        } catch (java.security.InvalidKeyException g){
            g.printStackTrace();
        } catch (javax.crypto.IllegalBlockSizeException h){
            h.printStackTrace();
        } catch (javax.crypto.BadPaddingException i){
            //i.printStackTrace();
            System.out.println("Bad key, cannot decrypt, permission denied!");
        } catch (java.io.IOException j){
            j.printStackTrace();
        }

    } //decrypt

    public static byte[] picToPassword(String path){
        f_p = new File(path);
        byte[] password = new byte[32];
        try {
            byte[] pic_password = Files.readAllBytes(f_p.toPath());
            System.out.println(password.length);

            int x = pic_password.length / 32;

            for(int i=0; i<32; i++){
                System.arraycopy(pic_password, i*x, password, i, 1);
                //System.out.println(pic_password[i*x] + " " + password[i]);
            }

        } catch (java.io.IOException e){
            e.printStackTrace();
        }
        return password;
    } //picToPassword
}
