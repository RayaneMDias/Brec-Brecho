package br.com.brecbrecho.controller;
import java.io.IOException;
import br.com.brecbrecho.dao.ClienteDAO;
import br.com.brecbrecho.dao.FornecedorDAO;
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.model.Fornecedor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/editarUsuario")
public class AdminEditarUsuarioServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;
    private FornecedorDAO fornecedorDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
        fornecedorDAO = new FornecedorDAO();
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String tipo = request.getParameter("type");
            int id = Integer.parseInt(request.getParameter("id"));

            if ("cliente".equals(tipo)) {
                Cliente cliente = clienteDAO.buscarClientePorId(id);
                request.setAttribute("usuarioParaEditar", cliente);
                
            } else if ("fornecedor".equals(tipo)) {
                Fornecedor fornecedor = fornecedorDAO.buscarFornecedorPorId(id);
                request.setAttribute("usuarioParaEditar", fornecedor);
            }

            request.setAttribute("tipoUsuario", tipo); 
            
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/editarUsuario.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gerenciarUsuarios?msg=erro_carregar");
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        String tipo = request.getParameter("tipo");
        int id = Integer.parseInt(request.getParameter("id"));
        
       
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cep = request.getParameter("cep");
        String rua = request.getParameter("rua");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
       

        boolean sucesso = false;
        
        try {
            if ("cliente".equals(tipo)) {
                Cliente cliente = clienteDAO.buscarClientePorId(id); 
                
                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setCpf(request.getParameter("cpf")); 
                cliente.setCep(cep);
                cliente.setRua(rua);
                cliente.setNumero(numero);
                cliente.setBairro(bairro);
                cliente.setCidade(cidade);
                cliente.setEstado(estado);
                
               
                sucesso = clienteDAO.atualizarCliente(cliente);
                
            } else if ("fornecedor".equals(tipo)) {
                Fornecedor f = fornecedorDAO.buscarFornecedorPorId(id); 
                f.setNomeLoja(nome); 
                f.setEmail(email);
                f.setCpfCnpj(request.getParameter("cpfCnpj"));
                f.setTelefone(request.getParameter("telefone"));
                f.setDescricao(request.getParameter("descricao"));
                f.setCep(cep);
                f.setRua(rua);
                f.setNumero(numero);
                f.setBairro(bairro);
                f.setCidade(cidade);
                f.setEstado(estado);
                
                sucesso = fornecedorDAO.atualizarFornecedor(f);
            }

            if (sucesso) {
                response.sendRedirect("gerenciarUsuarios?msg=edit_sucesso");
            } else {
                response.sendRedirect("gerenciarUsuarios?msg=edit_erro");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gerenciarUsuarios?msg=edit_erro_excecao");
        }
    }
}