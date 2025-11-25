package br.com.brecbrecho.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Importar HttpSession
import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession(false); 
            
            if (session != null) {
                session.invalidate();
            }
            
          
            response.sendRedirect("home?msg=logout_sucesso");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}