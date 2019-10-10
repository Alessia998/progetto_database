create user user_dirigente1 with password 'pw1';
grant all on database progetto to user_dirigente1;

---custode
--GRANT USAGE ON SCHEMA public TO '${user}';
GRANT SELECT ON turno to '${user}';

---magazziniere
--GRANT USAGE ON SCHEMA public TO '${user}';
GRANT SELECT ON magazzino, spazio, prodotto, contiene TO '${user}';

---imiegato
--GRANT USAGE ON SCHEMA public TO '${user}';
GRANT SELECT ON magazzino, spazio, prodotto, turno, filiale, trasferimenti, contratto, cliente, piano, contiene, spedizione, veicolo, prod_sped TO '${user}';

grant all on turno, contratto, cliente, veicolo to '${user}';


--fattorino
GRANT SELECT ON spedizione, veicolo, prod_sped TO '${user}';

---cliente
GRANT SELECT ON contratto, prodotto, piano TO '${user}';
grant insert , select , update, delete on spedizione to '${user}';
