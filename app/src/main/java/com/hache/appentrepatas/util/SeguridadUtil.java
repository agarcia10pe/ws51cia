package com.hache.appentrepatas.util;

import com.hache.appentrepatas.dto.UsuarioDTO;
import com.hache.appentrepatas.helper.SharedPreferencesManager;

public  class SeguridadUtil {

    public static void asignarUsuario(UsuarioDTO usuarioDTO) {
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_USER, usuarioDTO.getCorreo());
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_NOMBRE, usuarioDTO.getNombre());
        SharedPreferencesManager.setSomeStringValue(Constants.PREF_APELLIDO, usuarioDTO.getApePaterno());
        SharedPreferencesManager.setSomeIntValue(Constants.PREF_TIPO_USUARIO, usuarioDTO.getIdTipoUsu());
    }

    public static UsuarioDTO getUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCorreo(SharedPreferencesManager.getSomeStringValue(Constants.PREF_USER));
        usuarioDTO.setNombre(SharedPreferencesManager.getSomeStringValue(Constants.PREF_NOMBRE));
        usuarioDTO.setApePaterno(SharedPreferencesManager.getSomeStringValue(Constants.PREF_APELLIDO));
        usuarioDTO.setIdTipoUsu((byte) SharedPreferencesManager.getSomeIntValue(Constants.PREF_TIPO_USUARIO));
        return usuarioDTO;
    }

    public static boolean esAutenticado() {
        return (SharedPreferencesManager.getSomeStringValue(Constants.PREF_USER) != null);
    }

    public static void logout() {
        SharedPreferencesManager.remove(Constants.PREF_USER);
        SharedPreferencesManager.remove(Constants.PREF_NOMBRE);
        SharedPreferencesManager.remove(Constants.PREF_APELLIDO);
        SharedPreferencesManager.remove(Constants.PREF_TIPO_USUARIO);
    }
}
