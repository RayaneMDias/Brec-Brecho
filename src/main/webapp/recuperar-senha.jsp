<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Brec Brechó — Recuperar Senha</title>
 
<link rel="stylesheet" href="${baseURL}/css/Styles.css">
 
</head>
<body>
 
	<jsp:include page="/partes/header.jsp" />
 
	<main>
		<section class="card formulario" style="max-width: 600px; margin: 2rem auto;">
			<h2>Redefinir Senha</h2>
 
			<c:if test="${param.erro == 'dados_invalidos'}">
				<p class="feedback-msg feedback-erro">Email ou CPF não encontrados. Verifique os dados.</p>
			</c:if>
			<c:if test="${param.erro == 'excecao'}">
				<p class="feedback-msg feedback-erro">Ocorreu um erro inesperado. Tente novamente.</p>
			</c:if>
 
			<form action="redefinirSenha" method="POST" class="form-recuperar">
 
				<label for="email">Seu Email cadastrado</label>
                <input id="email" name="email" type="email" required>
                
                <label for="cpf">Seu CPF cadastrado</label>
                <input id="cpf" name="cpf" type="text" required>
 
				<label for="novaSenha">Sua NOVA Senha</label>
				<div class="password-container">
					<input id="novaSenha" name="novaSenha" type="password"
                           placeholder="Mínimo 6 caracteres" required
                           autocomplete="new-password">
 
					<span class="password-icon"
						onclick="toggleSenha('novaSenha', 'icon-eye-nova', 'icon-eye-slash-nova')">
 
						<svg id="icon-eye-nova" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 24 24" fill="currentColor">
							<path
								d="M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5C21.27 7.61 17 4.5 12 4.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z" />
						</svg>
                        <svg id="icon-eye-slash-nova" style="display: none;"
							xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
							fill="currentColor">
							<path
								d="M12 7c2.76 0 5 2.24 5 5 0 .65-.13 1.26-.36 1.83l2.92 2.92c1.51-1.26 2.7-2.89 3.43-4.75-1.73-4.39-6-7.5-11-7.5-1.4 0-2.74.25-3.98.7l2.16 2.16C10.74 7.13 11.35 7 12 7zM2 4.27l2.28 2.28.46.46C3.08 8.3 1.78 9.93 1 12c1.73 4.39 6 7.5 11 7.5 1.55 0 3.03-.3 4.38-.84l.42.42L21.73 22 23 20.73 3.27 3 2 4.27zM7.53 9.8l1.55 1.55c-.05.21-.08.43-.08.65 0 1.66 1.34 3 3 3 .22 0 .44-.03.65-.08l1.55 1.55c-.67.33-1.41.53-2.2.53-2.76 0-5-2.24-5-5 0-.79.2-1.53.53-2.2zm4.31-.78l3.15 3.15.02-.16c0-1.66-1.34-3-3-3l-.17.01z" />
						</svg>
					</span>
				</div>
                
                <small style="color: gray; display: block; margin-bottom: 1rem;">
                    A senha deve conter pelo menos 6 caracteres, uma letra maiúscula, uma minúscula e um caractere especial.
                </small>
 
				<button class="btn-principal btn-full-width" type="submit">
                    Atualizar Minha Senha
                </button>
			</form>
		</section>
	</main>
 
	<jsp:include page="/partes/footer.jsp" />
 
	<script>
		function toggleSenha(inputId, iconEyeId, iconEyeSlashId) {
			var input = document.getElementById(inputId);
			var iconEye = document.getElementById(iconEyeId);
			var iconEyeSlash = document.getElementById(iconEyeSlashId);
 
			if (input.type === "password") {
				input.type = "text";
				iconEye.style.display = "none";
				iconEyeSlash.style.display = "inline";
			} else {
				input.type = "password";
				iconEye.style.display = "inline";
				iconEyeSlash.style.display = "none";
			}
		}
	</script>
</body>
</html>
