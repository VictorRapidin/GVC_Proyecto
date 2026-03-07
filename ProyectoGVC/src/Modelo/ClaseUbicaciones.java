
package Modelo;

public class ClaseUbicaciones {
    private int idUbicacion;
    private int ruta; 
    private String rc;
    private String codigo;
    private String calle1;
    private String calle2;
    private String sentido;
    private String estado;
    private String fechaInstalacion;
    private String tipoUbicacion;
    private double latitud;
    private double longitud;

    public ClaseUbicaciones() {
        this.idUbicacion = 0;
        this.ruta = 0;
        this.rc = "None";
        this.codigo = "None";
        this.calle1 = "None";
        this.calle2 = "None";
        this.sentido = "None";
        this.estado = "ACTIVA";
        this.fechaInstalacion = "None";
        this.tipoUbicacion = "None";
        this.latitud = 0;
        this.longitud = 0;
    }

    public ClaseUbicaciones(int idUbicacion, int ruta, String rc, String codigo, String calle1, String calle2, String sentido, String estado, String fechaInstalacion, String tipoUbicacion, double latitud, double longitud) {
        this.idUbicacion = idUbicacion;
        this.ruta = ruta;
        this.rc = rc;
        this.codigo = codigo;
        this.calle1 = calle1;
        this.calle2 = calle2;
        this.sentido = sentido;
        this.estado = estado;
        this.fechaInstalacion = fechaInstalacion;
        this.tipoUbicacion = tipoUbicacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public int getRuta() {
        return ruta;
    }

    public String getRc() {
        return rc;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCalle1() {
        return calle1;
    }

    public String getCalle2() {
        return calle2;
    }

    public String getSentido() {
        return sentido;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaInstalacion() {
        return fechaInstalacion;
    }

    public String getTipoUbicacion() {
        return tipoUbicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaInstalacion(String fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public void setTipoUbicacion(String tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    
    
}
