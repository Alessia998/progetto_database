--crea custode
create user srfndr98p02g393p with password 'srf';
grant usage on schema public to srfndr98p02g393p;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to srfndr98p02g393p;
grant execute on all functions in schema public to srfndr98p02g393p;

create user srfndr98p02g395p with password 'srf';
grant usage on schema public to srfndr98p02g395p;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to srfndr98p02g395p;
grant execute on all functions in schema public to srfndr98p02g395p;

create user ppltcu05p04d494f with password 'ppl';
grant usage on schema public to ppltcu05p04d494f;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to ppltcu05p04d494f;
grant execute on all functions in schema public to ppltcu05p04d494f;

create user frtocu56p07d662f with password 'frt';
grant usage on schema public to frtocu56p07d662f;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to frtocu56p07d662f;
grant execute on all functions in schema public to frtocu56p07d662f;

--crea fattorino
create user frccrl98p02g394p with password 'frc';
grant usage on schema public to frccrl98p02g394p;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to frccrl98p02g394p;
grant all on spedizione, trasferimenti to frccrl98p02g394p;
grant execute on all functions in schema public to frccrl98p02g394p;

create user frrgvn98p02g395p with password 'frr';
grant usage on schema public to frrgvn98p02g395p;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to frrgvn98p02g395p;
grant all on spedizione, trasferimenti to frrgvn98p02g395p;
grant execute on all functions in schema public to frrgvn98p02g395p;

create user bnccrl05p04d494f with password 'bnc';
grant usage on schema public to bnccrl05p04d494f;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to bnccrl05p04d494f;
grant all on spedizione, trasferimenti to bnccrl05p04d494f;
grant execute on all functions in schema public to bnccrl05p04d494f;

create user vrdfdr56p07d662f with password 'vrd';
grant usage on schema public to vrdfdr56p07d662f;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to vrdfdr56p07d662f;
grant all on spedizione, trasferimenti to vrdfdr56p07d662f;
grant execute on all functions in schema public to vrdfdr56p07d662f;

create user grbmra56p07d662f with password 'grb';
grant usage on schema public to grbmra56p07d662f;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user to grbmra56p07d662f;
grant all on spedizione, trasferimenti to grbmra56p07d662f;
grant execute on all functions in schema public to grbmra56p07d662f;


--crea dirigente
create user srfcrl98p02g394p with password 'srf' createrole;
grant usage on schema public to srfcrl98p02g394p with grant option;
grant all on all tables in schema public to srfcrl98p02g394p with grant option;
grant all on my_seq1, my_seq2 to srfcrl98p02g394p with grant option;
grant execute on all functions in schema public to srfcrl98p02g394p with grant option;

create user mntfrc98p02g395p with password 'mnt' createrole;
grant usage on schema public to mntfrc98p02g395p with grant option;
grant all on all tables in schema public to mntfrc98p02g395p with grant option;
grant all on my_seq1, my_seq2 to mntfrc98p02g395p with grant option;
grant execute on all functions in schema public to mntfrc98p02g395p with grant option;

create user rsscrl05p04d494f with password 'rss' createrole;
grant usage on schema public to rsscrl05p04d494f with grant option;
grant all on all tables in schema public to rsscrl05p04d494f with grant option;
grant all on my_seq1, my_seq2 to rsscrl05p04d494f with grant option;
grant execute on all functions in schema public to rsscrl05p04d494f with grant option;

create user spsfdr56p07d662f with password 'sps' createrole;
grant usage on schema public to spsfdr56p07d662f with grant option;
grant all on all tables in schema public to spsfdr56p07d662f with grant option;
grant all on my_seq1, my_seq2 to spsfdr56p07d662f with grant option;
grant execute on all functions in schema public to spsfdr56p07d662f with grant option;

create user gllmra56p07d662f with password 'gll' createrole;
grant usage on schema public to gllmra56p07d662f with grant option;
grant all on all tables in schema public to gllmra56p07d662f with grant option;
grant all on my_seq1, my_seq2 to gllmra56p07d662f with grant option;
grant execute on all functions in schema public to gllmra56p07d662f with grant option;

--crea cliente
create user srdvrl900p02p39f with password 'srd';
grant usage on schema public to srdvrl900p02p39f;
grant select on contratto, prodotto, spazio_contratto, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to srdvrl900p02p39f;
grant all on spedizione, prod_sped, my_seq1, contiene to srdvrl900p02p39f;
grant execute on all functions in schema public to srdvrl900p02p39f;

create user sfdvrl900p03p39f with password 'sfd';
grant usage on schema public to sfdvrl900p03p39f;
grant select on contratto, prodotto, spazio_contratto, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to sfdvrl900p03p39f;
grant all on spedizione, prod_sped, my_seq1, contiene to sfdvrl900p03p39f;
grant execute on all functions in schema public to sfdvrl900p03p39f;

create user srdvrl900p32p39f with password 'srd';
grant usage on schema public to srdvrl900p32p39f;
grant select on contratto, prodotto, spazio_contratto, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to srdvrl900p32p39f;
grant all on spedizione, prod_sped, my_seq1, contiene to srdvrl900p32p39f;
grant execute on all functions in schema public to srdvrl900p32p39f;

create user sslvrl900p02p39f with password 'ssl';
grant usage on schema public to sslvrl900p02p39f;
grant select on contratto, prodotto, spazio_contratto, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to sslvrl900p02p39f;
grant all on spedizione, prod_sped, my_seq1, contiene to sslvrl900p02p39f;
grant execute on all functions in schema public to sslvrl900p02p39f;

create user mrdmss900p02p39f with password 'mrd';
grant usage on schema public to mrdmss900p02p39f;
grant select on contratto, prodotto, spazio_contratto, dirigente, custode, impiegato, fattorino, cliente, magazziniere, pg_user, veicolo, magazzino, filiale, spazio to mrdmss900p02p39f;
grant all on spedizione, prod_sped, my_seq1, contiene to mrdmss900p02p39f;
grant execute on all functions in schema public to mrdmss900p02p39f;

--crea impiegato
create user sfdvrl910p03p39f with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39f with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39f with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39f with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39f with grant option;

create user sfdvrl910p03p39g with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39g with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39g with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39g with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39g with grant option;

create user sfdvrl910p03p39h with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39h with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39h with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39h with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39h with grant option;

create user sfdvrl910p03p39i with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39i with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39i with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39i with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39i with grant option;

create user sfdvrl910p03p39k with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39k with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39k with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39k with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39k with grant option;

create user sfdvrl910p03p39l with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39l with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39l with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39l with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39l with grant option;

create user sfdvrl910p03p39m with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39m with grant option;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione, pg_user, veicolo, spazio_contratto to sfdvrl910p03p39m with grant option;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2, spedizione, my_seq1 to sfdvrl910p03p39m with grant option;
grant execute on all functions in schema public to sfdvrl910p03p39m with grant option;

--create magazziniere
create user bldmsm910p03p39f with password 'bld';
grant usage on schema public to bldmsm910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to bldmsm910p03p39f;
grant all on contiene to bldmsm910p03p39f;
grant execute on all functions in schema public to bldmsm910p03p39f;

create user tnolcu910p03p39f with password 'tno';
grant usage on schema public to tnolcu910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to tnolcu910p03p39f;
grant all on contiene to tnolcu910p03p39f;
grant execute on all functions in schema public to tnolcu910p03p39f;

create user rzzrcc910p03p39f with password 'rzz';
grant usage on schema public to rzzrcc910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to rzzrcc910p03p39f;
grant all on contiene to rzzrcc910p03p39f;
grant execute on all functions in schema public to rzzrcc910p03p39f;

create user dfrcrt910p03p39f with password 'dfr';
grant usage on schema public to dfrcrt910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to dfrcrt910p03p39f;
grant all on contiene to dfrcrt910p03p39f;
grant execute on all functions in schema public to dfrcrt910p03p39f;

create user lmbfrr910p03p39f with password 'lmb';
grant usage on schema public to lmbfrr910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to lmbfrr910p03p39f;
grant all on contiene to lmbfrr910p03p39f;
grant execute on all functions in schema public to lmbfrr910p03p39f;

create user frrnz910p03p39f with password 'frr';
grant usage on schema public to frrnz910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to frrnz910p03p39f;
grant all on contiene to frrnz910p03p39f;
grant execute on all functions in schema public to frrnz910p03p39f;

create user tmblbr910p03p39f with password 'tmb';
grant usage on schema public to tmblbr910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, pg_user, filiale, spazio_contratto to tmblbr910p03p39f;
grant all on contiene to tmblbr910p03p39f;
grant execute on all functions in schema public to tmblbr910p03p39f;
