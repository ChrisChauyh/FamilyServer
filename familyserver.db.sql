BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "User" (
	"username"	VARCHAR(255) NOT NULL,
	"password"	VARCHAR(255) NOT NULL,
	"email"	VARCHAR(255) NOT NULL,
	"firstName"	VARCHAR(30) NOT NULL,
	"lastName"	VARCHAR(30) NOT NULL,
	"gender"	CHAR(1) NOT NULL,
	"personID"	VARCHAR(20) NOT NULL,
	PRIMARY KEY("personID")
);
CREATE TABLE IF NOT EXISTS "Person" (
	"personID"	VARCHAR(30) NOT NULL,
	"associatedUsername"	VARCHAR(30) NOT NULL,
	"firstName"	VARCHAR(30) NOT NULL,
	"lastName"	VARCHAR(30) NOT NULL,
	"gender"	CHAR(1) NOT NULL,
	"fatherID"	VARCHAR(30),
	"motherID"	VARCHAR(30),
	"spouseID"	VARCHAR(30),
	PRIMARY KEY("personID")
);
CREATE TABLE IF NOT EXISTS "Event" (
	"eventID"	VARCHAR(30) NOT NULL,
	"associatedUsername"	VARCHAR(30) NOT NULL,
	"personID"	VARCHAR(30) NOT NULL,
	"latitude"	FLOAT(20) NOT NULL,
	"longitude"	FLOAT(20) NOT NULL,
	"country"	VARCHAR(30) NOT NULL,
	"city"	VARCHAR(30) NOT NULL,
	"eventType"	VARCHAR(30) NOT NULL,
	"year"	INT(4) NOT NULL,
	PRIMARY KEY("eventID")
);
CREATE TABLE IF NOT EXISTS "Authtokens" (
	"authtoken"	VARCHAR(30) NOT NULL,
	"username"	VARCHAR(30) NOT NULL,
	PRIMARY KEY("authtoken")
);
COMMIT;
