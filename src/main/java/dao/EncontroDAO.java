package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import model.Encontro;

public class EncontroDAO {
	//CREATE
	public void insert(Encontro encontro) {
        String sql = "INSERT INTO encontro(data_encontro, id_mae, descricao, andamento) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDate(1, Date.valueOf(encontro.getData()));
            stmt.setInt(2, encontro.getIdMaeResp());
            stmt.setString(3, encontro.getDescricao());
            stmt.setString(4, encontro.getStatus());

            stmt.executeUpdate();

            // Recupera o ID gerado e atualiza no objeto
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                encontro.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	//READ
	public List<Encontro> listAll() {
        String sql = "SELECT * FROM encontro";
        List<Encontro> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Encontro encontro = new Encontro(
                    rs.getInt("id"),
                    rs.getDate("data").toLocalDate(),
                    rs.getString("descricao"),
                    rs.getString("status"),
                    rs.getInt("idMaeResp")
                );

                lista.add(encontro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
	
	public Encontro findById(int id) {
        String sql = "SELECT * FROM encontro WHERE id = ?";
        Encontro encontro = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    encontro = new Encontro(
                		rs.getInt("id"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("descricao"),
                        rs.getString("status"),
                        rs.getInt("idMaeResp")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return encontro; 
    }
}
