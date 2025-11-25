package br.com.brecbrecho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

// Importa sua classe de conexão
import br.com.brecbrecho.util.ConexaoDB;
// Importa o Model
import br.com.brecbrecho.model.Cliente;

public class ClienteDAO {

	public boolean cadastrarCliente(Cliente cliente) {

		String sql = "INSERT INTO cliente (nome, email, senha, cpf, cep, rua, numero, bairro, cidade, estado) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		String hash = BCrypt.hashpw(cliente.getSenha(), BCrypt.gensalt());

		
		try (Connection conn = ConexaoDB.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

			
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());

		
			ps.setString(3,hash); 
			ps.setString(4, cliente.getCpf());
			ps.setString(5, cliente.getCep());
			ps.setString(6, cliente.getRua());
			ps.setString(7, cliente.getNumero());
			ps.setString(8, cliente.getBairro());
			ps.setString(9, cliente.getCidade());
			ps.setString(10, cliente.getEstado());
			
	
			ps.executeUpdate();

			return true; 

		} catch (SQLException e) {
			e.printStackTrace();
			
			return false; 
		}
	}

	
	public Cliente validarLogin(String email, String senha) {

	
	    String sql = "SELECT * FROM cliente WHERE email = ?";
	    Cliente cliente = null;

	    try (Connection conn = ConexaoDB.getConexao(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, email);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                String hashArmazenado = rs.getString("senha");

	                if (BCrypt.checkpw(senha, hashArmazenado)) {
	                    
	                    cliente = new Cliente();
	                    cliente.setIdCliente(rs.getInt("id_cliente"));
	                    cliente.setNome(rs.getString("nome"));
	                    cliente.setEmail(rs.getString("email"));
	                    cliente.setCpf(rs.getString("cpf"));
	                    cliente.setCep(rs.getString("cep"));
	                    cliente.setRua(rs.getString("rua"));
	                    cliente.setNumero(rs.getString("numero"));
	                    cliente.setBairro(rs.getString("bairro"));
	                    cliente.setCidade(rs.getString("cidade"));
	                    cliente.setEstado(rs.getString("estado"));
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cliente; 
	}

	public boolean atualizarCliente(Cliente cliente) {

		
		String sql = "UPDATE cliente SET nome = ?, email = ?, senha = ?, cep = ?, "
				+ "rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ? " + "WHERE id_cliente = ?";

		String hash = BCrypt.hashpw(cliente.getSenha(), BCrypt.gensalt());
		try (Connection conn = ConexaoDB.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, hash); 
			ps.setString(4, cliente.getCep());
			ps.setString(5, cliente.getRua());
			ps.setString(6, cliente.getNumero());
			ps.setString(7, cliente.getBairro());
			ps.setString(8, cliente.getCidade());
			ps.setString(9, cliente.getEstado());

	
			ps.setInt(10, cliente.getIdCliente());

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0; 

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Cliente> listarTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
        	System.err.println("!!! ERRO AO LISTAR CLIENTES: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    public boolean excluirCliente(int idCliente) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
           
            e.printStackTrace();
            return false;
        }
    }

    public Cliente buscarClientePorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Cliente cliente = null;
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCpf(rs.getString("cpf")); 
                    cliente.setCep(rs.getString("cep"));
                    cliente.setRua(rs.getString("rua"));
                    cliente.setNumero(rs.getString("numero"));
                    cliente.setBairro(rs.getString("bairro"));
                    cliente.setCidade(rs.getString("cidade"));
                    cliente.setEstado(rs.getString("estado"));
                    cliente.setSenha(rs.getString("senha")); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
   public Cliente verificarPorEmailECPF(String email, String cpf) {
		String sql = "SELECT * FROM cliente WHERE email = ? AND cpf = ?";
		Cliente cliente = null;

		try (Connection conn = ConexaoDB.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.setString(2, cpf);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					cliente = new Cliente();
					cliente.setIdCliente(rs.getInt("id_cliente"));

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}

	public boolean atualizarSenha(int idCliente, String novaSenha) {
		String hash = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
		String sql = "UPDATE cliente SET senha = ? WHERE id_cliente = ?";

		try (Connection conn = ConexaoDB.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, hash);
			ps.setInt(2, idCliente);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
