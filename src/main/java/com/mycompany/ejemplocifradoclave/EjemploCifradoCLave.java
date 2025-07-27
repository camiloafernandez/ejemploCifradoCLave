/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejemplocifradoclave;

/**
 *
 * @author kmilo
 */

import java.awt.HeadlessException;
import java.security.*;
import java.util.Base64;
import javax.swing.JOptionPane;
public class EjemploCifradoCLave {

  public static void main(String[] args) {
        try {
            // 1. Solicitar contraseña
            String contraseña = JOptionPane.showInputDialog("Ingrese la contraseña:");

            // 2. Generar par de claves RSA
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            KeyPair claves = generador.generateKeyPair();
            PrivateKey clavePrivada = claves.getPrivate();
            PublicKey clavePublica = claves.getPublic();

            // 3. Firmar la contraseña con clave privada
            Signature firma = Signature.getInstance("SHA256withRSA");
            firma.initSign(clavePrivada);
            firma.update(contraseña.getBytes());
            byte[] firmaBytes = firma.sign();
            String firmaBase64 = Base64.getEncoder().encodeToString(firmaBytes);

            // 4. Verificar la firma con clave pública
            firma.initVerify(clavePublica);
            firma.update(contraseña.getBytes());
            boolean esValida = firma.verify(Base64.getDecoder().decode(firmaBase64));

            // 5. Mostrar resultados
            JOptionPane.showMessageDialog(null,
                "Contraseña original: " + contraseña +
                "\nFirma digital (Base64):\n" + firmaBase64 +
                "\n¿Firma válida? " + esValida);

        } catch (HeadlessException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
