package br.com.brecbrecho.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Importa o Model que acabamos de criar
import br.com.brecbrecho.model.SaldoFornecedor; 
import br.com.brecbrecho.util.ConexaoDB;

public class SaldoDAO {

  
    public SaldoFornecedor buscarOuCriarPorFornecedor(int idFornecedor) {
        String sqlSelect = "SELECT * FROM SaldoFornecedor WHERE id_fornecedor = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            
            ps.setInt(1, idFornecedor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   
                    SaldoFornecedor saldo = new SaldoFornecedor();
                    saldo.setIdSaldo(rs.getInt("id_saldo"));
                    saldo.setIdFornecedor(rs.getInt("id_fornecedor"));
                    saldo.setSaldoDisponivel(rs.getBigDecimal("saldo_disponivel"));
                    saldo.setSaldoPendente(rs.getBigDecimal("saldo_pendente"));
                    return saldo;
                }
            }

          
            String sqlInsert = "INSERT INTO SaldoFornecedor (id_fornecedor, saldo_disponivel, saldo_pendente) VALUES (?, 0.00, 0.00)";
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, idFornecedor);
                psInsert.executeUpdate();
            }
            
          
            SaldoFornecedor saldoNovo = new SaldoFornecedor();
            saldoNovo.setIdFornecedor(idFornecedor);
            return saldoNovo;

        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }
    
   
    public boolean creditarSaldoDisponivel(int idFornecedor, BigDecimal valor) {
       
        buscarOuCriarPorFornecedor(idFornecedor);
        
        
        String sql = "UPDATE SaldoFornecedor SET saldo_disponivel = saldo_disponivel + ? WHERE id_fornecedor = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, valor);
            ps.setInt(2, idFornecedor);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
  
    public boolean creditarSaldoPendente(int idFornecedor, BigDecimal valor) {
       
        buscarOuCriarPorFornecedor(idFornecedor);
        
   
        String sql = "UPDATE SaldoFornecedor SET saldo_pendente = saldo_pendente + ? WHERE id_fornecedor = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, valor);
            ps.setInt(2, idFornecedor);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}