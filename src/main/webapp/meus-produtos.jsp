<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<fmt:setLocale value="pt_BR"/>
 
<c:if test="${empty sessionScope.usuarioLogado || sessionScope.tipoUsuario != 'fornecedor'}">
    <% response.sendRedirect(request.getContextPath() + "/login.jsp?msg=acesso_negado"); %>
</c:if>
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Meus Produtos — Brec Brechó</title>
    
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
    
</head>
<body>
 
    <jsp:include page="/partes/header.jsp" />
 
    <main>
        <section class="card">
            <h2>Meus Produtos</h2>
 
            <c:if test="${param.msg == 'salvo_sucesso'}">
                <p class="feedback-msg feedback-sucesso">Produto salvo com sucesso!</p>
            </c:if>
            <c:if test="${param.msg == 'excluido_sucesso'}">
                <p class="feedback-msg feedback-sucesso">Produto excluído com sucesso!</p>
            </c:if>
            <c:if test="${param.msg == 'erro_excluir' || param.msg == 'erro_carregar' || param.msg == 'erro_permissao'}">
                <p class="feedback-msg feedback-erro">Ocorreu um erro ou você não tem permissão para esta ação.</p>
            </c:if>
 
            <div style="margin-bottom: 1rem;">
                <a href="${baseURL}/produto?acao=novo" class="btn-principal">
                    + Cadastrar Novo Produto
                </a>
            </div>
 
            <c:choose>
                <c:when test="${empty requestScope.listaMeusProdutos}">
                    <p class="small">Você ainda não cadastrou nenhum produto.</p>
                </c:when>
                
                <c:otherwise>
 
                    <table class="tabela-admin">
                        <thead>
                            <tr>
                                <th>Foto</th>
                                <th>Nome</th>
                                <th>Preço</th>
                                <th>Estoque</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="produto" items="${requestScope.listaMeusProdutos}">
                                <tr>
                                    <td><img src="${produto.fotoBase64}" alt="${produto.nome}"></td>
                                    <td>${produto.nome}</td>
                                    <td>
                                        <fmt:formatNumber value="${produto.preco}" type="currency" />
                                    </td>
                                    <td>${produto.estoque}</td>
                                    
                                    <td class="col-acoes">
                                        <a href="${baseURL}/produto?acao=carregar&id=${produto.idProduto}" class="btn-acao">
                                            ✏️ Editar
                                        </a>
                                        <a href="${baseURL}/produto?acao=excluir&id=${produto.idProduto}"
                                           class="btn-acao btn-excluir-icone"
                                           title="Excluir"
                                           onclick="return confirm('Tem certeza que deseja excluir este produto? Esta ação não pode ser desfeita.')">
                                            🗑️
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
 
        </section>
    </main>
 
    <jsp:include page="/partes/footer.jsp" />
 
</body>
</html>
