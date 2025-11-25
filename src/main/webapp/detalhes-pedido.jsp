<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 
<fmt:setLocale value="pt_BR" />
 
<c:set var="pedido" value="${requestScope.pedidoDetalhado}" />
<c:set var="cliente" value="${requestScope.clientePedido}" />
 
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Comprovante - Pedido #${pedido.idPedido}</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #eee;
	margin: 0;
	padding: 20px;
}
 
.folha-recibo {
	background-color: white;
	width: 700px;
	min-height: 800px;
	margin: 0 auto;
	padding: 40px;
	box-sizing: border-box;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
 
.header-recibo {
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 2px solid #000;
	padding-bottom: 10px;
}
 
.header-recibo .logo-recibo {
	font-weight: 800;
	font-size: 1.5rem;
}
 
.header-recibo .info-pedido {
	text-align: right;
}
 
.detalhes-cliente {
	margin-top: 20px;
	padding: 15px;
	background-color: #f9f9f9;
	border: 1px solid #eee;
	border-radius: 5px;
}
 
.tabela-itens {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}
 
.tabela-itens th, .tabela-itens td {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: left;
}
 
.tabela-itens th {
	background-color: #f2f2f2;
}
 
.tabela-itens .col-valor {
	text-align: right;
	width: 120px;
}
 
.resumo-total {
	margin-top: 20px;
	padding-top: 10px;
	border-top: 1px dashed #ccc;
	width: 40%;
	margin-left: 60%;
	font-size: 1.1rem;
}
 
.resumo-total table {
	width: 100%;
}
 
.resumo-total td {
	padding: 5px;
}
 
.resumo-total .total-final {
	font-weight: bold;
	font-size: 1.2rem;
	border-top: 1px solid #000;
}
 
.botoes-acao {
	position: fixed;
	top: 20px;
	right: 20px;
	text-align: right;
}
 
.botoes-acao button, .botoes-acao a {
	padding: 15px 30px;
	background-color: #007bff;
	color: white;
	border: none;
	cursor: pointer;
	font-size: 1rem;
	border-radius: 5px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
	text-decoration: none;
	font-family: Arial, sans-serif;
	display: block;
}
 
.botoes-acao a {
	background-color: #6c757d;
	margin-top: 10px;
	font-size: 0.9rem;
	padding: 10px 20px;
}
 
@media print {
	body {
		background-color: white;
		padding: 0;
	}
	.folha-recibo {
		box-shadow: none;
		width: 100%;
		margin: 0;
		padding: 0;
	}
	.botoes-acao {
		display: none;
	}
}
</style>
</head>
<body>
 
	<div class="botoes-acao">
		<button class="btn-print" onclick="window.print()">🖨️
			Imprimir Comprovante</button>
		<a href="meus-pedidos">Voltar para Meus Pedidos</a>
	</div>
 
	<div class="folha-recibo">
		<div class="header-recibo">
			<div class="logo-recibo">Brec Brechó</div>
			<div class="info-pedido">
				<strong>Comprovante de Pedido</strong><br> Pedido
				#${pedido.idPedido}<br> Data:
				<fmt:formatDate value="${pedido.dataPedido}" pattern="dd/MM/yyyy" />
			</div>
		</div>
 
		<div class="detalhes-cliente">
			<strong>Cliente:</strong> ${cliente.nome}<br> <strong>Email:</strong>
			${cliente.email}<br> <strong>Endereço de Entrega:</strong>
			${cliente.rua}, ${cliente.numero} - ${cliente.bairro},
			${cliente.cidade} - ${cliente.estado} (CEP: ${cliente.cep})
		</div>
 
		<h3>Itens Comprados</h3>
		<table class="tabela-itens">
			<thead>
				<tr>
					<th>Produto</th>
					<th class="col-valor">Qtd.</th>
					<th class="col-valor">Preço Unit.</th>
					<th class="col-valor">Subtotal</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="subtotalItens" value="0" />
				<c:forEach var="item" items="${pedido.itens}">
					<tr>
						<td>${item.produto.nome}</td>
						<td class="col-valor">${item.quantidade}</td>
						<td class="col-valor"><fmt:formatNumber
								value="${item.precoUnitarioVenda}" type="currency" /></td>
						<td class="col-valor"><fmt:formatNumber
								value="${item.precoUnitarioVenda * item.quantidade}"
								type="currency" /></td>
					</tr>
					<c:set var="subtotalItens"
						value="${subtotalItens + (item.quantidade * item.precoUnitarioVenda)}" />
				</c:forEach>
			</tbody>
		</table>
 
		<div class="resumo-total">
			<table>
				<tr>
					<td>Subtotal (Itens):</td>
					<td class="col-valor"><fmt:formatNumber
							value="${subtotalItens}" type="currency" /></td>
				</tr>
				<tr>
					<td>Frete:</td>
					<td class="col-valor"><fmt:formatNumber value="15.00"
							type="currency" /></td>
				</tr>
				<tr>
					<td class="total-final">Valor Total:</td>
					<td class="col-valor total-final"><fmt:formatNumber
							value="${pedido.valorTotal}" type="currency" /></td>
				</tr>
			</table>
		</div>
		<div
			style="text-align: center; margin-top: 40px; font-size: 0.9rem; color: #777;">
			<p>Obrigado por comprar no Brec Brechó!</p>
			<p>Forma de Pagamento: ${pedido.formaPagamento}</p>
		</div>
 
	</div>
 
</body>
</html>
