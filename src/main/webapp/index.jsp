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
<title>Brec Brechó — Página Inicial</title>
 
<link rel="stylesheet" href="${baseURL}/css/Styles.css">
 
</head>
<body>
 
	<jsp:include page="/partes/header.jsp" />
 
	<main>
		<section id="home" class="card">
 
			<c:if test="${not empty listaPromovidos}">
				<div class="slider-container">
					<button class="prev">&#10094;</button>
					<div class="slider">
						<div class="slides">
							<c:forEach var="produto" items="${listaPromovidos}">
								<a href="${baseURL}/produto-detalhe?id=${produto.idProduto}">
									<img src="${produto.fotoBase64}" alt="${produto.nome}">
								</a>
							</c:forEach>
						</div>
					</div>
					<button class="next">&#10095;</button>
				</div>
			</c:if>
 
			<div class="chamariscos">
				<div class="card">💳 10% de desconto no PIX</div>
				<div class="card">🚚 Frete fixo R$ 15,00 para todo Brasil</div>
			</div>
 
			<div style="text-align: center; margin: 2rem;">
				<c:choose>
					<c:when test="${sessionScope.tipoUsuario == 'fornecedor'}">
						<a href="${baseURL}/produto?acao=novo">
							<button id="btnVendaPecas">
								<img src="${baseURL}/img/venda-icon.png" alt="Venda Icon">
								Venda suas peças aqui
							</button>
						</a>
					</c:when>
					<c:otherwise>
						<a href="${baseURL}/login.jsp">
							<button id="btnVendaPecas">
								<img src="${baseURL}/img/venda-icon.png" alt="Venda Icon">
								Venda suas peças aqui
							</button>
						</a>
					</c:otherwise>
				</c:choose>
			</div>
 
			<h2 class="home-section-title">Destaques da Semana</h2>
 
			<div id="catalogoLista" class="catalogo-grid">
				<c:choose>
					<c:when test="${empty listaRecentes}">
						<p class="small" style="text-align: center; width: 100%;">
							Nenhum produto novo cadastrado esta semana.</p>
					</c:when>
					<c:otherwise>
						<c:forEach var="produto" items="${listaRecentes}">
							<div class="produto-card">
								<img src="${produto.fotoBase64}" alt="${produto.nome}">
								<h3>${produto.nome}</h3>
								<p>
									<strong>Tamanho:</strong> ${produto.tamanho}
								</p>
								<p>
									<strong>Preço:</strong>
									<fmt:formatNumber value="${produto.preco}" type="currency"
										currencyCode="BRL" />
								</p>
 
								<a
									href="${baseURL}/carrinho?acao=adicionar&id=${produto.idProduto}"
									class="btn-principal"> Adicionar ao Carrinho </a>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
 
			<div class="home-links" style="margin-top: 2rem;">
				<a href="${baseURL}/cliente.jsp" class="btn">Cadastro Cliente</a>
				<a href="${baseURL}/fornecedor.jsp" class="btn">Cadastro Fornecedor</a>
				<a href="${baseURL}/como-vender.jsp" class="btn">Como Vender? </a>
				<a href="${baseURL}/catalogo" class="btn">Ver Catálogo</a>
				<a href="${baseURL}/carrinho" class="btn">Ver Carrinho</a>
				<a href="${baseURL}/login.jsp" class="btn">Login</a>
			</div>
 
		</section>
	</main>
 
	<jsp:include page="/partes/footer.jsp" />
 
	<script src="${baseURL}/js/carrossel.js"></script>
 
</body>
</html>
Oracle Java Technologies | Oracle
Java can help reduce costs, drive innovation, & improve application services; the #1 programming language for IoT, enterprise architecture, and cloud computing.
 