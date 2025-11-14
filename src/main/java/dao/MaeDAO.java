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
import model.Mae;

public class MaeDAO {
	//CREATE
	public void insert(Mae mae) {
        String sql = "INSERT INTO mae(nome, endereco, telefone, dataAniversario) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, mae.getNome());
            stmt.setString(2, mae.getEndereco());
            stmt.setString(3, mae.getTelefone());
            stmt.setDate(4, Date.valueOf(mae.getDataAniversario()));

            stmt.executeUpdate();

            // Recupera o ID gerado e atualiza no objeto
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                mae.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	//READ
	public List<Mae> listAll() {
        String sql = "SELECT * FROM mae";
        List<Mae> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mae mae = new Mae(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("endereco"),
                    rs.getDate("dataAniversaro").toLocalDate()
                );

                lista.add(mae);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
	
	public Mae findById(int id) {
        String sql = "SELECT * FROM mae WHERE id = ?";
        Mae mae = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mae = new Mae(
	            		rs.getInt("id"),
	                    rs.getString("nome"),
	                    rs.getString("telefone"),
	                    rs.getString("endereco"),
	                    rs.getDate("dataAniversaro").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mae; 
    }

}
