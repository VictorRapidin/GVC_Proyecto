
package Modelo;

public class ClaseIncidencias {
    
    private int idIncidencia;
    private String tipo;
    private String descripcion;
    private String fechaReporte;
    private String estado;
    private int idActividad;

    
    //Constructor vacio
    public ClaseIncidencias() {
        this.idIncidencia = 0;
        this.tipo = "None";
        this.descripcion = "None";
        this.fechaReporte = "None";
        this.estado = "PENDIENTE";
        this.idActividad = 0;
    }
    
    //Constructor personalizado

    public ClaseIncidencias(int idIncidencia, String tipo, String descripcion, String fechaReporte, String estado, int idActividad) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.estado = estado;
        this.idActividad = idActividad;
    }
    //Getters

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public String getEstado() {
        return estado;
    }

    public int getIdActividad() {
        return idActividad;
    }
    //Setters

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaReporte(String fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
    
    
}
