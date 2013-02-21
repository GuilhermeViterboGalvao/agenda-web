<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Enviar imagem</title>
	</head>
	<body>
		<form action="../uploadcontatofoto.do" method="post" enctype="multipart/form-data">
			<input type="hidden" value="2" name="id">
			<input name="imagem" type="file" accept="image/jpeg; image/gif; image/bmp; image/png" id="imagem" class="dados" maxlength="60" tabindex="1" value="/" /> 
			<input type="submit" value="enviar">
		</form>
	</body>
</html>