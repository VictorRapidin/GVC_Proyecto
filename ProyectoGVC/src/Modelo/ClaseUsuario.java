
package Modelo;

public class ClaseUsuario {
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
    public ClaseUsuario(String Nombre, String Apellido, String Correo, String Password, boolean Activo, String Rol) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Correo = Correo;
        this.Password = Password;
        this.Activo = Activo;
        this.Rol = Rol;
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
    
}
