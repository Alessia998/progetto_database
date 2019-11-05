--crea custode
create user srfndr98p02g393p with password 'srf';
grant usage on schema public to srfndr98p02g393p;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere to srfndr98p02g393p;
grant execute on all functions in schema public to srfndr98p02g393p;

create user srfndr98p02g395p with password 'srf';
grant usage on schema public to srfndr98p02g395p;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere to srfndr98p02g395p;
grant execute on all functions in schema public to srfndr98p02g395p;

create user ppltcu05p04d494f with password 'ppl';
grant usage on schema public to ppltcu05p04d494f;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere to ppltcu05p04d494f;
grant execute on all functions in schema public to ppltcu05p04d494f;

create user frtocu56p07d662f with password 'frt';
grant usage on schema public to frtocu56p07d662f;
grant select on turno, dirigente, custode, impiegato, fattorino, cliente, magazziniere to frtocu56p07d662f;
grant execute on all functions in schema public to frtocu56p07d662f;

--crea fattorino
create user frccrl98p02g394p with password 'frc';
grant usage on schema public to frccrl98p02g394p;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere to frccrl98p02g394p;
grant all on spedizione, trasferimenti to frccrl98p02g394p;
grant execute on all functions in schema public to frccrl98p02g394p;

create user frrgvn98p02g395p with password 'frr';
grant usage on schema public to frrgvn98p02g395p;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere to frrgvn98p02g395p;
grant all on spedizione, trasferimenti to frrgvn98p02g395p;
grant execute on all functions in schema public to frrgvn98p02g395p;

create user bnccrl05p04d494f with password 'bnc';
grant usage on schema public to bnccrl05p04d494f;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere to bnccrl05p04d494f;
grant all on spedizione, trasferimenti to bnccrl05p04d494f;
grant execute on all functions in schema public to bnccrl05p04d494f;

create user vrdfdr56p07d662f with password 'vrd';
grant usage on schema public to vrdfdr56p07d662f;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere to vrdfdr56p07d662f;
grant all on spedizione, trasferimenti to vrdfdr56p07d662f;
grant execute on all functions in schema public to vrdfdr56p07d662f;

create user grbmra56p07d662f with password 'grb';
grant usage on schema public to grbmra56p07d662f;
grant select on prod_sped, prod_trasf, dirigente, custode, impiegato, fattorino, cliente, magazziniere to grbmra56p07d662f;
grant all on spedizione, trasferimenti to grbmra56p07d662f;
grant execute on all functions in schema public to grbmra56p07d662f;


--crea dirigente
create user srfcrl98p02g394p with password 'srf' createrole;
grant usage on schema public to srfcrl98p02g394p;
grant select on all tables in schema public to srfcrl98p02g394p;
grant all on custode, magazziniere, impiegato, fattorino, veicolo to srfcrl98p02g394p;
grant execute on all functions in schema public to srfcrl98p02g394p;

create user mntfrc98p02g395p with password 'mnt' createrole;
grant usage on schema public to mntfrc98p02g395p;
grant select on all tables in schema public to mntfrc98p02g395p;
grant all on custode, magazziniere, impiegato, fattorino, veicolo to mntfrc98p02g395p;
grant execute on all functions in schema public to mntfrc98p02g395p;

create user rsscrl05p04d494f with password 'rss' createrole;
grant usage on schema public to rsscrl05p04d494f;
grant select on all tables in schema public to rsscrl05p04d494f;
grant all on custode, magazziniere, impiegato, fattorino, veicolo to rsscrl05p04d494f;
grant execute on all functions in schema public to rsscrl05p04d494f;

create user spsfdr56p07d662f with password 'sps' createrole;
grant usage on schema public to spsfdr56p07d662f;
grant select on all tables in schema public to spsfdr56p07d662f;
grant all on custode, magazziniere, impiegato, fattorino, veicolo to spsfdr56p07d662f;
grant execute on all functions in schema public to spsfdr56p07d662f;

create user gllmra56p07d662f with password 'gll' createrole;
grant usage on schema public to gllmra56p07d662f;
grant select on all tables in schema public to gllmra56p07d662f;
grant all on custode, magazziniere, impiegato, fattorino, veicolo to gllmra56p07d662f;
grant execute on all functions in schema public to gllmra56p07d662f;

--crea cliente
create user srdvrl900p02p39f with password 'srd';
grant usage on schema public to srdvrl900p02p39f;
grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere to srdvrl900p02p39f;
grant all on spedizione, prod_sped, my_seq1 to srdvrl900p02p39f;
grant execute on all functions in schema public to srdvrl900p02p39f;

create user sfdvrl900p03p39f with password 'sfd';
grant usage on schema public to sfdvrl900p03p39f;
grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere to sfdvrl900p03p39f;
grant all on spedizione, prod_sped, my_seq1 to sfdvrl900p03p39f;
grant execute on all functions in schema public to sfdvrl900p03p39f;

create user srdvrl900p32p39f with password 'srd';
grant usage on schema public to srdvrl900p32p39f;
grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere to srdvrl900p32p39f;
grant all on spedizione, prod_sped, my_seq1 to srdvrl900p32p39f;
grant execute on all functions in schema public to srdvrl900p32p39f;

create user sslvrl900p02p39f with password 'ssl';
grant usage on schema public to sslvrl900p02p39f;
grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere to sslvrl900p02p39f;
grant all on spedizione, prod_sped, my_seq1 to sslvrl900p02p39f;
grant execute on all functions in schema public to sslvrl900p02p39f;

create user mrdmss900p02p39f with password 'mrd';
grant usage on schema public to mrdmss900p02p39f;
grant select on contratto, prodotto, spazio_contratto, contiene, dirigente, custode, impiegato, fattorino, cliente, magazziniere to mrdmss900p02p39f;
grant all on spedizione, prod_sped, my_seq1 to mrdmss900p02p39f;
grant execute on all functions in schema public to mrdmss900p02p39f;

--crea impiegato
create user sfdvrl910p03p39f with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39f;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39f;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39f;
grant execute on all functions in schema public to sfdvrl910p03p39f;

create user sfdvrl910p03p39g with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39g;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39g;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39g;
grant execute on all functions in schema public to sfdvrl910p03p39g;

create user sfdvrl910p03p39h with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39h;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39h;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39h;
grant execute on all functions in schema public to sfdvrl910p03p39h;

create user sfdvrl910p03p39i with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39i;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39i;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39i;
grant execute on all functions in schema public to sfdvrl910p03p39i;

create user sfdvrl910p03p39k with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39k;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39k;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39k;
grant execute on all functions in schema public to sfdvrl910p03p39k;

create user sfdvrl910p03p39l with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39l;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39l;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39l;
grant execute on all functions in schema public to sfdvrl910p03p39l;

create user sfdvrl910p03p39m with password 'sfd' createrole;
grant usage on schema public to sfdvrl910p03p39m;
grant select on filiale, magazzino, spazio, dirigente, custode, impiegato, fattorino, magazziniere, spedizione to sfdvrl910p03p39m;
grant all on cliente, contratto, prodotto, trasferimenti, contiene, spazio_contratto, prod_sped, prod_trasf, my_seq2 to sfdvrl910p03p39m;
grant execute on all functions in schema public to sfdvrl910p03p39m;

--create magazziniere
create user bldmsm910p03p39f with password 'bld';
grant usage on schema public to bldmsm910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to bldmsm910p03p39f;
grant all on contiene to bldmsm910p03p39f;
grant execute on all functions in schema public to bldmsm910p03p39f;

create user tnolcu910p03p39f with password 'tno';
grant usage on schema public to tnolcu910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to tnolcu910p03p39f;
grant all on contiene to tnolcu910p03p39f;
grant execute on all functions in schema public to tnolcu910p03p39f;

create user rzzrcc910p03p39f with password 'rzz';
grant usage on schema public to rzzrcc910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to rzzrcc910p03p39f;
grant all on contiene to rzzrcc910p03p39f;
grant execute on all functions in schema public to rzzrcc910p03p39f;

create user dfrcrt910p03p39f with password 'dfr';
grant usage on schema public to dfrcrt910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to dfrcrt910p03p39f;
grant all on contiene to dfrcrt910p03p39f;
grant execute on all functions in schema public to dfrcrt910p03p39f;

create user lmbfrr910p03p39f with password 'lmb';
grant usage on schema public to lmbfrr910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to lmbfrr910p03p39f;
grant all on contiene to lmbfrr910p03p39f;
grant execute on all functions in schema public to lmbfrr910p03p39f;

create user frrnz910p03p39f with password 'frr';
grant usage on schema public to frrnz910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to frrnz910p03p39f;
grant all on contiene to frrnz910p03p39f;
grant execute on all functions in schema public to frrnz910p03p39f;

create user tmblbr910p03p39f with password 'tmb';
grant usage on schema public to tmblbr910p03p39f;
grant select on magazziniere, magazzino, spazio, prodotto, dirigente, custode, impiegato, fattorino, cliente, filiale to tmblbr910p03p39f;
grant all on contiene to tmblbr910p03p39f;
grant execute on all functions in schema public to tmblbr910p03p39f;
