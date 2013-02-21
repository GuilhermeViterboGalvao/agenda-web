create trigger DeleteTelefoneEmail before delete on Contato
for each row begin 
	delete from Telefone where cdContato = old.cdContato;
	delete from Email where cdContato = old.cdContato;
end