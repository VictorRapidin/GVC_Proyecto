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

    public ClaseUbicaciones() {
        this.idUbicacion = 0;
        this.ruta = 0;
        this.rc = "None";
        this.codigo = "";
        this.calle1 = "";
        this.calle2 = "";
        this.sentido = "";
        this.estado = "ACTIVA";
        this.fechaInstalacion = "";
        this.tipoUbicacion = "PARABUS";
    }

    // --- GETTERS ---
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

    // --- SETTERS ---
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
}
