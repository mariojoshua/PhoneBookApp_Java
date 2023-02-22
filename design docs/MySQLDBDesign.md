## Data Required to be persisted
Name name,
List\<String\> phoneNumbers,
String address,
List\<String\> tags,
List\<String\> email,
Date (dateOfBirth) 

## Database Creation 
`CREATE DATABASE contactAPP`

## To view Database
 `SHOW TABLES FROM contactAPP`

## Table Creation
```sql
CREATE TABLE phonebook_master (
	ID integer PRIMARY KEY AUTO_INCREMENT
	name varchar(100)
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
)

CREATE TABLE tags (
	ID integer PRIMARY KEY AUTO_INCREMENT,
	contacts_ID integer,
	tag varchar(25)
)

CREATE TABLE phonenumber (
	ID integer PRIMARY KEY AUTO_INCREMENT,
	contacts_ID integer,	
	phoneNumber integer
)

CREATE TABLE email (
	ID integer PRIMARY KEY AUTO_INCREMENT,
	contacts_ID,	
	emailid varchar(40)
)
```