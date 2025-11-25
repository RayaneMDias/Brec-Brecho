package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.List;

// Nossos pacotes
import br.com.brecbrecho.dao.PedidoDAO;
import br.com.brecbrecho.model.Fornecedor;
import br.com.brecbrecho.model.Pedido;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/minhas-vendas")
public class MinhasVendasServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO;

    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
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
           
            Fornecedor fornecedorLogado = (Fornecedor) session.getAttribute("usuarioLogado");
            
            
            List<Pedido> listaVendas = pedidoDAO.listarPedidosPorFornecedor(fornecedorLogado.getIdFornecedor());

            request.setAttribute("listaVendas", listaVendas);

          
            RequestDispatcher dispatcher = request.getRequestDispatcher("/minhas-vendas.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Não foi possível carregar suas vendas.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}