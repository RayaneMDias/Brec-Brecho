package br.com.brecbrecho.controller;

import java.io.IOException;

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


@WebServlet("/produto-detalhe")
public class ProdutoDetalheServlet extends HttpServlet {
    
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
            
            int idProduto = Integer.parseInt(request.getParameter("id"));

       
            Produto produto = produtoDAO.buscarProdutoPorId(idProduto);

            if (produto != null) {
        
                request.setAttribute("produto", produto);
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/produto-detalhe.jsp");
                dispatcher.forward(request, response);
                
            } else {
                response.sendRedirect("catalogo?msg=erro_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("catalogo?msg=erro_id");
        }
    }
}