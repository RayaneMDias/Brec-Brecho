<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
 
<!doctype html>
<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Brec Brechó — Cadastro Fornecedor</title>
    <link rel="stylesheet" href="${baseURL}/css/Styles.css">
</head>
<body>
 
    <jsp:include page="/partes/header.jsp" />
 
    <main>
        <c:if test="${param.msg == 'erro' || param.msg == 'erro_excecao'}">
            <p class="feedback-msg feedback-erro" style="max-width: 600px; margin: 1rem auto;">
                Ocorreu um erro ao tentar seu cadastro. Verifique seus dados (email ou CPF/CNPJ podem já existir) e tente novamente.
            </p>
        </c:if>
 
        <form id="form-fornecedor" class="formulario" action="cadastrarFornecedor" method="POST">
            <h2>Cadastro de Fornecedor</h2>
 
            <label for="nomeFornecedor">Nome / Loja</label>
            <input type="text" id="nomeFornecedor" name="nomeFornecedor" required>
 
            <label for="emailFornecedor">Email</label>
            <input type="email" id="emailFornecedor" name="emailFornecedor" required>
 
            <label for="cpfCnpjFornecedor">CPF/CNPJ</label>
            <input type="text" id="cpfCnpjFornecedor" name="cpfCnpjFornecedor" required>
 
            <label for="cepFornecedor">CEP</label>
            <input type="text" id="cepFornecedor" name="cepFornecedor" required>
 
            <label for="ruaFornecedor">Rua</label>
            <input type="text" id="ruaFornecedor" name="ruaFornecedor" required>
 
            <label for="numeroFornecedor">Número</label>
            <input type="text" id="numeroFornecedor" name="numeroFornecedor" required>
 
            <label for="bairroFornecedor">Bairro</label>
            <input type="text" id="bairroFornecedor" name="bairroFornecedor" required>
 
            <label for="cidadeFornecedor">Cidade</label>
            <input type="text" id="cidadeFornecedor" name="cidadeFornecedor" required>
 
            <label for="estadoFornecedor">Estado</label>
            <input type="text" id="estadoFornecedor" name="estadoFornecedor" required>
 
            <label for="telefoneFornecedor">Telefone</label>
            <input type="text" id="telefoneFornecedor" name="telefoneFornecedor">
 
            <label for="descricaoFornecedor">Descrição</label>
            <textarea id="descricaoFornecedor" name="descricaoFornecedor" rows="3"></textarea>
 
            <label for="senhaFornecedor">Senha</label>
            <div class="password-container">
	            <input type="password" id="senhaFornecedor" name="senhaFornecedor" placeholder="Mínimo 6 caracteres" required>
	            <span class="password-icon"
	                onclick="toggleSenha('senhaFornecedor', 'icon-eye-fornecedor', 'icon-eye-slash-fornecedor')">
	                <svg id="icon-eye-fornecedor" xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 24 24" fill="currentColor">
						<path
							d="M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5C21.27 7.61 17 4.5 12 4.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z" />
					</svg>
	                <svg id="icon-eye-slash-fornecedor" style="display: none;"
						xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
						fill="currentColor">
						<path
							d="M12 7c2.76 0 5 2.24 5 5 0 .65-.13 1.26-.36 1.83l2.92 2.92c1.51-1.26 2.7-2.89 3.43-4.75-1.73-4.39-6-7.5-11-7.5-1.4 0-2.74.25-3.98.7l2.16 2.16C10.74 7.13 11.35 7 12 7zM2 4.27l2.28 2.28.46.46C3.08 8.3 1.78 9.93 1 12c1.73 4.39 6 7.5 11 7.5 1.55 0 3.03-.3 4.38-.84l.42.42L21.73 22 23 20.73 3.27 3 2 4.27zM7.53 9.8l1.55 1.55c-.05.21-.08.43-.08.65 0 1.66 1.34 3 3 3 .22 0 .44-.03.65-.08l1.55 1.55c-.67.33-1.41.53-2.2.53-2.76 0-5-2.24-5-5 0-.79.2-1.53.53-2.2zm4.31-.78l3.15 3.15.02-.16c0-1.66-1.34-3-3-3l-.17.01z" />
					</svg>
	            </span>
            </div>
            <small style="color: gray;">A senha deve conter pelo menos 6 caracteres, uma letra maiúscula, uma minúscula e um caractere especial.</small>
 
            <button type="submit" class="btn-principal btn-full-width">Cadastrar</button>
        </form>
    </main>
 
    <jsp:include page="/partes/footer.jsp" />
 
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const inputCep = document.getElementById('cepFornecedor');
            const form = document.getElementById('form-fornecedor');
 
            if (inputCep) {
                inputCep.addEventListener('blur', () => {
                    let cep = inputCep.value.replace(/\D/g, '');
                    if (cep.length === 8) {
                        fetch('https://viacep.com.br/ws/' + cep + '/json/')
                            .then(res => res.json())
                            .then(data => {
                                if (!data.erro) {
                                    document.getElementById('ruaFornecedor').value = data.logradouro || "";
                                    document.getElementById('bairroFornecedor').value = data.bairro || "";
                                    document.getElementById('cidadeFornecedor').value = data.localidade || "";
                                    document.getElementById('estadoFornecedor').value = data.uf || "";
                                } else {
                                    alert("CEP não encontrado!");
                                }
                            })
                            .catch(err => console.error("Erro ao buscar CEP:", err));
                    }
                });
            }
 
            if(form) {
                form.addEventListener('submit', function (event) {
                    const senha = document.getElementById('senhaFornecedor').value;
                    const regexSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{6,}$/;
                    if (!regexSenha.test(senha)) {
                        event.preventDefault();
                        alert("A senha deve ter no mínimo 6 caracteres, incluindo letra maiúscula, letra minúscula e caractere especial.");
                    }
                });
            }
        });
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
Oracle Java Technologies | Oracle
Java can help reduce costs, drive innovation, & improve application services; the #1 programming language for IoT, enterprise architecture, and cloud computing.
 