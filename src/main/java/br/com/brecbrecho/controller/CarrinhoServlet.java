package br.com.brecbrecho.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Imports Jakarta EE
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Nossos pacotes
import br.com.brecbrecho.dao.PedidoDAO;
import br.com.brecbrecho.dao.ProdutoDAO; // Precisamos do ProdutoDAO
import br.com.brecbrecho.model.Cliente;
import br.com.brecbrecho.model.ItemPedido;
import br.com.brecbrecho.model.Pedido;
import br.com.brecbrecho.model.Produto;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static final double FRETE = 15.00;
    private static final double DESCONTO_PIX_BOLETO = 0.10; // 10%

    private ProdutoDAO produtoDAO; // <-- PRECISAMOS DO PRODUTODAO
    private PedidoDAO pedidoDAO;

    public void init() {
        produtoDAO = new ProdutoDAO(); // <-- INICIALIZE ELE
        pedidoDAO = new PedidoDAO();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "ver"; 
        }

        switch (acao) {
            case "adicionar":
                adicionarItem(request, response);
                break;
            
            case "aumentar":
                aumentarItem(request, response);
                break;
            case "diminuir":
                diminuirItem(request, response);
                break;
        
            case "remover":
                removerItem(request, response);
                break;
            case "esvaziar":
                esvaziarCarrinho(request, response);
                break;
            case "ver":
            default:
               
                RequestDispatcher dispatcher = request.getRequestDispatcher("carrinho.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
       
        String acao = request.getParameter("acao");
        if ("finalizar".equals(acao)) {
            finalizarCompra(request, response);
        } else {
            doGet(request, response);
        }
    }

   

   
    private Map<Integer, ItemPedido> getCarrinho(HttpSession session) {
        Map<Integer, ItemPedido> carrinho = (Map<Integer, ItemPedido>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new HashMap<>();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    private void adicionarItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Map<Integer, ItemPedido> carrinho = getCarrinho(session);

        try {
            int idProduto = Integer.parseInt(request.getParameter("id"));
            Produto produto = produtoDAO.buscarProdutoPorId(idProduto);

            if (produto == null || produto.getEstoque() <= 0) {
                response.sendRedirect("catalogo?msg=erro_estoque");
                return;
            }

            ItemPedido itemNoCarrinho = carrinho.get(idProduto);

            if (itemNoCarrinho == null) {
                ItemPedido novoItem = new ItemPedido();
                novoItem.setIdProduto(idProduto);
                novoItem.setQuantidade(1);
                novoItem.setPrecoUnitarioVenda(produto.getPreco());
                novoItem.setProduto(produto);
                carrinho.put(idProduto, novoItem);
            } else {
              
                aumentarItem(request, response);
                return; 
            }

            session.setAttribute("carrinho", carrinho);
            response.sendRedirect("carrinho");

        } catch (NumberFormatException e) {
            response.sendRedirect("catalogo?msg=erro_id");
        }
    }

   
    
    private void aumentarItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<Integer, ItemPedido> carrinho = getCarrinho(session);
        try {
            int idProduto = Integer.parseInt(request.getParameter("id"));
            ItemPedido item = carrinho.get(idProduto);
            
            if (item != null) {
                Produto produto = produtoDAO.buscarProdutoPorId(idProduto); 
                if (item.getQuantidade() < produto.getEstoque()) {
                    item.setQuantidade(item.getQuantidade() + 1); 
                } else {
                    session.setAttribute("msg_carrinho", "Estoque máximo atingido para " + produto.getNome());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("carrinho"); 
    }

    private void diminuirItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<Integer, ItemPedido> carrinho = getCarrinho(session);
        try {
            int idProduto = Integer.parseInt(request.getParameter("id"));
            ItemPedido item = carrinho.get(idProduto);
            
            if (item != null) {
                if (item.getQuantidade() > 1) {
                    item.setQuantidade(item.getQuantidade() - 1); 
                } else {
                    
                    carrinho.remove(idProduto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("carrinho"); 
    }
    

    private void removerItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<Integer, ItemPedido> carrinho = getCarrinho(session);

        if (carrinho != null) {
            try {
                int idProduto = Integer.parseInt(request.getParameter("id"));
                carrinho.remove(idProduto);
                session.setAttribute("carrinho", carrinho);
            } catch (NumberFormatException e) {
             
            }
        }
        response.sendRedirect("carrinho");
    }

    private void esvaziarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("carrinho");
        response.sendRedirect("carrinho");
    }

   
    private void finalizarCompra(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        HttpSession session = request.getSession(false); 

     
        if (session == null || session.getAttribute("usuarioLogado") == null || !"cliente".equals(session.getAttribute("tipoUsuario"))) {
            response.sendRedirect("login.jsp?msg=erro_finalizar");
            return;
        }
        Cliente clienteLogado = (Cliente) session.getAttribute("usuarioLogado");

       
        Map<Integer, ItemPedido> carrinho = (Map<Integer, ItemPedido>) session.getAttribute("carrinho");
        if (carrinho == null || carrinho.isEmpty()) {
            response.sendRedirect("carrinho?msg=vazio");
            return;
        }
        
        
        String formaPagamento = request.getParameter("formaPagamento"); 

        try {
           
            double subtotal = 0.0;
            for (ItemPedido item : carrinho.values()) {
                subtotal += item.getPrecoUnitarioVenda() * item.getQuantidade();
            }
            
            double totalFinal = subtotal + FRETE; 
            
            if ("pix".equals(formaPagamento) || "boleto".equals(formaPagamento)) {
                totalFinal = totalFinal * (1.0 - DESCONTO_PIX_BOLETO); 
            }

           
            Pedido novoPedido = new Pedido();
            novoPedido.setIdCliente(clienteLogado.getIdCliente());
            novoPedido.setFormaPagamento(formaPagamento);
            novoPedido.setValorTotal(totalFinal);
            novoPedido.setItens(new ArrayList<>(carrinho.values()));

           
            int idNovoPedido = pedidoDAO.salvarPedido(novoPedido);

          
            if (idNovoPedido > 0) { 
                session.removeAttribute("carrinho"); 
                response.sendRedirect("meus-pedidos?id=" + idNovoPedido + "&msg=compra_sucesso");
            } else {
                response.sendRedirect("carrinho?msg=erro_finalizar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("carrinho?msg=erro_excecao");
        }
    }
}