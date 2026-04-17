
package Modelo;

public class ClaseUsuario {
    private int idUsuario;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Password;
    private boolean Activo;
    private String Rol;

    
    //Constructor vacio
    public ClaseUsuario() {
        this.Nombre = "None";
        this.Apellido = "None";
        this.Correo = "None";
        this.Password = "None";
        this.Activo = false;
        this.Rol = "None";
    }

    //Constructor generado
    public ClaseUsuario(int idUsuario, String Nombre, String Apellido, String Correo, String Password, boolean Activo, String Rol) {
        this.idUsuario = idUsuario;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Correo = Correo;
        this.Password = Password;
        this.Activo = Activo;
        this.Rol = Rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getPassword() {
        return Password;
    }

    public boolean isActivo() {
        return Activo;
    }

    public String getRol() {
        return Rol;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }
    
}
