/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis.odontologia.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tesis.odontologia.core.dao.CatedraDao;
import tesis.odontologia.core.dao.UsuarioDao;
import tesis.odontologia.core.domain.materia.Catedra;
import tesis.odontologia.core.domain.usuario.Usuario;
import tesis.odontologia.core.service.CatedraService;
import tesis.odontologia.core.service.UsuarioService;

/**
 *
 * @author Maxi
 */
@Service(value = "usuarioService")
@Transactional(readOnly = true)
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, UsuarioDao> implements UsuarioService {

    private UsuarioDao usuarioDao;

    @Autowired
    public UsuarioServiceImpl(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
        super.setDao(usuarioDao);
    }

}
