BEGIN TRANSACTION;

-- Eliminar tablas si existen
DROP TABLE IF EXISTS "AgreementType";
DROP TABLE IF EXISTS "Member";
DROP TABLE IF EXISTS "Event";
DROP TABLE IF EXISTS "Balance";
DROP TABLE IF EXISTS "Invoice";
DROP TABLE IF EXISTS "Payment";
DROP TABLE IF EXISTS "Sponsorship";
DROP TABLE IF EXISTS "COIIPA_GBMember";
DROP TABLE IF EXISTS "Company";

-- Crear las tablas desde cero
CREATE TABLE "AgreementType" (
    "agreement_id" INTEGER PRIMARY KEY,
    "agreement_invoice" INTEGER NOT NULL,
    "agreement_subsidy" VARCHAR(50) NOT NULL,
    "sponsorship_id" INTEGER NOT NULL,
    FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);

CREATE TABLE "Company" (
    "company_id" INTEGER PRIMARY KEY,
    "company_name" TEXT NOT NULL,
    "company_numSeats" INTEGER NOT NULL,
    "company_email" TEXT NOT NULL
);

CREATE TABLE "Member" (
    "member_id" INTEGER PRIMARY KEY,
    "member_name" TEXT NOT NULL,
    "member_email" TEXT,
    "member_phone" TEXT,
    "company_id" INTEGER NOT NULL,
    FOREIGN KEY("company_id") REFERENCES "Company"("company_id")
);

CREATE TABLE "Event" (
    "event_id" INTEGER PRIMARY KEY,
    "event_name" VARCHAR(30) NOT NULL,
    "event_date" DATE NOT NULL,
    "event_edition" VARCHAR(10) NOT NULL, 
    "event_endDate" INTEGER NOT NULL,
    "event_status" TEXT CHECK("event_status" IN ('Planned', 'Ongoing', 'Completed', 'Closed')),
    "event_fee" INTEGER NOT NULL
);



CREATE TABLE "Balance" (
    "balance_id" INTEGER PRIMARY KEY,
    "concept" TEXT NOT NULL,         
    "event_id" INTEGER NOT NULL,     
    "amount" INTEGER NOT NULL,       
    "description" TEXT,              
    "dateOfPaid" DATE,               
    FOREIGN KEY("event_id") REFERENCES "Event"("event_id")
);


CREATE TABLE "Invoice" (
    "invoice_id" INTEGER PRIMARY KEY,
    "taxData_name" VARCHAR(30) NOT NULL,
    "taxData_Fnumber" VARCHAR(30) NOT NULL,
    "invoice_date" DATE NOT NULL,
    "sponsorship_id" INTEGER,
    FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);

CREATE TABLE "Payment" (
    "payment_id" INTEGER PRIMARY KEY,
    "payment_amount" REAL NOT NULL,
    "payment_date" DATE NOT NULL,
    "payment_status" TEXT NOT NULL CHECK("payment_status" IN ('Paid', 'Overpaid', 'Underpaid', 'Unpaid')),
    "sponsorship_id" INTEGER,
    "balance_id" INTEGER,
    FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id"),
    FOREIGN KEY("balance_id") REFERENCES "Balance"("balance_id")
);

CREATE TABLE "Sponsorship" (
    "sponsorship_id" INTEGER PRIMARY KEY,
    "sponsorship_name" VARCHAR(50),
    "sponsorship_agreementDate" DATE NOT NULL,
    "company_id" INTEGER NOT NULL,
    "event_id" INTEGER NOT NULL,
    "payment_id" INTEGER,
    "invoice_id" INTEGER,
    FOREIGN KEY("company_id") REFERENCES "Company"("company_id"),
    FOREIGN KEY("event_id") REFERENCES "Event"("event_id")
);

CREATE TABLE "COIIPA_GBMember" (
    "gb_id" INTEGER PRIMARY KEY,
    "gb_name" VARCHAR(50) NOT NULL,
    "gb_rank" VARCHAR(50) NOT NULL
);

COMMIT;