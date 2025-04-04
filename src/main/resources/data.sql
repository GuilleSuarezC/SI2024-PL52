BEGIN TRANSACTION;

-- Borrar datos previos en orden inverso a las dependencias (hijos primero)
DELETE FROM "Movement";
DELETE FROM "Invoice";
DELETE FROM "Sponsorship"; -- Depende de Member, Edition, COIIPA_GBMember, Balance
DELETE FROM "Balance"; -- Depende de Edition
DELETE FROM "Event";
DELETE FROM "COIIPA_GBMember";
DELETE FROM "Member"; -- Depende de Company
DELETE FROM "Company";
DELETE FROM "SponsorshipLevel";

-- Insertar datos en la tabla "Company"
INSERT INTO "Company" (company_name, company_taxNumber, company_numSeats, company_email, company_address) VALUES
    ('Tech Solutions S.L.', 'B12345678', 10, 'tech_solutions@gmail.com', 'C/ Hermanos Felgueroso 2'),
    ('Innovate Asturias', 'B87654321', 15, 'innovate_asturias@gmail.com', 'Av. La costa 34'),
    ('UNIOVI Research', 'G12349876', 20, 'uniovi_research@uniovi.es', 'C/La playa 5');


INSERT INTO "Member" (member_name, member_email, member_phone, company_id) VALUES
    ('Carlos Pérez', 'carlos.perez@techsolutions.com', '600123456', 1),
    ('Laura Gómez', 'laura.gomez@innovateasturias.com', '600654321', 2),
    ('David Fernández', 'david.fernandez@uniovi.es', '600987654', 3),
    ('Ana Martínez', 'ana.martinez@techsolutions.com', '600112233', 3),
    ('Luis Miguel', 'luis.miguel@cajarural.com', '678415210', 4);


-- Poblar Event 
INSERT INTO "Event" (event_name, event_edition, event_date, event_endDate, event_status, event_fee) VALUES  
    ('ImpulsoTIC Week', 'XII', '2025-02-15', '2025-02-18', 'Planned', 3000),  
    ('Hackathon UNIOVI', 'IV', '2025-03-05', '2025-03-07', 'Planned', 4000),  
    ('Asturias AI Summit', 'III', '2025-04-12', '2025-04-13', 'Planned', 5000),
    ('ImpulsoTIC Week 2024', '2024', '2024-11-11', '2024-11-15', 'Closed', 3000),
    ('ImpulsoTIC Week 2025', '2025', '2025-11-10', '2025-11-14', 'Planned', 2000),
    ('Hour Of Code 2025', '2025', '2025-12-08', '2025-12-19', 'Planned', 750);
     
 

-- Poblar Balance
INSERT INTO "Balance" (concept, event_id, amount, balance_status, description, dateOfPaid) VALUES
    ('Sponsorship Tech Solutions S.L.', 1, 3000, 'Paid', 'Tech Solutions S.L.', '2023-01-01'),
    ('Sponsorship Innovate Asturias', 2, 4000, 'Paid', 'Innovate Asturias', '2023-02-01'),
    ('Expense', 3, -1000, 'Unpaid', 'Rent a building for the event', NULL),
    ('AI Research Fund', 3, 5000, 'Unpaid', 'AI Research Fund sponsorship', NULL),
    ('Sponsorship Future Innovators', 3, 5000, 'Unpaid', 'Future Innovators sponsorship', NULL),
    ('Caja Rural de Asturias - ImpulsoTIC Week 2024', 4, 3000, 'Paid', 'Caja Rural de Asturias - ImpulsoTIC Week 2024', '2024-11-01'),
    ('Communication Services', 4, -6000, 'Paid', 'Communication Services', '2024-11-02'),
    ('CCII Grant', 4, 1000, 'Paid', 'CCII Grant', '2024-11-03'),
    ('Caja Rural de Asturias - Hour Of Code 2025', 6, 750, 'Unpaid', 'Caja Rural de Asturias - Hour Of Code 2025', NULL);

    

-- Poblar COIIPA_GBMember

INSERT INTO "COIIPA_GBMember" (gb_name, gb_rank) VALUES
    ('Juan Ramón', 'Dean'),
    ('José García Fanjul', 'Vicedean'),
    ('Rosa', 'Secretary');

-- Insertar datos en la tabla "Event"
INSERT INTO "Event" (event_name, event_edition, event_date, event_endDate, event_status, event_fee) VALUES  
    ('ImpulsoTIC Week', 'XII', '2025-02-15', '2025-02-18', 'Planned', 3000.0),  
    ('Hackathon UNIOVI', 'IV', '2025-03-05', '2025-03-07', 'Planned', 4000.0),  
    ('Asturias AI Summit', 'III', '2025-04-12', '2025-04-13', 'Planned', 5000.0);

-- Insertar datos en la tabla "Balance"
INSERT INTO "Balance" (concept, event_id, amount, balance_status, description, dateOfPaid) VALUES
    ('Sponsorship Tech Solutions S.L.', 1, 3000.0, 'Paid', 'Tech Solutions S.L.', '2023-01-01'),
    ('Sponsorship Innovate Asturias', 2, 4000.0, 'Paid', 'Innovate Asturias', '2023-02-01'),
    ('Expense', 3, -1000.0, 'Unpaid', 'Rent a building for the event', NULL),
    ('AI Research Fund', 3, 5000.0, 'Unpaid', 'AI Research Fund sponsorship', NULL),
    ('Sponsorship Future Innovators', 3, 5000.0, 'Unpaid', 'Future Innovators sponsorship', NULL);

-- Insertar datos en la tabla "Sponsorship"
INSERT INTO "Sponsorship" (sponsorship_name, sponsorship_agreementDate, company_id, event_id, gb_id, balance_id) VALUES
    ('Tech Innovators', '2024-08-01', 1, 1, 1, 1),
    ('Asturias Digital', '2024-08-10', 2, 2, 2, 2),
    ('AI Research Fund', '2024-08-15', 3, 3, 3, 4),
    ('Future Innovators', '2024-10-01', 3, 3, 3, 5),
    ('Caja Rural de Asturias', '2024-10-01', 4, 4, 1, 6),
    ('Caja Rural de Asturias', '2025-01-16', 4, 6, 2, 9);



-- Insertar datos en la tabla "Invoice"
INSERT INTO "Invoice" (taxData_name, invoice_date, invoice_advance, invoice_number, sponsorship_id) VALUES
    ('Tech Solutions S.L.', '2025-02-01',0, 'FAC-001', 1),
    ('Innovate Asturias', '2025-02-20', 0, 'FAC-002', 2),
    ('UNIOVI Research', '2025-03-25', 0, 'FAC-003', 3),
    ('Future Innovators Inc.', '2025-07-01', 0, 'FAC-004', 4);


-- Insertar datos en la tabla "Movement"
INSERT INTO "Movement" (movement_amount, movement_date, balance_id) VALUES
    (3000, '2024-09-10', 1),
    (4000, '2024-09-15', 2),
    (3000, '2024-11-01', 6),
    (-6000, '2024-11-02', 7),
    (1000, '2024-11-03', 8);

COMMIT;
