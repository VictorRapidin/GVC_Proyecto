
package Modelo;


public class ClaseCampania {
    private int idCampania;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private String estado;

    //Constructor vacio
    public ClaseCampania() {
        this.idCampania = 0;
        this.nombre = "None";
        this.descripcion = "None";
        this.fechaInicio = "None";
        this.fechaFin = "None";
        this.estado = "PLANEADA";
    }

    //Constructor personalizado
    public ClaseCampania(int idCampania, String nombre, String descripcion, String fechaInicio, String fechaFin, String estado) {
        this.idCampania = idCampania;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }
    //Getters
    public int getIdCampania() {
        return idCampania;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    
    //Setters
    public void setIdCampania(int idCampania) {
        this.idCampania = idCampania;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}
