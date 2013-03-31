
create sequence bid_counter
start with 1000
increment by 1;

create sequence borid_counter
start with 1000
increment by 1;

create sequence fineid_counter
start with 1000
increment by 1;

create sequence hid_counter
start with 1000
increment by 1;



create table Book (
callNumber varchar(20),
isbn varchar(20),
title varchar(40),
mainAuthor varchar(40),
publisher varchar(40),
year number(4,0),
PRIMARY KEY (callNumber)
);

create table BorrowerType (
type varchar(20),
bookTimeLimit integer,
PRIMARY KEY (type)
);

create table Borrower (
bid integer,
password varchar(20),
name varchar(40),
address varchar(40),
phone number(10,0),
emailAddress varchar(40),
sinOrStNo varchar(10),
expiryDate date,
type varchar(20) not null,
PRIMARY KEY (bid),
FOREIGN KEY (type) REFERENCES BorrowerType(type)
);


create table HasAuthor (
callNumber,
name varchar(40),
PRIMARY KEY (callNumber, name),
FOREIGN KEY (callNumber) REFERENCES Book(callNumber)
);

create table HasSubject (
callNumber,
subject varchar(40),
PRIMARY KEY (callNumber, subject),
FOREIGN KEY (callNumber) REFERENCES Book(callNumber)
);

create table BookCopy(
callNumber,
copyNo varchar(20),
status varchar(7),
PRIMARY KEY (callNumber, copyNo),
FOREIGN KEY (callNumber) REFERENCES Book(callNumber)
);

create table HoldRequest (
hid integer,
bid integer,
callNumber,
issuedDate date,
PRIMARY KEY (hid),
FOREIGN KEY (bid) REFERENCES Borrower (bid),
FOREIGN KEY (callNumber) REFERENCES Book(callNumber)
);

create table Borrowing (
borid integer,
bid,
callNumber,
copyNo,
outDate date,
inDate date,
PRIMARY KEY (borid),
FOREIGN KEY (bid) REFERENCES Borrower (bid),
FOREIGN KEY (copyNo,callNumber) REFERENCES BookCopy(copyNo,callNumber)
);

create table Fine (
fid integer,
amount number,
issuedDate date,
paidDate date,
borid,
PRIMARY KEY (fid),
FOREIGN KEY (borid) REFERENCES Borrowing (borid)
);
