<%@page contentType="text/xml" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<c:choose>
	<c:when test="${not empty telefone}">
		<app>
			<telefone idTipoTelefone="${telefone.cdTipo}" numero="${telefone.nmTelefone}">
				<contato id="${telefone.contato.cdContato}" caminhoFoto="${telefone.contato.nmCaminhoFoto}">${telefone.contato.nmContato}</contato>
			</telefone>
		</app>
	</c:when>
	<c:otherwise>
		<app>
			<mensagem>${mensagem}</mensagem>
		</app>
	</c:otherwise>
</c:choose>