/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import tesis.odontologia.core.exception.GenericException;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "CAMPOSINO")
public class CampoSiNo extends DetalleHistoriaClinica {

    private Boolean siNo;

    public CampoSiNo() {
        siNo = false;
    }

    public CampoSiNo(String nombre, Integer grupo) {
        super(nombre, grupo);
    }

    public Boolean getSiNo() {
        return siNo;
    }

    public void setSiNo(Boolean siNo) {
        this.siNo = siNo;
    }
    
}
