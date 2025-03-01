BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "AgreementType" (
	"agreement_id"	int NOT NULL AUTOINCREMENT,
	"agreement_invoice"	int NOT NULL,
	"agreement_subsidy"	varchar(50) NOT NULL,
	"sponsorship_id"	INT NOT NULL,
	PRIMARY KEY("agreement_id"),
	FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);
CREATE TABLE IF NOT EXISTS "Company" (
	"company_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"company_name"	TEXT NOT NULL,
	"company_numSeats"	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS "Member" (
	"member_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"member_name"	TEXT NOT NULL,
	"member_email"	TEXT,
	"member_phone"	TEXT,
	"company_id"	INTEGER NOT NULL,
	FOREIGN KEY("company_id") REFERENCES "Company"("company_id")
);
CREATE TABLE IF NOT EXISTS "Event" (
	"event_id"	int NOT NULL AUTOINCREMENT,
	"event_name"	varchar(30) NOT NULL,
	"event_date"	date NOT NULL,
	"event_status"	TEXT CHECK("event_status" IN ('Planned', 'Ongoing', 'Completed', 'Closed')),
	"event_fee" int NOT NULL,
	PRIMARY KEY("event_id")
);
CREATE TABLE IF NOT EXISTS "Balance" (
    "balance_id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "event_id" INTEGER NOT NULL,
    "source" TEXT NOT NULL,
    "estimated_income" INTEGER,
    "paid_income" INTEGER,
    "estimated_expenses" INTEGER,
    "paid_expenses" INTEGER,
    FOREIGN KEY("event_id") REFERENCES "Event"("event_id")
);
CREATE TABLE IF NOT EXISTS "Invoice" (
	"invoice_id"	int NOT NULL AUTOINCREMENT,
	"taxData_name"	varchar(30) NOT NULL,
	"taxData_Fnumber"	varchar(30) NOT NULL,
	"invoice_date"	date NOT NULL,
	"sponsorship_id"	int,
	PRIMARY KEY("invoice_id"),
	FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);
CREATE TABLE IF NOT EXISTS "Payment" (
	"payment_id"	int NOT NULL AUTOINCREMENT,
	"payment_amount"	float NOT NULL,
	"payment_date"	date NOT NULL,
	"payment_status"	TEXT NOT NULL CHECK("payment_status" IN ('Paid', 'Overpaid', 'Underpaid', 'Unpaid')),
	"sponsorship_id"	int,
	PRIMARY KEY("payment_id"),
	FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);
CREATE TABLE IF NOT EXISTS "Sponsorship" (
	"sponsorship_id"	int NOT NULL AUTOINCREMENT,
	"sponsorship_name"	varchar(50),
	"sponsorship_agreementDate"	date NOT NULL,
	"company_id"	int NOT NULL,
	"event_id"	int NOT NULL,
	"payment_id"	int NOT NULL,
	"invoice_id"	int NOT NULL,
	PRIMARY KEY("sponsorship_id"),
	FOREIGN KEY("member_id") REFERENCES "Member"("member_id"),
	FOREIGN KEY("event_id") REFERENCES "Event"("event_id")
);
CREATE TABLE IF NOT EXISTS "COIIPA_GBMember" (
	"gb_id" int NOT NULL AUTOINCREMENT,
	"gb_name" varchar(50) NOT NULL,
	"gb_rank" varchar(50) NOT NULL
);
COMMIT;
