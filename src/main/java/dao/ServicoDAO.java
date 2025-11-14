package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.Servico;

public class ServicoDAO {
	//READ
	public List<Servico> listAll() {
        String sql = "SELECT * FROM servico";
        List<Servico> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servico servico = new Servico(
                    rs.getInt("id_servico"),
                    rs.getString("nome")
                );

                lista.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
	
	public Servico findById(int id) {
        String sql = "SELECT * FROM servico WHERE id_servico = ?";
        Servico servico = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servico = new Servico(
                		rs.getInt("id_servico"),
                        rs.getString("nome")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servico; 
    }
}
