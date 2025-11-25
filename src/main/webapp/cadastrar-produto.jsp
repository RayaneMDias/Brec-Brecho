<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<c:if test="${empty sessionScope.usuarioLogado || sessionScope.tipoUsuario != 'fornecedor'}">
   
    <% response.sendRedirect("login.jsp?msg=acesso_negado"); %>
</c:if>
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Cadastro de Produto — Brec Brechó</title>
    <link rel="stylesheet" href="css/Styles.css">
</head>
<body>
 
    <jsp:include page="partes/header.jsp" />
 
    <main>
 
        <c:set var="editMode" value="${not empty requestScope.produtoParaEditar}" />
        <c:set var="produto" value="${requestScope.produtoParaEditar}" />
 
        <form id="form-produto" class="formulario" action="produto" method="POST" enctype="multipart/form-data">
 
            <h2>${editMode ? 'Editar Produto' : 'Cadastro de Produto'}</h2>
 
            <c:if test="${param.msg == 'erro_salvar'}">
                <p style="color:red; background:#ffe0e0; padding: 10px; border-radius: 5px;">
                    Erro ao salvar o produto. Verifique os campos e tente novamente.
                </p>
            </c:if>
 
            <c:if test="${editMode}">
                <input type="hidden" name="idProduto" value="${produto.idProduto}">
            </c:if>
 
            <label for="nomeProduto">Nome do Produto</label>
            <input type="text" id="nomeProduto" name="nomeProduto" value="${produto.nome}" required>
 
            <label for="descricaoProduto">Descrição</label>
            <textarea id="descricaoProduto" name="descricaoProduto" rows="3">${produto.descricao}</textarea>
 
            <label for="tamanhoProduto">Tamanho</label>
            <input type="text" id="tamanhoProduto" name="tamanhoProduto" value="${produto.tamanho}" required>
 
            <label for="fotoProduto">Foto</label>
 
            <c:if test="${editMode && not empty produto.fotoBase64}">
                <div style="margin-bottom: 10px;">
                    <img src="${produto.fotoBase64}" alt="Foto Atual" style="width: 100px; height: 100px; object-fit: cover; border-radius: 8px;">
                    <br><small>Foto atual. Envie uma nova apenas se desejar substituí-la.</small>
                </div>
            </c:if>
 
            <input type="file" id="fotoProduto" name="fotoProduto" accept="image/*" ${editMode ? '' : 'required'}>
 
            <label for="quantidadeProduto">Estoque</label>
            <input type="number" id="quantidadeProduto" name="quantidadeProduto" value="${produto.estoque}" required>
 
            <label for="precoProduto">Preço</label>
            <input type="number" id="precoProduto" name="precoProduto" value="${produto.preco}" step="0.01" required>
            
            <div style="font-size: 0.9rem; color: #333; margin: -10px 0 15px 0; padding: 8px; background-color: #f4f4f4; border-radius: 4px;">
    Taxa da plataforma (30%): <strong><span id="taxaValor">R$ 0,00</span></strong>
    <br>
    Você receberá: <strong><span id="valorReceber">R$ 0,00</span></strong>
</div>
 
 
            <label for="prazoProduto">Prazo de locação</label>
            <select id="prazoProduto" name="prazoProduto" required>
                <option value="">Selecione...</option>
                <option value="30" ${produto.prazoLocacaoDias == 30 ? 'selected' : ''}>30 dias</option>
                <option value="60" ${produto.prazoLocacaoDias == 60 ? 'selected' : ''}>60 dias</option>
            </select>
 
            <button type="submit" class="btn-principal btn-full-width">Salvar Produto</button>
        </form>
    </main>
 
    <jsp:include page="partes/footer.jsp" />
 
</body>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const inputPreco = document.getElementById("precoProduto");
        const spanTaxa = document.getElementById("taxaValor");
        const spanReceber = document.getElementById("valorReceber");
        const TAXA_PERCENTUAL = 0.18;
 
        function calcularComissao() {
            let preco = parseFloat(inputPreco.value) || 0;
            let taxa = preco * TAXA_PERCENTUAL;
            let recebido = preco - taxa;
 
            spanTaxa.textContent = taxa.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
            spanReceber.textContent = recebido.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        }
 
        inputPreco.addEventListener("input", calcularComissao);
 
        calcularComissao();
    });
</script>
</html>