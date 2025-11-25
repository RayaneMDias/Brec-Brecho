package br.com.brecbrecho.controller;


import jakarta.servlet.RequestDispatcher; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List; 


import br.com.brecbrecho.dao.ProdutoDAO;
import br.com.brecbrecho.model.Produto;


@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    public void init() {
        produtoDAO = new ProdutoDAO(); 
    }

 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
          
            String termoBusca = request.getParameter("busca");
            
            List<Produto> listaDeProdutos;

            
            if (termoBusca != null && !termoBusca.trim().isEmpty()) {
                listaDeProdutos = produtoDAO.buscarProdutosPorNome(termoBusca);
                request.setAttribute("termoBuscado", termoBusca); 
            } else {
                
                listaDeProdutos = produtoDAO.listarTodosProdutos();
            }
            
           
            request.setAttribute("listaProdutos", listaDeProdutos);
            
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("catalogo.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Não foi possível carregar o catálogo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}