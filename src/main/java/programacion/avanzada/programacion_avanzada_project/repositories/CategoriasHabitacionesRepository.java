package programacion.avanzada.programacion_avanzada_project.repositories;

import programacion.avanzada.programacion_avanzada_project.models.CategoriaHabitacionModel;
import programacion.avanzada.programacion_avanzada_project.models.HabitacionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriasHabitacionesRepository {

    private final Connection conn;

    public CategoriasHabitacionesRepository(Connection conn) {
        this.conn = conn;
    }

    public List<CategoriaHabitacionModel> list() {
        List<CategoriaHabitacionModel> categorias = new ArrayList<>();
        String sql = "SELECT CATEGORIA_CODIGO, CATEGORIA_NOMBRE, HABITACIONES, HABITACIONES_DISPONIBLES FROM VW_CATEGORIAS_HABITACIONES";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categorias.add(new CategoriaHabitacionModel(
                        rs.getString("CATEGORIA_CODIGO"),
                        rs.getString("CATEGORIA_NOMBRE"),
                        rs.getInt("HABITACIONES"),
                        rs.getInt("HABITACIONES_DISPONIBLES")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public String getCategoryName(String code){
        String name = "";
        String sql = "SELECT CATEGORIA_NOMBRE FROM VW_CATEGORIAS_HABITACIONES WHERE CATEGORIA_CODIGO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                name = rs.getString("CATEGORIA_NOMBRE");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    public boolean create(CategoriaHabitacionModel categoria) {
        String sql = "INSERT INTO TBL_CATEGORIAS_HABITACIONES (CATEGORIA_CODIGO, CATEGORIA_NOMBRE) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.CATEGORIA_CODIGO);
            stmt.setString(2, categoria.CATEGORIA_NOMBRE);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CategoriaHabitacionModel categoria) {
        String sql = "UPDATE TBL_CATEGORIAS_HABITACIONES SET CATEGORIA_NOMBRE = ? WHERE CATEGORIA_CODIGO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.CATEGORIA_CODIGO);
            stmt.setString(2, categoria.CATEGORIA_NOMBRE);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String codigo) {
        String sql = "DELETE FROM TBL_CATEGORIAS_HABITACIONES WHERE CATEGORIA_CODIGO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
