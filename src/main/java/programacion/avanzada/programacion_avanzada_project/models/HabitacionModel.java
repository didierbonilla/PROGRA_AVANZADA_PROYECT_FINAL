package programacion.avanzada.programacion_avanzada_project.models;

public class HabitacionModel {

    private String HABITACION_CODIGO;
    private String CATEGORIA_CODIGO;
    private String CATEGORIA_NOMBRE;
    private int PISO;
    private int NUMERO;
    private int CAPACIDAD;
    private double PRECIO;
    private String RESERVADA; // "Y" o "N"

    public HabitacionModel(String HABITACION_CODIGO, String CATEGORIA_CODIGO, String CATEGORIA_NOMBRE,
                           int PISO, int NUMERO, int CAPACIDAD, double PRECIO, String RESERVADA) {
        this.HABITACION_CODIGO = HABITACION_CODIGO;
        this.CATEGORIA_CODIGO = CATEGORIA_CODIGO;
        this.CATEGORIA_NOMBRE = CATEGORIA_NOMBRE;
        this.PISO = PISO;
        this.NUMERO = NUMERO;
        this.CAPACIDAD = CAPACIDAD;
        this.PRECIO = PRECIO;
        this.RESERVADA = RESERVADA;
    }

    public String getHABITACION_CODIGO() { return HABITACION_CODIGO; }
    public String getCATEGORIA_CODIGO() { return CATEGORIA_CODIGO; }
    public String getCATEGORIA_NOMBRE() { return CATEGORIA_NOMBRE; }
    public int getPISO() { return PISO; }
    public int getNUMERO() { return NUMERO; }
    public int getCAPACIDAD() { return CAPACIDAD; }
    public double getPRECIO() { return PRECIO; }
    public String getRESERVADA() { return RESERVADA; }

    public void setHABITACION_CODIGO(String HABITACION_CODIGO) { this.HABITACION_CODIGO = HABITACION_CODIGO; }
    public void setCATEGORIA_CODIGO(String CATEGORIA_CODIGO) { this.CATEGORIA_CODIGO = CATEGORIA_CODIGO; }
    public void setCATEGORIA_NOMBRE(String CATEGORIA_NOMBRE) { this.CATEGORIA_NOMBRE = CATEGORIA_NOMBRE; }
    public void setPISO(int PISO) { this.PISO = PISO; }
    public void setNUMERO(int NUMERO) { this.NUMERO = NUMERO; }
    public void setCAPACIDAD(int CAPACIDAD) { this.CAPACIDAD = CAPACIDAD; }
    public void setPRECIO(double PRECIO) { this.PRECIO = PRECIO; }
    public void setRESERVADA(String RESERVADA) { this.RESERVADA = RESERVADA; }

    public boolean isReserved() {
        return "Y".equalsIgnoreCase(RESERVADA);
    }
}


