package br.com.brecbrecho.controller;

import java.io.IOException;

// Nossos pacotes
import br.com.brecbrecho.dao.SaldoDAO;
import br.com.brecbrecho.model.Fornecedor;
import br.com.brecbrecho.model.SaldoFornecedor;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/minha-carteira")
public class MinhaCarteiraServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private SaldoDAO saldoDAO;

    @Override
    public void init() throws ServletException {
        saldoDAO = new SaldoDAO();
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
            
       
            SaldoFornecedor saldo = saldoDAO.buscarOuCriarPorFornecedor(fornecedorLogado.getIdFornecedor());

        
            request.setAttribute("saldoFornecedor", saldo);

          
            RequestDispatcher dispatcher = request.getRequestDispatcher("/minha-carteira.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Não foi possível carregar sua carteira.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}