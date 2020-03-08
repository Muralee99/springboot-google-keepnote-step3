/* Start of Script */
/*user table creation*/
create table user (
user_id int not null auto_increment, 
user_name varchar(70) not null, 
user_added_date datetime, 
user_password varchar(32), 
user_mobile varchar(15), PRIMARY KEY (user_id));
/*Note table fields: note_id, note_title, note_content, note_status, note_creation_date*/
create table note(
note_id int not null auto_increment,
note_title varchar(70) not null,
note_content varchar(250),
note_status varchar(15),
note_creation_date datetime, primary key(note_id));
/*Category table fields : category_id, category_name, category_descr, category_creation_date, category_creator*/
create table category(
category_id int not null auto_increment,
category_name varchar(70) not null,
category_descr varchar(250),
category_creation_date datetime, 
category_creator varchar(70), primary key(category_id));
/*Reminder table fields : reminder_id, reminder_name, reminder_descr, reminder_type, reminder_creation_date, reminder_creator*/
create table reminder(
reminder_id int not null auto_increment,
reminder_name varchar(70) not null,
reminder_descr varchar(250),
reminder_type varchar(15),
reminder_creation_date datetime, 
reminder_creator varchar(70), primary key(reminder_id));
/* NoteCategory table fields : notecategory_id, note_id, category_id */
create table notecategory (
notecategory_id int not null auto_increment primary key,
note_id int not null,
category_id int not null,
FOREIGN KEY fk_note(note_id)
REFERENCES note(note_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
FOREIGN KEY fk_category(category_id)
REFERENCES category(category_id)
ON UPDATE CASCADE
ON DELETE CASCADE);
/*Notereminder table fields : notereminder_id, note_id, reminder_id*/
create table notereminder (
notereminder_id int not null auto_increment primary key,
note_id int not null,
reminder_id int not null,
FOREIGN KEY fk_reminder_note(note_id)
REFERENCES note(note_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
FOREIGN KEY fk_reminder(reminder_id)
REFERENCES reminder(reminder_id)
ON UPDATE CASCADE
ON DELETE CASCADE);
/*usernote table fields : usernote_id, user_id, note_id*/
create table usernote (
usernote_id int not null auto_increment primary key,
user_id int not null,
note_id int not null,
FOREIGN KEY fk_user_note(note_id)
REFERENCES note(note_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
FOREIGN KEY fk_user(user_id)
REFERENCES user(user_id)
ON UPDATE CASCADE
ON DELETE CASCADE);
/* user insert queries */
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Baskaran Murugesan',(select now() + 1), '123456789','9943624569');
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Nawin Adithya',(select now()- INTERVAL 24 DAY), '123456789','9943344569');
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Satheesh Kumar',(select now()- INTERVAL 12 DAY), '123456789','9473624569');
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Velmurugan',(select now() + INTERVAL 5 DAY), '123456789','9949724569');
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Mami Bhanja',(select now() + INTERVAL 15 DAY), '123456789','9949724569');
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Tara Shankar',(select now() - INTERVAL 30 DAY), '123456789','9949724789');
insert into user (user_name,user_added_date,user_password,user_mobile)
values('Sudheer',(select now() - INTERVAL 15 DAY), '123456789','9532724569');
/*insert category table*/
insert into note (note_title,note_content,note_status,note_creation_date)
values('Shopping items','Snacks and Nuts','INCOMPLETE',(select now()));
insert into note (note_title,note_content,note_status,note_creation_date)
values('Pending Work home','broom and mob','COMPLETE',(select now()));
insert into note (note_title,note_content,note_status,note_creation_date)
values('Today Task List','code and test','INPROGRESS',(select now()));
insert into note (note_title,note_content,note_status,note_creation_date)
values('Important Names','Nawin and Viveka','INPROGRESS',(select now()));
insert into note (note_title,note_content,note_status,note_creation_date)
values('Pending Work Office','code and commit','INCOMPLETE',(select now()));
insert into note (note_title,note_content,note_status,note_creation_date)
values('New English Word','Desponent','COMPLETE',(select now()));
/*Insert Category*/
insert into category(category_name,category_descr,category_creation_date,category_creator)
values('Home Activity','All home related personal things',(select now() + INTERVAL 10 DAY),1);
insert into category(category_name,category_descr,category_creation_date,category_creator)
values('Physical Activity','All physical activity related things',(select now() - INTERVAL 15 DAY),2);
insert into category(category_name,category_descr,category_creation_date,category_creator)
values('Entertainment','Time to be spent for entertainment',(select now() - INTERVAL 1 DAY),3);
insert into category(category_name,category_descr,category_creation_date,category_creator)
values('Official','Work related to job',(select now() + INTERVAL 6 DAY),2);
insert into category(category_name,category_descr,category_creation_date,category_creator)
values('Learning Activity','All about learning new stuff',(select now() - INTERVAL 5 DAY),4);
/* reminder insert queries */
insert into reminder (reminder_name,reminder_descr,reminder_type,reminder_creation_date, reminder_creator)
values('Shopping','Shopping reminder for today','LOW',(select now()+ INTERVAL 24 DAY),1);
insert into reminder (reminder_name,reminder_descr,reminder_type,reminder_creation_date, reminder_creator)
values('Wash untencils','home work reminder for today','MEDIUM',(select now() + INTERVAL 4 DAY),4);
insert into reminder (reminder_name,reminder_descr,reminder_type,reminder_creation_date, reminder_creator)
values('Office Work','Task to be completed today','HIGH',(select now() + INTERVAL 10 DAY),1);
insert into reminder (reminder_name,reminder_descr,reminder_type,reminder_creation_date, reminder_creator)
values('Learning New word','New word learning for today','MEDIUM',(select now()- INTERVAL 15 DAY),2);
insert into reminder (reminder_name,reminder_descr,reminder_type,reminder_creation_date, reminder_creator)
values('Pending Office Work','Important mails to be sent','CRITICAL',(select now()- INTERVAL 8 DAY),3);
/* insert usernote entries */
insert into usernote (user_id,note_id) values (1,1);
insert into usernote (user_id,note_id) values (1,2);
insert into usernote (user_id,note_id) values (1,3);
insert into usernote (user_id,note_id) values (2,1);
insert into usernote (user_id,note_id) values (2,2);
insert into usernote (user_id,note_id) values (2,3);
insert into usernote (user_id,note_id) values (3,1);
insert into usernote (user_id,note_id) values (3,2);
insert into usernote (user_id,note_id) values (3,3);
insert into usernote (user_id,note_id) values (3,4);
insert into usernote (user_id,note_id) values (4,1);
insert into usernote (user_id,note_id) values (4,2);
insert into usernote (user_id,note_id) values (4,3);
insert into usernote (user_id,note_id) values (4,4);
insert into usernote (user_id,note_id) values (4,5);
insert into usernote (user_id,note_id) values (5,1);
/*insert note category */
insert into notecategory (note_id,category_id) values (1,2);
insert into notecategory (note_id,category_id) values (1,3);
insert into notecategory (note_id,category_id) values (2,2);
insert into notecategory (note_id,category_id) values (2,4);
insert into notecategory (note_id,category_id) values (3,1);
insert into notecategory (note_id,category_id) values (3,2);
insert into notecategory (note_id,category_id) values (3,3);
insert into notecategory (note_id,category_id) values (3,4);
insert into notecategory (note_id,category_id) values (1,1);
insert into notecategory (note_id,category_id) values (2,3);
/*insert queries notereminder */
insert into notereminder (note_id,reminder_id) values (1,2);
insert into notereminder (note_id,reminder_id) values (1,3);
insert into notereminder (note_id,reminder_id) values (2,2);
insert into notereminder (note_id,reminder_id) values (2,4);
insert into notereminder (note_id,reminder_id) values (3,1);
insert into notereminder (note_id,reminder_id) values (3,2);
insert into notereminder (note_id,reminder_id) values (3,3);
insert into notereminder (note_id,reminder_id) values (3,4);
insert into notereminder (note_id,reminder_id) values (1,1);
insert into notereminder (note_id,reminder_id) values (2,3);
insert into notereminder (note_id,reminder_id) values (4,4);
insert into notereminder (note_id,reminder_id) values (4,1);
insert into notereminder (note_id,reminder_id) values (4,3);
insert into notereminder (note_id,reminder_id) values (1,4);
insert into notereminder (note_id,reminder_id) values (4,3);
/* Fetch the row from User table based on Id and Password. */
select * from user where user_id=1 and user_password='123456789';
/* Fetch all the rows from Note table based on the field note_creation_date.*/
select * from note where date(note_creation_date) = '2018-10-24';
/* Fetch all the Categories created after the particular Date. */
select * from category where date(category_creation_date) > '2018-10-10';
/* Fetch all the Note ID from UserNote table for a given User. */
select * from usernote where user_id = 2;
/* Write Update query to modify particular Note for the given note Id. */
update note set note_content='Coding and Testing completed', note_status='COMPLETE' where note_id=3;
/* Fetch all the Notes from the Note table by a particular User. */
select * from note where note_id in (select distinct note_id from usernote where user_id=1);
/* Fetch all the Notes from the Note table for a particular Category. */
select * from note where note_id in (select distinct note_id from notecategory where category_id = 1);
/* Fetch all the reminder details for a given note id. */
select * from reminder where reminder_id in (select distinct reminder_id from notereminder 
where note_id = 1);
/* Fetch the reminder details for a given reminder id. */
select * from reminder where reminder_id=1;
/* Write a query to create a new Note from particular User (Use Note and UserNote tables - insert statement).*/
insert into note (note_title,note_content,note_status,note_creation_date)
values('ReTry Trigger','Trigger is Working','COMPLETE',(select now() + INTERVAL 12 DAY));
insert into usernote (user_id,note_id) values (5, (select max(note_id) from note));
/* Write a query to create a new Note from particular User to particular Category(Use Note and NoteCategory tables - insert statement) */
insert into note (note_title,note_content,note_status,note_creation_date)
values('Try Trigger For more tables','Trigger is Working for more than one table','COMPLETE',(select now() + INTERVAL 8 DAY));
insert into usernote (user_id,note_id) values (1, (select max(note_id) from note));
insert into notecategory (category_id,note_id) values (3, (select max(note_id) from note));
 
/* Write a query to set a reminder for a particular note (Use Reminder and NoteReminder tables - insert statement) */
insert into reminder (reminder_name,reminder_descr,reminder_type,reminder_creation_date, reminder_creator)
values('Pending Office Work','Important mails to be sent','CRITICAL',(select now()- INTERVAL 8 DAY),3);
insert into notereminder (note_id,reminder_id) values ((select max(note_id) from note),(select max(reminder_id) from reminder));
/* Write a query to delete particular Note added by a User(Note and UserNote tables - delete statement) */
	 delete from usernote where note_id=7;
     delete from note where note_id=7;
    
/* Write a query to delete particular Note from particular Category(Note and NoteCategory tables - delete statement) */
     delete from notecategory where note_id=8;
     delete from note where note_id=8;
/* Create a trigger to delete all matching records from UserNote, NoteReminder and NoteCategory table when :*/ 
/* 1. A particular note is deleted from Note table (all the matching records from UserNote, NoteReminder and NoteCategory 
 should be removed automatically)  */
    
CREATE TRIGGER note_delete_trig BEFORE DELETE ON note
  FOR EACH ROW
  BEGIN
      delete from usernote where note_id=old.note_id;
      delete from notereminder where note_id=old.note_id;
     delete from notecategory where note_id=old.note_id;
  END;
 
/* 2. A particular user is deleted from User table (all the matching notes should be removed automatically) */
  
 CREATE TRIGGER user_delete_trig BEFORE DELETE ON user
  FOR EACH ROW
  begin
	   DECLARE vNote_id int;
	  DECLARE vCate_id int;
	 DECLARE vRemind_id int;
	  SET @vNote_id := (SELECT note_id FROM usernote WHERE user_id = old.user_id);
	 SET @vRemind_id := (SELECT reminder_id FROM notereminder WHERE note_id = vNote_id);
	SET @vCate_id := (SELECT category_id FROM notecategory WHERE note_id = vNote_id);
      delete from usernote where note_id=old.user_id;
      delete from notereminder where note_id=vNote_id;
      delete from notecategory where note_id=vNote_id;
      delete from note where note_id=vNote_id;
      delete from reminder where reminder_id=vRemind_id;
      delete from category where category_id=vCate_id;
  END;
 /* End of Script */
commit;