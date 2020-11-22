/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aescifrado1;

/**
 *
 * @author yonda
 */
public class AESCifrado1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final String claveCif = "HiImMerlinThisPassIs192b";            
        String plainText = "Hola Criptografia 2021-1";            
             
        CifradorAES cipher = new CifradorAES();
             
        String cifrado = cipher.cifrar(plainText, claveCif);
        String descifrado = cipher.descifrar(cifrado, claveCif);
             
        System.out.println("Texto Plano:      " + plainText);
        System.out.println("Texto Cifrado:    " + cifrado);
        System.out.println("Texto Descifrado: " + descifrado);  
    }
}
