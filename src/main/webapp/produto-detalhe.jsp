<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
<fmt:setLocale value="pt_BR"/>
 
<c:set var="produto" value="${requestScope.produto}" />
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    
    <title>${produto.nome} — Brec Brechó</title>
    
    <link rel="stylesheet" href="css/Styles.css">
    
    <style>
        .detalhe-produto-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 2rem;
            max-width: 1000px;
            margin: 0 auto;
        }
        
        .detalhe-imagem img {
            width: 100%;
            max-height: 500px;
            object-fit: cover;
            border-radius: 12px;
            border: 1px solid #eee;
        }
        
        .detalhe-info h2 {
            font-size: 2rem;
            color: var(--marrom-escuro);
            margin-top: 0;
            margin-bottom: 1rem;
        }
        
        .detalhe-info .preco {
            font-size: 2.2rem;
            font-weight: bold;
            color: var(--marrom);
            margin-bottom: 1.5rem;
        }
        
        .detalhe-info .descricao {
            font-size: 1rem;
            line-height: 1.6;
            color: #555;
            margin-bottom: 1.5rem;
        }
        
        .detalhe-info .info-extra {
            font-size: 0.9rem;
            color: #777;
            margin-bottom: 1.5rem;
        }
        
        .detalhe-info .btn-comprar {
            width: 100%;
            padding: 1rem;
            font-size: 1.2rem;
            font-weight: bold;
        }
 
        @media (max-width: 768px) {
            .detalhe-produto-grid {
                grid-template-columns: 1fr;
            }
            .detalhe-info h2 {
                font-size: 1.5rem;
            }
            .detalhe-info .preco {
                font-size: 1.8rem;
            }
        }
    </style>
</head>
<body>
 
    <jsp:include page="partes/header.jsp" />
 
    <main>
        <section class="card">
            
            <c:choose>
                <c:when test="${not empty produto}">
                    <div class="detalhe-produto-grid">
 
                        <div class="detalhe-imagem">
                            <img src="${produto.fotoBase64}" alt="${produto.nome}">
                        </div>
 
                        <div class="detalhe-info">
                            <h2>${produto.nome}</h2>
                            
                            <div class="preco">
                                <fmt:formatNumber value="${produto.preco}" type="currency" currencyCode="BRL" />
                            </div>
                            
                            <div class="descricao">
                                <p>${produto.descricao}</p>
                            </div>
                            
                            <div class="info-extra">
                                <p><strong>Tamanho:</strong> ${produto.tamanho}</p>
                                <p><strong>Estoque:</strong> ${produto.estoque}</p>
                            </div>
 
                            <c:choose>
                                <c:when test="${produto.estoque > 0}">
                                    <a href="carrinho?acao=adicionar&id=${produto.idProduto}" class="btn-link">
                                        <button class="btn btn-comprar">Adicionar ao Carrinho</button>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-comprar" disabled>Indisponível</button>
                                </c:otherwise>
                            </c:choose>
                            
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <h2 style="text-align: center;">Oops! Produto não encontrado.</h2>
                    <p style="text-align: center;">
                        <a href="catalogo">Voltar ao catálogo</a>
                    </p>
                </c:otherwise>
            </c:choose>
            
        </section>
    </main>
 
    <jsp:include page="partes/footer.jsp" />
 
</body>
</html>
