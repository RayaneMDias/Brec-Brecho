<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
<fmt:setLocale value="pt_BR"/>
 
<c:if test="${sessionScope.tipoUsuario != 'fornecedor'}">
    <% response.sendRedirect("login.jsp?msg=acesso_negado"); %>
</c:if>
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Minhas Vendas — Brec Brechó</title>
    
    <link rel="stylesheet" href="css/Styles.css">
    
    <style>
        .tabela-vendas {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
            margin-bottom: 2rem;
            font-size: 0.9rem;
        }
        .tabela-vendas th, .tabela-vendas td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
            vertical-align: middle;
        }
        .tabela-vendas th { background-color: #f2f2f2; }
        .tabela-vendas .col-acoes { width: 180px; }
        
        .status-pago { color: #007bff; font-weight: bold; }
        .status-enviado { color: #5a009c; }
        .status-entregue { color: #008000; }
        .status-concluido { color: #555; }
    </style>
</head>
<body>
 
    <jsp:include page="partes/header.jsp" />
 
    <main>
        <section class="card">
            <h2>Minhas Vendas</h2>
            <p class="small">Acompanhe os pedidos dos seus produtos e prepare-os para o envio.</p>
 
            <%-- 1. Tabela de Vendas --%>
            <c:choose>
                <c:when test="${empty requestScope.listaVendas}">
                    <p class="small">Você ainda não realizou nenhuma venda.</p>
                </c:when>
                <c:otherwise>
                    <table class="tabela-vendas">
                        <thead>
                            <tr>
                                <th>Pedido ID</th>
                                <th>Data</th>
                                <th>Cliente</th>
                                <th>Valor Total</th>
                                <th>Status</th>
                                <th class="col-acoes">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pedido" items="${requestScope.listaVendas}">
                                <tr>
                                    <td>#${pedido.idPedido}</td>
                                    <td>
                                        <fmt:formatDate value="${pedido.dataPedido}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td>${pedido.cliente.nome}</td>
                                    <td>
                                        <fmt:formatNumber value="${pedido.valorTotal}" type="currency" />
                                    </td>
 
                                    <c:set var="statusClass" value="" />
                                    <c:if test="${pedido.statusPedido == 'PAGO'}"><c:set var="statusClass" value="status-pago" /></c:if>
                                    <c:if test="${pedido.statusPedido == 'ENVIADO'}"><c:set var="statusClass" value="status-enviado" /></c:if>
                                    <c:if test="${pedido.statusPedido == 'ENTREGUE'}"><c:set var="statusClass" value="status-entregue" /></c:if>
                                    <c:if test="${pedido.statusPedido == 'CONCLUIDO'}"><c:set var="statusClass" value="status-concluido" /></c:if>
                                    
                                    <td class="${statusClass}">
                                        <strong>${pedido.statusPedido}</strong>
                                    </td>
                                    
                                    <td class="col-acoes">
 
                                        <c:if test="${pedido.statusPedido == 'PAGO' || pedido.statusPedido == 'ENVIADO'}">
 
                                            <a href="guia-envio?id=${pedido.idPedido}" target="_blank">
                                                <button style="background-color: #007bff;">Imprimir Guia de Envio</button>
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            
        </section>
    </main>
 
    <jsp:include page="partes/footer.jsp" />
 
</body>
</html>
