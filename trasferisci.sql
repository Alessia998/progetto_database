create or replace function trasferisci(cf varchar(16), data_spedizione date, fattorino varchar(16), targa varchar(20), n1 int, c1 varchar(255),n2 int, c2 varchar(255),codice_prodotto varchar(255), q int)
returns int as $$
declare
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

ordine = q;
manda =0;

--seleziono il massimo della quantità che possiede un
select max(co.quantita) into cont from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr
where pr.codice = co.codice and
      sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = $9 and
	  sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c
	  and contr.cf_cli = $1 and sp.num = n1 and sp.cod = c1;

IF NOT FOUND THEN
    RAISE EXCEPTION 'Il prodotto % non trovato.', $9;
END IF;

if cont < 1
	then
		raise exception 'Quantità non sufficiente nel magazzino';
	else
		   LOOP
      		EXIT WHEN ordine <= 0 ;


			select max(co.quantita) into cont from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr
			where pr.codice = co.codice and
				  sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = $9 and
				  sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c
				  and contr.cf_cli = $1 and sp.num = n1 and sp.cod = c1;

			IF NOT FOUND THEN
  			  exit;
			END IF;

			select sp.id_spazio into indir from prodotto pr, contiene co, spazio sp, spazio_contratto spc, contratto contr
			where pr.codice = co.codice and
				  sp.cod = co.cod and sp.num = co.num and sp.id_spazio = co.id_spazio and co.codice = $9 and
				  sp.cod = spc.cod and sp.num = spc.num and sp.id_spazio = spc.id_spazio and spc.num_c = contr.num_c
				  and contr.cf_cli = $1 and sp.num = n1 and sp.cod = c1
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
