<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<fmt:setLocale value="pt_BR" />
 
<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Painel Admin — Gerenciar Pedidos</title>
 
<link rel="stylesheet" href="${baseURL}/css/Styles.css">
 
</head>
<body>
 
	<jsp:include page="/partes/header.jsp" />
 
	<main>
		<section class="card" style="margin-bottom: 25px;">
			<h2>Gerenciar Pedidos</h2>
 
			<c:if test="${not empty requestScope.mensagem}">
				<p class="feedback-msg feedback-sucesso">
					${requestScope.mensagem}</p>
			</c:if>
 
			<c:choose>
				<c:when test="${empty requestScope.listaPedidos}">
					<p class="small">Nenhum pedido encontrado no sistema.</p>
				</c:when>
				<c:otherwise>
 
					<c:forEach var="pedido" items="${requestScope.listaPedidos}">
						<div class="pedido-card">
							<div class="pedido-header">
								<div>
									<strong>Pedido #${pedido.idPedido}</strong>
								</div>
								<div>
									Cliente: <strong>${pedido.cliente.nome}</strong>
								</div>
								<div>
									Data:
									<fmt:formatDate value="${pedido.dataPedido}"
										pattern="dd/MM/yyyy HH:mm" />
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
 
								<div class="pedido-acoes">
									<strong>Status Atual:</strong>
 
									<c:set var="statusClass" value="status-pendente" />
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
 
									<h4 class="${statusClass}">${pedido.statusPedido}</h4>
 
									<c:if test="${pedido.statusPedido == 'ENTREGUE'}">
										<small>Entregue em: <br> <fmt:formatDate
												value="${pedido.dataEntrega}" pattern="dd/MM/yyyy HH:mm" />
										</small>
									</c:if>
 
									<hr>
									<strong>Ações do Admin:</strong>
 
									<c:choose>
										<c:when test="${pedido.statusPedido == 'PENDENTE_PAGAMENTO'}">
											<a
												href="gerenciarPedidos?acao=marcarPago&id=${pedido.idPedido}"
												class="btn btn-azul"> Marcar como PAGO </a>
											<a
												href="gerenciarPedidos?acao=cancelar&id=${pedido.idPedido}"
												onclick="return confirm('Certeza?')"
												class="btn btn-vermelho"> Cancelar Pedido </a>
										</c:when>
 
										<c:when test="${pedido.statusPedido == 'PAGO'}">
											<a
												href="gerenciarPedidos?acao=marcarEnviado&id=${pedido.idPedido}"
												class="btn btn-roxo"> Marcar como ENVIADO </a>
										</c:when>
 
										<c:when test="${pedido.statusPedido == 'ENVIADO'}">
											<a
												href="gerenciarPedidos?acao=marcarEntregue&id=${pedido.idPedido}"
												class="btn btn-verde"> Marcar como ENTREGUE </a>
										</c:when>
 
										<c:when test="${pedido.statusPedido == 'ENTREGUE'}">
											<p class="small">Aguardando 15 dias para liberação do
												pagamento.</p>
										</c:when>
 
										<c:otherwise>
											<p class="small">Nenhuma ação disponível.</p>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</section>
 
		<section class="card" style="background-color: #f9f9f9;">
			<%@ include file="nav-admin.jspf"%>
		</section>
 
	</main>
 
	<jsp:include page="/partes/footer.jsp" />
 
</body>
</html>
