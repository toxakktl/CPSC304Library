/* Some tuples cannot exist without their foreign reference existing first, so the oder of inserting these examples has some restrictions. And the sequences have been created in the library.sql which creates the tables and sequences. */


/* Book */

insert into book values ('PL2848.Y8 S54','9787546201597', 'Shen Diao Xia Lu', 'Jin, Yong','Guangzhou Chu Ban She',2009);

insert into book values ('PL2960.U826 C36','9787535435804', 'Cao Yang Nian Hua : Bei x Da De Gu Shi', 'Sun, Rui','Changjiang wen yi chu ban she', 2008);

insert into book values ('DS778.M3 T87','7506533731', 'Dong fang ju ren Mao Zedong', 'Li, Jie','Beijing: Jie Fang Jun Chu Ban She',1997);

insert into book values ('QL463.H78','0721647820', 'Biology of insects', 'Horn, David J.','Philadelphia : Saunders',1978);

insert into book values ('BL4582.A78 U98','7506535483', 'Envrionment Protection', 'Apol, Kevin Y.','Philadelphia : Saunders',2009);




/* BorrowerType */

insert into borrowertype values ('Student', 14);

insert into borrowertype values ('Faculty', 120);

insert into borrowertype values ('Staff', 14);

/* Borrower */

insert into borrower values (bid_counter.nextval, 'kevin123123','Kevin Ye','1234 Kevin St. Richmond B.C. Canada',7781234567,'kevin@kevin.com','47618103',sysdate+120,'Student');

insert into borrower values (bid_counter.nextval, 'paulo123123','Paulo Apolinar','1234 Paulo St. White Rock B.C. Canada',7782345678,'paulo@paulo.com','41218103',sysdate+115,'Student');

insert into borrower values (bid_counter.nextval, 'tokhtar123', 'Tokhtar Yelemessov','1234 Tokhtar St. Coquitlum B.C. Canada',7782345678,'tokhtar@tokhtar.com','41213203',sysdate+138,'Staff');

insert into borrower values (bid_counter.nextval,'alex123', 'Alex Yuen', '1234 Alex St. Burnaby B.C.Canada',7781237581,'alex@yuen.com','12856742',sysdate+ 150,'Faculty');


/* HasAuthor */

insert into hasauthor values ('PL2848.Y8 S54', 'Jin, Yong');

insert into hasauthor values ('PL2960.U826 C36', 'Sun, Rui');

insert into hasauthor values ('DS778.M3 T87', 'Li, Jie');

insert into hasauthor values ('QL463.H78', 'Horn, David J.');

insert into hasauthor values ('BL4582.A78 U98', 'Apol, Kevin Y.');


/* HasSubject */

insert into hassubject values ('PL2848.Y8 S54','Fiction');

insert into hassubject values ('PL2960.U826 C36','Novel');

insert into hassubject values ('DS778.M3 T87','Novel');

insert into hassubject values ('QL463.H78','Biology');

insert into hassubject values ('DS778.M3 T87','Environment');



/* BookCopy */

insert into bookcopy values ('PL2848.Y8 S54','C1','out');

insert into bookcopy values ('PL2848.Y8 S54','C2','on-hold');

insert into bookcopy values ('PL2960.U826 C36','C1','in');

insert into bookcopy values ('PL2960.U826 C36','C2','out');

insert into bookcopy values ('DS778.M3 T87','C1','out');

insert into bookcopy values ('DS778.M3 T87','C2','in');

insert into bookcopy values ('QL463.H78','C1','on-hold');

insert into bookcopy values ('QL463.H78','C2','out');

insert into bookcopy values ('QL463.H78','C3','on-hold');

insert into bookcopy values ('BL4582.A78 U98','C1','out');

insert into bookcopy values ('BL4582.A78 U98','C2','on-hold');

insert into bookcopy values ('BL4582.A78 U98','C3','out');




/* HoldRequest */

insert into holdrequest values (hid_counter.nextval, 1003, 'PL2848.Y8 S54', sysdate-2);

insert into holdrequest values (hid_counter.nextval, 1002, 'QL463.H78', sysdate-1);

insert into holdrequest values (hid_counter.nextval, 1003, 'QL463.H78', sysdate-3);

insert into holdrequest values (hid_counter.nextval, 1002, 'BL4582.A78 U98', sysdate-4);


/* Borrowing */


insert into borrowing values (borid_counter.nextval, 1003, 'PL2848.Y8 S54','C1',sysdate-28,sysdate -14);

insert into borrowing values (borid_counter.nextval, 1002, 'PL2960.U826 C36','C2',sysdate-56,sysdate - 42);

insert into borrowing values (borid_counter.nextval, 1001, 'BL4582.A78 U98','C3',sysdate-41 ,null);

insert into borrowing values (borid_counter.nextval, 1001, 'PL2848.Y8 S54','C1',sysdate-1,sysdate + 13);

insert into borrowing values (borid_counter.nextval, 1002, 'PL2960.U826 C36','C2',sysdate,sysdate + 14);

insert into borrowing values (borid_counter.nextval, 1003, 'DS778.M3 T87','C1',sysdate,sysdate + 14);

insert into borrowing values (borid_counter.nextval, 1004, 'QL463.H78','C2',sysdate,sysdate + 120);

insert into borrowing values (borid_counter.nextval, 1004, 'BL4582.A78 U98', 'C1', sysdate-5, sysdate+115);

insert into borrowing values (borid_counter.nextval, 1001, 'BL4582.A78 U98', 'C3', sysdate, sysdate+14);

/* Fine */

insert into fine values (fineid_counter.nextval, 2.05, sysdate-5, sysdate, 1001);

insert into fine values (fineid_counter.nextval, 3.06, sysdate-4, null, 1002);

insert into fine values (fineid_counter.nextval, 1.11, sysdate-2, TO_DATE('11/10/2002','MM/DD/YYYY'), 1003);

