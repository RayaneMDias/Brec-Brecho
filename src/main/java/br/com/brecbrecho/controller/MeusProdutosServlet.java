package br.com.brecbrecho.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Para verificar o login
import java.io.IOException;
import java.util.List;

// Nossos pacotes
import br.com.brecbrecho.dao.ProdutoDAO;
import br.com.brecbrecho.model.Fornecedor; // Precisamos do Fornecedor
import br.com.brecbrecho.model.Produto;


@WebServlet("/meus-produtos")
public class MeusProdutosServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    public void init() {
        produtoDAO = new ProdutoDAO();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); 

       
        if (session == null || !"fornecedor".equals(session.getAttribute("tipoUsuario"))) {
            
            response.sendRedirect("login.jsp?msg=acesso_negado");
            return; 
        }
        

        try {
            
            Fornecedor fornecedorLogado = (Fornecedor) session.getAttribute("usuarioLogado");
            int idFornecedor = fornecedorLogado.getIdFornecedor();

            
            List<Produto> listaMeusProdutos = produtoDAO.listarProdutosPorFornecedor(idFornecedor);
            
         
            request.setAttribute("listaMeusProdutos", listaMeusProdutos);
            
      
            RequestDispatcher dispatcher = request.getRequestDispatcher("meus-produtos.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Não foi possível carregar seus produtos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}