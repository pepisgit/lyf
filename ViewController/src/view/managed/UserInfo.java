package view.managed;

import java.io.Serializable;

import model.entities.Usuarios;

public class UserInfo implements Serializable
{
    private SessionBean session;
    
    private String inputusuario;
    private String inputpassword;
    
    private Usuarios usuario;
    private boolean autenticado;

    public void authenticateUser()
    {
        if (inputusuario != null && !inputusuario.isEmpty())
        {
            Usuarios u = session.getServicio().qUsuarioSistema(inputusuario, inputpassword);
            if (u != null)
            {
                this.autenticado = true;
                this.usuario = u;
            }
            else
            {
                // TODO Mensaje de error
            }
            return;
        }
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setInputusuario(String inputusuario) {
        this.inputusuario = inputusuario;
    }

    public String getInputusuario() {
        return inputusuario;
    }

    public void setInputpassword(String inputpassword) {
        this.inputpassword = inputpassword;
    }

    public String getInputpassword() {
        return inputpassword;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getUsuario() {
        return usuario;
    }
}
