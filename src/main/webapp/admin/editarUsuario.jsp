<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<c:set var="usuario" value="${requestScope.usuarioParaEditar}" />
<c:set var="tipo" value="${requestScope.tipoUsuario}" />
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Admin: Editar Usuário</title>
<link rel="stylesheet" href="${baseURL}/css/Styles.css">
</head>
<body>
 
	<jsp:include page="/partes/header.jsp" />
 
	<main>
		<div class="card"
			style="margin-bottom: 25px; background-color: #f9f9f9;">
			<%@ include file="nav-admin.jspf"%>
		</div>
		<form id="form-admin-edit" class="formulario" action="editarUsuario"
			method="POST">
			<h2>Admin: Editar ${tipo == 'cliente' ? 'Cliente' : 'Fornecedor'}
				(ID: ${tipo == 'cliente' ? usuario.idCliente : usuario.idFornecedor})
			</h2>
			<input type="hidden" name="id" value="${tipo == 'cliente' ? usuario.idCliente : usuario.idFornecedor}">
			<input type="hidden" name="type" value="${tipo}">
 
			<label for="nome">Nome</label>
			<input type="text" id="nome" name="nome" value="${tipo == 'fornecedor' ? usuario.nomeLoja : usuario.nome}" required>
			<label for="email">Email</label>
			<input type="email" id="email" name="email" value="${usuario.email}" required>
 
			<c:if test="${tipo == 'cliente'}">
				<label for="cpf">CPF</label>
				<input type="text" id="cpf" name="cpf" value="${usuario.cpf}" required>
			</c:if>
			<c:if test="${tipo == 'fornecedor'}">
				<label for="cpfCnpj">CPF/CNPJ</label>
				<input type="text" id="cpfCnpj" name="cpfCnpj" value="${usuario.cpfCnpj}" required>
			</c:if>
 
			<label for="cep">CEP</label>
			<input type="text" id="cep" name="cep" value="${usuario.cep}" required>
			<label for="rua">Rua</label>
			<input type="text" id="rua" name="rua" value="${usuario.rua}" required>
			<label for="numero">Número</label>
			<input type="text" id="numero" name="numero" value="${usuario.numero}" required>
			<label for="bairro">Bairro</label>
			<input type="text" id="bairro" name="bairro" value="${usuario.bairro}" required>
			<label for="cidade">Cidade</label>
			<input type="text" id="cidade" name="cidade" value="${usuario.cidade}" required>
			<label for="estado">Estado</label>
			<input type="text" id="estado" name="estado" value="${usuario.estado}" required>
 
			<c:if test="${tipo == 'fornecedor'}">
				<div id="campo-telefone">
					<label for="telefone">Telefone</label>
					<input type="text" id="telefone" name="telefone" value="${usuario.telefone}">
				</div>
				<div id="campo-descricao">
					<label for="descricao">Descrição</label>
					<textarea id="descricao" name="descricao" rows="3">${usuario.descricao}</textarea>
				</div>
			</c:if>
 
			<label for="senha">Senha (Hash Criptografado)</label>
			<input type="text" id="senha" name="senha" value="${usuario.senha}"
				disabled> <small style="color: gray;"> A senha é
				criptografada. Para resetar a senha, uma nova funcionalidade é
				necessária. </small>
 
			<button type="submit" class="btn-principal btn-full-width">Salvar
				Alterações</button>
 
			<a href="gerenciarUsuarios" class="btn"
				style="text-align: center; display: block; margin-top: 1rem;">Cancelar</a>
		</form>
	</main>
 
	<jsp:include page="/partes/footer.jsp" />
 
</body>
</html>
