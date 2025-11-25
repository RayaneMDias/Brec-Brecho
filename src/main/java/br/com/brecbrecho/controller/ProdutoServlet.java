package br.com.brecbrecho.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
// import java.util.List; // Não é usado diretamente aqui

// Imports Jakarta EE (Tomcat 10+)
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

// Nossos pacotes
import br.com.brecbrecho.dao.ProdutoDAO;
import br.com.brecbrecho.model.Fornecedor;
import br.com.brecbrecho.model.Produto;
import br.com.brecbrecho.model.Administrador; // Precisamos saber o que é um Admin


@WebServlet("/produto")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, 
    maxFileSize = 1024 * 1024 * 10, 
    maxRequestSize = 1024 * 1024 * 15 
)
public class ProdutoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProdutoDAO produtoDAO;

    public void init() {
        produtoDAO = new ProdutoDAO();
    }

  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
    
        String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

        
        if (tipoUsuario == null || (!"fornecedor".equals(tipoUsuario) && !"admin".equals(tipoUsuario))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=acesso_negado");
            return;
        }
        
       
        
        String acao = request.getParameter("acao");

        try {
            if ("excluir".equals(acao)) {
               
                int idProduto = Integer.parseInt(request.getParameter("id"));
                Produto p = produtoDAO.buscarProdutoPorId(idProduto);
                
                boolean temPermissao = false;

               
                if ("admin".equals(tipoUsuario)) {
                    temPermissao = true;
                } 
               
                else if ("fornecedor".equals(tipoUsuario)) {
                    Fornecedor f = (Fornecedor) session.getAttribute("usuarioLogado");
                    if (p != null && p.getIdFornecedor() == f.getIdFornecedor()) {
                        temPermissao = true;
                    }
                }

                if (temPermissao) {
                    produtoDAO.excluirProduto(idProduto);
            
                    String redirectURL = "admin".equals(tipoUsuario) ? "admin/gerenciarProdutos" : "meus-produtos";
                    response.sendRedirect(redirectURL + "?msg=excluido_sucesso");
                } else {
                    String redirectURL = "admin".equals(tipoUsuario) ? "admin/gerenciarProdutos" : "meus-produtos";
                    response.sendRedirect(redirectURL + "?msg=erro_excluir");
                }
                
            } else if ("carregar".equals(acao)) {
                
                int idProduto = Integer.parseInt(request.getParameter("id"));
                Produto p = produtoDAO.buscarProdutoPorId(idProduto);
                
                boolean temPermissao = false;

                if ("admin".equals(tipoUsuario)) {
                    temPermissao = true;
                } 
                else if ("fornecedor".equals(tipoUsuario)) {
                    Fornecedor f = (Fornecedor) session.getAttribute("usuarioLogado");
                    if (p != null && p.getIdFornecedor() == f.getIdFornecedor()) {
                        temPermissao = true;
                    }
                }

                if (p != null && temPermissao) {
                    request.setAttribute("produtoParaEditar", p);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("cadastrar-produto.jsp");
                    dispatcher.forward(request, response);
                } else {
                    String redirectURL = "admin".equals(tipoUsuario) ? "admin/gerenciarProdutos" : "meus-produtos";
                    response.sendRedirect(redirectURL + "?msg=erro_carregar");
                }
                
            } else if ("novo".equals(acao) && "admin".equals(tipoUsuario)) {
                 
                 response.sendRedirect(request.getContextPath() + "/admin/gerenciarProdutos?msg=admin_nao_cria");
            
            } else {
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("cadastrar-produto.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String redirectURL = "admin".equals(tipoUsuario) ? "admin/gerenciarProdutos" : "meus-produtos";
            response.sendRedirect(redirectURL + "?msg=erro_geral");
        }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        
        String tipoUsuario = (session != null) ? (String) session.getAttribute("tipoUsuario") : null;

        if (tipoUsuario == null || (!"fornecedor".equals(tipoUsuario) && !"admin".equals(tipoUsuario))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=acesso_negado");
            return;
        }
       

        String redirectURL = "admin".equals(tipoUsuario) ? "admin/gerenciarProdutos" : "meus-produtos";

        try {
           
            String nome = request.getParameter("nomeProduto");
            String descricao = request.getParameter("descricaoProduto");
            String tamanho = request.getParameter("tamanhoProduto");
            int estoque = Integer.parseInt(request.getParameter("quantidadeProduto"));
            double preco = Double.parseDouble(request.getParameter("precoProduto"));
            int prazo = Integer.parseInt(request.getParameter("prazoProduto"));
            
            String idProdutoStr = request.getParameter("idProduto");

           
            Part fotoPart = request.getPart("fotoProduto");
            String fotoBase64 = null;

            if (fotoPart != null && fotoPart.getSize() > 0) {
                String mimeType = fotoPart.getContentType();
                InputStream inputStream = fotoPart.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int nRead;
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                byte[] imageBytes = buffer.toByteArray();
                String base64Encoded = Base64.getEncoder().encodeToString(imageBytes);
                fotoBase64 = "data:" + mimeType + ";base64," + base64Encoded;
            }

            
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setTamanho(tamanho);
            produto.setEstoque(estoque);
            produto.setPreco(preco);
            produto.setPrazoLocacaoDias(prazo);

            if (idProdutoStr == null || idProdutoStr.isEmpty()) {
            
                
              
                if ("admin".equals(tipoUsuario)) {
                     response.sendRedirect(request.getContextPath() + "/admin/gerenciarProdutos?msg=admin_nao_cria");
                     return;
                }
                
                Fornecedor fornecedorLogado = (Fornecedor) session.getAttribute("usuarioLogado");
                produto.setIdFornecedor(fornecedorLogado.getIdFornecedor());
                produto.setFotoBase64(fotoBase64); 
                produtoDAO.cadastrarProduto(produto);
                
            } else {
            
                int idProduto = Integer.parseInt(idProdutoStr);
                produto.setIdProduto(idProduto);
                
                Produto produtoExistente = produtoDAO.buscarProdutoPorId(idProduto);
                if (produtoExistente == null) {
                    response.sendRedirect(redirectURL + "?msg=erro_nao_encontrado");
                    return;
                }
                
                
                if ("fornecedor".equals(tipoUsuario)) {
                    Fornecedor fornecedorLogado = (Fornecedor) session.getAttribute("usuarioLogado");
                    if (produtoExistente.getIdFornecedor() != fornecedorLogado.getIdFornecedor()) {
                         response.sendRedirect("meus-produtos?msg=erro_permissao");
                         return;
                    }
                }
              
                produto.setIdFornecedor(produtoExistente.getIdFornecedor());
                
                if (fotoBase64 == null) {
                 
                    produto.setFotoBase64(produtoExistente.getFotoBase64());
                } else {
                 
                    produto.setFotoBase64(fotoBase64);
                }
                
                produtoDAO.atualizarProduto(produto);
            }
            
           
            response.sendRedirect(redirectURL + "?msg=salvo_sucesso");

        } catch (Exception e) {
            e.printStackTrace();
        
            response.sendRedirect(redirectURL + "?msg=erro_salvar");
        }
    }
}