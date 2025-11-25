package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.List;

// Nossos pacotes
import br.com.brecbrecho.dao.ProdutoDAO;
import br.com.brecbrecho.model.Produto;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/home") 
public class HomeServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    @Override
    public void init() throws ServletException {
        produtoDAO = new ProdutoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
         
            List<Produto> listaPromovidos = produtoDAO.listarProdutosPromovidos();
            List<Produto> listaRecentes = produtoDAO.listarProdutosRecentes();

          
            request.setAttribute("listaPromovidos", listaPromovidos);
            request.setAttribute("listaRecentes", listaRecentes);

            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}