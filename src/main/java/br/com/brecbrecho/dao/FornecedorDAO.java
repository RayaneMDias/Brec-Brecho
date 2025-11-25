package br.com.brecbrecho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

// Nossos pacotes
import br.com.brecbrecho.util.ConexaoDB;
import br.com.brecbrecho.model.Fornecedor;

public class FornecedorDAO {

	public boolean cadastrarFornecedor(Fornecedor fornecedor) {

		String sql = "INSERT INTO fornecedor (nome_loja, email, senha, cpf_cnpj, telefone, descricao, cep, rua, numero, bairro, cidade, estado) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		String hash = BCrypt.hashpw(fornecedor.getSenha(), BCrypt.gensalt());
		 

		try (Connection conn = ConexaoDB.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fornecedor.getNomeLoja());
			ps.setString(2, fornecedor.getEmail());
			ps.setString(3, hash); 
			ps.setString(4, fornecedor.getCpfCnpj());
			ps.setString(5, fornecedor.getTelefone());
			ps.setString(6, fornecedor.getDescricao());
			ps.setString(7, fornecedor.getCep());
			ps.setString(8, fornecedor.getRua());
			ps.setString(9, fornecedor.getNumero());
			ps.setString(10, fornecedor.getBairro());
			ps.setString(11, fornecedor.getCidade());
			ps.setString(12, fornecedor.getEstado());

			ps.executeUpdate();
			return true; 

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Fornecedor validarLogin(String email, String senha) {

	   
	    String sql = "SELECT * FROM fornecedor WHERE email = ?";
	    Fornecedor fornecedor = null;

	    try (Connection conn = ConexaoDB.getConexao(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, email);

	        try (ResultSet rs = ps.executeQuery()) {

	     
	            if (rs.next()) {
	                
	                
	                String hashArmazenado = rs.getString("senha");

	             
	                if (BCrypt.checkpw(senha, hashArmazenado)) {
	                    
	                    
	                    fornecedor = new Fornecedor();
	                    fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
	                    fornecedor.setNomeLoja(rs.getString("nome_loja"));
	                    fornecedor.setEmail(rs.getString("email"));
	                    fornecedor.setCpfCnpj(rs.getString("cpf_cnpj"));
	                    fornecedor.setTelefone(rs.getString("telefone"));
	                    fornecedor.setDescricao(rs.getString("descricao"));
	                    fornecedor.setCep(rs.getString("cep"));
	                    fornecedor.setRua(rs.getString("rua"));
	                    fornecedor.setNumero(rs.getString("numero"));
	                    fornecedor.setBairro(rs.getString("bairro"));
	                    fornecedor.setCidade(rs.getString("cidade"));
	                    fornecedor.setEstado(rs.getString("estado"));
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return fornecedor; 
	}
	public boolean atualizarFornecedor(Fornecedor fornecedor) {

		
		String sql = "UPDATE fornecedor SET nome_loja = ?, email = ?, senha = ?, "
				+ "telefone = ?, descricao = ?, cep = ?, rua = ?, numero = ?, " + "bairro = ?, cidade = ?, estado = ? "
				+ "WHERE id_fornecedor = ?";
		
		String hash = BCrypt.hashpw(fornecedor.getSenha(), BCrypt.gensalt());


		try (Connection conn = ConexaoDB.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fornecedor.getNomeLoja());
			ps.setString(2, fornecedor.getEmail());
			ps.setString(3, hash); // Lembre-se do HASH
			ps.setString(4, fornecedor.getTelefone());
			ps.setString(5, fornecedor.getDescricao());
			ps.setString(6, fornecedor.getCep());
			ps.setString(7, fornecedor.getRua());
			ps.setString(8, fornecedor.getNumero());
			ps.setString(9, fornecedor.getBairro());
			ps.setString(10, fornecedor.getCidade());
			ps.setString(11, fornecedor.getEstado());

			ps.setInt(12, fornecedor.getIdFornecedor());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0; 

		} catch (SQLException e) {
			e.printStackTrace();
	
			return false;
		}
	}
	public List<Fornecedor> listarTodosFornecedores() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor ORDER BY nome_loja";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setIdFornecedor(rs.getInt("id_fornecedor"));
                f.setNomeLoja(rs.getString("nome_loja"));
                f.setEmail(rs.getString("email"));
                f.setCpfCnpj(rs.getString("cpf_cnpj"));
                f.setTelefone(rs.getString("telefone"));
                fornecedores.add(f);
            }
        } catch (SQLException e) {
        	System.err.println("!!! ERRO AO LISTAR CLIENTES: " + e.getMessage());
            e.printStackTrace();
        }
        return fornecedores;
    }

   
    public boolean excluirFornecedor(int idFornecedor) {
     
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idFornecedor);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Fornecedor buscarFornecedorPorId(int idFornecedor) {
        String sql = "SELECT * FROM fornecedor WHERE id_fornecedor = ?";
        Fornecedor f = null;
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idFornecedor);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    f = new Fornecedor();
                    f.setIdFornecedor(rs.getInt("id_fornecedor"));
                    f.setNomeLoja(rs.getString("nome_loja"));
                    f.setEmail(rs.getString("email"));
                    f.setCpfCnpj(rs.getString("cpf_cnpj")); 
                    f.setTelefone(rs.getString("telefone"));
                    f.setDescricao(rs.getString("descricao"));
                    f.setCep(rs.getString("cep"));
                    f.setRua(rs.getString("rua"));
                    f.setNumero(rs.getString("numero"));
                    f.setBairro(rs.getString("bairro"));
                    f.setCidade(rs.getString("cidade"));
                    f.setEstado(rs.getString("estado"));
                    f.setSenha(rs.getString("senha")); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    public Fornecedor verificarPorEmailECPFCNPJ(String email, String cpfCnpj) {
        String sql = "SELECT id_fornecedor FROM fornecedor WHERE email = ? AND cpf_cnpj = ?";
        Fornecedor fornecedor = null;
 
        try (Connection conn = ConexaoDB.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, email);
            ps.setString(2, cpfCnpj);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fornecedor = new Fornecedor();
                    fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fornecedor; 
    }
 
   
    public boolean atualizarSenha(int idFornecedor, String novaSenha) {
        String hash = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        String sql = "UPDATE fornecedor SET senha = ? WHERE id_fornecedor = ?";
 
        try (Connection conn = ConexaoDB.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, hash);
            ps.setInt(2, idFornecedor);
 
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}