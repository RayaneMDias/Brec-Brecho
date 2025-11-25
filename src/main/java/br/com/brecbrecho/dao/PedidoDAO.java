package br.com.brecbrecho.dao;

import java.math.BigDecimal; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import br.com.brecbrecho.model.Cliente; 
import br.com.brecbrecho.model.ItemPedido;
import br.com.brecbrecho.model.Pedido;
import br.com.brecbrecho.model.Produto;
import br.com.brecbrecho.util.ConexaoDB;

public class PedidoDAO {

  
    public int salvarPedido(Pedido pedido) { 
        
     
        String sqlPedido = "INSERT INTO Pedido (id_cliente, valor_total, forma_pagamento, status_pedido) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO ItemPedido (id_pedido, id_produto, quantidade, preco_unitario_venda) VALUES (?, ?, ?, ?)";
        String sqlEstoque = "UPDATE Produto SET estoque = estoque - ? WHERE id_produto = ?";
      

        Connection conn = null;
        PreparedStatement psPedido = null;
        PreparedStatement psItem = null;
        PreparedStatement psEstoque = null;
        ResultSet generatedKeys = null;
        int idPedidoGerado = 0; 

        try {
            conn = ConexaoDB.getConexao();
            conn.setAutoCommit(false); 

           
            psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, pedido.getIdCliente());
            psPedido.setDouble(2, pedido.getValorTotal());
            psPedido.setString(3, pedido.getFormaPagamento());
            psPedido.setString(4, "PENDENTE_PAGAMENTO"); 
            psPedido.executeUpdate();

            
            generatedKeys = psPedido.getGeneratedKeys();
            if (generatedKeys.next()) {
                idPedidoGerado = generatedKeys.getInt(1); 
            } else {
                throw new SQLException("Falha ao obter ID do pedido, nenhum ID gerado.");
            }

           
            psItem = conn.prepareStatement(sqlItem);
            psEstoque = conn.prepareStatement(sqlEstoque);

            for (ItemPedido item : pedido.getItens()) {
                psItem.setInt(1, idPedidoGerado);
                psItem.setInt(2, item.getIdProduto());
                psItem.setInt(3, item.getQuantidade());
                psItem.setDouble(4, item.getPrecoUnitarioVenda());
                psItem.addBatch(); 

                psEstoque.setInt(1, item.getQuantidade());
                psEstoque.setInt(2, item.getIdProduto());
                psEstoque.addBatch();
            }

            psItem.executeBatch(); 
            psEstoque.executeBatch(); 

            conn.commit(); 
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            idPedidoGerado = 0; 
        } finally {
          
            try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) {}
            try { if (psPedido != null) psPedido.close(); } catch (SQLException e) {}
            try { if (psItem != null) psItem.close(); } catch (SQLException e) {}
            try { if (psEstoque != null) psEstoque.close(); } catch (SQLException e) {}
            try { 
                if (conn != null) { 
                    conn.setAutoCommit(true); 
                    conn.close(); 
                } 
            } catch (SQLException e) {}
        }
        
        return idPedidoGerado; 
    }
 
    public List<Pedido> listarPedidosPorCliente(int idCliente) {
        
        String sql = "SELECT p.*, i.*, pr.nome as nome_produto, pr.foto_base64 " +
                       "FROM Pedido p " +
                       "JOIN ItemPedido i ON p.id_pedido = i.id_pedido " +
                       "JOIN Produto pr ON i.id_produto = pr.id_produto " +
                       "WHERE p.id_cliente = ? " +
                       "ORDER BY p.data_pedido DESC, i.id_item_pedido ASC";

        Map<Integer, Pedido> pedidoMap = new LinkedHashMap<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPedido = rs.getInt("p.id_pedido");
                    Pedido pedido = pedidoMap.get(idPedido);

                    if (pedido == null) {
                        pedido = new Pedido();
                        pedido.setIdPedido(idPedido);
                        pedido.setIdCliente(rs.getInt("p.id_cliente"));
                        pedido.setDataPedido(rs.getTimestamp("p.data_pedido"));
                        pedido.setValorTotal(rs.getDouble("p.valor_total"));
                        pedido.setFormaPagamento(rs.getString("p.forma_pagamento"));
                        pedido.setStatusPedido(rs.getString("p.status_pedido"));
                        pedido.setDataEntrega(rs.getTimestamp("p.data_entrega")); 
                        pedido.setItens(new ArrayList<>());
                        
                        pedidoMap.put(idPedido, pedido);
                    }

                    ItemPedido item = new ItemPedido();
                    item.setIdItemPedido(rs.getInt("i.id_item_pedido"));
                    item.setIdPedido(idPedido);
                    item.setIdProduto(rs.getInt("i.id_produto"));
                    item.setQuantidade(rs.getInt("i.quantidade"));
                    item.setPrecoUnitarioVenda(rs.getDouble("i.preco_unitario_venda"));
                    
                    Produto produto = new Produto();
                    produto.setIdProduto(rs.getInt("i.id_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    produto.setFotoBase64(rs.getString("pr.foto_base64"));
                    item.setProduto(produto); 

                    pedido.getItens().add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(pedidoMap.values());
    }

    public List<Pedido> listarTodosPedidos() {
        String sql = "SELECT p.*, i.*, pr.nome as nome_produto, c.nome as nome_cliente " +
                       "FROM Pedido p " +
                       "JOIN ItemPedido i ON p.id_pedido = i.id_pedido " +
                       "JOIN Produto pr ON i.id_produto = pr.id_produto " +
                       "JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                       "ORDER BY p.data_pedido DESC, i.id_item_pedido ASC";

        Map<Integer, Pedido> pedidoMap = new LinkedHashMap<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPedido = rs.getInt("p.id_pedido");
                    Pedido pedido = pedidoMap.get(idPedido);

                    if (pedido == null) {
                        pedido = new Pedido();
                        pedido.setIdPedido(idPedido);
                        pedido.setIdCliente(rs.getInt("p.id_cliente"));
                        pedido.setDataPedido(rs.getTimestamp("p.data_pedido"));
                        pedido.setValorTotal(rs.getDouble("p.valor_total"));
                        pedido.setFormaPagamento(rs.getString("p.forma_pagamento"));
                        pedido.setStatusPedido(rs.getString("p.status_pedido"));
                        pedido.setDataEntrega(rs.getTimestamp("p.data_entrega")); 
                        
                        Cliente c = new Cliente();
                        c.setNome(rs.getString("nome_cliente"));
                        pedido.setCliente(c); 
                        
                        pedido.setItens(new ArrayList<>());
                        pedidoMap.put(idPedido, pedido);
                    }

                    ItemPedido item = new ItemPedido();
                    item.setIdItemPedido(rs.getInt("i.id_item_pedido"));
                    item.setIdPedido(idPedido);
                    item.setIdProduto(rs.getInt("i.id_produto"));
                    item.setQuantidade(rs.getInt("i.quantidade"));
                    item.setPrecoUnitarioVenda(rs.getDouble("i.preco_unitario_venda"));
                    
                    Produto produto = new Produto();
                    produto.setIdProduto(rs.getInt("i.id_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    item.setProduto(produto);

                    pedido.getItens().add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(pedidoMap.values());
    }

   
    public boolean atualizarStatusPedido(int idPedido, String novoStatus) {
        String sql = "UPDATE Pedido SET status_pedido = ? WHERE id_pedido = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, novoStatus);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public boolean marcarComoEntregue(int idPedido) {
        String sql = "UPDATE Pedido SET status_pedido = 'ENTREGUE', data_entrega = NOW() " +
                     "WHERE id_pedido = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pedido> listarPedidosProntosParaPagamento() {
        
      
        String sql = "SELECT p.*, i.*, pr.nome as nome_produto, c.nome as nome_cliente " +
                       "FROM Pedido p " +
                       "JOIN ItemPedido i ON p.id_pedido = i.id_pedido " +
                       "JOIN Produto pr ON i.id_produto = pr.id_produto " +
                       "JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                       "WHERE p.status_pedido = 'ENTREGUE' " +
                       "AND p.data_entrega <= DATE_SUB(NOW(), INTERVAL 15 DAY) " + 
                       "ORDER BY p.data_entrega ASC, i.id_item_pedido ASC";

       
        Map<Integer, Pedido> pedidoMap = new LinkedHashMap<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPedido = rs.getInt("p.id_pedido");
                    Pedido pedido = pedidoMap.get(idPedido);

                    if (pedido == null) {
                        pedido = new Pedido();
                        
                        pedido.setIdPedido(idPedido);
                        pedido.setIdCliente(rs.getInt("p.id_cliente"));
                        pedido.setDataPedido(rs.getTimestamp("p.data_pedido"));
                        pedido.setValorTotal(rs.getDouble("p.valor_total"));
                        pedido.setFormaPagamento(rs.getString("p.forma_pagamento"));
                        pedido.setStatusPedido(rs.getString("p.status_pedido"));
                        pedido.setDataEntrega(rs.getTimestamp("p.data_entrega"));
                        
                        Cliente c = new Cliente();
                        c.setNome(rs.getString("nome_cliente"));
                        pedido.setCliente(c);
                        
                        pedido.setItens(new ArrayList<>());
                        pedidoMap.put(idPedido, pedido);
                    }

                    ItemPedido item = new ItemPedido();
                    item.setIdItemPedido(rs.getInt("i.id_item_pedido"));
                    item.setIdPedido(idPedido);
                    item.setIdProduto(rs.getInt("i.id_produto"));
                    item.setQuantidade(rs.getInt("i.quantidade"));
                    item.setPrecoUnitarioVenda(rs.getDouble("i.preco_unitario_venda"));
                    
                    Produto produto = new Produto();
                    produto.setIdProduto(rs.getInt("i.id_produto"));
                    produto.setNome(rs.getString("nome_produto"));
                    item.setProduto(produto); 

                    pedido.getItens().add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(pedidoMap.values());
    }
    public Pedido buscarPedidoCompleto(int idPedido) {
 
        List<Pedido> pedidos = this.listarTodosPedidos(); 
        for(Pedido p : pedidos) {
            if(p.getIdPedido() == idPedido) {
                return p;
            }
        }
        return null; 
    }

     public List<Pedido> listarPedidosPorFornecedor(int idFornecedor) {
         
       
         String sql = "SELECT DISTINCT p.id_pedido, p.*, c.nome as nome_cliente " +
                        "FROM Pedido p " +
                        "JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                        "JOIN ItemPedido i ON p.id_pedido = i.id_pedido " +
                        "JOIN Produto pr ON i.id_produto = pr.id_produto " +
                        "WHERE pr.id_fornecedor = ? " +
                        "AND p.status_pedido IN ('PAGO', 'ENVIADO', 'ENTREGUE', 'CONCLUIDO') " + 
                        "ORDER BY p.data_pedido DESC";

         Map<Integer, Pedido> pedidoMap = new LinkedHashMap<>();

         try (Connection conn = ConexaoDB.getConexao();
              PreparedStatement ps = conn.prepareStatement(sql)) {
             
             ps.setInt(1, idFornecedor);

             try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     int idPedido = rs.getInt("p.id_pedido");
                     
                     if (!pedidoMap.containsKey(idPedido)) {
                         Pedido pedido = new Pedido();
                         pedido.setIdPedido(idPedido);
                         pedido.setDataPedido(rs.getTimestamp("p.data_pedido"));
                         pedido.setValorTotal(rs.getDouble("p.valor_total"));
                         pedido.setStatusPedido(rs.getString("p.status_pedido"));
                         
                        
                         Cliente c = new Cliente();
                         c.setNome(rs.getString("nome_cliente"));
                         pedido.setCliente(c); 
                         
                         pedidoMap.put(idPedido, pedido);
                     }
                 }
             }
             
            
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return new ArrayList<>(pedidoMap.values());
     }
}