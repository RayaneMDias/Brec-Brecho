<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<fmt:setLocale value="pt_BR"/>
 
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Brec Brechó - Catálogo</title>
 
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
</head>
<body>
 
    <jsp:include page="/partes/header.jsp" />
 
    <main>
        <section class="card">
            <h2>Catálogo</h2>
 
            <form action="catalogo" method="GET" style="margin-bottom: 2rem; max-width: 500px;">
                <label for="busca" style="font-weight: 600;">Buscar por nome:</label>
                <div style="display: flex; gap: 10px;">
                    <input type="search" id="busca" name="busca"
                           value="${requestScope.termoBuscado}"
                           placeholder="Ex: Blusa, Calça, Vestido..."
                           style="flex: 1; padding: 0.8rem; font-size: 1rem;">
                           
                    <button type="submit" class="btn-principal" style="padding: 0 1.5rem;">Buscar</button>
                </div>
            </form>
 
            <c:if test="${param.msg == 'erro_estoque'}">
                 <p class="feedback-msg feedback-erro">
                    Produto indisponível ou sem estoque!
                </p>
            </c:if>
            <c:if test="${param.msg == 'erro_id'}">
                 <p class="feedback-msg feedback-erro">
                    Produto não encontrado.
                </p>
            </c:if>
            
            <div id="catalogoLista" class="catalogo-grid">
                <c:forEach var="produto" items="${requestScope.listaProdutos}">
                    <div class="produto-card">
                        
                        <img src="${produto.fotoBase64}" alt="${produto.nome}">
                        <h3>${produto.nome}</h3>
                        
                        <c:if test="${not empty produto.descricao}">
                            <p>${produto.descricao}</p>
                        </c:if>
                        
                        <p><strong>Tamanho:</strong> ${produto.tamanho}</p>
                        <p><strong>Preço:</strong>
                            <fmt:formatNumber value="${produto.preco}" type="currency" currencyCode="BRL" />
                        </p>
                        <p><strong>Estoque:</strong> ${produto.estoque}</p>
 
                        <c:choose>
                            <c:when test="${produto.estoque > 0}">
                                <p><strong>Status:</strong> Disponível</p>
 
                                <a href="${baseURL}/carrinho?acao=adicionar&id=${produto.idProduto}" class="btn-principal">
                                    Adicionar ao Carrinho
                                </a>
                            </c:when>
                            <c:otherwise>
                                <p><strong>Status:</strong> Indisponível</p>
 
                                <button class="btn-principal" disabled>Indisponível</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
                
                <c:if test="${empty requestScope.listaProdutos}">
                    <c:choose>
                        <c:when test="${not empty requestScope.termoBuscado}">
                            <p class="small" style="text-align:center; width:100%;">
                                Nenhum produto encontrado para a busca: "<strong><c:out value="${requestScope.termoBuscado}"/></strong>"
                            </p>
                            <p style="text-align:center; width:100%; margin-top: 10px;">
 
                                <a href="${baseURL}/catalogo" class="btn">Limpar Busca</a>
                            </p>
                        </c:when>
                        
                        <c:otherwise>
                            <p class="small" style="text-align:center; width:100%;">
                                Nenhum produto cadastrado no momento. Volte em breve!
                            </p>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </section>
    </main>
 
    <jsp:include page="/partes/footer.jsp" />
 
</body>
</html>
