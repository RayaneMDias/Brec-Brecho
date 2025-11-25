package br.com.brecbrecho.controller;

import java.io.IOException;

import br.com.brecbrecho.dao.ClienteDAO;
import br.com.brecbrecho.dao.PedidoDAO;
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.model.Fornecedor;
import br.com.brecbrecho.model.Pedido;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/guia-envio")
public class GuiaEnvioServlet extends HttpServlet {
    
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

        
        if (session == null || !"fornecedor".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect("login.jsp?msg=acesso_negado");
            return;
        }

        try {
            int idPedido = Integer.parseInt(request.getParameter("id"));
            
           
            Pedido pedido = pedidoDAO.buscarPedidoCompleto(idPedido);
            
            if (pedido == null) {
                response.sendRedirect("minhas-vendas?msg=erro_pedido");
                return;
            }

            
            Cliente clienteDestino = clienteDAO.buscarClientePorId(pedido.getIdCliente());
            
          
            Fornecedor remetente = (Fornecedor) session.getAttribute("usuarioLogado");

           
            request.setAttribute("dadosPedido", pedido);
            request.setAttribute("dadosDestinatario", clienteDestino);
            request.setAttribute("dadosRemetente", remetente);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/guia-envio.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("minhas-vendas?msg=erro_geral");
        }
    }
}