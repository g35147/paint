-- =======================================================
--   DDL de création de la BD Paint
-- =======================================================

-- Suppression des tables si BD existe deja
-------------------------------------------

drop table SEQUENCE;
drop table POINT;
drop table SHAPE;
drop table FIGURE;

-- Creation des tables
----------------------

create table FIGURE (
     id   numeric(10) not null,
     figurename varchar(50) not null,
     datemaj DATE,

     constraint IDFIGURE    primary key (id),
     constraint UniqueName  unique (figurename)
     );

create table SHAPE (
     id             numeric(10) not null,
     shapetype      varchar(20) not null,
     strokecolor    char(6),
     fillcolor      char(6),
     strokewidth    numeric(20,10) not null,
     figure         numeric(10),
     shape          numeric(10),
     radius         numeric(20,10),
	 
     constraint IDSHAPE              primary key (id)
     );

create table POINT (
     id             numeric(10) not null,
     x              numeric(20,10) not null,
     y              numeric(20,10) not null,
     shape          numeric(10) not null,
     listorder      numeric(10) not null,
	 
     constraint IDPOINT           primary key (id)
     );
	 
create table SEQUENCE (
     SEQ_NAME varchar(50) not null primary key,
     SEQ_COUNT numeric(10) not null
     );
	 
-- Contraintes
--------------

alter table POINT add constraint Shape_of_Point
     foreign key (shape)
     references SHAPE
     ON DELETE CASCADE;

alter table SHAPE add constraint Shape_Of_Shape
     foreign key (shape)
     references SHAPE
     ON DELETE CASCADE;
	 
alter table SHAPE add constraint Figure_Of_Shape
     foreign key (figure)
     references FIGURE
     ON DELETE CASCADE;
	 	 
alter table SHAPE add constraint XOR_Figure_Shape
     Check ((figure is null AND shape is not null) 
            OR (figure is not null AND shape is null));

alter table SHAPE add constraint Radius_AND_Circle
     Check ((radius is null AND shapetype != 'Circle') 
            OR (radius is not null AND shapetype = 'Circle'));
	 
-- Remplissage des tables
-------------------------

-- exemple de remplissagle
--------------------------

INSERT INTO FIGURE (ID, FIGURENAME) VALUES(1, 'premierDessin');

-- un rectangle bleu au position (20,20) (40,20) (40,40) (20,40)

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, FIGURE, SHAPE, RADIUS, STROKEWIDTH) 
       VALUES(1, 'Rectangle', '0000ff', '7f7fff', 1, null, null, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(1, 20, 20, 0, 1);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(2, 100, 20, 1, 1);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(3, 100, 100, 2, 1);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(4, 20, 100, 3, 1);


-- un cercle rouge ayant pour centre (200, 200) avec un rayon de 50

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, FIGURE, SHAPE, RADIUS, STROKEWIDTH) 
       VALUES(2, 'Circle', 'ff0000', 'ff7f7f', 1, null, 50, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(5, 200, 200, 0, 2);

-- une ligne orange
INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, FIGURE, STROKEWIDTH)
       VALUES(3, 'Line', 'ffa500', 'ffa500', 1, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(6, 200, 100, 0, 3);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(7, 280, 110, 1, 3);



--
INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, FIGURE, STROKEWIDTH)
       VALUES(10, 'ShapeBlock', 'ffa500', 'ffa500', 1, 10);
-- un block de shape

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, SHAPE, STROKEWIDTH)
       VALUES(4, 'ShapeBlock', 'ffa500', 'ffa500', 10, 10);

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, SHAPE, STROKEWIDTH)
       VALUES(5, 'Rectangle', '000000', '808080', 4, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(8, 270, 270, 0, 5);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(9, 350, 270, 1, 5);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(10, 350, 350, 2, 5);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(11, 270, 350, 3, 5);

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, SHAPE, RADIUS ,STROKEWIDTH)
       VALUES(6, 'Circle', '000000', '808080', 4, 50, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(12, 400, 400, 0, 6);
-- un autre  block de shape
INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, SHAPE, STROKEWIDTH)
       VALUES(7, 'ShapeBlock', 'ffa500', 'ffa500', 10, 10);

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, SHAPE, STROKEWIDTH)
       VALUES(8, 'Rectangle', '000000', '808080', 7, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(14, 370, 270, 0, 8);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(15, 450, 270, 1, 8);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(16, 450, 350, 2, 8);
INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(17, 370, 350, 3, 8);

INSERT INTO SHAPE (ID, SHAPETYPE, STROKECOLOR, FILLCOLOR, SHAPE, RADIUS ,STROKEWIDTH)
       VALUES(9, 'Circle', '000000', '808080', 7, 50, 10);

INSERT INTO POINT (ID, X, Y, LISTORDER, SHAPE) VALUES(18, 500, 400, 0, 9);
-- les nextdIds
insert into sequence(SEQ_NAME,SEQ_COUNT) values('Figure', 2);
insert into sequence(SEQ_NAME,SEQ_COUNT) values('Point', 19);
insert into sequence(SEQ_NAME,SEQ_COUNT) values('Shape', 9);
