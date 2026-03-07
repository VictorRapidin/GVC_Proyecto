
package Modelo;

public class ClaseActividad {
    private int idActividad;
    private String nombreActividad;
    private String descripcion;
    private String fechaCreacion;
    private String fechaProgramada;
    private String fechaInicio;
    private String fechaFinalizacion;
    private String estado;
    private String prioridad;
    private int idCreador;
    private int idAsignado;
    private int idUbicacion;
    private int idCampania;

    //Constructor vacio
    public ClaseActividad() {
        this.idActividad = 0;
        this.nombreActividad = "None";
        this.descripcion = "None";
        this.fechaCreacion = "None";
        this.fechaProgramada = "None";
        this.fechaInicio = "None";
        this.fechaFinalizacion = "None";
        this.estado = "PENDIENTE";
        this.prioridad = "MEDIA";
        this.idCreador = 0;
        this.idAsignado = 0;
        this.idUbicacion = 0;
        this.idCampania = 0;
    }
    
    //Constructor personalizado

    public ClaseActividad(int idActividad, String nombreActividad, String descripcion, String fechaCreacion, String fechaProgramada, String fechaInicio, String fechaFinalizacion, String estado, String prioridad, int idCreador, int idAsignado, int idUbicacion, int idCampania) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaProgramada = fechaProgramada;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.idCreador = idCreador;
        this.idAsignado = idAsignado;
        this.idUbicacion = idUbicacion;
        this.idCampania = idCampania;
    }
    
    //Getters

    public int getIdActividad() {
        return idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public int getIdCreador() {
        return idCreador;
    }

    public int getIdAsignado() {
        return idAsignado;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public int getIdCampania() {
        return idCampania;
    }
    
    //Setters

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }

    public void setIdAsignado(int idAsignado) {
        this.idAsignado = idAsignado;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public void setIdCampania(int idCampania) {
        this.idCampania = idCampania;
    }
    
    
    
    
}
