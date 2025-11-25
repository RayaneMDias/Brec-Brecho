package br.com.brecbrecho.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// ... (imports do jakarta.servlet...)
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Nossos pacotes (AGORA INCLUINDO O ADMIN)
import br.com.brecbrecho.dao.ClienteDAO;
import br.com.brecbrecho.dao.FornecedorDAO;
import br.com.brecbrecho.dao.AdministradorDAO; // <-- 1. IMPORTAR O NOVO DAO
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.model.Fornecedor;
import br.com.brecbrecho.model.Administrador; // <-- 1. IMPORTAR O NOVO MODEL

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private ClienteDAO clienteDAO;
    private FornecedorDAO fornecedorDAO;
    private AdministradorDAO administradorDAO; 

    public void init() {
        clienteDAO = new ClienteDAO();
        fornecedorDAO = new FornecedorDAO();
        administradorDAO = new AdministradorDAO(); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String email = request.getParameter("loginEmail");
            String senha = request.getParameter("loginSenha");

            
            Cliente cliente = clienteDAO.validarLogin(email, senha);

            if (cliente != null) {
              
                HttpSession session = request.getSession(); 
                session.setAttribute("usuarioLogado", cliente);
                session.setAttribute("tipoUsuario", "cliente");
                response.sendRedirect("home");
                
            } else {
                
                Fornecedor fornecedor = fornecedorDAO.validarLogin(email, senha);

                if (fornecedor != null) {
                  
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogado", fornecedor);
                    session.setAttribute("tipoUsuario", "fornecedor");
                    response.sendRedirect("home");

                } else {
                    
                   
                    Administrador admin = administradorDAO.validarLogin(email, senha);
                    
                    if (admin != null) {
                       
                        HttpSession session = request.getSession();
                        session.setAttribute("usuarioLogado", admin);
                        session.setAttribute("tipoUsuario", "admin"); 
                        
                      
                        response.sendRedirect("home"); 
                        
                    } else {
                       
                        response.sendRedirect("login.jsp?msg=erro_login");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?msg=erro_excecao");
        }
    }
}