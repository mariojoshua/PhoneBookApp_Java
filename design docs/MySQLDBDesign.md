## Data Required to be persisted
String phoneBook (1-n) <br>
Name name, (1-1) <br>
List\<String\> phoneNumbers,(1-n) <br>
String address, (1-1) <br>
List\<String\> tags,(m-n) <br>
List\<String\> email, (1-n) <br>
Date (dateOfBirth) (1-1) <br>

## Database Creation 
`CREATE DATABASE contactAPP`

## To view Database
 `SHOW TABLES FROM contactAPP`

## Table Creation
```sql
CREATE TABLE phonebook_master (
	ID integer PRIMARY KEY AUTO_INCREMENT
	name varchar(100) UNIQUE NOT NULL
)

CREATE TABLE contacts (
	phonebook_ID integer,
	ID integer PRIMARY KEY AUTO_INCREMENT,
	gender enum('M','F','O'),
	fullname varchar(200) NOT NULL,
	petname varchar(100), 
	dateOfBirth date, 
	address varchar(120),
	createdDate DATETIME DEFAULT CURRENT_TIMESTAMP,
	modifiedDate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	FOREIGN KEY (phonebook_ID) REFERENCES phonebook_master(id);
)

CREATE TABLE tags (
	ID integer PRIMARY KEY AUTO_INCREMENT,
	tag varchar(25)
)

CREATE TABLE contacts_tags(
	ID integer PRIMARY KEY AUTO_INCREMENT,
	contacts_ID integer NOT NULL ,
	tag_ID integer NOT NULL, 
	FOREIGN KEY (contacts_id) REFERENCES contacts(ID),
	FOREIGN KEY (tag_ID) REFERENCES tags(ID)
)

CREATE TABLE phonenumber (
	ID integer PRIMARY KEY AUTO_INCREMENT,
	contacts_ID integer,	
	phoneNumber varchar(20),
	FOREIGN KEY (contacts_ID) REFERENCES contacts(ID)
)

ALTER TABLE contactApp.phonenumber
MODIFY COLUMN phoneNumber varchar(20)

CREATE TABLE email (
	ID integer PRIMARY KEY AUTO_INCREMENT,
	contacts_ID,	
	emailid varchar(40) UNIQUE
	FOREIGN KEY (contacts_ID) REFERENCES contacts(ID)
)
```

## Alter Table

```sql
-- To add a constraint
ALTER TABLE email
ADD FOREIGN KEY (contacts_ID) REFERENCES contacts(ID);

--To remove a column
ALTER TABLE tags
DROP contacts_ID

--To add a constraint
ALTER TABLE contactApp.phonebook_master
MODIFY COLUMN name varchar(50) NOT NULL;

--To add unique contraint
ALTER TABLE contactApp.phonebook_master
MODIFY COLUMN name varchar(50) UNIQUE NOT NULL;

ALTER TABLE contactApp.email
MODIFY COLUMN emailid varchar(40) UNIQUE
