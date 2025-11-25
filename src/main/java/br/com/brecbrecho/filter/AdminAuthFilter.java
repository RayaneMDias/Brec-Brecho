package br.com.brecbrecho.filter;

import java.io.IOException;

// Imports Jakarta EE (Tomcat 10+)
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter; // Importante
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter("/admin/*") 
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
      
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        
        HttpSession session = httpRequest.getSession(false);

        boolean isAdmin = false;

        
        if (session != null && "admin".equals(session.getAttribute("tipoUsuario"))) {
            isAdmin = true;
        }

       
        if (isAdmin) {
           
            chain.doFilter(request, response);
        } else {
         
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?msg=acesso_negado");
        }
    }

    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
     
    }

    @Override
    public void destroy() {
        
    }
}