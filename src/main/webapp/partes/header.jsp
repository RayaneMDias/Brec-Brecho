<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<div id="topo"></div>
<header>
 
	<div class="brand">
 
		<a href="${baseURL}/home"> <img
			src="${baseURL}/img/logo-brec2.png" alt="Logo Brec Brechó"
			id="logo-principal">
		</a>
	</div>
	<div class="header-search">
		<form action="${baseURL}/catalogo" method="GET">
			<input type="search" name="busca"
				placeholder="O que você está procurando?"
				value="${requestScope.termoBuscado}">
			<button type="submit" title="Buscar">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
					fill="currentColor">
<path
						d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z" />
</svg>
			</button>
		</form>
	</div>
	<div class="menu-toggle" onclick="toggleMenu()">&#9776;</div>
	<nav id="main-nav">
		<a href="${baseURL}/home">Início</a> <a href="${baseURL}/catalogo">Catálogo</a>
 
		<c:if test="${sessionScope.tipoUsuario != 'admin'}">
			<a href="${baseURL}/carrinho" class="cart-link" title="Carrinho">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
					fill="currentColor">
<path
						d="M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zM1 2v2h2l3.6 7.59-1.35 2.45c-.16.28-.25.61-.25.96 0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49c.08-.14.12-.31.12-.48 0-.55-.45-1-1-1H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.9 2 1.99 2 2-.9 2-2-.9-2-2-2z" />
</svg> <c:if
					test="${not empty sessionScope.carrinho && fn:length(sessionScope.carrinho) > 0}">
					<span class="cart-badge">
						${fn:length(sessionScope.carrinho)} </span>
				</c:if>
			</a>
		</c:if>
 
		<c:choose>
			<c:when test="${not empty sessionScope.usuarioLogado}">
 
				<span
					style="font-size: 0.9rem; color: var(--marrom); margin-right: 5px;">
					Olá, <strong> <c:choose>
							<c:when test="${sessionScope.tipoUsuario == 'fornecedor'}">
                                ${sessionScope.usuarioLogado.nomeLoja}
</c:when>
							<c:otherwise>
                                ${sessionScope.usuarioLogado.nome}
</c:otherwise>
						</c:choose>
				</strong>!
				</span>
 
				<c:if test="${sessionScope.tipoUsuario == 'cliente'}">
					<a href="${baseURL}/meus-pedidos"
						class="btn-border btn-border-azul">Meus Pedidos</a>
				</c:if>
				<c:if test="${sessionScope.tipoUsuario == 'fornecedor'}">
					<a href="${baseURL}/meus-produtos">Meus Produtos</a>
					<a href="${baseURL}/minhas-vendas"
						class="btn-border btn-border-verde">Vendas</a>
					<a href="${baseURL}/minha-carteira"
						class="btn-border btn-border-roxo">Carteira</a>
				</c:if>
				<c:if test="${sessionScope.tipoUsuario == 'admin'}">
					<a href="${baseURL}/admin/verMensagens">Painel Admin</a>
				</c:if>
 
				<a href="${baseURL}/perfil.jsp">👤 Perfil</a>
				<a href="${baseURL}/logout" id="btnSair">Sair</a>
			</c:when>
			<c:otherwise>
				<a href="${baseURL}/como-vender.jsp">Como Vender</a>
				<a href="${baseURL}/login.jsp">Login</a>
			</c:otherwise>
		</c:choose>
	</nav>
</header>
 
<script>
	function toggleMenu() {
		var nav = document.getElementById("main-nav");
		nav.classList.toggle("show");
	}
</script>
 