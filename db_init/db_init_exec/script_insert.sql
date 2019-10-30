insert into custode values ('SRFNDR98P02G393P','Andrea','Serafini','1998-09-02','3394958490','andreaserafini1998@icloud.com'),('SRFNDR98P02G394P','Francesco','Rossi','2000-05-24','3394958840','francescorossi@icloud.com'),
('SRFNDR98P02G395P','Paolo','Magradze','1978-04-02','33456723277','paolomagradze@icloud.com'),
('PPLTCU05P04D494F','Carlo','Golfieri','1961-09-02','32845672945','carlogolfieri@icloud.com'),
('FRTOCU56P07D662F','Mario','Rossi','1981-09-02','2344059782','mariorossi@icloud.com');

insert into fattorino values ('FRCCRL98P02G394P','Carlo','Franceschini','2000-05-24','3394958840','carlofranceschini@icloud.com'),
('FRRGVN98P02G395P','Giovanni','Ferrari','1978-04-02','33456723277','giovanniferrari@icloud.com'),
('BNCCRL05P04D494F','Carlo','Bianchi','1961-09-02','32845672945','carlobianchi@icloud.com'),
('VRDFDR56P07D662F','Federico','Verdi','1981-09-02','2344059782','federicoverdi@icloud.com'),
('GRBMRA56P07D662F','Mario','Garibaldi','1981-09-02','2344059782','mariorossi@icloud.com');

insert into dirigente values ('SRFCRL98P02G394P','Carlo','Serafini','2000-05-24','3394958840','carloserafini@mail.com'),
('MNTFRC98P02G395P','Francesco','Monti','1978-04-02','33456723277','francescomonti@mail.com'),
('RSSCRL05P04D494F','Carlo','Russo','1961-09-02','32845672945','carlorusso@mail.com'),
('SPSFDR56P07D662F','Federico','Esposito','1981-09-02','2344059782','federicoesposito@mail.com'),
('GLLMRA56P07D662F','Mario','Gallo','1981-09-02','2344059782','mariogallo@mail.com');

insert into veicolo values ('AB123CD','Fiat','Talento',50),
('CD456EF','Opel', 'Movano', 70),
('EF789GH','Mercedes','Sprinter',100);

insert into filiale values ('0000000001','Trasporti Bologna','Bologna','Zamboni',8,'567887894','SRFCRL98P02G394P'),
('0000000002','Trasporti Roma','Roma','Verdi',8,'234887894','MNTFRC98P02G395P'),
('0000000003','Trasporti Firenze','Firenze','Garibaldi',6,'234845894','RSSCRL05P04D494F'),
('0000000004','Trasporti Napoli','Napoli','Diaz',6,'234845123','SPSFDR56P07D662F');

select insert_magazzino('Magazzino 1 Bologna', 'Bologna', 'Rossi', 8, '1234567890', '0000000001');
select insert_magazzino('Magazzino 2 Bologna', 'Bologna', 'Verdi', 8, '1234567890', '0000000001');
select insert_magazzino('Magazzino 1 Firenze', 'Firenze', 'Verdi', 8, '1234567890', '0000000003');
select insert_magazzino('Magazzino 1 Roma', 'Roma', 'Rossi', 8, '1234567890', '0000000002');
select insert_magazzino('Magazzino 2 Roma', 'Roma', 'Verdi', 8, '1234567891', '0000000002');
select insert_magazzino('Magazzino 3 Roma', 'Roma', 'Garibaldi', 8, '1234567892', '0000000002');
select insert_magazzino('Magazzino 1 Napoli', 'Napoli', 'Verdi', 8, '1234567890', '0000000004');

insert into turno values ('SRFNDR98P02G393P', '2019-08-09',2,'0000000001'),
('SRFNDR98P02G393P', '2019-08-10',2,'0000000001'),
('SRFNDR98P02G393P', '2019-08-11',2,'0000000001'),
('SRFNDR98P02G395P', '2019-08-11',1,'0000000001'),
('SRFNDR98P02G395P', '2019-08-12',1,'0000000001'),
('PPLTCU05P04D494F', '2019-08-11',1,'0000000003'),
('PPLTCU05P04D494F', '2019-08-12',1,'0000000003'),
('FRTOCU56P07D662F', '2019-08-11',1,'0000000004');

select insert_spazio(1,'0000000001','Magazzino 1 Bologna spazio 1');
select insert_spazio(1,'0000000001','Magazzino 1 Bologna spazio 2');
select insert_spazio(1,'0000000001','Magazzino 1 Bologna spazio 3');
select insert_spazio(1,'0000000001','Magazzino 1 Bologna spazio 4');
select insert_spazio(1,'0000000001','Magazzino 1 Bologna spazio 5');
select insert_spazio(2,'0000000001','Magazzino 2 Bologna spazio 1');
select insert_spazio(2,'0000000001','Magazzino 2 Bologna spazio 2');
select insert_spazio(2,'0000000001','Magazzino 2 Bologna spazio 3');
select insert_spazio(2,'0000000001','Magazzino 2 Bologna spazio 4');
select insert_spazio(2,'0000000001','Magazzino 2 Bologna spazio 5');
select insert_spazio(1,'0000000003','Magazzino 1 Firenze spazio 1');
select insert_spazio(1,'0000000003','Magazzino 1 Firenze spazio 2');
select insert_spazio(1,'0000000003','Magazzino 1 Firenze spazio 3');
select insert_spazio(1,'0000000003','Magazzino 1 Firenze spazio 4');
select insert_spazio(1,'0000000003','Magazzino 1 Firenze spazio 5');
select insert_spazio(1,'0000000003','Magazzino 1 Firenze spazio 6');
select insert_spazio(2,'0000000002','Magazzino 1 Roma spazio 1');
select insert_spazio(2,'0000000002','Magazzino 1 Roma spazio 2');
select insert_spazio(2,'0000000002','Magazzino 1 Roma spazio 3');
select insert_spazio(3,'0000000002','Magazzino 1 Roma spazio 4');
select insert_spazio(2,'0000000002','Magazzino 2 Roma spazio 1');
select insert_spazio(2,'0000000002','Magazzino 2 Roma spazio 2');
select insert_spazio(2,'0000000002','Magazzino 2 Roma spazio 3');
select insert_spazio(2,'0000000002','Magazzino 2 Roma spazio 4');
select insert_spazio(3,'0000000002','Magazzino 3 Roma spazio 1');
select insert_spazio(3,'0000000002','Magazzino 3 Roma spazio 2');
select insert_spazio(3,'0000000002','Magazzino 3 Roma spazio 3');
select insert_spazio(3,'0000000002','Magazzino 3 Roma spazio 4');
select insert_spazio(1,'0000000004','Magazzino 1 Napoli spazio 1');
select insert_spazio(1,'0000000004','Magazzino 1 Napoli spazio 2');
select insert_spazio(1,'0000000004','Magazzino 1 Napoli spazio 3');
select insert_spazio(1,'0000000004','Magazzino 1 Napoli spazio 4');

insert into piano values ('Standard', 50, 'PianoStandard'),
('Gold', 40, 'PianoGold'),
('Top', 30, 'PianoTop');

insert into cliente values ('SRDVRL900P02P39F','Franco','Colombo','3457896543','Standard'),
('SFDVRL900P03P39F','Sandro','Rossi','3457896543','Gold'),
('SRDVRL900P32P39F','Giovanni','De Luca','3457896543','Gold'),
('SSLVRL900P02P39F','Mattia','Mancini','3457896543','Standard'),
('MRDMSS900P02P39F','Massimo','Meridio','3457896543','Top');

insert into impiegato values ('SFDVRL910P03P39F','Maria','Rossi','1980-09-01','05364102','mariarossi@mail.com','0000000001'),
('SFDVRL910P03P39G','Maria','Verdi','1980-09-01','05364102','mariaverdi@mail.com','0000000001'),
('SFDVRL910P03P39H','Carlo','Pedersoli','1980-09-01','05364102','carlopedersoli@mail.com','0000000002'),
('SFDVRL910P03P39I','Renato','Zero','1980-09-01','05364102','renatozero@mail.com','0000000003'),
('SFDVRL910P03P39K','Vasco','Rossi','1980-09-01','05364102','vascorossi@mail.com','0000000003'),
('SFDVRL910P03P39L','Fabrizio','De Andrè','1980-09-01','05364102','faber@mail.com','0000000004'),
('SFDVRL910P03P39M','Giacomo','Leopardi','1980-09-01','05364102','siepe@mail.com','0000000004');

insert into contratto values ('0000000001','2020-07-01','2021-07-01',4,'SFDVRL910P03P39F','SRDVRL900P02P39F'),
('0000000002','2020-07-01','2021-07-01',4,'SFDVRL910P03P39G','SFDVRL900P03P39F'),
('0000000003','2020-09-30','2021-05-17',4,'SFDVRL910P03P39G','SRDVRL900P32P39F'),
('0000000004','2020-02-21','2021-03-23',4,'SFDVRL910P03P39K','SSLVRL900P02P39F'),
('0000000005','2020-04-11','2021-09-25',4,'SFDVRL910P03P39K','MRDMSS900P02P39F');

insert into magazziniere values ('BLDMSM910P03P39F','Massimo','Boldi','1980-09-01','05364102','massimoboldi@mail.com',2,'0000000001'),
('TNOLCU910P03P39F','Luca','Toni','1980-09-01','05364102','lucatoni@mail.com',1,'0000000001'),
('RZZRCC910P03P39F','Riccardo','Rizzo','1980-09-01','05364102','lucatoni@mail.com',2,'0000000002'),
('DFRCRT910P03P39F','Cristina','De Francesco','1980-09-01','05364102','lucatoni@mail.com',2,'0000000002'),
('LMBFRR910P03P39F','Ferruccio','Lamborghini','1980-09-01','05364102','lucatoni@mail.com',2,'0000000002'),
('FRRNZ910P03P39F','Enzo','Ferrari','1980-09-01','05364102','lucatoni@mail.com',1,'0000000003'),
('TMBLBR910P03P39F','Alberto','Tomba','1980-09-01','05364102','lucatoni@mail.com',1,'0000000004');

insert into prodotto values
('yylltihwjl1','adasio','descr adasio'),
('ruondpqytb','galore amos','galore amos'),
('lrqhnyodrb','swift','descr swift'),
('bsplgefebj','eqity amos','descr eqity'),
('fwehrmwxsknhbxi','bee','descr bee'),
('dtdgnsfxvzanhjo','motivation fim','descr fim'),
('cjhdaykzivjqzxl','mf','magic fim'),
('skibw','Cs','Classics fim'),
('fhd3b3p894fx83p','Hitch','hitch dry'),
('pro12345','pro','pro1 professional');

/*insert into possiede values
('yylltihwjl1','SFDVRL900P03P39F'),
('ruondpqytb','MRDMSS900P02P39F'),
('lrqhnyodrb','SRDVRL900P32P39F'),
('bsplgefebj','SRDVRL900P32P39F'),
('fwehrmwxsknhbxi','SRDVRL900P32P39F'),
('skibw','SSLVRL900P02P39F'),
('fhd3b3p894fx83p','SSLVRL900P02P39F');*/

insert into contiene values
(3,1,'0000000001','yylltihwjl1',10),
(1,1,'0000000001','ruondpqytb',100),
(5,2,'0000000001','lrqhnyodrb',7),
(7,2,'0000000002','bsplgefebj',1000),
(7,2,'0000000002','fwehrmwxsknhbxi',90),
(2,3,'0000000002','dtdgnsfxvzanhjo',750),
(1,1,'0000000003','cjhdaykzivjqzxl',40),
(2,1,'0000000003','skibw',40),
(3,1,'0000000003','fhd3b3p894fx83p',1);

select spedisci('FRRGVN98P02G395P','2019-01-01','CD456EF','Italia','Bologna','Via del Chionso','13','3965226884','SFDVRL900P03P39F',5,'yylltihwjl1','0000000001',1,3);
select spedisci('FRRGVN98P02G395P','2019-01-01','CD456EF','Italia','Bologna','Via Arturo Bellalli','13','2469569851','MRDMSS900P02P39F',5,'ruondpqytb','0000000001',1,1);
select spedisci('BNCCRL05P04D494F','2019-02-01','CD456EF','Italia','Bologna','Via Gaetano Salvemini','13','3698554246','SRDVRL900P32P39F',5,'bsplgefebj','0000000002',2,7);
select spedisci('BNCCRL05P04D494F','2019-02-01','CD456EF','Italia','Bologna','Via Gaetano Salvemini','13','3698554246','SRDVRL900P32P39F',5,'bsplgefebj','0000000002',2,7);

insert into spazio_contratto values
(3,1,'0000000001','0000000002'),
(4,1,'0000000001','0000000002'),
(5,2,'0000000001','0000000003'),
(7,2,'0000000002','0000000003'),
(1,2,'0000000002','0000000003'),
(2,3,'0000000002','0000000003'),
(1,1,'0000000003','0000000002'),
(2,1,'0000000003','0000000004'),
(3,1,'0000000003','0000000005'),
(1,1,'0000000001','0000000005'),
(2,1,'0000000001','0000000005');

select trasferisci('MRDMSS900P02P39F','2020-10-09','VRDFDR56P07D662F','EF789GH','',1,'0000000001',2,'0000000002','ruondpqytb',26);
