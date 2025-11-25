package br.com.brecbrecho.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

// Nossos DAOs e Models
import br.com.brecbrecho.dao.PedidoDAO;
import br.com.brecbrecho.dao.ProdutoDAO;
import br.com.brecbrecho.dao.SaldoDAO;
import br.com.brecbrecho.model.Pedido;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/liberarPagamentos")
public class LiberarPagamentosServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO;
    private ProdutoDAO produtoDAO;
    private SaldoDAO saldoDAO;
    
    
    private static final double TAXA_PLATAFORMA = 0.30;
 
    private static final double FRETE_FIXO = 15.00;

    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
        produtoDAO = new ProdutoDAO();
        saldoDAO = new SaldoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String msg = null;

        try {
            
            if ("pagar".equals(acao)) {
                int idPedido = Integer.parseInt(request.getParameter("id"));
                
                
                boolean sucesso = processarPagamento(idPedido);
                
                if (sucesso) {
                    msg = "Pagamento do Pedido #" + idPedido + " liberado com sucesso!";
                } else {
                    msg = "ERRO: Não foi possível liberar o pagamento do Pedido #" + idPedido;
                }
            }

            
            List<Pedido> listaPagamentos = pedidoDAO.listarPedidosProntosParaPagamento();

       
            request.setAttribute("listaPagamentos", listaPagamentos);
            if (msg != null) {
                request.setAttribute("mensagem", msg);
            }

            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/liberarPagamentos.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro crítico: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/gerenciarPedidos.jsp");
            dispatcher.forward(request, response);
        }
    }

    
    private boolean processarPagamento(int idPedido) {
        try {
          
            Pedido pedido = pedidoDAO.buscarPedidoCompleto(idPedido);
            if (pedido == null || !"ENTREGUE".equals(pedido.getStatusPedido())) {
                return false; 
            }

          
            int idProdutoLider = pedido.getItens().get(0).getIdProduto();
            int idFornecedor = produtoDAO.buscarProdutoPorId(idProdutoLider).getIdFornecedor();

          
            double valorDosProdutos = pedido.getValorTotal() - FRETE_FIXO;
            double valorLiquido = valorDosProdutos * (1.0 - TAXA_PLATAFORMA); 
            
           
            double valorTotalAPagar = valorLiquido + FRETE_FIXO;
            
         
            BigDecimal valorCreditado = BigDecimal.valueOf(valorTotalAPagar);

          
            saldoDAO.creditarSaldoDisponivel(idFornecedor, valorCreditado);

           
            pedidoDAO.atualizarStatusPedido(idPedido, "CONCLUIDO");
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}