<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Como Vender — Brec Brechó</title>
    <link rel="stylesheet" href="css/Styles.css">
    
    <style>
        .conteudo-institucional {
            max-width: 800px;
            margin: 0 auto;
        }
        .conteudo-institucional h3 {
            margin-top: 2rem;
            border-bottom: 2px solid #f0f0f0;
            padding-bottom: 5px;
        }
        .conteudo-institucional li {
            margin-bottom: 10px;
            line-height: 1.6;
        }
    </style>
</head>
<body>
 
    <jsp:include page="partes/header.jsp" />
 
    <main>
        <section class="card conteudo-institucional">
            <h2>Quer Vender Suas Peças?</h2>
            <p class="small">Veja como é fácil transformar suas roupas em renda extra e promover a moda circular!</p>
 
            <h3>Como Funciona o Brec Brechó?</h3>
            <p>
                Nosso site funciona como um marketplace (um shopping virtual) focado em moda sustentável. Nós conectamos você, que tem peças incríveis paradas no armário, com pessoas que procuram por achados únicos. Você cadastra, nós cuidamos da venda e do pagamento!
            </p>
 
            <h3>Passo a Passo Para Ser um Vendedor</h3>
            <ol>
                <li>
                    <strong>Crie sua Conta:</strong> Vá até a página de <a href="fornecedor.jsp">Cadastro de Fornecedor</a>. Você pode se cadastrar tanto com CPF quanto com CNPJ.
                </li>
                <li>
                    <strong>Cadastre Seus Produtos:</strong> Após o login, acesse "Meus Produtos" no menu e clique em "Cadastrar Novo Produto". Tire boas fotos, descreva a peça com carinho, defina o tamanho e o preço.
                </li>
                <li>
                    <strong>Escolha o Prazo:</strong> Seu produto ficará disponível em nosso catálogo por 30 ou 60 dias.
                </li>
                <li>
                    <strong>Aguarde a Venda:</strong> Nós cuidamos de tudo! Quando um cliente comprar seu produto, você será notificado.
                </li>
            </ol>
 
            <h3>Quanto Custa? (Nossas Taxas)</h3>
            <p>
                A transparência é nosso maior valor. Manter o site funcionando (servidores, sistema de pagamento, marketing) tem um custo.
            </p>
            <ul>
                <li>
                    <strong>Comissão Única:</strong> Cobramos uma taxa fixa de <strong>30% sobre o valor de cada venda</strong>.
                </li>
                <li>
                    <strong>Exemplo:</strong> Se você cadastrar um vestido por R$ 100,00, a taxa será de R$ 30,00. Você receberá <strong>R$ 70,00</strong> pela venda. Você pode ver esse cálculo ao vivo quando cadastra seu produto!
                </li>
                <li>
                    <strong>Frete:</strong> O valor do frete pago pelo cliente (R$ 15,00) é repassado a você para cobrir seus custos de envio.
                </li>
            </ul>
 
            <h3>Como Funciona o Envio?</h3>
            <p>
                A logística é simples:
            </p>
            <ol>
                <li>Após a venda, você receberá os dados do comprador.</li>
                <li>Acesse a venda no seu painel e imprima a "Guia de Envio" (com os dados de remetente e destinatário).</li>
                <li>Embrulhe o produto com carinho, cole a guia na caixa e poste nos Correios ou transportadora de sua preferência.</li>
            </ol>
 
            <h3>Pronto para começar?</h3>
            <div style="text-align:center; margin-top: 2rem;">
                <a href="fornecedor.jsp">
                    <button class="btn" style="padding: 15px 30px; font-size: 1rem;">Quero ser um Fornecedor!</button>
                </a>
            </div>
            
        </section>
    </main>
 
    <jsp:include page="partes/footer.jsp" />
 
</body>
</html>
