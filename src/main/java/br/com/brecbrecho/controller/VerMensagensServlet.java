package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.List;

// Nossos pacotes
import br.com.brecbrecho.dao.MensagemSACDAO;
import br.com.brecbrecho.model.MensagemSAC;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/verMensagens")
public class VerMensagensServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private MensagemSACDAO mensagemDAO;

    @Override
    public void init() throws ServletException {
        mensagemDAO = new MensagemSACDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String acao = request.getParameter("acao");
            if ("marcarLida".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                mensagemDAO.marcarComoLida(id);
                response.sendRedirect("verMensagens"); 
                return;
            }

            List<MensagemSAC> listaMensagens = mensagemDAO.listarTodasMensagens();

            request.setAttribute("listaMensagens", listaMensagens);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/verMensagens.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/index.jsp?msg=erro_admin");
        }
    }
}