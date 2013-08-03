/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.domain.historiaclinica;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Maxi
 */
@Entity
@DiscriminatorValue(value = "CAMPOENUMERABLE")
public class CampoEnumerable extends DetalleHistoriaClinica {

    private Boolean checked;

    public CampoEnumerable() {
        checked = false;
    }

    public CampoEnumerable(String nombre, Integer grupo) {
        super(nombre, grupo);
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
  
}
