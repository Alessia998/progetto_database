
--
create table if not exists custode(
cf varchar(16) not null,
nome varchar(255) not null,
cognome varchar (255) not null,
data_nascita date,
tel varchar(30),
mail varchar(255),

PRIMARY KEY (cf)
);

---
create table if not exists fattorino(
cf varchar(16) not null,
nome varchar(255) not null,
cognome varchar (255) not null,
data_nascita date,
tel varchar(30),
mail varchar(255),

PRIMARY KEY (cf)
);


---
create table if not exists dirigente(
cf varchar(16) not null,
nome varchar(255) not null,
cognome varchar (255) not null,
data_nascita date,
tel varchar(30),
mail varchar(255),

PRIMARY KEY (cf)
);

create table if not exists piano(
  nome_piano varchar(255) not null,
  importo real not null check(importo > 0),
  descrizione varchar(255) not null,

  PRIMARY KEY (nome_piano)
);

create table if not exists veicolo(
  targa varchar(20),
  marca varchar(255),
  modello varchar(255),
  capacita int check(capacita > 0),

  PRIMARY KEY (targa)
);

create table if not exists filiale(
  cod varchar(10) not null,
  nome varchar(255) not null,
  citta varchar(255) not null,
  via varchar(255) not null,
  numero int not null,
  tel varchar(30),
  --FK
  cf varchar(16) not null,


  PRIMARY KEY (cod),
  FOREIGN KEY (cf) REFERENCES dirigente
);



create table if not exists magazzino(
  num int,
  denominazione varchar(255),
  citta varchar(255),
  via varchar(255),
  numero int,
  tel varchar(30),
  --FK
  cod varchar(255),

  PRIMARY KEY (num,cod),
  FOREIGN key (cod) REFERENCES filiale
);

create table if not exists turno(
  cf varchar(16) not null,
  data_t date not null,
  --FK
  num int,
  cod varchar(255),


  PRIMARY KEY (cf,data_t),
  FOREIGN key (cf) REFERENCES custode,
  FOREIGN KEY (num,cod) REFERENCES magazzino
);


create table if not exists spazio(
  id_spazio int,
  --FK
  num int not null,
  cod varchar(255) not null,
  descrizione varchar(255),

  PRIMARY KEY (id_spazio,num,cod),
  FOREIGN key (num,cod) REFERENCES magazzino
);

create table if not exists cliente(
  cf_cli varchar(16) not null,
  nome varchar(255) not null,
  cognome varchar(255) not null,
  tel varchar(30),
  --FK
  nome_piano varchar(255) not null,

  PRIMARY KEY (cf_cli),
  FOREIGN KEY (nome_piano) REFERENCES piano

);

create table if not exists impiegato(
  cf varchar(16) not null,
  nome varchar(255) not null,
  cognome varchar (255) not null,
  data_nascita date,
  tel varchar(30),
  mail varchar(255),
--FK
  cod varchar(255) not null,

PRIMARY KEY (cf),
FOREIGN KEY (cod) REFERENCES filiale
);


 create table if not exists contratto(
   num_c varchar(255) not null, --num_c = numero del contratto
   data_inizio date not null check (data_inizio >= current_date),
   data_fine date check (data_inizio < data_fine),
   num_spazi int not null check (num_spazi > 0),
   --FK
   cf varchar(16) not null,
   cf_cli varchar(16) not null,


   PRIMARY KEY (num_c),
   FOREIGN KEY (cf_cli) REFERENCES cliente,
   FOREIGN KEY (cf) REFERENCES impiegato

 );


create table if not exists magazziniere(
  cf varchar(16) not null,
  nome varchar(255) not null,
  cognome varchar (255) not null,
  data_nascita date,
  tel varchar(30),
  mail varchar(255),
--FK
num int not null,
cod varchar(255) not null,

PRIMARY KEY (cf),
FOREIGN KEY (num,cod) REFERENCES magazzino
);

create table if not exists prodotto(
  codice varchar(255) not null,
  nome_prod varchar(255),
  descrizione varchar(255),

  PRIMARY KEY (codice)
);

/*create table if not exists possiede(
  codice varchar(255) not null,
  cf_cli varchar(16) not null,

  PRIMARY KEY (codice, cf_cli),
  FOREIGN key (codice) REFERENCES prodotto,
  FOREIGN key (cf_cli) REFERENCES cliente

);*/

create table if not exists contiene(
  --FK
  id_spazio int not null,
  num int not null,
  cod varchar(255) not null,
  codice varchar(255) not null,
  quantita int not null check(quantita >= 0),

  PRIMARY KEY (id_spazio, num, cod, codice),
  FOREIGN KEY (id_spazio, num, cod ) REFERENCES spazio,
  FOREIGN KEY (codice ) REFERENCES prodotto
);


create sequence if not exists my_seq1;

create table if not exists spedizione(
  num_sped int default nextval('my_seq1'::regclass) not null,
  cf varchar(16) not null,
  data_sp date not null,
  targa varchar(20) not null,
  paese varchar(255) not null,
  citta varchar(255) not null,
  via varchar(255) not null,
  numero varchar(10) not null,
  tel varchar(30),
  stato_consegna varchar(11), --in consegna, consegnato
  --FK
  cf_cli varchar(16) not null,


  PRIMARY key (num_sped),
  FOREIGN key (cf ) REFERENCES fattorino,
  FOREIGN KEY (targa) REFERENCES veicolo,
  FOREIGN KEY (cf_cli) REFERENCES cliente

);


create table if not exists spazio_contratto(
  id_spazio int not null,
  num int not null,
  cod varchar(255),
  num_c varchar(255),

  PRIMARY KEY (id_spazio,num,cod,num_c),
  FOREIGN KEY (id_spazio, num, cod) REFERENCES spazio,
  FOREIGN KEY (num_c) REFERENCES contratto

);

create sequence if not exists my_seq2;

create table if not exists trasferimenti(
  num_sped int default nextval('my_seq2'::regclass) not null,
  data_sp date not null,
  cf varchar(16) not null,
  targa varchar(20) not null,
  stato_consegna varchar(11),
  num1 int not null,
  cod1 varchar(255) not null,
  num2 int not null,
  cod2 varchar(255) not null,

  PRIMARY key (num_sped),
  FOREIGN KEY (num1, cod1) REFERENCES magazzino(num,cod),
  FOREIGN KEY (num2, cod2) REFERENCES magazzino(num,cod),
  FOREIGN key (cf ) REFERENCES fattorino,
  FOREIGN KEY (targa) REFERENCES veicolo
);

create table if not exists prod_sped(
  codice varchar(255) not null,
  num_sped int not null,
  quantita int not null,

  PRIMARY KEY (codice, num_sped),
  FOREIGN KEY (codice) REFERENCES prodotto,
  FOREIGN KEY (num_sped) REFERENCES spedizione
);

create table if not exists prod_trasf(
  codice varchar(255) not null,
  num_sped int not null,
  quantita int not null,

  PRIMARY KEY (codice, num_sped),
  FOREIGN KEY (codice) REFERENCES prodotto,
  FOREIGN KEY (num_sped) REFERENCES trasferimenti
);
