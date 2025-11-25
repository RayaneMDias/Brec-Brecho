
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<c:set var="baseURL" value="${pageContext.request.contextPath}" />



<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Painel Admin — Gerenciar Usuários</title>


<link rel="stylesheet" href="${baseURL}/css/Styles.css">


</head>
<body>

	<jsp:include page="/partes/header.jsp" />

	<main>
		<section class="card" style="margin-bottom: 25px;">
			<h2>Gerenciar Usuários</h2>

			<c:if test="${not empty requestScope.mensagem}">
				<c:set var="tipoMsg"
					value="${requestScope.mensagem.startsWith('Erro') ? 'feedback-erro' : 'feedback-sucesso'}" />
				<p class="feedback-msg ${tipoMsg}">${requestScope.mensagem}</p>
			</c:if>

			<h3>Clientes</h3>
			<c:choose>
				<c:when test="${empty requestScope.listaClientes}">
					<p class="small">Nenhum cliente cadastrado.</p>
				</c:when>
				<c:otherwise>
					<table class="tabela-admin">
						<thead>
							<tr>
								<th>ID</th>
								<th>Nome</th>
								<th>Email</th>
								<th>CPF</th>
								<th>Cidade/Estado</th>
								<th class="col-acoes">Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cliente" items="${requestScope.listaClientes}">
								<tr>
									<td>${cliente.idCliente}</td>
									<td>${cliente.nome}</td>
									<td>${cliente.email}</td>
									<td>${cliente.cpf}</td>
									<td>${cliente.cidade}/ ${cliente.estado}</td>

								
									<td class="col-acoes"><a
										href="editarUsuario?type=cliente&id=${cliente.idCliente}"
										class="btn-acao"> ✏️ Editar </a> <a
										href="gerenciarUsuarios?acao=excluirCliente&id=${cliente.idCliente}"
										class="btn-acao btn-excluir-icone" title="Excluir"
										onclick="return confirm('ATENÇÃO:\\nTem certeza que deseja excluir este CLIENTE?\\n(Isso pode falhar se ele tiver pedidos registrados).')">
											🗑️ </a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>

			<h3>Fornecedores</h3>
			<c:choose>
				<c:when test="${empty requestScope.listaFornecedores}">
					<p class="small">Nenhum fornecedor cadastrado.</p>
				</c:when>
				<c:otherwise>
					<table class="tabela-admin">
						<thead>
							<tr>
								<th>ID</th>
								<th>Nome Loja</th>
								<th>Email</th>
								<th>CPF/CNPJ</th>
								<th>Telefone</th>
								<th class="col-acoes">Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="f" items="${requestScope.listaFornecedores}">
								<tr>
									<td>${f.idFornecedor}</td>
									<td>${f.nomeLoja}</td>
									<td>${f.email}</td>
									<td>${f.cpfCnpj}</td>
									<td>${f.telefone}</td>

									
									<td class="col-acoes"><a
										href="editarUsuario?type=fornecedor&id=${f.idFornecedor}"
										class="btn-acao"> ✏️ Editar </a> <a
										href="gerenciarUsuarios?acao=excluirFornecedor&id=${f.idFornecedor}"
										class="btn-acao btn-excluir-icone" title="Excluir"
										onclick="return confirm('ATENÇÃO:\\nTem certeza que deseja excluir este FORNECEDOR?\\n(Todos os produtos cadastrados por ele serão excluídos JUNTOS).')">
											🗑️ </a></td>
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