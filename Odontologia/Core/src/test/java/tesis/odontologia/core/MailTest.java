/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core;

import javax.mail.MessagingException;
import org.testng.annotations.Test;
import tesis.odontologia.core.mail.SMTPConfig;

/**
 *
 * @author Enzo
 */
public class MailTest extends AbstractTest {

    @Test
    public void crearsmtpConfig() throws MessagingException {
        if (SMTPConfig.sendMail(false,"Probando envío de correo", " Cuerpo del Mensaje.", "mau.g.sistemas@gmail.com,enzo.biancato@hotmail.com")) {

            System.out.println("envío Correcto");

        } else {
            System.out.println("envío Fallido");
        }

    }
}
