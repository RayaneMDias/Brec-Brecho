package br.com.brecbrecho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt; // Importa o BCrypt

import br.com.brecbrecho.model.Administrador;
import br.com.brecbrecho.util.ConexaoDB;

public class AdministradorDAO {

   
    public Administrador validarLogin(String email, String senha) {
        
        String sql = "SELECT * FROM Administrador WHERE email = ?";
        Administrador admin = null;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String hashArmazenado = rs.getString("senha");

                    if (BCrypt.checkpw(senha, hashArmazenado)) {
                        
                        admin = new Administrador();
                        admin.setIdAdmin(rs.getInt("id_admin"));
                        admin.setNome(rs.getString("nome"));
                        admin.setEmail(rs.getString("email"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin; 
    }

    public boolean cadastrarAdministrador(Administrador admin) {
        
        String sql = "INSERT INTO Administrador (nome, email, senha) VALUES (?, ?, ?)";
      
        String hash = BCrypt.hashpw(admin.getSenha(), BCrypt.gensalt());

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, admin.getNome());
            ps.setString(2, admin.getEmail());
            ps.setString(3, hash); 

            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}