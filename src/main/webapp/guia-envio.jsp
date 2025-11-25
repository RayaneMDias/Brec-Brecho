<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<c:if test="${empty sessionScope.usuarioLogado}">
    <% response.sendRedirect("login.jsp"); %>
</c:if>
 
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Guia de Envio - Pedido #${dadosPedido.idPedido}</title>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ccc;
            margin: 0;
            padding: 20px;
        }
  
        .folha-etiqueta {
            background-color: white;
            width: 210mm;
            min-height: 297mm;
            margin: 0 auto;
            padding: 15mm;
            box-sizing: border-box;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            position: relative;
        }
        
        .caixa-etiqueta {
            border: 2px dashed #000;
            padding: 20px;
            margin-bottom: 40px;
            border-radius: 10px;
        }
        
        h1 {
            text-align: center;
            text-transform: uppercase;
            border-bottom: 2px solid #000;
            padding-bottom: 10px;
            margin-top: 0;
        }
        
        .secao {
            margin-bottom: 20px;
        }
        
        .secao h3 {
            background-color: #000;
            color: #fff;
            padding: 5px 10px;
            margin-bottom: 10px;
            display: inline-block;
            text-transform: uppercase;
            font-size: 0.9rem;
        }
        
        .endereco p {
            margin: 5px 0;
            font-size: 1.1rem;
        }
        
        .grande {
            font-size: 1.4rem;
            font-weight: bold;
        }
        
        .info-pedido {
            margin-top: 50px;
            border-top: 1px solid #ccc;
            padding-top: 20px;
            font-size: 0.8rem;
            color: #555;
        }
 
        .btn-print {
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 15px 30px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 1rem;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
        .btn-voltar {
            position: fixed;
            top: 80px;
            right: 20px;
            padding: 10px 20px;
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-family: sans-serif;
            font-size: 0.9rem;
        }
 
        @media print {
            body {
                background-color: white;
                padding: 0;
            }
            .folha-etiqueta {
                box-shadow: none;
                width: 100%;
                margin: 0;
            }
            .btn-print, .btn-voltar {
                display: none;
            }
        }
    </style>
</head>
<body>
 
    <button class="btn-print" onclick="window.print()">🖨️ Imprimir Etiqueta</button>
    <a href="minhas-vendas" class="btn-voltar">Voltar</a>
 
    <div class="folha-etiqueta">
        
        <div class="caixa-etiqueta">
            <h1>Destinatário</h1>
            
            <div class="secao endereco">
                <p class="grande">${dadosDestinatario.nome}</p>
                <p>${dadosDestinatario.rua}, ${dadosDestinatario.numero}</p>
                <p>${dadosDestinatario.bairro}</p>
                <p>${dadosDestinatario.cidade} - ${dadosDestinatario.estado}</p>
                <br>
                <p><strong>CEP: ${dadosDestinatario.cep}</strong></p>
            </div>
        </div>
 
        <div class="caixa-etiqueta" style="border-style: solid; border-width: 1px;">
            <div class="secao">
                <h3>Remetente</h3>
                <div class="endereco" style="font-size: 0.9rem;">
                    <p><strong>${dadosRemetente.nomeLoja}</strong></p>
                    <p>${dadosRemetente.rua}, ${dadosRemetente.numero} - ${dadosRemetente.bairro}</p>
                    <p>${dadosRemetente.cidade} - ${dadosRemetente.estado}</p>
                    <p>CEP: ${dadosRemetente.cep}</p>
                </div>
            </div>
        </div>
 
        <div class="info-pedido">
            <p><strong>Declaração de Conteúdo Simplificada</strong></p>
            <p>Pedido Brec Brechó: #${dadosPedido.idPedido}</p>
            <p>Data da Venda: ${dadosPedido.dataPedido}</p>
            <br>
            <p>Este pacote contém produtos de moda sustentável.</p>
        </div>
 
        <div style="text-align:center; margin-top: 50px;">
            <div class="logo" style="font-weight:800; font-size: 2rem;">BB</div>
            <div>Brec Brechó</div>
        </div>
 
    </div>
 
</body>
</html>
Oracle Java Technologies | Oracle
Java can help reduce costs, drive innovation, & improve application services; the #1 programming language for IoT, enterprise architecture, and cloud computing.
 