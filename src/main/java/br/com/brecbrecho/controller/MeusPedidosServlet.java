package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.List;

// Nossos pacotes
import br.com.brecbrecho.dao.ClienteDAO; // <-- 1. IMPORTAR CLIENTEDAO
import br.com.brecbrecho.dao.PedidoDAO;
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.model.Pedido;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/meus-pedidos")
public class MeusPedidosServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO; 

    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
        clienteDAO = new ClienteDAO(); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        
        if (session == null || !"cliente".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect("login.jsp?msg=acesso_negado");
            return;
        }

        try {
            Cliente clienteLogado = (Cliente) session.getAttribute("usuarioLogado");
            
           
            String idParam = request.getParameter("id");

            if (idParam != null && !idParam.isEmpty()) {
              
                
                int idPedido = Integer.parseInt(idParam);
                
                
                Pedido pedido = pedidoDAO.buscarPedidoCompleto(idPedido);
                
              
                Cliente cliente = clienteDAO.buscarClientePorId(clienteLogado.getIdCliente());
                
               
                if (pedido != null && cliente != null && pedido.getIdCliente() == clienteLogado.getIdCliente()) {
                    
                   
                    request.setAttribute("pedidoDetalhado", pedido);
                    request.setAttribute("clientePedido", cliente);
                    
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/detalhes-pedido.jsp");
                    dispatcher.forward(request, response);
                } else {
                   
                    response.sendRedirect("meus-pedidos?msg=erro_pedido");
                }

            } else {
                
                
                List<Pedido> listaPedidos = pedidoDAO.listarPedidosPorCliente(clienteLogado.getIdCliente());
                request.setAttribute("listaPedidos", listaPedidos);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/meus-pedidos.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Não foi possível carregar seus pedidos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}