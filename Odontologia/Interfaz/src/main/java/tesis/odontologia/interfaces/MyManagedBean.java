/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.interfaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Maxi
 */
@ManagedBean(name = "MyManagedBean")
@ViewScoped
public class MyManagedBean {

    public String mainContent = "/defaultContent.xhtml";
    
    /**
     * Creates a new instance of MyManagedBean
     */
    public MyManagedBean() {
    }
  
    
    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }
    
    public void changeInterfaz(String interfaz) {
        mainContent = interfaz;
    }
}
