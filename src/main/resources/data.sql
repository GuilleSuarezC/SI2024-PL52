BEGIN TRANSACTION;

-- Borrar datos previos en orden inverso a las dependencias (hijos primero)
DELETE FROM "AgreementType";
DELETE FROM "Payment";
DELETE FROM "Invoice";
DELETE FROM "Sponsorship"; -- Depende de Member, Edition, COIIPA_GBMember, Balance
DELETE FROM "Balance"; -- Depende de Edition
DELETE FROM "Edition"; -- Depende de Event
DELETE FROM "Event";
DELETE FROM "COIIPA_GBMember";
DELETE FROM "Member"; -- Depende de Company
DELETE FROM "Company";

-- Poblar Company
INSERT INTO "Company" (company_name, company_numSeats, company_email) VALUES
    ('Tech Solutions S.L.', 10, 'tech_solutions@gmail.com'),
    ('Innovate Asturias', 15, 'innovate_asturias@gmail.com'),
    ('UNIOVI Research', 20, 'uniovi_research@uniovi.es');

-- Poblar Member
INSERT INTO "Member" (member_name, member_email, member_phone, company_id) VALUES
    ('Carlos Pérez', 'carlos.perez@techsolutions.com', '600123456', 1),
    ('Laura Gómez', 'laura.gomez@innovateasturias.com', '600654321', 2),
    ('David Fernández', 'david.fernandez@uniovi.es', '600987654', 3);

-- Poblar Event
INSERT INTO "Event" (event_name) VALUES
    ('ImpulsoTIC Week'),
    ('Hackathon UNIOVI'),
    ('Asturias AI Summit');

-- Poblar Edition (corregido: edition_duration en lugar de edition_endDate)
INSERT INTO "Edition" (edition_name, edition_date, edition_endDate, edition_status, edition_fee, event_id) VALUES  
    ('XII', '2025-02-15', 3, 'Planned', 3000, 1),  
    ('IV', '2025-03-05', 2, 'Planned', 4000, 2),  
    ('III', '2025-04-12', 1, 'Planned', 5000, 3); 

-- Poblar Balance
INSERT INTO "Balance" (edition_id, concept, amount, description, dateOfPaid) VALUES
    (1, 'Sponsorship Tech Solutions S.L.', 3000, 'Tech Solutions S.L.', '2023-01-01'),
    (2, 'Sponsorship Innovate Asturias', 4000, 'Innovate Asturias', '2023-02-01'),
    (3, 'Expense', -1000, 'Rent a building for the event', '2023-03-01');

-- Poblar COIIPA_GBMember
INSERT INTO "COIIPA_GBMember" (gb_name, gb_rank) VALUES
    ('Juan Ramón', 'Dean'),
    ('José García Fanjul', 'Vicedean'),
    ('Rosa', 'Secretary');

-- Poblar Sponsorship
INSERT INTO "Sponsorship" (sponsorship_name, sponsorship_agreementDate, member_id, edition_id, gb_id, balance_id) VALUES
    ('Tech Innovators', '2024-08-01', 1, 1, 1, 1),
    ('Asturias Digital', '2024-08-10', 2, 2, 2, 2),
    ('AI Research Fund', '2024-08-15', 3, 3, 3, 3);

-- Poblar Invoice
INSERT INTO "Invoice" (taxData_name, taxData_Fnumber, invoice_date, sponsorship_id) VALUES
    ('Tech Solutions S.L.', 'B12345678', '2025-02-01', 1),
    ('Innovate Asturias', 'B87654321', '2025-02-20', 2),
    ('UNIOVI Research', 'G12349876', '2025-03-25', 3);

-- Poblar Payment
INSERT INTO "Payment" (payment_amount, payment_date, payment_status, sponsorship_id, balance_id) VALUES
    (3000, '2024-09-10', 'Paid', 1, 1),
    (4000, '2024-09-15', 'Paid', 2, 2),
    (5000, '2024-09-20', 'Paid', 3, 3);

-- Poblar AgreementType
INSERT INTO "AgreementType" (agreement_invoice, agreement_subsidy, sponsorship_id) VALUES
    (1, 'No', 1),
    (2, 'No', 2),
    (3, 'Yes', 3);

COMMIT;