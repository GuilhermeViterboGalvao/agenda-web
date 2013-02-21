create trigger DeleteContatoTelefoneEmail before delete on Usuario
for each row begin 
	delete from Contato where nmLogin = old.nmLogin;
	set @id = (select cdContato from Contato where nmLogin = old.nmLogin);
	delete from Telefone where cdContato = @id;
	delete from Email where cdContato = @id;
end