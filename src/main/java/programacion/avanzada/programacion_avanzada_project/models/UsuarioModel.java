package programacion.avanzada.programacion_avanzada_project.models;

public class UsuarioModel {
    private int id;
    private String usuario;
    private String nombre;
    private String clave;
    private String rol;
    private String email;

    public UsuarioModel() {}

    public UsuarioModel(int id, String usuario, String nombre, String clave, String rol, String email) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
        this.email = email;
    }

    public UsuarioModel(String usuario, String nombre, String clave) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
    }

    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getClave() { return clave; }
    public String getRol() { return rol; }
    public String getEmail() { return email; }

    public void setId(int id) { this.id = id; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setClave(String clave) { this.clave = clave; }
    public void setRol(String rol) { this.rol = rol; }
    public void setEmail(String email) { this.email = email; }
}
