package programacion.avanzada.programacion_avanzada_project.repositories;

import programacion.avanzada.programacion_avanzada_project.Alertas;
import programacion.avanzada.programacion_avanzada_project.models.HabitacionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionesRepository {
    private final Connection conn;

    public HabitacionesRepository(Connection conn) {
        this.conn = conn;
    }

    public List<HabitacionModel> list() {
        List<HabitacionModel> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM VW_HABITACIONES";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                habitaciones.add(mapToModel(rs));
            }

        } catch (SQLException e) {
            Alertas.error("Error Interno SQL",e.getMessage());
        }

        return habitaciones;
    }

    public List<HabitacionModel> listByCategory(String categoriaCodigo) {
        List<HabitacionModel> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM VW_HABITACIONES WHERE CATEGORIA_CODIGO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoriaCodigo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                habitaciones.add(mapToModel(rs));
            }

        } catch (SQLException e) {
            Alertas.error("Error Interno SQL",e.getMessage());
        }

        return habitaciones;
    }

    public HabitacionModel find(String habitacionCodigo) {
        HabitacionModel item = null;
        String sql = "SELECT * FROM VW_HABITACIONES WHERE HABITACION_CODIGO = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, habitacionCodigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = mapToModel(rs);
            }

        } catch (SQLException e) {
            Alertas.error("Error Interno SQL",e.getMessage());
        }

        return item;
    }

    private HabitacionModel mapToModel(ResultSet rs) {

        try {
            return new HabitacionModel(
                rs.getString("HABITACION_CODIGO"),
                rs.getString("CATEGORIA_CODIGO"),
                rs.getString("CATEGORIA_NOMBRE"),
                rs.getInt("PISO"),
                rs.getInt("NUMERO"),
                rs.getInt("CAPACIDAD"),
                rs.getDouble("PRECIO"),
                rs.getString("RESERVADA")
            );
        }
        catch (SQLException e) {
            Alertas.error("Error Interno SQL",e.getMessage());
            return null;
        }
    }
}
