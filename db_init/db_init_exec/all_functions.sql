/*questo file contiene tutte le funzioni necessarie che devono essere eseguite dopo insert scripts.
Funzioni:
insert_magazzino()
registra un nuovo magazzino

insert_spazio()
registra un nuovo spazio all'interno del magazzino

spedisci()
gestisce la spedizione

assegna_spazio()
assegna lo spazio dato in input al contratto dato in input

calcola_piano()
quando si crea un nuovo spazio viene chiamata questa funzione
che calcola il piano tariffario

esiste_cliente()
funzione che restituisce vero se il cf esiste nella tabella
falso altrimneti

nuovo_contratto()
crea un nuovo contratto

crea_cliente()
crea un nuovo cliente

trasferisci()
esegue il trasferimento di un prodotto da un magazzino all'altro
Nota: la funzione non registra in automatico i prodotti nel secondo magazzino.

elimina_contiene()
controlla i dati in input ed elimina il collegamento spazio - prodotto

who_is()
dato un username restituisce che ruolo ha nel sistema

insert_contiene()
inserisce o aggiorna un prodotto in uno spazio

*/



---dichiarazione della funzione insert_magazzino
--funzione che crea un nuovo magazzino. input: denominazione, città, via, numero, telefono, codice filiale
create or replace function insert_magazzino(denominazione char(255),citta char(255),via char(255),numero int ,tel varchar(30),cod_f varchar(10))
returns int as $$
declare
  num_magazzino int;

begin
  --cerco il numero progressivo all'interno del codice della filiale $6
  select count (cod)+1 into num_magazzino from magazzino where cod = $6;

  --registra un nuovo Magazzino
  insert into magazzino(num,denominazione,citta,via,numero,tel,cod)
    values(num_magazzino, $1, $2, $3, $4, $5, $6);

	return null ;
END
$$
LANGUAGE plpgsql;

---fine funzione

--dichiarazione della funzione insert_spazio
--funzione che crea un nuovo spazio. input: numero del magazzino, codice della filiale, descrizione dello spazio
create or replace function insert_spazio(numero_magazzino int, codice_filiale varchar(16) ,  descr varchar(255))
returns int as $$
declare
  num_spazio int;

begin
  --cerco il numero progressivo all'interno del magazzino dato in input
  select count (cod)+1 into num_spazio from spazio where num=$1 and cod=$2 ;

  --registro un nuovo spazio
  insert into spazio(id_spazio,num,cod,descrizione)
    values(num_spazio, $1, $2, $3);

	return null ;
END
$$
LANGUAGE plpgsql;

--fine funzione

--dichiarazione della funzione spedizione
--gestisce la spedizione
--input : codice fiscale del cliente, data della spedizione, targa del veicolo usato, paese , città, via e numero di dove va spedito il prodotto, telefono , codice del cliente che richiede la spedizione, la quantità spedita, il codice del prodotto, il codice della filiale, il numero del magazzino e l'ID dello spazio
create or replace function spedisci(cf varchar(16), data_sp date, targa varchar(20), paese varchar(255), citta varchar(255), via varchar(255), numero varchar(10), tel varchar(30), codice_cli varchar(16), quantita int, codice_prodotto varchar(255), fil varchar(10), mag int, spa int)
returns int as $$
declare
 codice_fiscale varchar(16);
 posseduto int;
 contenuto int;
 codice_cliente alias for $9;
 q alias for $10;
 cont int;
 num_sp int;
begin

	--verifico se il cliente ha lo spazio indicato
	select cliente.cf_cli into codice_fiscale from spazio_contratto, contratto, cliente
    where spazio_contratto.num_c = contratto.num_c and contratto.cf_cli = cliente.cf_cli
      and spazio_contratto.id_spazio = spa and spazio_contratto.num = mag and spazio_contratto.cod = fil;

	if codice_fiscale != codice_cli
		then
			raise exception 'Non corrisponde codice cliente con lo spazio';
	 end if;

	--inizializzo la quantità del prodotto x contenuta nello spazio posseduto dal cliente in input
	select contiene.quantita into contenuto from contiene
    where id_spazio = spa and num = mag and cod = fil
      and codice = codice_prodotto;

	--controllo se la quantità richiesta per la spedizione del prodotto x è sufficiente
	if contenuto < quantita
	 then
	  raise EXCEPTION  'La quantità non è sufficiente';
	end if;

	--aggiorno la quantità che è contenuta nello spazio
	update contiene set quantita = contiene.quantita - q
    where id_spazio = spa and num = mag and cod = fil
      and contiene.codice = codice_prodotto;

	select count (*) into cont from spedizione where spedizione.data_sp = $2 and cf_cli = codice_cliente
	and spedizione.targa = $3 and spedizione.paese = $4 and spedizione.citta = $5 and spedizione.via = $6 and spedizione.numero = $7;

	if cont != 1
	   then
	     --inserisco un nuovo record nella "spedizione"
		insert into spedizione(cf, data_sp, targa, paese, citta, via, numero, tel, stato_consegna, cf_cli) values
		(cf, data_sp, targa, paese, citta, via, numero, tel, 'In consegna', codice_cliente);
    end if;

	--cerco qual è il numero spedizione
	select num_sped into num_sp from spedizione where spedizione.data_sp = $2 and cf_cli = codice_cliente
	and spedizione.targa = $3 and spedizione.paese = $4 and spedizione.citta = $5 and spedizione.via = $6 and spedizione.numero = $7;

	--vedo se esiste già la spedizione con lo stesso codice del prodotto, codice fiscale, data e numero spedizione
	select count (*) into cont from prod_sped where prod_sped.num_sped = num_sp and prod_sped.codice = codice_prodotto;

	if cont = 1
	   then
	      update prod_sped set quantita = prod_sped.quantita + q where prod_sped.num_sped = num_sp and prod_sped.codice = codice_prodotto;
	  else

	--inserisco un nuovo record nella "prod_sped"
	insert into prod_sped(codice, num_sped, quantita)
	values (codice_prodotto, num_sp, q);

	end if;
	return null ;
END
$$
LANGUAGE plpgsql;

--esempio : select spedisci ('FRRGVN98P02G395P','2019-01-01','CD456EF','Georgia','Bologna','Via del Chionso','13','3965226884','MRDMSS900P02P39F',20,'ruondpqytb','0000000001',1,4);

--dichiarazione dellla funzione assegna_spazio
--la funzione assegna lo spazio dato in input al contratto dato in input
--input: codice della filiale, numero del magazzino, ID dello spazio e numero del contratto
create or replace function assegna_spazio(fil varchar(10), mag int, spa int, contr varchar(255))
returns int as $$
declare
	q int;
	acq int;
	cont int;
begin

	--verifico se lo spazio è libero
	select count (*) into cont from spazio_contratto
	where cod = fil and mag = num and spa = id_spazio;

	if cont > 0
	then
		raise exception 'lo spazio non è libero';
	end if;

	--conto quanti spazi sono già assegnati al contratto
	select count (*) into q from spazio_contratto, contratto, spazio
     where contratto.num_c = contr and contratto.num_c = spazio_contratto.num_c and
		spazio_contratto.id_spazio = spazio.id_spazio and spazio_contratto.cod = spazio.cod and spazio.num = spazio_contratto.num;

   --conto quanti spazi sono stati acquistati nel contratto
  select sum (num_spazi) into acq from contratto where num_c = contr;

   if acq - q < 1
     then
	    raise exception 'il contratto ha esaurito gli spazi';
	end if;

   insert into spazio_contratto values(spa,mag,fil,contr);

return null;
end
$$
language plpgsql;

--dichiarazione della funzione calcola_piano
--la funzione calcola il piano tariffario del cliente con il codice fiscale preso in input
create or replace function calcola_piano(cf varchar(16))
returns varchar(255) as $$
declare
  gg int;
  rec record;
  roof int;
begin
	roof =0;

	 FOR rec IN select data_fine, data_inizio, num_spazi from contratto where cf_cli = $1
    LOOP
		SELECT (DATE_PART('year', rec.data_fine::date) - DATE_PART('year', rec.data_inizio::date)) * 12 +
              (DATE_PART('month', rec.data_fine::date) - DATE_PART('month', rec.data_inizio::date)) into gg;

		roof = roof + gg * rec.num_spazi;
    END LOOP;

	if roof < 100
	   then
	    	return 'Standard';
	end if;
    if roof < 500
	     then
	    	return 'Gold';
	   else
	   		return 'Top';
	 end if;

	 return null ;
END
$$
LANGUAGE plpgsql;

--funzione che restituisce vero se il cf esiste nella tabella
--falso altrimneti
create or replace function esiste_cliente(cf text)
returns bool as $func$
declare
_ text;
BEGIN

select cf_cli into _ from cliente
where cf_cli = $1;
IF found
  THEN
    return true;
end if;

return false;
END
$func$
LANGUAGE plpgsql;

--dichiarazione della funzione nuovo_contratto
--crea un nuovo contratto. input : numero del contratto, data di inizio del contratto, data di fine del contratto, condice fiscale del cliente e codice fiscsale dell'impiegato che registra il contratto
create or replace function nuovo_contratto(num_c varchar(255), datai date, dataf date, num_spazi int, cf varchar(255), cf_cli varchar(255))
returns int as $$
declare
	piano varchar(255);
begin
	if (esiste_cliente($6))
		then
			insert into contratto values ($1, $2, $3, $4, $5, $6);
		else
			raise exception 'Il codice fiscale inserito non esiste nel DB dei clienti';
	end if;

	select calcola_piano(cf_cli) into piano;

	update cliente set nome_piano = piano where cliente.cf_cli = $6;

	return null;
end
$$
language plpgsql;

--dichiarazione della funzione crea_cliente
--crea un nuovo cliente, input : codice fiscale, nome, cognome e telefono
create or replace function crea_cliente(cf varchar(16), nome varchar(255), cognome varchar(255), tel varchar(30))
returns int as $$
declare
	cf1 varchar(16);
begin
	--cerco se esiste già il cliente
	select cf_cli into cf1 from cliente where cliente.cf_cli = $1 ;

	if cf1 = $1
		then
		   raise exception 'Il cliente esiste già';
	end if;


	--registro il nuovo cliente
	insert into cliente values($1, $2, $3, $4, 'Standard');


	return null;
end
$$
language plpgsql;

--dichiarazione della funzione trasferisci
--gestisce il trasferimento
--input : codice del cliente, data della spedizione, codice fiscale del fattorino che eseguisce il trasferimento, targa del veicolo utilizzato, paese, numero del magazzino di partenza, codice della filiale di partenza, numero del magazzino di arrivo, codice della filiale di arrivo, codice del prodotto spedito e quantità spedita
create or replace function trasferisci(cf varchar(16), data_spedizione date, fattorino varchar(16), targa varchar(20), paese varchar(255), n1 int, c1 varchar(255),n2 int, c2 varchar(255),codice_prodotto varchar(255), q int)
returns int as $$
declare
paese_f varchar(255);
citta_f varchar(255);
via_f varchar(255);
numero_f varchar(10);
tel_f varchar(30);
numero_spedizione int;

ordine int;
cont int; --quantità contenuta in uno spazio
manda int;
indir int;


BEGIN


--controllo la data
if data_spedizione < current_date
  then
  raise exception 'La data non valida';
end if;

--controllo che il prodotto non sia mandato nello stesso magazzino
if c1 = c2 and n1 = n2
   then
      raise exception 'Non si può mandare nello stesso magazzino';
end if;

-- paese default Italia
if paese = ''
  then
    paese = 'Italia';
  else
    paese_f = paese;
end if;

--ricavo le informazioni da c1,n1

select citta into citta_f from magazzino
where num = n1 and cod = c1;

select via into via_f from magazzino
where num = n1 and cod = c1;

select numero into numero_f from magazzino
where num = n1 and cod = c1;

select tel into tel_f from magazzino
where num = n1 and cod = c1;

ordine = q;
manda =0;

select co.quantita into cont from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr
where pr.codice = co.codice and
      sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = $10 and
	  sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c
	  and contr.cf_cli = $1 and sp.num = n1 and sp.cod = c1 and co.quantita > 0
order by co.quantita desc
limit 1;

IF NOT FOUND THEN
    RAISE EXCEPTION 'Il prodotto % non trovato.', $10;
END IF;

if cont < 1
	then
		raise exception 'Quantità non sufficiente nel magazzino';
	else
		   LOOP
      		EXIT WHEN ordine <= 0 ;


			select co.quantita into cont from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr
			where pr.codice = co.codice and
				  sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = $10 and
				  sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c
				  and contr.cf_cli = $1 and sp.num = n1 and sp.cod = c1 and co.quantita > 0
			order by co.quantita desc
			limit 1;
			IF NOT FOUND THEN
  			  exit;
			END IF;

			select sp.id_spazio into indir from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr
			where pr.codice = co.codice and
				  sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = $10 and
				  sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c
				  and contr.cf_cli = $1 and sp.num = n1 and sp.cod = c1 and co.quantita > 0
			order by co.quantita desc
			limit 1;

			if ordine >= cont
				then
					update contiene
					set quantita = quantita - cont
					where cod = c1 and num = n1 and id_spazio = indir;

					manda = manda + cont;
					ordine = ordine - cont;

				else
					update contiene
					set quantita = quantita - ordine
					where cod = c1 and num = n1 and id_spazio = indir;

					manda = manda + ordine;
					ordine =0;

			end if;

   		   END LOOP ;
end if;




--inserisco un nuovo trasferimento
select num_sped into numero_spedizione from trasferimenti
where data_sp = data_spedizione and trasferimenti.cf = fattorino and stato_consegna = 'In consegna'
	and num1 = n1 and num2 = n2 and c1 = cod1 and c2 = cod2;
IF NOT FOUND THEN
    	insert into trasferimenti(data_sp,cf,targa,stato_consegna,num1,cod1,num2,cod2)
		values (data_spedizione,fattorino,$4,'In consegna',n1,c1,n2,c2);

		insert into prod_trasf(codice,num_sped,quantita)
		values(codice_prodotto,(select num_sped from trasferimenti
		order by num_sped desc
		limit 1),manda);
		ELSE
			update prod_trasf
			set quantita = quantita + manda
			where num_sped = numero_spedizione;
END IF;


return null ;
END
$$
LANGUAGE plpgsql;

--dichiarazione della funzione elimina_contiene
--controlla i dati in input ed elimina il collegamento spazio - prodotto
--input : ID dello spazio, numero del magazzino, codice della filiale, codice del prodotto e quantità
create or replace function elimina_contiene(spa int, mag int, fil varchar(10),codice_prodotto varchar(255), q int)
returns int as $$
declare
quant int;

begin

	select quantita into quant from contiene
	where contiene.id_spazio = spa and contiene.num = mag and contiene.cod = fil and contiene.codice = codice_prodotto;
	if not found then
		raise exception 'Hai inserito i dati sbagliati.';
	end if;

	if quant < q
	    then
			raise exception 'Quantità non sufficiente.';
		else
			update contiene
			set quantita = quantita - q
			where contiene.id_spazio = spa and contiene.num = mag and contiene.cod = fil and contiene.codice = codice_prodotto;
	end if;

return null;
end
$$
language plpgsql;

--dichiarazione della funzione who_is
--dato un username restituisce che ruolo ha nel sistema
create or replace function who_is(un varchar(255))
returns text as $$
declare
cat text;
usr text;

begin

--admin
if (select usesuper from pg_user where usename = un)
then
	cat = 'admin';
	return cat;
end if;

--dirigente
select cf into cat from dirigente
where cf = un;
if found then
	cat = 'dirigente';
	return cat;
end if;

--custode
select cf into cat from custode
where cf = un;
if found then
	cat = 'custode';
	return cat;
end if;

--impiegato
select cf into cat from impiegato
where cf = un;
if found then
	cat = 'impiegato';
	return cat;
end if;

--fattorino
select cf into cat from fattorino
where cf = un;
if found then
	cat = 'fattorino';
	return cat;
end if;

--cliente
select cf_cli into cat from cliente
where cf_cli = un;
if found then
	cat = 'cliente';
	return cat;
end if;

--magazziniere
select cf into cat from magazziniere
where cf = un;
if found then
	cat = 'magazziniere';
	return cat;
end if;

return 'nan';
end
$$
language plpgsql;

--La procedura inserisce o aggiorna il prodotto in uno spazio_contratto
--input: id_spazio, magazzino, filiale, codice prodotto, quantità
--NOTA: per chiamare la procedura usa CALL <nome()>
CREATE OR REPLACE PROCEDURE insert_contiene(int, int, varchar(10), varchar(255), int)
LANGUAGE plpgsql
AS $$
declare
_ text;
BEGIN
	select codice into _ from contiene
	where contiene.id_spazio = $1 and
		  contiene.num = $2 and
		  contiene.cod = $3 and contiene.codice = $4;
	if not found --non questo tipo di prodotto in questo spazio
		then
			--inserisco
			insert into contiene values($1,$2,$3,$4,$5);
			commit;
			return;
	end if;

	--Il prodotto è già presente allora aggiungo
	update contiene set quantita = quantita + $5
	where contiene.id_spazio = $1 and
		  contiene.num = $2 and
		  contiene.cod = $3 and contiene.codice = $4;

    COMMIT;
END;
$$;

--useful code
--prodotti posseduti da un cliente
/*select prodotto.codice from contratto, spazio_contratto sp_co, spazio, contiene, prodotto
	where cf_cli = '<cf_cli>' and
	sp_co.num_c = contratto.num_c and
	spazio.cod = sp_co.cod and
	spazio.num = sp_co.num and
	spazio.id_spazio = sp_co.id_spazio and
	spazio.cod = contiene.cod and
	spazio.num = contiene.num and
	spazio.id_spazio = contiene.id_spazio and
	contiene.codice = prodotto.codice;
*/

--trigger function: elimina record dal contiene dove trova quantità = 0
CREATE OR REPLACE FUNCTION contiene_zero()
  RETURNS trigger AS $$
BEGIN

   delete from contiene where quantita = 0;

   RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

--trigger che chiama contiene_zero() dopo ogni update su "contiene"
CREATE TRIGGER contiene_zero_trig
  AFTER UPDATE
  ON contiene
  FOR EACH ROW
  EXECUTE PROCEDURE contiene_zero();
