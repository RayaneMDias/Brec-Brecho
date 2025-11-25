<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<fmt:setLocale value="pt_BR"/>
 
<c:if test="${sessionScope.tipoUsuario != 'fornecedor'}">
    <% response.sendRedirect(request.getContextPath() + "/login.jsp?msg=acesso_negado"); %>
</c:if>
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Minha Carteira — Brec Brechó</title>
    
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
    
</head><body>
 
    <jsp:include page="/partes/header.jsp" />
 
    <main>
        <section class="card">
            <h2>Minha Carteira</h2>
            <p class="small">Acompanhe seus ganhos e o status dos seus pagamentos.</p>
            
            <c:set var="saldo" value="${requestScope.saldoFornecedor}" />
 
            <div class="card-saldo saldo-disponivel">
                <h3>Saldo Disponível para Saque</h3>
                <div class="valor">
                    <fmt:formatNumber value="${saldo.saldoDisponivel}" type="currency" />
                </div>
                <p>Este é o valor total que você já pode sacar.</p>
 
                <button class="btn-principal btn-full-width" onclick="alert('Funcionalidade de saque em desenvolvimento!')">
                    Solicitar Saque
                </button>
            </div>
 
            <div class="card-saldo saldo-pendente">
                <h3>Saldo Pendente</h3>
                <div class="valor">
                    <fmt:formatNumber value="${saldo.saldoPendente}" type="currency" />
                </div>
                <p>
                    Este é o valor de vendas já entregues que está aguardando o
                    período de garantia de 15 dias antes de ser liberado para saque.
                </p>
            </div>
            
        </section>
    </main>
 
    <jsp:include page="/partes/footer.jsp" />
 
	</body>
</html>
