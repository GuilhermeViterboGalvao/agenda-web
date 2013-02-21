<%@page contentType="text/xml" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><?xml version="1.0" encoding="UTF-8"?>
<c:choose>
	<c:when test="${not empty contato}">
		<app>
			<contato id="${contato.cdContato}" caminhoFoto="${contato.nmCaminhoFoto}">${contato.nmContato}</contato>
		</app>
	</c:when>
	<c:otherwise>
	<app>
		<mensagem>${mensagem}</mensagem>
	</app>
	</c:otherwise>
</c:choose>