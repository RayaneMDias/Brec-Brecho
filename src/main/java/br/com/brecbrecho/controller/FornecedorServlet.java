package br.com.brecbrecho.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Nossos pacotes
import br.com.brecbrecho.model.Fornecedor;
import br.com.brecbrecho.dao.FornecedorDAO;


@WebServlet("/cadastrarFornecedor")
public class FornecedorServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private FornecedorDAO fornecedorDAO;

    public void init() {
        fornecedorDAO = new FornecedorDAO(); 
    }

  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            
            String nomeLoja = request.getParameter("nomeFornecedor");
            String email = request.getParameter("emailFornecedor");
            String cpfCnpj = request.getParameter("cpfCnpjFornecedor");
            String cep = request.getParameter("cepFornecedor");
            String rua = request.getParameter("ruaFornecedor");
            String numero = request.getParameter("numeroFornecedor");
            String bairro = request.getParameter("bairroFornecedor");
            String cidade = request.getParameter("cidadeFornecedor");
            String estado = request.getParameter("estadoFornecedor");
            String telefone = request.getParameter("telefoneFornecedor");
            String descricao = request.getParameter("descricaoFornecedor");
            String senha = request.getParameter("senhaFornecedor"); 

            
            Fornecedor novoFornecedor = new Fornecedor();
            novoFornecedor.setNomeLoja(nomeLoja);
            novoFornecedor.setEmail(email);
            novoFornecedor.setCpfCnpj(cpfCnpj);
            novoFornecedor.setCep(cep);
            novoFornecedor.setRua(rua);
            novoFornecedor.setNumero(numero);
            novoFornecedor.setBairro(bairro);
            novoFornecedor.setCidade(cidade);
            novoFornecedor.setEstado(estado);
            novoFornecedor.setTelefone(telefone);
            novoFornecedor.setDescricao(descricao);
            novoFornecedor.setSenha(senha);

           
            boolean sucesso = fornecedorDAO.cadastrarFornecedor(novoFornecedor);

            
            if (sucesso) {
                
                response.sendRedirect("login.jsp?msg=sucesso");
            } else {
               
                response.sendRedirect("fornecedor.jsp?msg=erro");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("fornecedor.jsp?msg=erro_excecao");
        }
    }
}