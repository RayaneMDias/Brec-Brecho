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
<title>Painel Admin — Gerenciar Produtos</title>
 
<link rel="stylesheet" href="${baseURL}/css/Styles.css">
 
</head>
<body>
 
	<jsp:include page="/partes/header.jsp" />
 
	<main>
		<section class="card" style="margin-bottom: 25px;">
			<h2>Gerenciar Produtos</h2>
			<p class="small">Modere todos os produtos cadastrados no site.</p>
 
			<c:if test="${not empty param.msg}">
				<c:set var="tipoMsg" value="feedback-erro" />
				<c:if
					test="${param.msg == 'salvo_sucesso' || param.msg == 'excluido_sucesso'}">
					<c:set var="tipoMsg" value="feedback-sucesso" />
				</c:if>
 
				<p class="feedback-msg ${tipoMsg}">
					<c:choose>
						<c:when test="${param.msg == 'salvo_sucesso'}">Produto salvo com sucesso!</c:when>
						<c:when test="${param.msg == 'excluido_sucesso'}">Produto excluído com sucesso!</c:when>
						<c:when test="${param.msg == 'admin_nao_cria'}">Administradores não podem criar produtos, apenas editá-los.</c:when>
						<c:when test="${param.msg == 'erro_permissao'}">Você não tem permissão para esta ação.</c:when>
						<c:otherwise>Ocorreu um erro na operação.</c:otherwise>
					</c:choose>
				</p>
			</c:if>
 
			<c:choose>
				<c:when test="${empty requestScope.listaProdutos}">
					<p class="small">Nenhum produto cadastrado no site.</p>
				</c:when>
				<c:otherwise>
					<table class="tabela-admin">
						<thead>
							<tr>
								<th class="col-img">Foto</th>
								<th>Nome</th>
								<th>Preço</th>
								<th>Estoque</th>
								<th>ID Fornec.</th>
								<th class="col-acoes">Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="produto" items="${requestScope.listaProdutos}">
								<tr>
									<td><img src="${produto.fotoBase64}" alt="${produto.nome}"></td>
									<td>${produto.nome}</td>
									<td><fmt:formatNumber value="${produto.preco}"
											type="currency" /></td>
									<td>${produto.estoque}</td>
									<td>${produto.idFornecedor}</td>
									<td class="col-acoes">
<a
										href="${baseURL}/produto?acao=carregar&id=${produto.idProduto}"
										class="btn-acao"> ✏️ Editar </a>
										<a
										href="${baseURL}/produto?acao=excluir&id=${produto.idProduto}"
										class="btn-acao btn-excluir-icone" title="Excluir"
										onclick="return confirm('Tem certeza que deseja excluir este produto? (ID: ${produto.idProduto})')">
											🗑️ </a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
