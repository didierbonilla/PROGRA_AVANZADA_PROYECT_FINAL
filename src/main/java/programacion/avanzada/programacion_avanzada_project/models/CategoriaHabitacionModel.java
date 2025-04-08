package programacion.avanzada.programacion_avanzada_project.models;

public class CategoriaHabitacionModel {
    public String CATEGORIA_CODIGO;
    public String CATEGORIA_NOMBRE;
    public Integer HABITACIONES;
    public Integer HABITACIONES_DISPONIBLES;

    public CategoriaHabitacionModel(String CATEGORIA_CODIGO, String CATEGORIA_NOMBRE, Integer HABITACIONES, Integer HABITACIONES_DISPONIBLES) {
        this.CATEGORIA_CODIGO = CATEGORIA_CODIGO;
        this.CATEGORIA_NOMBRE = CATEGORIA_NOMBRE;
        this.HABITACIONES = HABITACIONES;
        this.HABITACIONES_DISPONIBLES = HABITACIONES_DISPONIBLES;
    }

    public String getCATEGORIA_CODIGO() {
        return CATEGORIA_CODIGO;
    }

    public String getCATEGORIA_NOMBRE() {
        return CATEGORIA_NOMBRE;
    }

    public Integer getHABITACIONES() {
        return HABITACIONES;
    }

    public Integer getHABITACIONES_DISPONIBLES() {
        return HABITACIONES_DISPONIBLES;
    }

    public void setCATEGORIA_CODIGO(String CATEGORIA_CODIGO) {
        this.CATEGORIA_CODIGO = CATEGORIA_CODIGO;
    }

    public void setCATEGORIA_NOMBRE(String CATEGORIA_NOMBRE) {
        this.CATEGORIA_NOMBRE = CATEGORIA_NOMBRE;
    }

    public void setHABITACIONES(Integer HABITACIONES) {
        this.HABITACIONES = HABITACIONES;
    }

    public void setHABITACIONES_DISPONIBLES(Integer HABITACIONES_DISPONIBLES) {
        this.HABITACIONES_DISPONIBLES = HABITACIONES_DISPONIBLES;
    }

}
