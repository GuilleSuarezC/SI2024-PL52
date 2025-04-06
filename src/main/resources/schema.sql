BEGIN TRANSACTION;

-- Primero borramos las tablas si existen
DROP TABLE IF EXISTS "Invoice";
DROP TABLE IF EXISTS "Movement";
DROP TABLE IF EXISTS "Sponsorship";
DROP TABLE IF EXISTS "Balance";
DROP TABLE IF EXISTS "Event";
DROP TABLE IF EXISTS "Member";
DROP TABLE IF EXISTS "Company";
DROP TABLE IF EXISTS "COIIPA_GBMember";
DROP TABLE IF EXISTS "SponsorshipLevel";

-- Crear las tablas desde cero

CREATE TABLE "SponsorshipLevel" (
    "level_id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "sponsorship_id" INTEGER NOT NULL,
    "level_name" VARCHAR(255) NOT NULL,
    "level_price" DOUBLE NOT NULL,
    FOREIGN KEY ("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);

CREATE TABLE "Company" (
    "company_id" INTEGER PRIMARY KEY,
    "company_name" TEXT NOT NULL,
    "company_taxNumber" VARCHAR(30) NOT NULL,
    "company_numSeats" INTEGER NOT NULL,
    "company_email" TEXT NOT NULL,
    "company_address" TEXT NOT NULL
);

CREATE TABLE "Member" (
    "member_id" INTEGER PRIMARY KEY,
    "member_name" TEXT NOT NULL,
    "member_email" TEXT,
    "member_phone" TEXT,
    "company_id" INTEGER NOT NULL,
    FOREIGN KEY("company_id") REFERENCES "Company"("company_id")
);

CREATE TABLE "COIIPA_GBMember" (
    "gb_id" INTEGER PRIMARY KEY,
    "gb_name" VARCHAR(50) NOT NULL,
    "gb_rank" VARCHAR(50) NOT NULL
);

CREATE TABLE "Event" (
    "event_id" INTEGER PRIMARY KEY,
    "event_name" VARCHAR(30) NOT NULL,
    "event_edition" VARCHAR(30),
    "event_date" DATE NOT NULL,
    "event_endDate" DATE NOT NULL,
    "event_status" TEXT CHECK("event_status" IN ('Planned', 'Ongoing', 'Completed', 'Closed')) NOT NULL,
    "event_fee" REAL NOT NULL
);

CREATE TABLE "Balance" (
    "balance_id" INTEGER PRIMARY KEY,
    "concept" TEXT NOT NULL,         
    "event_id" INTEGER NOT NULL,     
    "amount" REAL NOT NULL,
    "balance_status" TEXT NOT NULL CHECK("balance_status" IN ('Paid', 'Estimated')),       
    "description" TEXT,            
    FOREIGN KEY("event_id") REFERENCES "Event"("event_id")
);

CREATE TABLE "Sponsorship" (
    "sponsorship_id" INTEGER PRIMARY KEY,
    "sponsorship_name" VARCHAR(50),
    "sponsorship_agreementDate" DATE NOT NULL,
    "company_id" INTEGER NOT NULL,
    "event_id" INTEGER NOT NULL,
    "gb_id" INTEGER NOT NULL,
    "balance_id" INTEGER NOT NULL,
    FOREIGN KEY("company_id") REFERENCES "Company"("company_id"),
    FOREIGN KEY("event_id") REFERENCES "Event"("event_id"),
    FOREIGN KEY("balance_id") REFERENCES "Balance"("balance_id"),
    FOREIGN KEY("gb_id") REFERENCES "COIIPA_GBMember"("gb_id")
);

CREATE TABLE "Invoice" (
    "invoice_id" INTEGER PRIMARY KEY,
    "taxData_name" VARCHAR(30) NOT NULL,
    "invoice_date" DATE NOT NULL,
    "invoice_advance" BOOLEAN DEFAULT FALSE,
    "invoice_number" TEXT NOT NULL,  
    "sponsorship_id" INTEGER NOT NULL,
    FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);


CREATE TABLE "Movement" (
    "movement_id" INTEGER PRIMARY KEY,
    "movement_amount" REAL NOT NULL,
    "movement_date" DATE NOT NULL,
    "balance_id" INTEGER,
    FOREIGN KEY("balance_id") REFERENCES "Balance"("balance_id")
);

COMMIT;