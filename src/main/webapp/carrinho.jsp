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
    <title>Brec Brechó — Carrinho</title>
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
</head>
<body>
    <jsp:include page="/partes/header.jsp" />
 
    <main>
        <section id="carrinho" class="card">
            <h2>Carrinho</h2>
            <c:if test="${not empty sessionScope.msg_carrinho}">
                <p class="feedback-msg feedback-erro">
                    ${sessionScope.msg_carrinho}
                </p>
                <c:remove var="msg_carrinho" scope="session" />
            </c:if>
 
            <c:choose>
                <c:when test="${empty sessionScope.carrinho}">
                    <p class="small">Seu carrinho está vazio.</p>
                </c:when>
                <c:otherwise>
                    <div class="carrinho-layout">
 
                        <div class="carrinho-lista">
                            <c:set var="subtotal" value="0" />
                            <c:forEach var="entry" items="${sessionScope.carrinho}">
                                <c:set var="item" value="${entry.value}" />
                                
                                <div class="item-carrinho">
                                    <div class="item-coluna-esquerda">
                                        <img src="${item.produto.fotoBase64}" alt="${item.produto.nome}">
                                        <div class="quantity-controls">
                                            <a href="carrinho?acao=diminuir&id=${item.produto.idProduto}" class="btn-qty" title="Diminuir">-</a>
                                            <span>${item.quantidade}</span>
                                            <a href="carrinho?acao=aumentar&id=${item.produto.idProduto}" class="btn-qty" title="Aumentar">+</a>
                                        </div>
                                    </div>
                                    <div class="item-coluna-info">
                                        <h3>${item.produto.nome}</h3>
                                        <p><strong>Tamanho:</strong> ${item.produto.tamanho}</p>
                                        <p><strong>Preço:</strong>
                                            <fmt:formatNumber value="${item.precoUnitarioVenda}" type="currency" />
                                        </p>
                                    </div>
                                    <a href="carrinho?acao=remover&id=${item.produto.idProduto}" class="btn-acao btn-excluir-icone" title="Remover">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                                            <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z" />
                                        </svg>
                                    </a>
                                </div>
                                <c:set var="subtotal" value="${subtotal + (item.quantidade * item.precoUnitarioVenda)}" />
                            </c:forEach>
                        </div>
 
                        <div class="carrinho-resumo">
                            <h3>Resumo da Compra</h3>
                            <c:set var="frete" value="15.00" />
                            <c:set var="total" value="${subtotal + frete}" />
                            
                            <form action="carrinho" method="POST" id="form-finalizar">
                                <input type="hidden" name="acao" value="finalizar">
 
                                <div class="carrinho-resumo-linha">
                                    <span>Subtotal:</span>
                                    <span><fmt:formatNumber value="${subtotal}" type="currency" /></span>
                                </div>
                                <div class="carrinho-resumo-linha">
                                    <span>Frete:</span>
                                    <span><fmt:formatNumber value="${frete}" type="currency" /></span>
                                </div>
                                <div class="carrinho-resumo-linha total">
                                    <span>Total:</span>
                                    <span><fmt:formatNumber value="${total}" type="currency" /></span>
                                </div>
                                <span id="total-carrinho-span" data-total-valor="${total}" style="display: none;"></span>
 
                                <hr style="margin: 1rem 0;">
 
                                <label for="formaPagamento" style="font-weight:bold; margin-bottom: 5px; display:block;">Forma de Pagamento:</label>
                                <select id="formaPagamento" name="formaPagamento" style="width: 100%; padding: 0.5rem;">
                                    <option value="pix">PIX (10% desconto)</option>
                                    <option value="boleto">Boleto (10% desconto)</option>
                                    <option value="cartao">Cartão de Crédito</option>
                                </select>
                                <div id="parcelamento" style="margin-top: .5rem; display: none;"></div>
 
                                <div style="margin-top: 1.5rem; display:flex; flex-direction: column; gap:.5rem">
                                    <button id="btnFinalizarCompra" type="submit" class="btn-principal btn-full-width">Finalizar Compra</button>
                                    <a href="carrinho?acao=esvaziar" id="btnEsvaziarCarrinho" class="btn btn-full-width">Esvaziar</a>
                                </div>
                            </form>
                        </div>
                        
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
    </main>
 
    <jsp:include page="/partes/footer.jsp" />
 
    <script>
        document.getElementById("formaPagamento").addEventListener("change", () => {
            const forma = document.getElementById("formaPagamento").value;
            const divParcelamento = document.getElementById("parcelamento");
            const totalSpan = document.getElementById("total-carrinho-span");
            if (!totalSpan) return;
            const total = parseFloat(totalSpan.getAttribute('data-total-valor'));
 
            if (forma === "cartao") {
                divParcelamento.style.display = "block";
                let opcoes = "<strong>Parcelamento:</strong><br>";
                const maxParcelas = 6;
                let valido = false;
                for (let i = 1; i <= maxParcelas; i++) {
                    const valorParcela = total / i;
                    if (i === 1 || valorParcela >= 30) {
                        valido = true;
                        opcoes += `<input type="radio" name="parcelas" value="\${i}" \${i === 1 ? 'checked' : ''}> \${i}x de R$ \${valorParcela.toFixed(2)}<br>`;
                    }
                }
                if (!valido) {
                    opcoes = "Somente 1x disponível (valor da parcela mínimo R$30).";
                }
                divParcelamento.innerHTML = opcoes;
            } else {
                divParcelamento.style.display = "none";
            }
        });
        document.getElementById("formaPagamento").dispatchEvent(new Event('change'));
    </script>
</body>
</html>
