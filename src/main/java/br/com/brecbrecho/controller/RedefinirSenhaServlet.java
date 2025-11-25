package br.com.brecbrecho.controller;
 
import java.io.IOException;
 
// DAOs e Models necessários

import br.com.brecbrecho.dao.ClienteDAO;

import br.com.brecbrecho.dao.FornecedorDAO; // <-- ADICIONADO

import br.com.brecbrecho.model.Cliente;

import br.com.brecbrecho.model.Fornecedor; // <-- ADICIONADO
 
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
 
@WebServlet("/redefinirSenha") 

public class RedefinirSenhaServlet extends HttpServlet {

    private ClienteDAO clienteDAO;

    private FornecedorDAO fornecedorDAO; 
 
    public void init() {

        clienteDAO = new ClienteDAO();

        fornecedorDAO = new FornecedorDAO();

    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {


        String email = request.getParameter("email");

        String cpf = request.getParameter("cpf"); 

        String novaSenha = request.getParameter("novaSenha");

        try {

      

            Cliente cliente = clienteDAO.verificarPorEmailECPF(email, cpf);
 
            if (cliente != null) {

             

                boolean atualizou = clienteDAO.atualizarSenha(cliente.getIdCliente(), novaSenha);

                if (atualizou) {

                   

                    response.sendRedirect("login.jsp?msg=sucesso_senha_redefinida");

                } else {

                    

                    response.sendRedirect("recuperar-senha.jsp?erro=excecao");

                }

            } else {

               

                Fornecedor fornecedor = fornecedorDAO.verificarPorEmailECPFCNPJ(email, cpf);

                if (fornecedor != null) {

                  

                    boolean atualizou = fornecedorDAO.atualizarSenha(fornecedor.getIdFornecedor(), novaSenha);

                    if (atualizou) {

               

                        response.sendRedirect("login.jsp?msg=sucesso_senha_redefinida");

                    } else {

                     

                        response.sendRedirect("recuperar-senha.jsp?erro=excecao");

                    }

                } else {

                   

                    response.sendRedirect("recuperar-senha.jsp?erro=dados_invalidos");

                }

            }
 
        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect("recuperar-senha.jsp?erro=excecao");

        }

    }

}
 