BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "AgreementType" (
	"agreement_id"	int NOT NULL,
	"agreement_invoice"	int NOT NULL,
	"agreement_subsidy"	varchar(50) NOT NULL,
	"sponsorship_id"	INT NOT NULL,
	PRIMARY KEY("agreement_id"),
	FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);
CREATE TABLE IF NOT EXISTS "Company" (
	"company_id"	int NOT NULL,
	"company_name"	varchar(50) NOT NULL,
	"company_member"	varchar(50) NOT NULL,
	"company_numSeats"	int NOT NULL,
	PRIMARY KEY("company_id")
);
CREATE TABLE IF NOT EXISTS "Event" (
	"event_id"	int NOT NULL,
	"event_name"	varchar(30) NOT NULL,
	"event_date"	date NOT NULL,
	"event_expenses"	int,
	"event_income"	int,
	"event_status"	TEXT CHECK("event_status" IN ('Planned', 'Ongoing', 'Completed', 'Closed')),
	"sponsorship_level"	TEXT NOT NULL CHECK("sponsorship_level" IN ('Bronze', 'Silver', 'Gold', 'Platinum')),
	PRIMARY KEY("event_id")
);
CREATE TABLE IF NOT EXISTS "Invoice" (
	"invoice_id"	int NOT NULL,
	"taxData_name"	varchar(30) NOT NULL,
	"taxData_Fnumber"	varchar(30) NOT NULL,
	"invoice_date"	date NOT NULL,
	"sponsorship_id"	int,
	PRIMARY KEY("invoice_id"),
	FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);
CREATE TABLE IF NOT EXISTS "Payment" (
	"payment_id"	int NOT NULL,
	"payment_amount"	float NOT NULL,
	"payment_date"	date NOT NULL,
	"payment_status"	TEXT NOT NULL CHECK("payment_status" IN ('Paid', 'Overpaid', 'Underpaid', 'Unpaid')),
	"sponsorship_id"	int,
	PRIMARY KEY("payment_id"),
	FOREIGN KEY("sponsorship_id") REFERENCES "Sponsorship"("sponsorship_id")
);
CREATE TABLE IF NOT EXISTS "Sponsorship" (
	"sponsorship_id"	int NOT NULL,
	"sponsorship_name"	varchar(50),
	"sponsorship_agreementDate"	date NOT NULL,
	"company_id"	int NOT NULL,
	"event_id"	int NOT NULL,
	"payment_id"	int NOT NULL,
	"invoice_id"	int NOT NULL,
	PRIMARY KEY("sponsorship_id"),
	FOREIGN KEY("company_id") REFERENCES "Company"("company_id"),
	FOREIGN KEY("event_id") REFERENCES "Event"("event_id")
);
COMMIT;
