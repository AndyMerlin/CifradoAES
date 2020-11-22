/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aescifrado1;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author yonda
 */
public class CifradorAES {
    private byte[] llaveCrifrado;
    private Cipher cifrador;
    private byte[] datos2Cif;
    private byte[] datosCif;
    private byte[] datos2Des;
    private byte[] datosDes;
    
    public CifradorAES() {
        try {
            //Se establece AES, Modo ECB con padding PKCS5
            cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(CifradorAES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private SecretKeySpec crearLlave(String llave) {
        try {
            llaveCrifrado = llave.getBytes("UTF-8");
            
            //Usamos algoritmo hash SHA-256
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            llaveCrifrado = sha.digest(llaveCrifrado);
            llaveCrifrado = Arrays.copyOf(llaveCrifrado, 24); //Rellena con 0 si no
                                                              //Alcanza el tamaño
            //Retorna la llave secreta
            SecretKeySpec sK = new SecretKeySpec(llaveCrifrado, "AES");
            return sK;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            System.out.println("Error al crear la llave de cifrado");
            return null;
        }
    }
    
    public String cifrar(String data, String llaveSecreta) {
        try {
            SecretKeySpec secretKey = this.crearLlave(llaveSecreta); //Se establece la llave 
            
            cifrador.init(Cipher.ENCRYPT_MODE, secretKey); //Modo cifrar
            datos2Cif = data.getBytes("UTF-8"); //Se obtiene el texto plano en bytes
            datosCif = cifrador.doFinal(datos2Cif); //Se hace el cifrado
            
            //Retorna el texto cifrado
            String encriptado = Base64.getEncoder().encodeToString(datosCif);
            return encriptado;
        } catch (InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Error al cifrar la informacion");
            return null;
        }
    }
    
    public String descifrar(String datosC, String llaveSecreta) {
        try {
            SecretKeySpec secretKey = this.crearLlave(llaveSecreta); //Se establece la llave 
            
            cifrador.init(Cipher.DECRYPT_MODE, secretKey); //Modo descifrar
            
            datos2Des = Base64.getDecoder().decode(datosC); //Se obtiene el texto cifrado
            datosDes = cifrador.doFinal(datos2Des); //Se hace el descifrado
            
            String datos = new String(datosDes); //Retorna el texto descifrado
            return datos;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Error al descifrar la informacion");
            return null;
        }
    }
}
