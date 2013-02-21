<%@page contentType="text/xml" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<c:choose>
	<c:when test="${not empty email}">
		<app>
			<email email="${email.nmEmail}">
					<contato id="${email.contato.cdContato}" caminhoFoto="${email.contato.nmCaminhoFoto}">${email.contato.nmContato}</contato>
			</email>
		</app>
	</c:when>
	<c:otherwise>
		<app>
			<mensagem>${mensagem}</mensagem>
		</app>
	</c:otherwise>
</c:choose>