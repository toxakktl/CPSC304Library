/* Deleteing will have the reverse order from the Inserting file since
   we have to delete the tables that contain the foreign keys first */

delete fine;
delete borrowing;
delete holdrequest;
delete bookcopy;
delete hassubject;
delete hasauthor;
delete borrower;
delete borrowertype;
delete book;

drop sequence bid_counter;
drop sequence borid_counter;
drop sequence hid_counter;
drop sequence fineid_counter;

drop table Fine;
drop table Borrowing;
drop table HoldRequest;
drop table BookCopy;
drop table HasSubject;
drop table HasAuthor;
drop table Borrower;
drop table BorrowerType;
drop table Book;