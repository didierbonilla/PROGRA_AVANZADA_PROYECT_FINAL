package programacion.avanzada.programacion_avanzada_project.models;

import java.time.LocalDate;

public class ReservaModel {
    private int RESERVA_ID;
    private LocalDate FECHA_CREACION;
    private String HABITACION_CODIGO;
    private String CATEGORIA_CODIGO;
    private String CATEGORIA_NOMBRE;
    private int PISO;
    private int NUMERO;
    private int CAPACIDAD;
    private String DNI;
    private String NOMBRE_CLIENTE;
    private LocalDate FECHA_ENTRADA;
    private LocalDate FECHA_SALIDA;
    private double PRECIO;
    private int DIAS;
    private double TOTAL;
    private String TIPO_PAGO;
    private String ESTADO;

    public ReservaModel() {}

    public ReservaModel(
        String HABITACION_CODIGO, String DNI, String NOMBRE_CLIENTE, LocalDate FECHA_ENTRADA,
        LocalDate FECHA_SALIDA, double PRECIO, int DIAS, double TOTAL, String TIPO_PAGO
    ) {
        this.HABITACION_CODIGO = HABITACION_CODIGO;
        this.DNI = DNI;
        this.NOMBRE_CLIENTE = NOMBRE_CLIENTE;
        this.FECHA_ENTRADA = FECHA_ENTRADA;
        this.FECHA_SALIDA = FECHA_SALIDA;
        this.PRECIO = PRECIO;
        this.DIAS = DIAS;
        this.TOTAL = TOTAL;
        this.TIPO_PAGO = TIPO_PAGO;
    }

    // Getters y setters
    public int getRESERVA_ID() { return RESERVA_ID; }
    public void setRESERVA_ID(int RESERVA_ID) { this.RESERVA_ID = RESERVA_ID; }

    public LocalDate getFECHA_CREACION() { return FECHA_CREACION; }
    public void setFECHA_CREACION(LocalDate FECHA_CREACION) { this.FECHA_CREACION = FECHA_CREACION; }

    public String getHABITACION_CODIGO() { return HABITACION_CODIGO; }
    public void setHABITACION_CODIGO(String HABITACION_CODIGO) { this.HABITACION_CODIGO = HABITACION_CODIGO; }

    public String getCATEGORIA_CODIGO() { return CATEGORIA_CODIGO; }
    public void setCATEGORIA_CODIGO(String CATEGORIA_CODIGO) { this.CATEGORIA_CODIGO = CATEGORIA_CODIGO; }

    public String getCATEGORIA_NOMBRE() { return CATEGORIA_NOMBRE; }
    public void setCATEGORIA_NOMBRE(String CATEGORIA_NOMBRE) { this.CATEGORIA_NOMBRE = CATEGORIA_NOMBRE; }

    public int getPISO() { return PISO; }
    public void setPISO(int PISO) { this.PISO = PISO; }

    public int getNUMERO() { return NUMERO; }
    public void setNUMERO(int NUMERO) { this.NUMERO = NUMERO; }

    public int getCAPACIDAD() { return CAPACIDAD; }
    public void setCAPACIDAD(int CAPACIDAD) { this.CAPACIDAD = CAPACIDAD; }

    public String getDNI() { return DNI; }
    public void setDNI(String DNI) { this.DNI = DNI; }

    public String getNOMBRE_CLIENTE() { return NOMBRE_CLIENTE; }
    public void setNOMBRE_CLIENTE(String NOMBRE_CLIENTE) { this.NOMBRE_CLIENTE = NOMBRE_CLIENTE; }

    public LocalDate getFECHA_ENTRADA() { return FECHA_ENTRADA; }
    public void setFECHA_ENTRADA(LocalDate FECHA_ENTRADA) { this.FECHA_ENTRADA = FECHA_ENTRADA; }

    public LocalDate getFECHA_SALIDA() { return FECHA_SALIDA; }
    public void setFECHA_SALIDA(LocalDate FECHA_SALIDA) { this.FECHA_SALIDA = FECHA_SALIDA; }

    public double getPRECIO() { return PRECIO; }
    public void setPRECIO(double PRECIO) { this.PRECIO = PRECIO; }

    public int getDIAS() { return DIAS; }
    public void setDIAS(int DIAS) { this.DIAS = DIAS; }

    public double getTOTAL() { return TOTAL; }
    public void setTOTAL(double TOTAL) { this.TOTAL = TOTAL; }

    public String getTIPO_PAGO() { return TIPO_PAGO; }
    public void setTIPO_PAGO(String TIPO_PAGO) { this.TIPO_PAGO = TIPO_PAGO; }

    public String getESTADO() { return ESTADO; }
    public void setESTADO(String ESTADO) { this.ESTADO = ESTADO; }
}
