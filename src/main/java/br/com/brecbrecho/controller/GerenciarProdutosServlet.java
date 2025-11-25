package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.List;

import br.com.brecbrecho.dao.ProdutoDAO;
import br.com.brecbrecho.model.Produto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/gerenciarProdutos")
public class GerenciarProdutosServlet extends HttpServlet {
    
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
           
            List<Produto> listaProdutos = produtoDAO.listarTodosProdutos();

            
            request.setAttribute("listaProdutos", listaProdutos);

            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/gerenciarProdutos.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao carregar produtos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/verMensagens.jsp"); // Volta p/ pág principal do admin
            dispatcher.forward(request, response);
        }
    }
}