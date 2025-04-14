-- #####################################################
-- INSERT TEST DATA
-- #####################################################

INSERT INTO users (id, username, email, first_name, last_name)
VALUES ('86259e24-8c83-4250-bd69-44e867701160', 'csokasi', 'csokasi@encotech.hu', 'Pál', 'Csókási'),
       ('e0aef6e8-6616-4b81-ba70-1073aed57f5d', 'gondos', 'gondos@encotech.hu', 'Dorottya', 'Göndös'),
       ('67b8b432-d9e6-479d-8e2f-da6742b8618f', 'iga', 'iga@encotech.hu', 'Benedek', 'Iga'),
       ('de3d9cb1-6095-430e-9d2a-ccc5648f8fa1', 'kalotai', 'kalotai@encotech.hu', 'János', 'Kalotai'),
       ('94ba0840-e08e-4483-90fe-73908c2ed22a', 'koch', 'koch@encotech.hu', 'Gabriell', 'Koch'),
       ('8c38241a-9734-4340-bdac-73db65382fd9', 'meszaros', 'meszaros@encotech.hu', 'László', 'Mészáros'),
       ('1615211a-af4c-4aa6-88c5-09cb2695f579', 'nahaj', 'nahaj@encotech.hu', 'Dániel', 'Nahaj'),
       ('69559f6d-75e4-4116-a26e-f371fc7436e3', 'poremba', 'poremba@encotech.hu', 'Marcell', 'Poremba');

INSERT INTO companies (name, address, contact_person, email, phone, country, city)
VALUES ('Bosch Magyarország', '1103 Budapest, Gyömrői út 120.', 'Kovács András', 'andras.kovacs@bosch.hu',
        '+36 1 879 2000', 'Magyarország', 'Budapest'),
       ('Trilak Festékgyártó Kft.', '1044 Budapest, Váci út 45.', 'Szabó Katalin', 'katalin.szabo@trilak.hu',
        '+36 1 233 3600', 'Magyarország', 'Budapest'),
       ('Rába Járműipari Holding Nyrt.', '9027 Győr, Martin út 1.', 'Tóth László', 'laszlo.toth@raba.hu',
        '+36 96 624 000', 'Magyarország', 'Győr'),
       ('Herendi Porcelánmanufaktúra Zrt.', '8440 Herend, Kossuth Lajos utca 140.', 'Fekete Gábor',
        'gabor.fekete@herend.com', '+36 88 523 100', 'Magyarország', 'Herend'),
       ('Richter Gedeon Nyrt.', '1103 Budapest, Gyömrői út 19-21.', 'Nagy Emese', 'emese.nagy@richter.hu',
        '+36 1 431 4000', 'Magyarország', 'Budapest');

-- Bosch Magyarország
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (1, 'Bosch Székesfehérvár', '8000 Székesfehérvár, Holland fasor 10.', 'Pintér Márton', 'marton.pinter@bosch.hu',
        '+36 22 534 100', 'Magyarország', 'Székesfehérvár', '8000'),
       (1, 'Bosch Hatvan', '3000 Hatvan, Robert Bosch út 1.', 'Horváth Gergely', 'gergely.horvath@bosch.hu',
        '+36 37 520 100', 'Magyarország', 'Hatvan', '3000');

-- Trilak Festékgyártó Kft.
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (2, 'Trilak Központ', '1044 Budapest, Váci út 45.', 'Németh Balázs', 'balazs.nemeth@trilak.hu', '+36 1 233 3601',
        'Magyarország', 'Budapest', '1044'),
       (2, 'Trilak Raktár', '1239 Budapest, Ócsai út 4.', 'Kiss Péter', 'peter.kiss@trilak.hu', '+36 1 233 3610',
        'Magyarország', 'Budapest', '1239');

-- Rába Járműipari Holding Nyrt.
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (3, 'Rába Futómű Kft.', '9027 Győr, Martin út 1.', 'Kovács Zoltán', 'zoltan.kovacs@raba.hu', '+36 96 624 001',
        'Magyarország', 'Győr', '9027'),
       (3, 'Rába Alkatrészgyártó', '9027 Győr, Budai út 1.', 'Török Eszter', 'eszter.torok@raba.hu', '+36 96 624 010',
        'Magyarország', 'Győr', '9027');

-- Herendi Porcelánmanufaktúra Zrt.
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (4, 'Herendi Látogatóközpont', '8440 Herend, Kossuth u. 140.', 'Kiss Judit', 'judit.kiss@herend.com',
        '+36 88 523 120', 'Magyarország', 'Herend', '8440'),
       (4, 'Herendi Termelés', '8440 Herend, Ipari park 5.', 'Szalai Gábor', 'gabor.szalai@herend.com',
        '+36 88 523 121', 'Magyarország', 'Herend', '8440');

-- Richter Gedeon Nyrt.
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (5, 'Richter Dorog', '2510 Dorog, Bécsi út 1.', 'Oláh Emese', 'emese.olah@richter.hu', '+36 33 431 200',
        'Magyarország', 'Dorog', '2510'),
       (5, 'Richter Budapesti Központ', '1103 Budapest, Gyömrői út 19-21.', 'Molnár Ákos', 'akos.molnar@richter.hu',
        '+36 1 431 4100', 'Magyarország', 'Budapest', '1103');


INSERT INTO equipments (name, identifier, description, manufacturer, type, serial_number, measuring_range, resolution,
                        accuracy, calibration_date, next_calibration_date)
VALUES
-- 1. TESTO 410-2
('TESTO 410-2', 'TESTO-410-2-001', 'Anemométer – légsebesség, hőmérséklet és relatív páratartalom mérése', 'Testo',
 'Anemométer', 'T4102SN12345', '0.4–20 m/s; -10–+50 °C; 0–100% RH', '0.1 m/s; 0.1 °C; 0.1% RH',
 '±0.2 m/s; ±0.5 °C; ±2.5% RH', '2024-05-15', '2025-05-15'),
-- 2. TESTO 511
('TESTO 511', 'TESTO-511-001', 'Digitális barométer – abszolút nyomás és magasság mérésére', 'Testo', 'Barométer',
 'T511SN67890', '300–1200 hPa; -2000–+9000 m', '0.1 hPa; 1 m', '±3 hPa; ±3 m', '2023-12-10', '2024-12-10'),
-- 3. SKC AirChek XR5000
('SKC AirChek XR5000', 'SKC-XR5000-001', 'Személyi levegőmintavevő szivattyú, munkahelyi környezethez', 'SKC Inc.',
 'Mintavevő szivattyú', 'XR5SN23456', '5–5000 ml/min', '1 ml/min', '±5%', '2024-03-20', '2025-03-20'),
-- 4. DryCal Defender 510
('DryCal Defender 510', 'DRYCAL-DEF510-001', 'Elektronikus elsődleges áramlásmérő légmintavevőkhöz', 'Mesa Labs',
 'Áramlásmérő', 'DEF510SN34567', '5–5000 ml/min', '1 ml/min', '±1%', '2024-01-12', '2025-01-12'),
-- 5. SKC IOM Sampler
('SKC IOM Sampler', 'SKC-IOM-001', 'Személyi pormintavevő – hab- és síkszűrővel alkalmazható', 'SKC Inc.',
 'Pormintavevő', 'IOMSN99887', '2 l/min', '0.1 l/min', '±5%', '2024-04-05', '2025-04-05'),
-- 6. Adsorbenssel töltött mintavételi csövek
('Mintavételi cső – adsorbenssel', 'ADSORB-TUBE-001',
 'Illékony szerves anyagokhoz, fluoridhoz, foszforsavhoz alkalmas mintavételi cső', 'SKC Inc.', 'Mintavételi cső',
 'TUBSN11223', '1–2 l/min', '0.1 l/min', 'n/a', '2024-02-01', '2025-02-01'),
-- 7. BW GasAlert MicroClip XL
('BW GasAlert MicroClip XL', 'BW-GASXL-001', 'CO, H2S, O2 és LEL gázérzékelő, többgázos műszer', 'Honeywell BW',
 'Gázérzékelő', 'GASXL123456', '0–500 ppm (CO)', '1 ppm', '±3 ppm', '2024-06-01', '2025-06-01');


INSERT INTO standards (standard_number, description, standard_type, identifier)
VALUES
-- SAMPLING típusú szabványok
('MSZ EN 689:2018+AC:2019', 'Munkahelyi levegő, vegyi anyag expozíció becslése', 'SAMPLING', 'MSZ_EN_689_2018_AC_2019'),
('MSZ 21452-1:1975', 'Nedvességtartalom mérése', 'SAMPLING', 'MSZ_21452_1_1975'),
('MSZ 21452-3:1975', 'Hőmérséklet mérése', 'SAMPLING', 'MSZ_21452_3_1975'),
('MSZ ISO 8756:1995', 'Levegőminőség adatainak figyelembevétele', 'SAMPLING', 'MSZ_ISO_8756_1995'),
('MDHS 14/4:2014', 'Por mintavétel és gravimetriás elemzés', 'SAMPLING', 'MDHS_14_4_2014'),
('MSZ 21862-22:1982', 'Illékony szerves anyagok mintavétele', 'SAMPLING', 'MSZ_21862_22_1982'),
('MDHS 70:1993', 'Szervetlen savak mintavétele', 'SAMPLING', 'MDHS_70_1993'),
('OSHA ID-165SG:1985', 'Foszforsav és fluorid mintavétele', 'SAMPLING', 'OSHA_ID_165SG_1985'),
('MSZ EN ISO 10882-2:2001', 'Szálló por és gázok mintavétele hegesztésnél', 'SAMPLING', 'MSZ_EN_ISO_10882_2_2001'),
('MSZ EN 45544-4:2016', 'Szervetlen gázok folyamatos mérése', 'SAMPLING', 'MSZ_EN_45544_4_2016'),

-- ANALYSES típusú szabványok
('MSZ EN 482:2012+Al:2016', 'Vegyi anyagok mérési eljárásainak követelményei', 'ANALYSES', 'MSZ_EN_482_2012_Al_2016'),
('ISO 16200-1:2001', 'Illékony szerves komponensek meghatározása', 'ANALYSES', 'ISO_16200_1_2001'),
('MSZ 448-18:2009', 'Oldott orto-foszfát tartalom meghatározása', 'ANALYSES', 'MSZ_448_18_2009'),
('MSZ 21862-9:1981', 'HF tartalom meghatározása', 'ANALYSES', 'MSZ_21862_9_1981'),
('EPA 10-3.5:1999', 'Mintaelőkészítés elemekhez', 'ANALYSES', 'EPA_10_3_5_1999'),
('EPA 6020B:2014', 'Elemtartalom meghatározása ICP-MS', 'ANALYSES', 'EPA_6020B_2014');

--Mintavétel típusa
INSERT INTO sampling_types (code, description)
VALUES ('A', 'adszorpciós mintavétel'),
       ('E', 'elnyeletős mintavétel'),
       ('S', 'szűréses mintavétel'),
       ('D', 'diffúz');

-- Mintavételi térfogatáram beállítási módja
INSERT INTO adjustment_methods (code, description)
VALUES ('SZ', 'szappanhártyás áramlásmérő'),
       ('K', 'elektronikus kalibrátor'),
       ('G', 'gázóra');

INSERT INTO clients (name, contact_person, email, phone, address, country, city, postal_code, tax_number)
VALUES
    -- Konzulens cégek
    ('EnviroExpert Tanácsadó Kft.', 'Papp Lilla', 'lilla.papp@enviroexpert.hu', '+36 30 123 4567', 'Tétényi út 72.',
     'Magyarország', 'Budapest', '1119', '67890123-2-05'),
    ('EcoSafe Solutions Zrt.', 'Takács Gergely', 'gergely.takacs@ecosafe.hu', '+36 70 987 6543', 'Bécsi út 267.',
     'Magyarország', 'Budapest', '1037', '78901234-1-77'),
    ('Környezettechnika Konzorcium Bt.', 'Kiss Anikó', 'anikokiss@ktkbt.hu', '+36 20 456 7890', 'Irgalmasok utcája 1.',
     'Magyarország', 'Pécs', '7621', '89012345-2-29'),

    ('Bosch Magyarország', 'Kovács András', 'andras.kovacs@bosch.hu', '+36 1 879 2000', 'Gyömrői út 120.',
     'Magyarország', 'Budapest', '1103', '12345678-1-42'),
    ('Trilak Festékgyártó Kft.', 'Szabó Katalin', 'katalin.szabo@trilak.hu', '+36 1 233 3600', 'Váci út 45.',
     'Magyarország', 'Budapest', '1044', '23456789-2-44'),
    ('Rába Járműipari Holding Nyrt.', 'Tóth László', 'laszlo.toth@raba.hu', '+36 96 624 000', 'Martin út 1.',
     'Magyarország', 'Győr', '9027', '34567890-1-08'),
    ('Herendi Porcelánmanufaktúra Zrt.', 'Fekete Gábor', 'gabor.fekete@herend.com', '+36 88 523 100',
     'Kossuth Lajos utca 140.', 'Magyarország', 'Herend', '8440', '45678901-2-19'),
    ('Richter Gedeon Nyrt.', 'Nagy Emese', 'emese.nagy@richter.hu', '+36 1 431 4000', 'Gyömrői út 19-21.',
     'Magyarország', 'Budapest', '1103', '56789012-1-33');

-- Projekt 1 – Bosch Magyarország
INSERT INTO projects (project_number, client_id, project_name, start_date, end_date, status, description)
VALUES ('PRJ-2024-001', 1, 'Légtechnikai audit és CO mérés', '2024-03-01', '2024-03-25', 'COMPLETED',
        'A gyártócsarnok szellőztető rendszerének teljeskörű auditja, gázkoncentráció mérésekkel.');
-- Projekt 2 – EcoSafe Solutions Zrt.
INSERT INTO projects (project_number, client_id, project_name, start_date, status, description)
VALUES ('PRJ-2024-002', 7, 'Folyamatban lévő veszélyesanyag kockázatelemzés', '2024-04-10', 'ONGOING',
        'Szerves oldószerek kibocsátásának értékelése és kockázatelemzés IPPC jelentéshez.');
-- Projekt 3 – Herendi Porcelánmanufaktúra Zrt.
INSERT INTO projects (project_number, client_id, project_name, start_date, end_date, status, description)
VALUES ('PRJ-2023-015', 4, 'Por emisszió vizsgálat – égetőkemencék', '2023-11-01', '2023-11-20', 'COMPLETED',
        'Szilárd részecske koncentráció meghatározása gravimetriás módszerrel.');
-- Projekt 4 – EnviroExpert Tanácsadó Kft.
INSERT INTO projects (project_number, client_id, project_name, start_date, status, description)
VALUES ('PRJ-2025-005', 6, 'Mintavételi módszerek validálása', '2025-01-15', 'ONGOING',
        'Új ISO és MSZ szabványok szerinti mintavételi módszerek bevezetése és összehasonlító vizsgálata.');
-- Projekt 5 – Rába Járműipari Holding Nyrt. – Emissziómérés
INSERT INTO projects (project_number, client_id, project_name, start_date, status, description)
VALUES ('PRJ-2024-003', 3, 'Kibocsátási vizsgálat – festőüzem VOC emissziók', '2024-04-01', 'ONGOING',
        'Illékony szerves vegyületek (VOC) mérés mintavételi csövekkel, MSZ 21862-22:1982 szabvány alapján.');
-- Projekt 6 – Környezettechnika Konzorcium Bt. – Zajmérés
INSERT INTO projects (project_number, client_id, project_name, start_date, end_date, status, description)
VALUES ('PRJ-2023-027', 8, 'Környezeti zajvizsgálat – ipari terület határérték ellenőrzés', '2023-10-05', '2023-10-07',
        'COMPLETED', 'Zajkibocsátás mérése 24 órás ciklusban több ponton, MSZ 18150 alkalmazásával.');
-- Projekt 7 – Trilak Festékgyártó Kft. – Munkahelyi légtér vizsgálat
INSERT INTO projects (project_number, client_id, project_name, start_date, status, description)
VALUES ('PRJ-2024-004', 2, 'Munkahelyi légtér vizsgálat – szerves oldószerek és por koncentráció', '2024-04-15',
        'ONGOING',
        'Teljes munkahelyi expozíció becslés MDHS 14/4:2014 és MSZ EN 689:2018 alapján; VOC és porfrakciók mérése személyi mintavevőkkel.');
-- Projekt 8 – Richter Gedeon Nyrt.
INSERT INTO projects (project_number, client_id, project_name, start_date, status, description)
VALUES ('PRJ-2024-008', 5, 'Munkahelyi légtér vizsgálat – gyógyszergyártó üzem', '2024-04-20', 'ONGOING',
        'Organikus oldószerek és por koncentráció mérése a tablettázó üzemben, MSZ EN 689 szerint.');

-- Projekt 9 – EnviroExpert Tanácsadó Kft. (saját vizsgálat laborvalidáláshoz)
INSERT INTO projects (project_number, client_id, project_name, start_date, status, description)
VALUES ('PRJ-2024-009', 6, 'Szimulált munkahelyi légtér vizsgálat – módszerteszt', '2024-04-22', 'ONGOING',
        'Laboratóriumi tesztelés validálási célra, személyi mintavétel és környezeti mérések kombinálása.');


-- Jegyzőkönyv 1 – Trilak (projekt_id: 7)
INSERT INTO sampling_records_dat_m200 (sampling_date, conducted_by, site_location_id, company_id, tested_plant,
                                       technology,
                                       shift_count_and_duration, workers_per_shift, exposure_time, temperature,
                                       humidity, wind_speed,
                                       pressure1, pressure2, other_environmental_conditions, air_flow_conditions,
                                       operation_mode,
                                       operation_break, local_air_extraction, serial_numbers_of_samples, project_number,
                                       remarks)
VALUES ('2024-04-15 08:00:00', 'e0aef6e8-6616-4b81-ba70-1073aed57f5d', 2, 2,
        'Festőüzem', 'Oldószer alapú festés',
        2, 8, 480, 21.5, 55.2, 0.8,
        1013.25, 1010.10, 'Zárt tér, minimális természetes szellőzés', 'Nincs mesterséges légcsere',
        'Napi 2 műszak', 'Ebédszünet + karbantartás 20 perc', 'Helyi elszívás: részleges',
        'TRI-24', 7,
        'Oldószergőz jelenlét érezhető volt a csarnok több pontján.');

-- Jegyzőkönyv 2 – Richter (projekt_id: 8)
INSERT INTO sampling_records_dat_m200 (sampling_date, conducted_by, site_location_id, company_id, tested_plant,
                                       technology,
                                       shift_count_and_duration, workers_per_shift, exposure_time, temperature,
                                       humidity, wind_speed,
                                       pressure1, pressure2, other_environmental_conditions, air_flow_conditions,
                                       operation_mode,
                                       operation_break, local_air_extraction, serial_numbers_of_samples, project_number,
                                       remarks)
VALUES ('2024-04-22 07:30:00', '67b8b432-d9e6-479d-8e2f-da6742b8618f', 9, 5,
        'Tablettázó üzem', 'Gyógyszer granulálás',
        3, 12, 480, 23.8, 48.9, 0.5,
        1010.80, 1008.60, 'Géptermek hőleadása jelentős', 'Légkondicionált tér, központi szűrés',
        'Folyamatos', '10 perces szünetek műszakonként', 'HEPA szűrős elszívás aktív',
        'RIC-24', 8,
        'A légsebesség alacsonyabb volt az optimálisnál a belső zónában.');

-- Jegyzőkönyv 3 – EnviroExpert (projekt_id: 9)
INSERT INTO sampling_records_dat_m200 (sampling_date, conducted_by, site_location_id, company_id, tested_plant,
                                       technology,
                                       shift_count_and_duration, workers_per_shift, exposure_time, temperature,
                                       humidity, wind_speed,
                                       pressure1, pressure2, other_environmental_conditions, air_flow_conditions,
                                       operation_mode,
                                       operation_break, local_air_extraction, serial_numbers_of_samples, project_number,
                                       remarks)
VALUES ('2024-04-24 10:00:00', '69559f6d-75e4-4116-a26e-f371fc7436e3', 10, 4,
        'Szimulált vizsgáló labor', 'Független mintavételi elrendezés',
        1, 2, 240, 22.2, 50.0, 0.0,
        1015.00, 1015.00, 'Laboratóriumi kontrollált körülmények', 'Statikus tér',
        'Egyszeri teszt', 'Nincs', 'Nem alkalmazott',
        'ENV-24', 9,
        'Kontrollmérés referencia mintákkal.');

-- Jegyzőkönyv 1 – Trilak
INSERT INTO sampling_record_equipments (fk_sampling_record_id, fk_equipment_id)
VALUES (1, 1), -- TESTO 410-2 (hőmérséklet, páratartalom)
       (1, 3), -- SKC AirChek XR5000 (mintavevő)
       (1, 5), -- SKC IOM Sampler (porminta)
       (1, 6);
-- Adsorbens cső (VOC)

-- Jegyzőkönyv 2 – Richter
INSERT INTO sampling_record_equipments (fk_sampling_record_id, fk_equipment_id)
VALUES (2, 1), -- TESTO 410-2
       (2, 2), -- TESTO 511 (nyomás)
       (2, 3), -- SKC AirChek XR5000
       (2, 5);
-- SKC IOM Sampler

-- Jegyzőkönyv 3 – EnviroExpert (laborvalidálás)
INSERT INTO sampling_record_equipments (fk_sampling_record_id, fk_equipment_id)
VALUES (3, 1), -- TESTO 410-2
       (3, 3), -- SKC AirChek XR5000
       (3, 4), -- DryCal Defender 510 (kalibrálás)
       (3, 6); -- Adsorbens cső


INSERT INTO contaminant_groups (name, description)
VALUES ('Lúgok', 'Erősen maró hatású bázikus vegyületek, például hidroxidok'),
       ('Fémek és fém-oxidok', 'Belélegezhető vagy respirábilis fémrészecskék'),
       ('Szervetlen savak', 'Sav jellegű szervetlen szennyezők'),
       ('Gázok', 'Szén-monoxid és hasonló légszennyezők'),
       ('Illékony szerves anyagok (VOC)', 'Szerves oldószerek és komponensek, alacsony forrásponttal'),
       ('Szénhidrogének', 'Egyszerű szénhidrogének, mint például heptán'),
       ('Szálló por', 'Respirábilis és belélegezhető porfrakciók a levegőben');


-- Lúgok
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Kálium-hidroxid (KOH)', 'Maró hatású lúg', 1),
       ('Nátrium-hidroxid (NaOH)', 'Maró hatású lúg', 1);

-- Fémek és fém-oxidok
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Nikkel (Ni)', 'Respirábilis és belélegezhető frakcióban is', 2),
       ('Mangán (Mn)', 'Respirábilis és belélegezhető frakcióban is', 2),
       ('Kobalt (Co)', 'Belélegezhető fémpor', 2),
       ('Cink-oxid (ZnO)', 'Fém-oxid gőzök belélegezhető formában', 2);

-- Szervetlen savak
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Foszforsav', 'Erősen korrozív savas komponens', 3),
       ('Fluoridok', 'Fluorvegyületek, gyakran savas közegben', 3);

-- Gázok és gőzök
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Szén-monoxid (CO)', 'Színtelen, szagtalan mérgező gáz', 4);

-- VOC – Illékony szerves anyagok
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Toluol', 'Aromás szénhidrogén, oldószer', 5),
       ('i-Propil-alkohol', 'Alkohol alapú VOC komponens', 5),
       ('Etanol', 'Illékony alkohol', 5),
       ('n-Butanol', 'Nagyobb molekulatömegű alkohol', 5),
       ('Aceton', 'Nagy illékonyságú keton', 5),
       ('n-Butil-acetát', 'Észter típusú VOC', 5),
       ('1-Metoxi-2-propil-acetát', 'Éter-észter típusú oldószer', 5),
       ('1-Metoxi-2-propanol', 'Éteres alkohol', 5),
       ('2-Butoxi-etanol', 'Glikol-éter vegyület', 5);

-- Szénhidrogének
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('n-Heptán', 'Alifás szénhidrogén, magas illékonyság', 6);

-- Szálló por
INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Szálló por (respirábilis és belélegezhető)', 'Munkahelyi légtérben lebegő részecskék', 7);


-- Base SI units
INSERT INTO measurement_units (unit_code, description, unit_category, base_unit_id, conversion_factor, standard_body)
VALUES ('mg', 'Milligram', 'Mass', NULL, NULL, 'SI'),
       ('ug', 'Microgram', 'Mass', 1, 0.001, 'SI'),
       ('m3', 'Cubic meter', 'Volume', NULL, NULL, 'SI'),
       ('l', 'Liter', 'Volume', 3, 0.001, 'SI'),
-- Concentration
       ('mg/m3', 'Milligrams per cubic meter', 'Concentration', NULL, NULL, 'SI'),
       ('ug/m3', 'Micrograms per cubic meter', 'Concentration', 5, 0.001, 'SI'),
       ('ppm', 'Parts per million', 'Concentration', NULL, NULL, 'ISO'),
       ('ppb', 'Parts per billion', 'Concentration', 7, 0.001, 'ISO'),
-- Flow rate
       ('l/min', 'Liters per minute', 'Flow rate', NULL, NULL, 'SI'),
       ('m3/h', 'Cubic meters per hour', 'Flow rate', NULL, NULL, 'SI'),
-- Velocity
       ('m/s', 'Meters per second', 'Velocity', NULL, NULL, 'SI'),
-- Pressure
       ('hPa', 'Hectopascal', 'Pressure', NULL, NULL, 'SI'),
       ('Pa', 'Pascal', 'Pressure', 12, 0.01, 'SI'),
-- Temperature
       ('C', 'Degrees Celsius', 'Temperature', NULL, NULL, 'SI'),
-- Humidity
       ('percent', 'Relative Humidity Percentage', 'Humidity', NULL, NULL, 'SI');


-- ------------------------------
-- Sampling Record ID = 1 (Trilak)
-- ------------------------------
INSERT INTO samples (sampling_record_id, sample_identifier, location, employee_name, temperature, humidity, pressure,
                     sample_volume_flow_rate, sample_volume_flow_rate_unit, start_time, end_time, sample_type, remarks)
VALUES (1, 'TRI-001', 'Festőüzem 1', 'Nagy László', 21.5, 55.0, 1013.2, 2.0000, 9, '2024-04-15 08:00',
        '2024-04-15 08:30', 'AK', 'Minta a keverő közelében'),
       (1, 'TRI-002', 'Festőüzem 1', 'Kovács Anna', 21.6, 54.8, 1013.3, 2.0000, 9, '2024-04-15 08:30',
        '2024-04-15 09:00', 'AK', NULL),
       (1, 'TRI-003', 'Festőüzem 2', 'Tóth Gábor', 21.4, 55.5, 1013.1, 2.0000, 9, '2024-04-15 09:00',
        '2024-04-15 09:30', 'AK', NULL),
       (1, 'TRI-004', 'Festőüzem 2', 'Szabó Eszter', 21.7, 55.3, 1013.0, 2.0000, 9, '2024-04-15 09:30',
        '2024-04-15 10:00', 'AK', NULL),
       (1, 'TRI-005', 'Festőüzem 3', 'Kiss Péter', 21.3, 55.1, 1012.8, 2.0000, 9, '2024-04-15 10:00',
        '2024-04-15 10:30', 'AK', NULL),
       (1, 'TRI-006', 'Festőüzem 3', 'Molnár Dóra', 21.5, 55.0, 1013.0, 2.0000, 9, '2024-04-15 10:30',
        '2024-04-15 11:00', 'AK', NULL),
       (1, 'TRI-007', 'Festőüzem 4', 'Németh Bence', 21.6, 55.2, 1013.1, 2.0000, 9, '2024-04-15 11:00',
        '2024-04-15 11:30', 'AK', NULL),
       (1, 'TRI-008', 'Festőüzem 4', 'Balogh Júlia', 21.8, 54.9, 1013.2, 2.0000, 9, '2024-04-15 11:30',
        '2024-04-15 12:00', 'AK', NULL),
       (1, 'TRI-009', 'Festőüzem 5', 'Farkas Gergely', 21.9, 55.4, 1012.9, 2.0000, 9, '2024-04-15 12:00',
        '2024-04-15 12:30', 'AK', NULL),
       (1, 'TRI-010', 'Festőüzem 5', 'Horváth Ágnes', 22.0, 55.6, 1012.7, 2.0000, 9, '2024-04-15 12:30',
        '2024-04-15 13:00', 'AK', NULL);

-- ------------------------------
-- Sampling Record ID = 2 (Richter)
-- ------------------------------
INSERT INTO samples (sampling_record_id, sample_identifier, location, employee_name, temperature, humidity, pressure,
                     sample_volume_flow_rate, sample_volume_flow_rate_unit, start_time, end_time, sample_type)
VALUES (2, 'RIC-001', 'Tablettázó 1', 'Szalai Imre', 23.8, 49.0, 1010.8, 1.8000, 9, '2024-04-22 07:30',
        '2024-04-22 08:00', 'AK'),
       (2, 'RIC-002', 'Tablettázó 1', 'Lukács Laura', 23.9, 49.2, 1010.7, 1.8000, 9, '2024-04-22 08:00',
        '2024-04-22 08:30', 'AK'),
       (2, 'RIC-003', 'Tablettázó 2', 'Kocsis Zoltán', 24.0, 49.1, 1010.9, 1.8000, 9, '2024-04-22 08:30',
        '2024-04-22 09:00', 'AK'),
       (2, 'RIC-004', 'Tablettázó 2', 'Kelemen Nóra', 23.7, 48.9, 1010.6, 1.8000, 9, '2024-04-22 09:00',
        '2024-04-22 09:30', 'AK'),
       (2, 'RIC-005', 'Tablettázó 3', 'Varga Tamás', 23.6, 49.3, 1010.5, 1.8000, 9, '2024-04-22 09:30',
        '2024-04-22 10:00', 'AK'),
       (2, 'RIC-006', 'Tablettázó 3', 'Sipos Dóra', 23.5, 49.4, 1010.4, 1.8000, 9, '2024-04-22 10:00',
        '2024-04-22 10:30', 'AK'),
       (2, 'RIC-007', 'Tablettázó 4', 'Barna Bálint', 23.4, 49.2, 1010.3, 1.8000, 9, '2024-04-22 10:30',
        '2024-04-22 11:00', 'AK'),
       (2, 'RIC-008', 'Tablettázó 4', 'Fülöp Emese', 23.3, 49.5, 1010.2, 1.8000, 9, '2024-04-22 11:00',
        '2024-04-22 11:30', 'AK'),
       (2, 'RIC-009', 'Tablettázó 5', 'Pintér Ádám', 23.2, 49.6, 1010.1, 1.8000, 9, '2024-04-22 11:30',
        '2024-04-22 12:00', 'AK'),
       (2, 'RIC-010', 'Tablettázó 5', 'Török Zsuzsanna', 23.1, 49.7, 1010.0, 1.8000, 9, '2024-04-22 12:00',
        '2024-04-22 12:30', 'AK');

-- ------------------------------
-- Sampling Record ID = 3 (EnviroExpert)
-- ------------------------------
INSERT INTO samples (sampling_record_id, sample_identifier, location, employee_name, temperature, humidity, pressure,
                     sample_volume_flow_rate, sample_volume_flow_rate_unit, start_time, end_time, sample_type)
VALUES (3, 'ENV-001', 'Labor 1', 'Vajda László', 22.2, 50.0, 1015.0, 1.5000, 9, '2024-04-24 10:00', '2024-04-24 10:20',
        'CK'),
       (3, 'ENV-002', 'Labor 1', 'Takács Irén', 22.3, 50.2, 1015.0, 1.5000, 9, '2024-04-24 10:20', '2024-04-24 10:40',
        'CK'),
       (3, 'ENV-003', 'Labor 1', 'Török Levente', 22.4, 50.1, 1015.0, 1.5000, 9, '2024-04-24 10:40', '2024-04-24 11:00',
        'CK'),
       (3, 'ENV-004', 'Labor 2', 'Szűcs Márta', 22.1, 50.3, 1015.0, 1.5000, 9, '2024-04-24 11:00', '2024-04-24 11:20',
        'CK'),
       (3, 'ENV-005', 'Labor 2', 'Simon Kristóf', 22.0, 50.4, 1015.0, 1.5000, 9, '2024-04-24 11:20', '2024-04-24 11:40',
        'CK'),
       (3, 'ENV-006', 'Labor 2', 'Kocsis Kitti', 21.9, 50.5, 1015.0, 1.5000, 9, '2024-04-24 11:40', '2024-04-24 12:00',
        'CK'),
       (3, 'ENV-007', 'Labor 3', 'Pásztor Balázs', 21.8, 50.6, 1015.0, 1.5000, 9, '2024-04-24 12:00',
        '2024-04-24 12:20', 'CK'),
       (3, 'ENV-008', 'Labor 3', 'Oláh Judit', 21.7, 50.7, 1015.0, 1.5000, 9, '2024-04-24 12:20', '2024-04-24 12:40',
        'CK'),
       (3, 'ENV-009', 'Labor 3', 'Vincze András', 21.6, 50.8, 1015.0, 1.5000, 9, '2024-04-24 12:40', '2024-04-24 13:00',
        'CK'),
       (3, 'ENV-010', 'Labor 3', 'Jakab Erika', 21.5, 50.9, 1015.0, 1.5000, 9, '2024-04-24 13:00', '2024-04-24 13:20',
        'CK');


-- Trilak minták: VOC + por
INSERT INTO sample_contaminants (fk_sample_id, fk_contaminant_id)
VALUES
-- TRI-001 to TRI-010 → VOC: Toluol (10), Aceton (14), n-Butil-acetát (15), Por (21)
(1, 10),
(1, 14),
(1, 15),
(1, 20),
(2, 10),
(2, 14),
(2, 15),
(2, 20),
(3, 10),
(3, 14),
(3, 15),
(3, 20),
(4, 10),
(4, 14),
(4, 15),
(4, 20),
(5, 10),
(5, 14),
(5, 15),
(5, 20),
(6, 10),
(6, 14),
(6, 15),
(6, 20),
(7, 10),
(7, 14),
(7, 15),
(7, 20),
(9, 10),
(9, 14),
(9, 15),
(9, 20),
(10, 10),
(10, 14),
(10, 15),
(10, 20);

-- Richter minták: Fémek (Ni: 3, Mn: 4, ZnO: 6), CO (9), Por (21)
INSERT INTO sample_contaminants (fk_sample_id, fk_contaminant_id)
VALUES (11, 3),
       (11, 4),
       (11, 6),
       (11, 9),
       (11, 20),
       (12, 3),
       (12, 4),
       (12, 6),
       (12, 9),
       (12, 20),
       (13, 3),
       (13, 4),
       (13, 6),
       (13, 9),
       (13, 20),
       (14, 3),
       (14, 4),
       (14, 6),
       (14, 9),
       (14, 20),
       (15, 3),
       (15, 4),
       (15, 6),
       (15, 9),
       (15, 20),
       (16, 3),
       (16, 4),
       (16, 6),
       (16, 9),
       (16, 20),
       (17, 3),
       (17, 4),
       (17, 6),
       (17, 9),
       (17, 20),
       (18, 3),
       (18, 4),
       (18, 6),
       (18, 9),
       (18, 20),
       (19, 3),
       (19, 4),
       (19, 6),
       (19, 9),
       (19, 20),
       (20, 3),
       (20, 4),
       (20, 6),
       (20, 9),
       (20, 20);

-- EnviroExpert (labor): reprezentatív tesztelés – 1-2 komponens minden csoportból
INSERT INTO sample_contaminants (fk_sample_id, fk_contaminant_id)
VALUES (21, 1),
       (21, 3),
       (21, 7),
       (21, 10),
       (21, 19),
       (22, 2),
       (22, 4),
       (22, 8),
       (22, 11),
       (22, 19),
       (23, 3),
       (23, 5),
       (23, 7),
       (23, 13),
       (23, 19),
       (24, 4),
       (24, 6),
       (24, 8),
       (24, 14),
       (24, 19),
       (25, 3),
       (25, 4),
       (25, 7),
       (25, 15),
       (25, 19),
       (26, 1),
       (26, 5),
       (26, 7),
       (26, 13),
       (26, 19),
       (27, 2),
       (27, 6),
       (27, 8),
       (27, 14),
       (27, 19),
       (28, 1),
       (28, 3),
       (28, 9),
       (28, 11),
       (28, 19),
       (29, 2),
       (29, 4),
       (29, 10),
       (29, 13),
       (29, 19),
       (30, 1),
       (30, 3),
       (30, 6),
       (30, 14),
       (30, 19);


-- Insert Analytical Laboratories
INSERT INTO laboratories (name, accreditation, contact_email, phone, address, website, created_at, updated_at)
VALUES ('Bálint Analitika Kft.', 'NAH-1-1666/2019', 'titkarsag@balintanalitika.hu', '+36 1 206 0732',
        '1116 Budapest, Kondorfa utca 6.', 'https://balintanalitika.hu/', NOW(), NOW()),
       ('Encotech Kft.', 'NAH-1-1201/2019', 'info@encotech.hu', '+36 1 303 7848', '1089 Budapest, Bláthy Ottó u. 41.',
        'https://encotech.hu/en', NOW(), NOW()),
       ('Biokör Kft.', 'NAH-1-5678/2021', 'info@biokor.hu', '+36 1 876 5432', '1037 Budapest, Montevideo utca 3.',
        'http://www.biokor.hu', NOW(), NOW()),
       ('SGS Hungária Kft.', 'NAH-1-4321/2019', 'sgs.hungaria@sgs.com', '+36 1 309 3300',
        '1124 Budapest, Sirály utca 4.', 'https://www.sgs.com/en-hu', NOW(), NOW()),
       ('Eurofins Analytical Services Hungary Kft.', 'NAH-1-8765/2022', 'info@eurofins.hu', '+36 1 555 1234',
        '1047 Budapest, Attila utca 1.', 'https://www.eurofins.hu/hu/environment-testing-en/', NOW(), NOW()),
       ('TÜV Rheinland InterCert Kft.', 'NAH-1-2345/2018', 'info@hu.tuv.com', '+36 1 461 1100',
        '1143 Budapest, Gizella út 51-57.', 'https://www.tuv.com/hungary/en/', NOW(), NOW());

-- Jelentés 1 – Trilak (Bálint Analitika)
INSERT INTO analytical_lab_reports (report_number, issue_date, laboratory_id)
VALUES ('TRI-RPT-2024-001', '2024-04-25', 1);

-- Jelentés 2 – Richter (Eurofins)
INSERT INTO analytical_lab_reports (report_number, issue_date, laboratory_id)
VALUES ('RIC-RPT-2024-001', '2024-04-26', 5);

-- Jelentés 3 – EnviroExpert (Biokör)
INSERT INTO analytical_lab_reports (report_number, issue_date, laboratory_id)
VALUES ('ENV-RPT-2024-001', '2024-04-27', 3);

INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (1, 2.4633, NULL, NULL, 5, FALSE, 0.0411, 8.65, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.4633,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (2, 1.4238, NULL, NULL, 5, FALSE, 0.0279, 8.77, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.4238,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (3, 0.7379, NULL, NULL, 5, FALSE, 0.0421, 6.63, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.7379,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (4, 1.0333, NULL, NULL, 5, FALSE, 0.0143, 4.82, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.0333,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (5, 1.7626, NULL, NULL, 5, FALSE, 0.0463, 5.89, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.7626,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (6, 1.636, NULL, NULL, 5, FALSE, 0.0413, 3.55, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.636, 5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (7, 1.1215, NULL, NULL, 5, FALSE, 0.029, 3.91, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.1215,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (8, 1.1585, NULL, NULL, 5, FALSE, 0.0181, 4.41, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.1585,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (9, 0.5774, NULL, NULL, 5, FALSE, 0.0421, 7.77, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.5774,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (10, 1.9063, NULL, NULL, 5, FALSE, 0.0289, 7.39, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.9063,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (11, 0.3096, NULL, NULL, 5, FALSE, 0.0261, 7.05, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.3096,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (12, 1.2421, NULL, NULL, 5, FALSE, 0.0279, 4.37, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.2421,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (13, 0.4139, NULL, NULL, 5, FALSE, 0.0201, 5.22, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.4139,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (14, 0.5106, NULL, NULL, 5, FALSE, 0.0128, 7.08, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.5106,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (15, 0.9967, NULL, NULL, 5, FALSE, 0.0141, 6.8, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.9967,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (16, 2.3059, NULL, NULL, 5, FALSE, 0.011, 2.57, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.3059,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (17, 0.8427, NULL, NULL, 5, FALSE, 0.0113, 7.13, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.8427,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (18, 1.0937, NULL, NULL, 5, FALSE, 0.0259, 9.53, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.0937,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (19, 1.3779, NULL, NULL, 5, FALSE, 0.0487, 4.75, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.3779,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (20, 2.3658, NULL, NULL, 5, FALSE, 0.0496, 5.46, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.3658,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (21, 0.9257, NULL, NULL, 5, FALSE, 0.0408, 2.55, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.9257,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (22, 0.7558, NULL, NULL, 5, FALSE, 0.0417, 8.41, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.7558,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (23, 2.4079, NULL, NULL, 5, FALSE, 0.0182, 7.02, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.4079,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (24, 2.1441, NULL, NULL, 5, FALSE, 0.0213, 7.47, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.1441,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (25, 1.9872, NULL, NULL, 5, FALSE, 0.036, 3.75, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.9872,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (26, 0.2068, NULL, NULL, 5, FALSE, 0.041, 6.04, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.2068,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (27, 0.5459, NULL, NULL, 5, FALSE, 0.0174, 7.22, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.5459,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (28, 1.7567, NULL, NULL, 5, FALSE, 0.0168, 8.12, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.7567,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (29, 2.3787, NULL, NULL, 5, FALSE, 0.033, 4.01, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.3787,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (30, 1.0732, NULL, NULL, 5, FALSE, 0.0168, 7.55, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.0732,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (31, 1.9405, NULL, NULL, 5, FALSE, 0.0394, 3.03, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.9405,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (32, 0.5454, NULL, NULL, 5, FALSE, 0.0314, 5.76, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.5454,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (33, 2.2647, NULL, NULL, 5, FALSE, 0.0403, 5.34, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.2647,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (34, 2.1574, NULL, NULL, 5, FALSE, 0.0159, 7.16, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.1574,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (35, 0.6626, NULL, NULL, 5, FALSE, 0.0196, 9.61, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.6626,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (36, 0.9635, NULL, NULL, 5, FALSE, 0.0428, 5.85, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.9635,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (37, 0.9214, NULL, NULL, 5, FALSE, 0.0365, 9.31, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 0.9214,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (38, 2.1764, NULL, NULL, 5, FALSE, 0.0108, 2.06, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 2.1764,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (39, 1.5245, NULL, NULL, 5, FALSE, 0.0394, 6.45, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.5245,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (40, 1.3967, NULL, NULL, 5, FALSE, 0.0399, 3.5, 'GC-FID (MSZ 21862-22:1982)', 1, '2024-04-26 00:00:00', 1.3967,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (41, 1.1062, NULL, NULL, 5, FALSE, 0.0393, 9.86, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.1062,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (42, 1.7482, NULL, NULL, 5, FALSE, 0.0154, 2.2, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.7482,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (43, 0.364, NULL, NULL, 5, FALSE, 0.0186, 5.06, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.364,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (44, 2.0124, NULL, NULL, 5, FALSE, 0.0186, 2.4, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.0124,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (45, 0.9205, NULL, NULL, 5, FALSE, 0.0353, 6.81, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.9205,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (46, 0.9761, NULL, NULL, 5, FALSE, 0.0257, 5.96, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.9761,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (47, 2.3976, NULL, NULL, 5, FALSE, 0.0322, 9.12, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.3976,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (48, 1.1445, NULL, NULL, 5, FALSE, 0.0415, 6.3, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.1445,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (49, 0.2173, NULL, NULL, 5, FALSE, 0.037, 5.56, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.2173,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (50, 0.7918, NULL, NULL, 5, FALSE, 0.0429, 9.74, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.7918,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (51, 2.2282, NULL, NULL, 5, FALSE, 0.0173, 8.37, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.2282,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (52, 0.4072, NULL, NULL, 5, FALSE, 0.0175, 6.23, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.4072,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (53, 1.6328, NULL, NULL, 5, FALSE, 0.0284, 5.29, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.6328,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (54, 2.4412, NULL, NULL, 5, FALSE, 0.0148, 9.62, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.4412,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (55, 1.1601, NULL, NULL, 5, FALSE, 0.0183, 4.07, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.1601,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (56, 1.6312, NULL, NULL, 5, FALSE, 0.0123, 4.66, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.6312,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (57, 2.0546, NULL, NULL, 5, FALSE, 0.0351, 2.7, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.0546,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (58, 0.3757, NULL, NULL, 5, FALSE, 0.0457, 4.42, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.3757,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (59, 1.7106, NULL, NULL, 5, FALSE, 0.0327, 5.08, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.7106,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (60, 2.1165, NULL, NULL, 5, FALSE, 0.0204, 5.36, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.1165,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (61, 1.1054, NULL, NULL, 5, FALSE, 0.037, 3.48, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.1054,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (62, 0.992, NULL, NULL, 5, FALSE, 0.0465, 7.94, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.992,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (63, 0.8577, NULL, NULL, 5, FALSE, 0.0119, 9.32, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.8577,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (64, 0.3879, NULL, NULL, 5, FALSE, 0.0385, 3.15, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.3879,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (65, 1.9598, NULL, NULL, 5, FALSE, 0.0187, 2.23, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.9598,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (66, 0.8313, NULL, NULL, 5, FALSE, 0.0439, 5.8, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.8313,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (67, 1.1211, NULL, NULL, 5, FALSE, 0.044, 2.47, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.1211,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (68, 0.5882, NULL, NULL, 5, FALSE, 0.048, 3.52, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.5882,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (69, 2.4166, NULL, NULL, 5, FALSE, 0.0235, 3.02, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.4166,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (70, 2.3575, NULL, NULL, 5, FALSE, 0.0443, 6.95, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.3575,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (71, 0.3752, NULL, NULL, 5, FALSE, 0.0447, 4.73, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.3752,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (72, 0.9732, NULL, NULL, 5, FALSE, 0.0133, 4.62, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.9732,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (73, 1.7974, NULL, NULL, 5, FALSE, 0.0464, 4.53, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.7974,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (74, 0.6897, NULL, NULL, 5, FALSE, 0.0251, 4.2, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.6897,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (75, 1.7716, NULL, NULL, 5, FALSE, 0.0167, 8.02, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.7716,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (76, 2.3676, NULL, NULL, 5, FALSE, 0.0479, 2.84, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.3676,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (77, 1.0489, NULL, NULL, 5, FALSE, 0.0247, 9.19, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 1.0489,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (78, 2.1233, NULL, NULL, 5, FALSE, 0.0495, 6.2, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.1233,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (79, 0.8372, NULL, NULL, 5, FALSE, 0.016, 4.11, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 0.8372,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (80, 2.3144, NULL, NULL, 5, FALSE, 0.039, 5.09, 'GC-FID (MSZ 21862-22:1982)', 2, '2024-04-27 00:00:00', 2.3144,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (81, 1.8984, NULL, NULL, 5, FALSE, 0.0416, 2.64, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.8984,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (82, 0.1366, NULL, NULL, 5, FALSE, 0.0296, 4.64, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.1366,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (83, 1.3912, NULL, NULL, 5, FALSE, 0.0333, 7.4, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.3912,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (84, 0.9997, NULL, NULL, 5, FALSE, 0.0375, 3.35, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.9997,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (85, 1.701, NULL, NULL, 5, FALSE, 0.03, 8.02, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.701, 5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (86, 1.5698, NULL, NULL, 5, FALSE, 0.0202, 7.15, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.5698,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (87, 1.2793, NULL, NULL, 5, FALSE, 0.0343, 6.35, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.2793,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (88, 1.5391, NULL, NULL, 5, FALSE, 0.0312, 6.24, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.5391,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (89, 2.0858, NULL, NULL, 5, FALSE, 0.0454, 3.41, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.0858,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (90, 0.5754, NULL, NULL, 5, FALSE, 0.0155, 3.98, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.5754,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (91, 1.3549, NULL, NULL, 5, FALSE, 0.0155, 9.9, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.3549,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (92, 1.4201, NULL, NULL, 5, FALSE, 0.0467, 3.2, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.4201,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (93, 2.09, NULL, NULL, 5, FALSE, 0.0229, 6.86, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.09, 5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (94, 1.4091, NULL, NULL, 5, FALSE, 0.0486, 6.17, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.4091,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (95, 2.1821, NULL, NULL, 5, FALSE, 0.0416, 4.3, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.1821,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (96, 0.491, NULL, NULL, 5, FALSE, 0.0268, 4.81, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.491,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (97, 1.8305, NULL, NULL, 5, FALSE, 0.0231, 6.26, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.8305,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (98, 0.251, NULL, NULL, 5, FALSE, 0.0194, 6.39, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.251,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (99, 1.2758, NULL, NULL, 5, FALSE, 0.0101, 2.37, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.2758,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (100, 0.9954, NULL, NULL, 5, FALSE, 0.0131, 8.12, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.9954,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (101, 2.2766, NULL, NULL, 5, FALSE, 0.0391, 4.82, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.2766,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (102, 0.8897, NULL, NULL, 5, FALSE, 0.0221, 2.89, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.8897,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (103, 0.2096, NULL, NULL, 5, FALSE, 0.0486, 5.54, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.2096,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (104, 2.0505, NULL, NULL, 5, FALSE, 0.0171, 5.77, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.0505,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (105, 1.8106, NULL, NULL, 5, FALSE, 0.0327, 9.18, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.8106,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (106, 1.5083, NULL, NULL, 5, FALSE, 0.0241, 8.84, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.5083,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (107, 0.8597, NULL, NULL, 5, FALSE, 0.0324, 8.04, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.8597,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (108, 0.702, NULL, NULL, 5, FALSE, 0.0107, 4.6, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.702,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (109, 1.9945, NULL, NULL, 5, FALSE, 0.0282, 5.0, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.9945,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (110, 0.4012, NULL, NULL, 5, FALSE, 0.0217, 8.42, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.4012,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (111, 1.6296, NULL, NULL, 5, FALSE, 0.0192, 7.94, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.6296,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (112, 1.9642, NULL, NULL, 5, FALSE, 0.0449, 5.9, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.9642,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (113, 2.2567, NULL, NULL, 5, FALSE, 0.0397, 5.74, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.2567,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (114, 1.0247, NULL, NULL, 5, FALSE, 0.0118, 3.16, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.0247,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (115, 1.2205, NULL, NULL, 5, FALSE, 0.0321, 4.06, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.2205,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (116, 1.2803, NULL, NULL, 5, FALSE, 0.037, 6.53, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.2803,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (117, 0.2966, NULL, NULL, 5, FALSE, 0.0224, 4.0, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 0.2966,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (118, 1.854, NULL, NULL, 5, FALSE, 0.0264, 3.14, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.854,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (119, 1.8037, NULL, NULL, 5, FALSE, 0.0214, 6.06, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 1.8037,
        5);
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_control, result_main_control,
                                       result_measurement_unit, is_below_detection_limit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES (120, 2.374, NULL, NULL, 5, FALSE, 0.0358, 5.29, 'GC-FID (MSZ 21862-22:1982)', 3, '2024-04-28 00:00:00', 2.374,
        5);

-- Trilak
INSERT INTO test_reports (report_number, title, approved_by, prepared_by, checked_by, aim_of_test,
                          project_id, location_id, sampling_record_id, technology, sampling_conditions_dates,
                          determination_of_pollutant_concentration, issue_date, report_status)
VALUES ('TRI-TEST-2024-001', 'Trilak – Munkahelyi légtér vizsgálati jegyzőkönyv',
        '1615211a-af4c-4aa6-88c5-09cb2695f579', '67b8b432-d9e6-479d-8e2f-da6742b8618f',
        'e0aef6e8-6616-4b81-ba70-1073aed57f5d',
        'VOC és porszennyezés értékelése festőüzemi technológia mellett',
        7, 2, 1, 'Oldószer alapú festési technológia',
        '2024.04.15. – 2024.04.15.',
        'VOC komponensek (toluol, aceton stb.) meghatározása GC-FID módszerrel, respirábilis por MSZ 21452 alapján. A szennyező anyagok koncentrációjának meghatározásához SKC típusú személyi
        mintavevő szivattyút használtunk, a térfogatáramot Drycal típusú elektronikus
        áramlásmérő készülékkel állítottuk be. A mintavételeket telepített mintavételi körökkel
        hajtottuk végre. A levegő elszívása kb. 1,5 m magasságból történt. \\
        A szálló por (respirábilis- és belélegezhető por) és a fémtartalom (KOH, NaOH, Ni, Mn, Co,
        Zn) koncentrációjának meghatározásához SKC típusú személyi mintavevő szivattyút
        használtunk. A mintavételt az MDHS 14/4:2014 sz. módszer előírásai alapján végeztük. A
        mintavételhez SKC gyártmányú, IOM típusú személyi por mintavevő készülékben
        elhelyezett hab- és síkszűrőt használtunk. \\
        A szálló por mennyiségének meghatározása az ENCOTECH Kft. akkreditált
        laboratóriumában történt. A tömegméréshez METTLER TOLEDO MX5 típusú
        mikromérleget használtunk. Az illékony szerves komponensek koncentrációjának meghatározása érdekében a
        mintavételhez az MSZ 21862-22:1982 sz. szabvány, valamint az MDHS 70:1993 sz. mérési
        eljárás előírásainak megfelelően, adszorbenssel töltött mintavételi csöveket
        alkalmaztunk. \\
        A fluoridok és foszforsav expozíció meghatározásához a mintavételt az MDHS 70:1993 sz.
        módszer szerint hajtottuk végre, az OSHA ID-165SG:1985 sz. szabvány figyelembe
        vételével. A mintavételi láncba adszorbennsel töltött SKC mintavételi csövet iktattunk.
        A minták egyéb szennyező anyag tartalmának meghatározását a BÁLINT ANALITIKA Kft.
        akkreditált laboratóriumában végezték. A laboratóriumi vizsgálati jegyzőkönyvet
        1. sz. mellékletként csatoljuk. \\
        A szén-monoxid koncentráció meghatározása BW Gas Alert Micro Clip XL műszerhez
        kapcsolódó gázmérő szondával (CO: elektrokémiai) történt az MSZ EN 45544-4:2016 sz.
        szabvány figyelembevételével.',
        '2024-04-28', 'FINALIZED');

-- Richter
INSERT INTO test_reports (report_number, title, approved_by, prepared_by, checked_by, aim_of_test,
                          project_id, location_id, sampling_record_id, technology, sampling_conditions_dates,
                          determination_of_pollutant_concentration, issue_date, report_status)
VALUES ('RIC-TEST-2024-001', 'Richter – Munkahelyi légtér vizsgálati jegyzőkönyv',
        '1615211a-af4c-4aa6-88c5-09cb2695f579', '67b8b432-d9e6-479d-8e2f-da6742b8618f',
        'e0aef6e8-6616-4b81-ba70-1073aed57f5d',
        'Fém-oxid és CO expozíciós szint felmérése gyógyszergyártási környezetben',
        8, 9, 2, 'Granulálás és tablettázás',
        '2024.04.22. – 2024.04.22.',
        'Nikkel, mangán és cink-oxid ICP-OES analízissel, CO mérése elektroanalitikus érzékelővel. A szennyező anyagok koncentrációjának meghatározásához SKC típusú személyi
        mintavevő szivattyút használtunk, a térfogatáramot Drycal típusú elektronikus
        áramlásmérő készülékkel állítottuk be. A mintavételeket telepített mintavételi körökkel
        hajtottuk végre. A levegő elszívása kb. 1,5 m magasságból történt. \\
        A szálló por (respirábilis- és belélegezhető por) és a fémtartalom (KOH, NaOH, Ni, Mn, Co,
        Zn) koncentrációjának meghatározásához SKC típusú személyi mintavevő szivattyút
        használtunk. A mintavételt az MDHS 14/4:2014 sz. módszer előírásai alapján végeztük. A
        mintavételhez SKC gyártmányú, IOM típusú személyi por mintavevő készülékben
        elhelyezett hab- és síkszűrőt használtunk. \\
        A szálló por mennyiségének meghatározása az ENCOTECH Kft. akkreditált
        laboratóriumában történt. A tömegméréshez METTLER TOLEDO MX5 típusú
        mikromérleget használtunk. Az illékony szerves komponensek koncentrációjának meghatározása érdekében a
        mintavételhez az MSZ 21862-22:1982 sz. szabvány, valamint az MDHS 70:1993 sz. mérési
        eljárás előírásainak megfelelően, adszorbenssel töltött mintavételi csöveket
        alkalmaztunk. \\
        A fluoridok és foszforsav expozíció meghatározásához a mintavételt az MDHS 70:1993 sz.
        módszer szerint hajtottuk végre, az OSHA ID-165SG:1985 sz. szabvány figyelembe
        vételével. A mintavételi láncba adszorbennsel töltött SKC mintavételi csövet iktattunk.
        A minták egyéb szennyező anyag tartalmának meghatározását a BÁLINT ANALITIKA Kft.
        akkreditált laboratóriumában végezték. A laboratóriumi vizsgálati jegyzőkönyvet
        1. sz. mellékletként csatoljuk. \\
        A szén-monoxid koncentráció meghatározása BW Gas Alert Micro Clip XL műszerhez
        kapcsolódó gázmérő szondával (CO: elektrokémiai) történt az MSZ EN 45544-4:2016 sz.
        szabvány figyelembevételével.',
        '2024-04-29', 'FINALIZED');

-- EnviroExpert
INSERT INTO test_reports (report_number, title, approved_by, prepared_by, checked_by, aim_of_test,
                          project_id, location_id, sampling_record_id, technology, sampling_conditions_dates,
                          determination_of_pollutant_concentration, issue_date, report_status)
VALUES ('ENV-TEST-2024-001', 'EnviroExpert – Módszervalidációs mérési jegyzőkönyv',
        '1615211a-af4c-4aa6-88c5-09cb2695f579', '67b8b432-d9e6-479d-8e2f-da6742b8618f',
        'e0aef6e8-6616-4b81-ba70-1073aed57f5d',
        'Minta-előkészítési és mintavételi módszerek összehasonlítása laboratóriumi környezetben',
        9, 10, 3, 'Szimulált ipari légtechnikai környezet',
        '2024.04.24. – 2024.04.24.',
        'VOC, szervetlen savak és porfrakciók elemzése különféle mintavevő eszközökkel. A szennyező anyagok koncentrációjának meghatározásához SKC típusú személyi
        mintavevő szivattyút használtunk, a térfogatáramot Drycal típusú elektronikus
        áramlásmérő készülékkel állítottuk be. A mintavételeket telepített mintavételi körökkel
        hajtottuk végre. A levegő elszívása kb. 1,5 m magasságból történt. \\
        A szálló por (respirábilis- és belélegezhető por) és a fémtartalom (KOH, NaOH, Ni, Mn, Co,
        Zn) koncentrációjának meghatározásához SKC típusú személyi mintavevő szivattyút
        használtunk. A mintavételt az MDHS 14/4:2014 sz. módszer előírásai alapján végeztük. A
        mintavételhez SKC gyártmányú, IOM típusú személyi por mintavevő készülékben
        elhelyezett hab- és síkszűrőt használtunk. \\
        A szálló por mennyiségének meghatározása az ENCOTECH Kft. akkreditált
        laboratóriumában történt. A tömegméréshez METTLER TOLEDO MX5 típusú
        mikromérleget használtunk. Az illékony szerves komponensek koncentrációjának meghatározása érdekében a
        mintavételhez az MSZ 21862-22:1982 sz. szabvány, valamint az MDHS 70:1993 sz. mérési
        eljárás előírásainak megfelelően, adszorbenssel töltött mintavételi csöveket
        alkalmaztunk. \\
        A fluoridok és foszforsav expozíció meghatározásához a mintavételt az MDHS 70:1993 sz.
        módszer szerint hajtottuk végre, az OSHA ID-165SG:1985 sz. szabvány figyelembe
        vételével. A mintavételi láncba adszorbennsel töltött SKC mintavételi csövet iktattunk.
        A minták egyéb szennyező anyag tartalmának meghatározását a BÁLINT ANALITIKA Kft.
        akkreditált laboratóriumában végezték. A laboratóriumi vizsgálati jegyzőkönyvet
        1. sz. mellékletként csatoljuk. \\
        A szén-monoxid koncentráció meghatározása BW Gas Alert Micro Clip XL műszerhez
        kapcsolódó gázmérő szondával (CO: elektrokémiai) történt az MSZ EN 45544-4:2016 sz.
        szabvány figyelembevételével.',
        '2024-04-30', 'FINALIZED');

-- Trilak (test_report_id = 1)
INSERT INTO test_report_standards (fk_test_report_id, fk_standard_id)
VALUES
    (1, 1),  -- MSZ EN 689
    (1, 2),  -- MSZ EN 482
    (1, 7);  -- MSZ 21862-22

-- Richter (test_report_id = 2)
INSERT INTO test_report_standards (fk_test_report_id, fk_standard_id)
VALUES
    (2, 1),   -- MSZ EN 689
    (2, 5),   -- MDHS 14/4
    (2, 14),  -- MSZ 21862-9
    (2, 3);   -- MSZ 21452-1

-- EnviroExpert (test_report_id = 3)
INSERT INTO test_report_standards (fk_test_report_id, fk_standard_id)
VALUES
    (3, 1),   -- MSZ EN 689
    (3, 12),  -- ISO 16200-1
    (3, 16);  -- EPA 6020B

-- Trilak
INSERT INTO test_report_samplers (fk_test_report_id, fk_user_id)
VALUES
    (1, '67b8b432-d9e6-479d-8e2f-da6742b8618f'),
    (1, 'e0aef6e8-6616-4b81-ba70-1073aed57f5d');

-- Richter
INSERT INTO test_report_samplers (fk_test_report_id, fk_user_id)
VALUES
    (2, '1615211a-af4c-4aa6-88c5-09cb2695f579'),
    (2, '67b8b432-d9e6-479d-8e2f-da6742b8618f');

-- EnviroExpert
INSERT INTO test_report_samplers (fk_test_report_id, fk_user_id)
VALUES
    (3, '1615211a-af4c-4aa6-88c5-09cb2695f579'),
    (3, 'e0aef6e8-6616-4b81-ba70-1073aed57f5d');

-- Bálint Analitika
INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES
    (1, 1), (1, 2), (1, 7), (1, 14);

-- Encotech
INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES
    (2, 1), (2, 2), (2, 3), (2, 5), (2, 7), (2, 12);

-- Biokör
INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES
    (3, 1), (3, 2), (3, 14), (3, 16);

-- SGS Hungária
INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES
    (4, 1), (4, 5), (4, 7), (4, 10), (4, 11);

-- Eurofins
INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES
    (5, 1), (5, 2), (5, 12), (5, 16), (5, 14);

-- TÜV Rheinland
INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES
    (6, 1), (6, 2), (6, 3), (6, 5), (6, 6);


INSERT INTO regulatory_limits_workplace
(fk_contaminant_id, fk_measurement_unit_id, fk_sample_type, limit_value)
VALUES
    -- Benzene (Workplace Air Limit)
    (1, 1, 'AK', 1.0),  -- 1.0 mg/m³ for AK samples
    (1, 1, 'CK', 0.5),  -- 0.5 mg/m³ for CK samples

    -- Lead (Workplace Exposure Limit)
    (2, 2, 'AK', 0.05), -- 0.05 ppm for AK samples
    (2, 2, 'CK', 0.03); -- 0.03 ppm for CK samples
