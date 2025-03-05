BEGIN TRANSACTION;


DELETE FROM "Member";
DELETE FROM "Balance";
DELETE FROM "Invoice";
DELETE FROM "Payment";
DELETE FROM "AgreementType";
DELETE FROM "Sponsorship";
DELETE FROM "COIIPA_GBMember";
DELETE FROM "Company";
DELETE FROM "Event";

-- Poblar Company
INSERT INTO "Company" (company_name, company_numSeats) VALUES
    ('Tech Solutions S.L.', 10),
    ('Innovate Asturias', 15),
    ('UNIOVI Research', 20);

-- Poblar Member
INSERT INTO "Member" (member_name, member_email, member_phone, company_id) VALUES
    ('Carlos Pérez', 'carlos.perez@techsolutions.com', '600123456', 1),
    ('Laura Gómez', 'laura.gomez@innovateasturias.com', '600654321', 2),
    ('David Fernández', 'david.fernandez@uniovi.es', '600987654', 3);

-- Poblar Event
INSERT INTO "Event" (event_name, event_date, event_edition, event_duration, event_status, event_fee) VALUES
    ('ImpulsoTIC Week', '2025-02-15', 'XII', 3, 'Planned', 3000),
    ('Hackathon UNIOVI', '2025-03-05', 'IV', 2, 'Planned', 4000),
    ('Asturias AI Summit', '2025-04-12', 'III', 1, 'Planned', 5000);


-- Poblar Balance con nueva estructura
INSERT INTO "Balance" (event_id, source, amount, description, dateOfPaid) VALUES
    (1, 'Sponsorship Tech Solutions S.L.', 3000, 'Tech Solutions S.L.', '2023-01-01'),
    (2, 'Sponsorship Innovate Asturias', 4000, 'Innovate Asturias', '2023-02-01'),
    (3, 'Expense', -1000, 'Rent a building for the event', '2023-03-01');


-- Poblar Sponsorship
INSERT INTO "Sponsorship" (sponsorship_name, sponsorship_agreementDate, company_id, event_id, payment_id, invoice_id) VALUES
    ('Tech Innovators', '2024-08-01', 1, 1, 1, 1),
    ('Asturias Digital', '2024-08-10', 2, 2, 2, 2),
    ('AI Research Fund', '2024-08-15', 3, 3, 3, 3);

	
-- Poblar Invoice
INSERT INTO "Invoice" (taxData_name, taxData_Fnumber, invoice_date, sponsorship_id) VALUES
    ('Tech Solutions S.L.', 'B12345678', '2025-02-01', 1),
    ('Innovate Asturias', 'B87654321', '2025-02-20', 2),
    ('UNIOVI Research', 'G12349876', '2025-03-25', 3);

	
-- Poblar Payment
INSERT INTO "Payment" (payment_amount, payment_date, payment_status, sponsorship_id) VALUES
    (3000, '2024-09-10', 'Paid', 1),
    (4000, '2024-09-15', 'Paid', 2);

-- Poblar AgreementType
INSERT INTO "AgreementType" (agreement_invoice, agreement_subsidy, sponsorship_id) VALUES
    (1, 'No', 1),
    (2, 'No', 2),
    (3, 'Yes', 3);

-- Poblar COIIPA_GBMember
INSERT INTO "COIIPA_GBMember" (gb_name, gb_rank) VALUES
    ('Juan Ramón', 'Decano'),
    ('José García Fanjul', 'Vicedecano'),
    ('Rosa', 'Secretaria');

COMMIT;
