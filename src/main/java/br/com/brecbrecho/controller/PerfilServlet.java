package br.com.brecbrecho.controller;

import java.io.IOException;

// Imports Jakarta EE
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Nossos pacotes
import br.com.brecbrecho.dao.ClienteDAO;
import br.com.brecbrecho.dao.FornecedorDAO;
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.model.Fornecedor;


@WebServlet("/salvarPerfil")
public class PerfilServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private ClienteDAO clienteDAO;
    private FornecedorDAO fornecedorDAO;

    public void init() {
        clienteDAO = new ClienteDAO();
        fornecedorDAO = new FornecedorDAO();
    }

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); 
        
       
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp?msg=acesso_negado");
            return;
        }

       
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
     
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cep = request.getParameter("cep");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String senha = request.getParameter("senha"); 

        boolean sucesso = false;

        try {
            if ("cliente".equals(tipoUsuario)) {
              
                
                
                Cliente clienteAtual = (Cliente) session.getAttribute("usuarioLogado");
                
                
                clienteAtual.setNome(nome);
                clienteAtual.setEmail(email);
                clienteAtual.setCep(cep);
                clienteAtual.setRua(rua);
                clienteAtual.setNumero(numero);
                clienteAtual.setBairro(bairro);
                clienteAtual.setCidade(cidade);
                clienteAtual.setEstado(estado);
                clienteAtual.setSenha(senha); 
              

                sucesso = clienteDAO.atualizarCliente(clienteAtual);
                
                if (sucesso) {
                   
                    session.setAttribute("usuarioLogado", clienteAtual);
                }

            } else if ("fornecedor".equals(tipoUsuario)) {
               

              
                Fornecedor fornecedorAtual = (Fornecedor) session.getAttribute("usuarioLogado");
                
               
                String telefone = request.getParameter("telefone");
                String descricao = request.getParameter("descricao");

                
                fornecedorAtual.setNomeLoja(nome); 
                fornecedorAtual.setEmail(email);
                fornecedorAtual.setCep(cep);
                fornecedorAtual.setRua(rua);
                fornecedorAtual.setNumero(numero);
                fornecedorAtual.setBairro(bairro);
                fornecedorAtual.setCidade(cidade);
                fornecedorAtual.setEstado(estado);
                fornecedorAtual.setSenha(senha);
                fornecedorAtual.setTelefone(telefone);
                fornecedorAtual.setDescricao(descricao);
                

              
                sucesso = fornecedorDAO.atualizarFornecedor(fornecedorAtual);
                
                if (sucesso) {
                   
                    session.setAttribute("usuarioLogado", fornecedorAtual);
                }
            }

           
            if (sucesso) {
                response.sendRedirect("perfil.jsp?msg=sucesso");
            } else {
                response.sendRedirect("perfil.jsp?msg=erro_atualizar");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("perfil.jsp?msg=erro_excecao");
        }
    }
}