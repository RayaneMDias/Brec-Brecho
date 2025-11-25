<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="pt_BR"/>

<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Painel Admin — Liberar Pagamentos</title>
    
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
    
   
    
</head>
<body>

    <jsp:include page="/partes/header.jsp" />

    <main>
        <section class="card" style="margin-bottom: 25px;">
            <h2>Liberar Pagamentos (Regra de 15 dias)</h2>
            <p class="small">
                Abaixo estão os pedidos que já foram marcados como "ENTREGUE" há 15 dias ou mais
                e estão prontos para ter o pagamento repassado ao fornecedor.
            </p>
            
            <c:if test="${not empty requestScope.mensagem}">
                <c:set var="tipoMsg" value="${requestScope.mensagem.startsWith('ERRO') ? 'feedback-erro' : 'feedback-sucesso'}" />
                <p class="feedback-msg ${tipoMsg}">
                    ${requestScope.mensagem}
                </p>
            </c:if>

            <c:choose>
                <c:when test="${empty requestScope.listaPagamentos}">
                    <p class="small">Nenhum pagamento pendente com mais de 15 dias.</p>
                </c:when>
                <c:otherwise>
                    <table class="tabela-admin">
                        <thead>
                            <tr>
                                <th>Pedido ID</th>
                                <th>Data Entrega</th>
                                <th>Cliente</th>
                                <th class="col-valor">Valor Total</th>
                                <th class="col-valor">Valor a Pagar*</th>
                                <th class="col-acoes">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pedido" items="${requestScope.listaPagamentos}">
                                
                                <c:set var="valorProdutos" value="${pedido.valorTotal - 15.00}" />
                                <c:set var="valorLiquido" value="${valorProdutos * 0.70}" /> <%-- 70% --%>
                                <c:set var="valorRepasse" value="${valorLiquido + 15.00}" /> <%-- 70% + Frete --%>
                                
                                <tr>
                                    <td>#${pedido.idPedido}</td>
                                    <td>
                                        <fmt:formatDate value="${pedido.dataEntrega}" 
                                                        pattern="dd/MM/yyyy" />
                                    </td>
                                    <td>${pedido.cliente.nome}</td>
                                    <td>
                                        <fmt:formatNumber value="${pedido.valorTotal}" type="currency" />
                                    </td>
                                    <td>
                                        <strong><fmt:formatNumber value="${valorRepasse}" type="currency" /></strong>
                                    </td>
                                    <td class="col-acoes">
                                        <a href="liberarPagamentos?acao=pagar&id=${pedido.idPedido}"
                                           class="btn-verde"
                                           onclick="return confirm('Tem certeza que deseja liberar o pagamento de <fmt:formatNumber value="${valorRepasse}" type="currency" /> para este pedido?')">
                                            Liberar Pagamento
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <small>
                        *Valor a Pagar = ( (Valor Total - R$ 15,00 Frete) * 70% ) + R$ 15,00 Frete
                    </small>
                </c:otherwise>
            </c:choose>
            
        </section>
        
       
        <section class="card" style="background-color: #f9f9f9;">
            <%@ include file="nav-admin.jspf" %>
        </section>

    </main>

    <jsp:include page="/partes/footer.jsp" />

</body>
</html>