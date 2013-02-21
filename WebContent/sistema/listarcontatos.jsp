<%@page pageEncoding="UTF-8"%>
<%@page contentType="application/json"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:choose>
	<c:when test="${not empty contatos}">		 
		var listaDeContatos = {
			"qtContatosPagina":${qtContatosPagina},
			"paginaAtual":${pagina},
			"paginas":${paginas},
			"contatos":[
			    <c:forEach var="contato" items="${contatos}" varStatus="i">
			    	{
				    	"contato":{
				    		"cdContato":${contato.cdContato},
				    		"nmContato":"${contato.nmContato}",
				    		"nmCaminhoFoto":"${contato.nmCaminhoFoto}",
				    		"telefones":[
								<c:forEach var="telefone" items="${contato.telefones}" varStatus="j">
									{"nmTelefone":${telefone.nmTelefone}, "cdTipoTelefone":${telefone.cdTipo}}<c:if test="${j.count lt fn:length(contato.telefones)}">,</c:if>
								</c:forEach>
				    		],
				    		"emails": [
				    			<c:forEach var="email" items="${contato.emails}" varStatus="j">
				    				{"nmEmail":"${email.nmEmail}"}<c:if test="${j.count lt fn:length(contato.emails)}">,</c:if>
				    			</c:forEach>
				    		]
				    	}
			    	}<c:if test="${i.count lt fn:length(contatos)}">,</c:if>
				</c:forEach>              
			]
		}
	</c:when>
	<c:otherwise>
		"mensagem":"${mensagem}"
	</c:otherwise>
</c:choose>