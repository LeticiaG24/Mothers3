package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.Rel;

public class RelDAO {
	//CREATE
	public void insert(Rel rel) {
        String sql = "INSERT INTO rel(id_mae, id_encontro, id_servico) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, rel.getIdMae());
            stmt.setInt(2, rel.getIdEncontro());
            stmt.setInt(3, rel.getIdServico());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	//READ
	public List<Rel> listAll() {
        String sql = "SELECT * FROM rel";
        List<Rel> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rel rel = new Rel(
                    rs.getInt("id_mae"),
                    rs.getInt("id_servico"),
                    rs.getInt("id_encontro")
                );

                lista.add(rel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
