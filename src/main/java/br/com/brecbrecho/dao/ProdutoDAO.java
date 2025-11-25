package br.com.brecbrecho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Importar ArrayList
import java.util.List;     // Importar List

import br.com.brecbrecho.util.ConexaoDB;
import br.com.brecbrecho.model.Produto;

public class ProdutoDAO {

   
    public boolean cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO Produto (id_fornecedor, nome, descricao, tamanho, foto_base64, estoque, preco, prazo_locacao_dias) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, produto.getIdFornecedor());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());
            ps.setString(4, produto.getTamanho());
            ps.setString(5, produto.getFotoBase64());
            ps.setInt(6, produto.getEstoque());
            ps.setDouble(7, produto.getPreco());
            ps.setInt(8, produto.getPrazoLocacaoDias());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean atualizarProduto(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, tamanho = ?, " +
                     "foto_base64 = ?, estoque = ?, preco = ?, prazo_locacao_dias = ? " +
                     "WHERE id_produto = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getTamanho());
            ps.setString(4, produto.getFotoBase64());
            ps.setInt(5, produto.getEstoque());
            ps.setDouble(6, produto.getPreco());
            ps.setInt(7, produto.getPrazoLocacaoDias());
            ps.setInt(8, produto.getIdProduto()); 
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 
    public boolean excluirProduto(int idProduto) {
        String sql = "DELETE FROM Produto WHERE id_produto = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idProduto);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
           
            e.printStackTrace();
            return false;
        }
    }

   
    public List<Produto> listarTodosProdutos() {
        String sql = "SELECT * FROM Produto ORDER BY data_cadastro DESC";
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                produtos.add(populateProduto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

  
    public List<Produto> listarProdutosPorFornecedor(int idFornecedor) {
        String sql = "SELECT * FROM Produto WHERE id_fornecedor = ? ORDER BY data_cadastro DESC";
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idFornecedor);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    produtos.add(populateProduto(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

   
    public Produto buscarProdutoPorId(int idProduto) {
        String sql = "SELECT * FROM Produto WHERE id_produto = ?";
        Produto produto = null;
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idProduto);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = populateProduto(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

 
    private Produto populateProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setIdProduto(rs.getInt("id_produto"));
        produto.setIdFornecedor(rs.getInt("id_fornecedor"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setTamanho(rs.getString("tamanho"));
        produto.setFotoBase64(rs.getString("foto_base64"));
        produto.setEstoque(rs.getInt("estoque"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setPrazoLocacaoDias(rs.getInt("prazo_locacao_dias"));
        produto.setDataCadastro(rs.getTimestamp("data_cadastro"));
        return produto;
    }

    public List<Produto> buscarProdutosPorNome(String termoBusca) {
        
        String sql = "SELECT * FROM Produto WHERE nome LIKE ? ORDER BY nome ASC";
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
           
            ps.setString(1, "%" + termoBusca + "%"); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                   
                    produtos.add(populateProduto(rs)); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public List<Produto> listarProdutosPromovidos() {
        List<Produto> produtos = new ArrayList<>();
      
        String sql = "SELECT * FROM Produto WHERE is_promovido = TRUE AND estoque > 0 LIMIT 5";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                
                produtos.add(populateProduto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    
    public List<Produto> listarProdutosRecentes() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produto " +
                     "WHERE is_promovido = FALSE AND estoque > 0 " +
                     "ORDER BY data_cadastro DESC " +
                     "LIMIT 6";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                produtos.add(populateProduto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}