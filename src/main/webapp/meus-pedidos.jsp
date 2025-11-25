<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<fmt:setLocale value="pt_BR" />
 
<c:if test="${sessionScope.tipoUsuario != 'cliente'}">
	<%
	response.sendRedirect(request.getContextPath() + "/login.jsp?msg=acesso_negado");
	%>
</c:if>
 
<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Meus Pedidos — Brec Brechó</title>
 
<link rel="stylesheet" href="${baseURL}/css/Styles.css">
 
</head>
<body>
 
	<jsp:include page="/partes/header.jsp" />
 
	<main>
		<section class="card">
			<h2>Meus Pedidos</h2>
 
			<c:if test="${param.msg == 'compra_sucesso'}">
				<p class="feedback-msg feedback-sucesso">
					<strong>Oba!</strong> Seu pedido foi realizado com sucesso!
				</p>
			</c:if>
			<c:if test="${param.msg == 'erro_pedido'}">
				<p class="feedback-msg feedback-erro">Pedido não encontrado ou
					não pertence a você.</p>
			</c:if>
 
			<c:choose>
				<c:when test="${empty requestScope.listaPedidos}">
					<p class="small">Você ainda não fez nenhum pedido.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="pedido" items="${requestScope.listaPedidos}">
						<div class="pedido-card">
							<div class="pedido-header">
								<div>
									<strong>Pedido #${pedido.idPedido}</strong>
								</div>
								<div>
									Data:
									<fmt:formatDate value="${pedido.dataPedido}"
										pattern="dd/MM/yyyy HH:mm" />
								</div>
 
								<c:set var="statusClass" value="status-pendente" />
								<c:if test="${pedido.statusPedido == 'PENDENTE_PAGAMENTO'}">
									<c:set var="statusClass" value="status-pendente" />
								</c:if>
								<c:if test="${pedido.statusPedido == 'PAGO'}">
									<c:set var="statusClass" value="status-pago" />
								</c:if>
								<c:if test="${pedido.statusPedido == 'ENVIADO'}">
									<c:set var="statusClass" value="status-enviado" />
								</c:if>
								<c:if test="${pedido.statusPedido == 'ENTREGUE'}">
									<c:set var="statusClass" value="status-entregue" />
								</c:if>
								<c:if test="${pedido.statusPedido == 'CANCELADO'}">
									<c:set var="statusClass" value="status-cancelado" />
								</c:if>
 
								<div class="${statusClass}">
									Status: <strong>${pedido.statusPedido}</strong>
								</div>
							</div>
 
							<div class="pedido-body">
								<div class="pedido-itens">
									<strong>Itens:</strong>
									<c:forEach var="item" items="${pedido.itens}">
										<div>
											(${item.quantidade}x) ${item.produto.nome} - (
											<fmt:formatNumber value="${item.precoUnitarioVenda}"
												type="currency" />
											cada)
										</div>
									</c:forEach>
									<hr>
									Total: <strong><fmt:formatNumber
											value="${pedido.valorTotal}" type="currency" /></strong>
									(${pedido.formaPagamento})
								</div>
 
								<div class="pedido-acoes"
									style="text-align: right; margin-top: 10px;">
 
									<a href="${baseURL}/meus-pedidos?id=${pedido.idPedido}"
										class="btn-principal"> Ver Detalhes/Recibo </a>
								</div>
 
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</section>
	</main>
 
	<jsp:include page="/partes/footer.jsp" />
 
</body>
</html>
