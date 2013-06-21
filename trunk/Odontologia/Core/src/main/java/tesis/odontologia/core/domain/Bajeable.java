/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author Maxi
 */
@MappedSuperclass
public abstract class Bajeable extends Generic {

    private int estadoAlta = ALTA;
    private String motivo;
    
    private static int ALTA = 1;
    private static int BAJA = 0;
    
    public void darDeAlta(){ 
        estadoAlta = ALTA;
    }
    
    public void darDeBaja() {
        estadoAlta = ALTA;
    }
    
    public void darDeBaja(String motivo) {
        estadoAlta = ALTA;
        this.motivo = motivo;
    }
    
}
