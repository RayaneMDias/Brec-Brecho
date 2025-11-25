
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="baseURL" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Painel Admin — Mensagens SAC</title>
    
   
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
    
   
    
</head>
<body>

    <jsp:include page="/partes/header.jsp" />

    <main>
        <section class="card" style="margin-bottom: 25px;">
            <h2>Painel Admin: Mensagens do SAC</h2>
            <p class="small">Mensagens enviadas pelo formulário "Fale Conosco".</p>

            <c:choose>
                <c:when test="${empty requestScope.listaMensagens}">
                    <p>Nenhuma mensagem nova encontrada.</p>
                </c:when>
                <c:otherwise>
                    <table class="tabela-mensagens">
                        <thead>
                            <tr>
                                <th class="col-data">Data</th>
                                <th class="col-nome">De</th>
                                <th>Mensagem</th>
                                <th class="col-acoes">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="msg" items="${requestScope.listaMensagens}">
                                <tr class="${msg.lida ? 'lida' : 'nao-lida'}">
                                    <td>
                                        <fmt:formatDate value="${msg.dataEnvio}" 
                                                        pattern="dd/MM/yyyy 'às' HH:mm" />
                                    </td>
                                    <td>
                                        <strong>${msg.nome}</strong><br>
                                        <small>${msg.email}</small>
                                    </td>
                                    <td>${msg.mensagem}</td>
                                    <td>
                                      
                                        <c:if test="${not msg.lida}">
                                            <a href="verMensagens?acao=marcarLida&id=${msg.idMensagem}" class="btn">
                                                Marcar como Lida
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            
        </section>

        
        <section class="card" style="background-color: #f9f9f9;">
            <%@ include file="nav-admin.jspf" %>
        </section>

    </main>

    <jsp:include page="/partes/footer.jsp" />

</body>
</html>