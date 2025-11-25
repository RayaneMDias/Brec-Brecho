<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<footer>
    <div class="footer-container">
 
        <div class="footer-grid">
 
            <div class="footer-coluna">
                <h3>Categorias</h3>
                <ul>
                    <li><a href="${baseURL}/catalogo?busca=vestido">Vestidos</a></li>
                    <li><a href="${baseURL}/catalogo?busca=blusa">Blusas</a></li>
                    <li><a href="${baseURL}/catalogo?busca=calca">Calças</a></li>
                    <li><a href="${baseURL}/catalogo?busca=bolsa">Bolsas</a></li>
                    <li><a href="${baseURL}/catalogo?busca=jaqueta">Jaquetas</a></li>
                </ul>
            </div>
 
            <div class="footer-coluna">
                <h3>Destaques</h3>
                <ul>
                    <li><a href="${baseURL}/catalogo?busca=promocao">Promoções</a></li>
                    <li><a href="${baseURL}/catalogo?busca=novidade">Novidades</a></li>
                    <li><a href="${baseURL}/catalogo?busca=mais-vendidos">Mais vendidos</a></li>
                </ul>
            </div>
 
            <div class="footer-coluna">
                <h3>Utilidades</h3>
                <ul>
                    <li><a href="${baseURL}/como-vender.jsp">Como vender</a></li>
                    <li><a href="${baseURL}/contato.jsp">Fale Conosco</a></li>
                    <li><a href="#">Segurança</a></li>
                    <li><a href="#">Política de Privacidade</a></li>
                    <li><a href="#">Termos de uso</a></li>
                </ul>
            </div>
 
            <div class="footer-coluna">
                <h3>Minha Conta</h3>
                <ul>
                    <li><a href="${baseURL}/login.jsp">Fazer Login</a></li>
                    <li><a href="${baseURL}/cliente.jsp">Cadastrar Cliente</a></li>
                    <li><a href="${baseURL}/fornecedor.jsp">Cadastrar Fornecedor</a></li>
                    <li><a href="${baseURL}/meus-pedidos.jsp">Meus Pedidos</a></li>
                    <li><a href="${baseURL}/minha-carteira.jsp">Minha Carteira</a></li>
                </ul>
            </div>
 
            <div class="footer-coluna">
                <h3>Siga a gente</h3>
                <ul class="social-links">
                    <li><a href="#" target="_blank">Instagram</a></li>
                    <li><a href="#" target="_blank">TikTok</a></li>
                    <li><a href="#" target="_blank">Facebook</a></li>
                    <li><a href="#" target="_blank">Twitter</a></li>
                </ul>
            </div>
            
        </div>
 
        <div class="footer-copyright">
            <p>
                © <%= java.time.Year.now().getValue() %> Brec Brechó — Feito com propósito
            </p>
            <p>
                <a href="https://wa.me/5547996654584" target="_blank">Atendimento via WhatsApp</a>
                |
                <a href="${baseURL}/contato.jsp">Fale Conosco</a>
            </p>
            <p style="margin-top: 10px;">
                <a href="#topo">Voltar ao topo</a>
            </p>
        </div>
        
    </div>
</footer>
 