
package Modelo;



public class ClaseMateriales {
    
    //Atributos
    private int idMaterial;
    private String nombreMaterial;
    private String tipo;
    private int cantidadDisponible;
    private int stockMinimo;

    //Metodos
    //Constructor vacio
    public ClaseMateriales() {
        this.idMaterial = 0; //No hay id de material definido
        this.nombreMaterial = "None"; //No hay definido nombre material
        this.tipo = "None"; //
        this.cantidadDisponible = 0;
        this.stockMinimo = 0;   
    }

    
    //Constructor personalizado
    public ClaseMateriales(int idMaterial, String nombreMaterial, String tipo, int cantidadDisponible, int stockMinimo) {
        this.idMaterial = idMaterial;
        this.nombreMaterial = nombreMaterial;
        this.tipo = tipo;
        this.cantidadDisponible = cantidadDisponible;
        this.stockMinimo = stockMinimo;
    }

    //Getters
    public int getIdMaterial() {
        return idMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }
    
    
}
