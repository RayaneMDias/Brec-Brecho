package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.List;

// Nossos pacotes
import br.com.brecbrecho.dao.PedidoDAO;
import br.com.brecbrecho.model.Pedido;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/gerenciarPedidos")
public class AdminGerenciarPedidosServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String msg = null; 

        try {
           
            if (acao != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                
                if ("marcarPago".equals(acao)) {
                    pedidoDAO.atualizarStatusPedido(id, "PAGO");
                    msg = "Pedido #" + id + " marcado como PAGO.";
                
                } else if ("marcarEnviado".equals(acao)) {
                    pedidoDAO.atualizarStatusPedido(id, "ENVIADO");
                    msg = "Pedido #" + id + " marcado como ENVIADO.";
                
                } else if ("marcarEntregue".equals(acao)) {
                   
                    pedidoDAO.marcarComoEntregue(id); 
                    msg = "Pedido #" + id + " marcado como ENTREGUE. (Iniciando contagem de 15 dias)";
                
                } else if ("cancelar".equals(acao)) {
                    pedidoDAO.atualizarStatusPedido(id, "CANCELADO");
                    msg = "Pedido #" + id + " foi CANCELADO.";
                }
            }

           
            List<Pedido> listaPedidos = pedidoDAO.listarTodosPedidos();

            
            request.setAttribute("listaPedidos", listaPedidos);
            if (msg != null) {
                request.setAttribute("mensagem", msg); 
            }

           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/gerenciarPedidos.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro crítico: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/gerenciarPedidos.jsp");
            dispatcher.forward(request, response);
        }
    }
}