package br.com.brecbrecho.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Nossos pacotes de Model e DAO
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.dao.ClienteDAO;


@WebServlet("/cadastrarCliente") 
public class ClienteServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L; 
    private ClienteDAO clienteDAO;

   
    public void init() {
        clienteDAO = new ClienteDAO(); 
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
          
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha"); 
            String cep = request.getParameter("cep");
            String rua = request.getParameter("rua");
            String numero = request.getParameter("numero");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            
           
            Cliente novoCliente = new Cliente();
            novoCliente.setNome(nome);
            novoCliente.setEmail(email);
            novoCliente.setCpf(cpf);
            novoCliente.setSenha(senha);
            novoCliente.setCep(cep);
            novoCliente.setRua(rua);
            novoCliente.setNumero(numero);
            novoCliente.setBairro(bairro);
            novoCliente.setCidade(cidade);
            novoCliente.setEstado(estado);

            
            boolean sucesso = clienteDAO.cadastrarCliente(novoCliente);

           
            if (sucesso) {
                
                response.sendRedirect("login.jsp?msg=sucesso");
            } else {
           
                response.sendRedirect("cliente.jsp?msg=erro");
            }

        } catch (Exception e) {
            e.printStackTrace();
         
            response.sendRedirect("cliente.jsp?msg=erro_excecao");
        }
    }
}