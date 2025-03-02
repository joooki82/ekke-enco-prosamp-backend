-- Enable necessary extensions
-- CREATE EXTENSION IF NOT EXISTS pg_trgm;


DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- ALTER ROLE prosamp WITH SUPERUSER;
-- CREATE EXTENSION IF NOT EXISTS pgaudit;
-- ALTER ROLE prosamp WITH NOSUPERUSER;

-- #############################################################################
-- TABLE: Users
-- #############################################################################
CREATE TABLE users
(
    id         UUID PRIMARY KEY, -- Stores Keycloak User ID
    username   VARCHAR(100) UNIQUE NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    role       VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (id, username, email, role, created_at, updated_at)
VALUES ('11111111-1111-1111-1111-111111111111', 'anonymous', 'anonymous@example.com', 'manager', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- #############################################################################
-- TABLE: Companies
-- #############################################################################
CREATE TABLE companies
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(255) UNIQUE NOT NULL,
    address        TEXT,
    contact_person VARCHAR(255)        NOT NULL,
    email          VARCHAR(255) UNIQUE,
    phone          VARCHAR(50),
    country        VARCHAR(100),
    city           VARCHAR(100),
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: Locations
-- #############################################################################
CREATE TABLE locations
(
    id             BIGSERIAL PRIMARY KEY,
    company_id     BIGINT       NOT NULL REFERENCES companies (id),
    name           VARCHAR(255) NOT NULL,
    address        TEXT,
    contact_person VARCHAR(255) NOT NULL,
    email          VARCHAR(255) ,
    phone          VARCHAR(50),
    country        VARCHAR(100),
    city           VARCHAR(100),
    postal_code    VARCHAR(20),
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_locations_company FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE RESTRICT,
    UNIQUE (company_id, name)
);


-- #############################################################################
-- TABLE: Equipments
-- #############################################################################
CREATE TABLE equipments
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(255)        NOT NULL,
    description     TEXT,
    producer        VARCHAR(255),
    measuring_range VARCHAR(255),
    resolution      VARCHAR(255),
    accuracy        VARCHAR(255),
    identifier      VARCHAR(255) UNIQUE NOT NULL,
    created_at      TIMESTAMP DEFAULT NOW(),
    updated_at      TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: Standards
-- #############################################################################
CREATE TABLE standards
(
    id              BIGSERIAL PRIMARY KEY,
    standard_number VARCHAR(255)        NOT NULL,
    description     TEXT,
    standard_type   VARCHAR(255),
    identifier      VARCHAR(255) UNIQUE NOT NULL,
    created_at      TIMESTAMP DEFAULT NOW(),
    updated_at      TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: Sampling Types
-- #############################################################################
CREATE TABLE sampling_types
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(10) UNIQUE NOT NULL,
    description TEXT               NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW(),
    updated_at  TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: Adjustment Methods
-- #############################################################################
CREATE TABLE adjustment_methods
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(10) UNIQUE NOT NULL,
    description TEXT               NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW(),
    updated_at  TIMESTAMP DEFAULT NOW()
);


-- #############################################################################
-- TABLE: Clients
-- #############################################################################
CREATE TABLE clients
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(255) UNIQUE NOT NULL,
    contact_person VARCHAR(255)        NOT NULL,
    email          VARCHAR(255),
    phone          VARCHAR(50),
    address        TEXT,
    country        VARCHAR(100),
    city           VARCHAR(100),
    postal_code    VARCHAR(20),
    tax_number     VARCHAR(50) UNIQUE,
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: Projects
-- #############################################################################
CREATE TABLE projects
(
    id             BIGSERIAL PRIMARY KEY,
    project_number VARCHAR(50) UNIQUE NOT NULL,
    client_id      BIGINT             NOT NULL,
    project_name   VARCHAR(255)       NOT NULL,
    start_date     DATE               NOT NULL,
    end_date       DATE,
    status         VARCHAR(50) DEFAULT 'ONGOING' CHECK (status IN ('ONGOING', 'COMPLETED', 'CANCELLED')),
    description    TEXT,
    created_at     TIMESTAMP   DEFAULT NOW(),
    updated_at     TIMESTAMP   DEFAULT NOW(),

    CONSTRAINT fk_project_client FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE RESTRICT
);



-- #############################################################################
-- TABLE: Sampling Records
-- #############################################################################
CREATE TABLE sampling_records_dat_m200
(
    id                             BIGSERIAL PRIMARY KEY,
    sampling_date                  TIMESTAMP NOT NULL,
    conducted_by                   UUID      NOT NULL,
    site_location_id               BIGINT    NOT NULL,
    company_id                     BIGINT    NOT NULL,
    tested_plant                   VARCHAR(255),
    technology                     VARCHAR(255),
    shift_count_and_duration       BIGINT CHECK (shift_count_and_duration > 0),
    workers_per_shift              BIGINT CHECK (workers_per_shift > 0),
    exposure_time                  BIGINT CHECK (exposure_time > 0),
    temperature                    NUMERIC(5, 2),
    humidity                       NUMERIC(5, 2) CHECK (humidity >= 0 AND humidity <= 100),
    wind_speed                     NUMERIC(5, 2),
    pressure1                      NUMERIC(7, 2),
    pressure2                      NUMERIC(7, 2),
    other_environmental_conditions TEXT,
    air_flow_conditions            VARCHAR(255),
    operation_mode                 VARCHAR(255),
    operation_break                VARCHAR(255),
    local_air_extraction           VARCHAR(255),
    serial_numbers_of_samples      VARCHAR(255),
    project_number                 BIGINT    NOT NULL,
    status                         VARCHAR(50) DEFAULT 'ACTIVE', -- Státusz: 'active', 'lost', 'broken', 'invalid'
    remarks                        TEXT,
    created_at                     TIMESTAMP   DEFAULT NOW(),
    updated_at                     TIMESTAMP   DEFAULT NOW(),
    CONSTRAINT fk_sampling_conducted_by FOREIGN KEY (conducted_by) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT fk_sampling_location FOREIGN KEY (site_location_id) REFERENCES locations (id) ON DELETE RESTRICT,
    CONSTRAINT fk_sampling_company FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE RESTRICT,
    CONSTRAINT fk_project_number FOREIGN KEY (project_number) REFERENCES projects (id) ON DELETE RESTRICT

);

-- #############################################################################
-- TABLE: Sampling Record Equipments (Join Table for Many-to-Many Relationship)
-- #############################################################################
CREATE TABLE sampling_record_equipments
(
    id                    BIGSERIAL PRIMARY KEY,
    fk_sampling_record_id BIGINT NOT NULL REFERENCES sampling_records_dat_m200 (id) ON DELETE CASCADE,
    fk_equipment_id       BIGINT NOT NULL REFERENCES equipments (id) ON DELETE RESTRICT,
    created_at            TIMESTAMP DEFAULT NOW(),
    UNIQUE (fk_sampling_record_id, fk_equipment_id)
);


-- #############################################################################
-- TABLE: Contaminant Groups
-- #############################################################################
CREATE TABLE contaminant_groups
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)        NOT NULL,
    created_at  TIMESTAMP DEFAULT NOW(),
    updated_at  TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: Contaminants
-- #############################################################################
CREATE TABLE contaminants
(
    id                   BIGSERIAL PRIMARY KEY,
    name                 VARCHAR(255) UNIQUE NOT NULL,
    description          VARCHAR(255),
    contaminant_group_id BIGINT              NOT NULL,
    created_at           TIMESTAMP DEFAULT NOW(),
    updated_at           TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_contaminant_group FOREIGN KEY (contaminant_group_id) REFERENCES contaminant_groups (id) ON DELETE RESTRICT

);

-- #############################################################################
-- Measurement units
-- #############################################################################
CREATE TABLE measurement_units
(
    id                BIGSERIAL PRIMARY KEY,
    unit_code         VARCHAR(20) UNIQUE NOT NULL,              -- e.g., "mg/m³", "ppm", "µg/L"
    description       TEXT               NOT NULL,              -- e.g., "Milligrams per cubic meter"
    unit_category     VARCHAR(50)        NOT NULL,              -- e.g., "Concentration", "Mass", "Volume"
    base_unit_id      BIGINT REFERENCES measurement_units (id), -- For unit conversion reference
    conversion_factor DOUBLE PRECISION,                         -- Factor to convert to base unit
    standard_body     VARCHAR(50),                              -- e.g., "SI", "ISO", "ASTM", "EPA"
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW()
);


-- #############################################################################
-- TABLE: Samples
-- #############################################################################
CREATE TABLE samples
(
    id                           BIGSERIAL PRIMARY KEY,
    sampling_record_id           BIGINT      NOT NULL,
    sample_identifier            VARCHAR(50) NOT NULL,
    location                     VARCHAR(255),
    employee_name                VARCHAR(255),
    temperature                  NUMERIC(5, 2),
    humidity                     NUMERIC(5, 2),
    pressure                     NUMERIC(7, 2),
    sample_volume_flow_rate      NUMERIC(5, 4),
    sample_volume_flow_rate_unit BIGINT      NOT NULL REFERENCES measurement_units (id) ON DELETE RESTRICT,
    start_time                   TIMESTAMP,
    end_time                     TIMESTAMP,
    sample_type                  VARCHAR(10) CHECK (sample_type IN ('AK', 'CK')) DEFAULT 'AK',
    status                       VARCHAR(50)                                     DEFAULT 'ACTIVE', -- Státusz: 'active', 'lost', 'broken', 'invalid'
    remarks                      VARCHAR(255),
    sampling_type_id             BIGINT,
    adjustment_method_id         BIGINT,
    sampling_flow_rate           NUMERIC(6, 3) CHECK (sampling_flow_rate > 0),
    created_at                   TIMESTAMP                                       DEFAULT NOW(),
    updated_at                   TIMESTAMP                                       DEFAULT NOW(),
    CONSTRAINT fk_samples_sampling_record FOREIGN KEY (sampling_record_id) REFERENCES sampling_records_dat_m200 (id) ON DELETE CASCADE,
    CONSTRAINT fk_sampling_type FOREIGN KEY (sampling_type_id) REFERENCES sampling_types (id) ON DELETE RESTRICT,
    CONSTRAINT fk_adjustment_method FOREIGN KEY (adjustment_method_id) REFERENCES adjustment_methods (id) ON DELETE RESTRICT

);

-- #############################################################################
-- TABLE: Sample Contaminants (Join Table for Many-to-Many Relationship)
-- #############################################################################
CREATE TABLE sample_contaminants
(
    id                BIGSERIAL PRIMARY KEY,
    fk_sample_id      BIGINT NOT NULL REFERENCES samples (id) ON DELETE CASCADE,
    fk_contaminant_id BIGINT NOT NULL REFERENCES contaminants (id) ON DELETE RESTRICT,
    created_at        TIMESTAMP DEFAULT NOW(),
    UNIQUE (fk_sample_id, fk_contaminant_id)
);

-- #############################################################################
-- Analytical laboratories
-- #############################################################################
CREATE TABLE laboratories
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) UNIQUE NOT NULL,
    accreditation VARCHAR(255), -- Accreditation ID (e.g., NAH-1-1666/2019)
    contact_email VARCHAR(255),
    phone         VARCHAR(50),
    address       TEXT,
    website       VARCHAR(255),
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- Analytical laboratory report
-- #############################################################################
CREATE TABLE analytical_lab_reports
(
    id            BIGSERIAL PRIMARY KEY,
    report_number VARCHAR(50) UNIQUE NOT NULL,                                                 -- External lab report identifier
    issue_date    DATE               NOT NULL,                                                 -- The date when the lab issued the report
    laboratory_id BIGINT             NOT NULL REFERENCES laboratories (id) ON DELETE RESTRICT, -- Which lab performed the tests
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);


-- #############################################################################
-- Sample analytical results
-- #############################################################################
CREATE TABLE sample_analytical_results
(
    id                       BIGSERIAL PRIMARY KEY,
    sample_id                BIGINT                                                         NOT NULL,
    contaminant_id           BIGINT                                                         NOT NULL,
    result_main              NUMERIC(10, 4) CHECK (result_main IS NULL OR result_main >= 0) NOT NULL,
    result_control           NUMERIC(10, 4) CHECK (result_control IS NULL OR result_control >= 0)           DEFAULT NULL,
    result_main_control      NUMERIC(10, 4) CHECK (result_main_control IS NULL OR result_main_control >= 0) DEFAULT NULL,
    measurement_unit         BIGINT                                                         NOT NULL REFERENCES measurement_units (id) ON DELETE RESTRICT,
    detection_limit          NUMERIC(10, 4) CHECK (detection_limit IS NULL OR detection_limit >= 0),
    measurement_uncertainty  NUMERIC(5, 2) CHECK (measurement_uncertainty IS NULL OR
                                                  measurement_uncertainty BETWEEN 0 AND 100),
    analysis_method          VARCHAR(255) CHECK (LENGTH(analysis_method) > 3),
    lab_report_id            BIGINT                                                         NOT NULL REFERENCES analytical_lab_reports (id) ON DELETE CASCADE,
    analysis_date            TIMESTAMP CHECK (analysis_date <= CURRENT_TIMESTAMP),
    calculated_concentration NUMERIC(10, 4) CHECK (calculated_concentration >= 0),
    created_at               TIMESTAMP                                                                      DEFAULT NOW(),
    updated_at               TIMESTAMP                                                                      DEFAULT NOW(),
    CONSTRAINT fk_sample FOREIGN KEY (sample_id)
        REFERENCES samples (id) ON DELETE RESTRICT,
    CONSTRAINT fk_contaminant FOREIGN KEY (contaminant_id)
        REFERENCES contaminants (id) ON DELETE RESTRICT
);

-- #############################################################################
-- Test reports
-- #############################################################################
CREATE TABLE test_reports
(
    id                                       BIGSERIAL PRIMARY KEY,
    report_number                            VARCHAR(50) UNIQUE NOT NULL,
    title                                    VARCHAR(255)       NOT NULL,
    approved_by                              UUID,
    prepared_by                              UUID,
    checked_by                               UUID,
    aim_of_test                              TEXT,
    project_id                               BIGINT             NOT NULL REFERENCES projects (id) ON DELETE RESTRICT,
    location_id                              BIGINT             NOT NULL REFERENCES locations (id) ON DELETE RESTRICT,
    sampling_record_id                       BIGINT             NOT NULL REFERENCES sampling_records_dat_m200 (id) ON DELETE RESTRICT,
    technology                               TEXT,
    sampling_conditions_dates                TEXT,
    determination_of_pollutant_concentration TEXT,
    issue_date                               DATE               NOT NULL,
    report_status                            VARCHAR(20) DEFAULT 'DRAFT' CHECK (report_status IN ('DRAFT', 'FINALIZED', 'APPROVED', 'REJECTED')), -- Status of report
    created_at                               TIMESTAMP   DEFAULT NOW(),
    updated_at                               TIMESTAMP   DEFAULT NOW()
);

CREATE TABLE test_report_standards
(
    id                BIGSERIAL PRIMARY KEY,
    fk_test_report_id BIGINT NOT NULL REFERENCES test_reports (id) ON DELETE RESTRICT,
    fk_standard_id    BIGINT NOT NULL REFERENCES standards (id) ON DELETE RESTRICT,
    created_at        TIMESTAMP DEFAULT NOW(),
    UNIQUE (fk_test_report_id, fk_standard_id)
);

CREATE TABLE test_report_samplers
(
    id                BIGSERIAL PRIMARY KEY,
    fk_test_report_id BIGINT NOT NULL REFERENCES test_reports (id) ON DELETE CASCADE,
    fk_user_id        UUID   NOT NULL REFERENCES users (id) ON DELETE RESTRICT,
    created_at        TIMESTAMP DEFAULT NOW(),
    UNIQUE (fk_test_report_id, fk_user_id)
);


-- #############################################################################
-- UPDATE TIMESTAMP
-- #############################################################################
CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.updated_at IS DISTINCT FROM OLD.updated_at THEN
        NEW.updated_at = NOW();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- ##########################################################################
-- Function to Apply `update_timestamp()` to All Tables With `updated_at`
-- ##########################################################################

CREATE OR REPLACE FUNCTION apply_update_timestamp_trigger()
    RETURNS event_trigger AS
$$
DECLARE
    tbl TEXT;
BEGIN
    FOR tbl IN
        SELECT table_name
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND column_name = 'updated_at'
          AND table_name NOT IN ('audit_logs', 'pgaudit')
          AND table_name NOT IN (SELECT event_object_table
                                 FROM information_schema.triggers
                                 WHERE trigger_name = format('trigger_update_%s', table_name))
        LOOP
            EXECUTE format(
                    'CREATE TRIGGER trigger_update_%I
                    BEFORE UPDATE ON %I
                    FOR EACH ROW EXECUTE FUNCTION update_timestamp();',
                    tbl, tbl
                    );
        END LOOP;
END;
$$ LANGUAGE plpgsql;


-- ##########################################################################
-- Event Trigger to Automatically Add Triggers on `CREATE TABLE`
-- ##########################################################################
CREATE EVENT TRIGGER trigger_auto_update_timestamp
    ON ddl_command_end
    WHEN TAG IN ('CREATE TABLE')
EXECUTE FUNCTION apply_update_timestamp_trigger();


-- #############################################################################
-- INDICES
-- #############################################################################

CREATE INDEX idx_locations_company ON locations (company_id);

CREATE INDEX idx_sampling_records_location_company ON sampling_records_dat_m200 (site_location_id, company_id);

CREATE INDEX idx_samples_sampling_record ON samples (sampling_record_id);

CREATE INDEX idx_sample_contaminants_sample ON sample_contaminants (fk_sample_id);

CREATE INDEX idx_sample_contaminants_contaminant ON sample_contaminants (fk_contaminant_id);

CREATE UNIQUE INDEX idx_users_email ON users (email);

CREATE UNIQUE INDEX idx_equipments_identifier ON equipments (identifier);

CREATE INDEX idx_sampling_records_date ON sampling_records_dat_m200 (sampling_date DESC);

CREATE INDEX idx_projects_client ON projects (client_id);

CREATE INDEX idx_sampling_records_project ON sampling_records_dat_m200 (project_number);


-- #############################################################################
-- LOGGING
-- #############################################################################
ALTER SYSTEM SET pgaudit.log = 'write, ddl';

CREATE TABLE audit_logs
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id    UUID REFERENCES users (id),
    table_name TEXT NOT NULL,
    action     TEXT CHECK (action IN ('INSERT', 'UPDATE', 'DELETE')),
    record_id  UUID NOT NULL, -- The affected row’s ID
    changes    JSONB,         -- Stores old and new values
    timestamp  TIMESTAMP DEFAULT NOW()
);

-- # TODO set when the user make transactions
-- # SET LOCAL app.current_user = 'some-user-id';

CREATE OR REPLACE FUNCTION audit_log()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO audit_logs (user_id, table_name, action, record_id, changes)
    VALUES (COALESCE(
                    NULLIF(current_setting('app.current_user', TRUE), '')::UUID,
                    '11111111-1111-1111-1111-111111111111' -- Anonymous user UUID
            ), -- Capture application user
            TG_TABLE_NAME, -- Gets the table name dynamically
            TG_OP, -- Captures the operation (INSERT, UPDATE, DELETE)
            CASE WHEN TG_OP = 'DELETE' THEN OLD.id ELSE NEW.id END, -- Get ID of affected row
            jsonb_build_object(
                    'old', CASE WHEN TG_OP = 'UPDATE' OR TG_OP = 'DELETE' THEN row_to_json(OLD) ELSE NULL END,
                    'new', CASE WHEN TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN row_to_json(NEW) ELSE NULL END
            ));
    RETURN CASE
               WHEN TG_OP = 'DELETE' THEN OLD
               ELSE NEW
        END;
END;
$$ LANGUAGE plpgsql;


-- #############################################################################
-- # Solution: Auto-Apply Logging to New Tables
-- # Create an Event Trigger to Detect New Tables
-- #############################################################################
-- #############################################################################
-- Function: Auto-Apply Logging to New Tables
-- Detects New Tables and Adds an Audit Trigger
-- #############################################################################
CREATE OR REPLACE FUNCTION apply_audit_trigger()
    RETURNS event_trigger AS
$$
DECLARE
    tbl TEXT;
BEGIN
    FOR tbl IN
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'public'
          AND table_name NOT IN ('audit_logs')
          AND table_name NOT IN (SELECT event_object_table
                                 FROM information_schema.triggers
                                 WHERE trigger_name LIKE 'audit_trigger_%')
        LOOP
            EXECUTE format(
                    'CREATE TRIGGER audit_trigger_%s
                    AFTER INSERT OR UPDATE OR DELETE ON %I
                    FOR EACH ROW EXECUTE FUNCTION audit_log();',
                    tbl, tbl
                    );
        END LOOP;
END;
$$ LANGUAGE plpgsql;


-- #############################################################################
-- Event Trigger: Automatically Applies Logging to New Tables
-- #############################################################################
CREATE EVENT TRIGGER trigger_auto_audit
    ON ddl_command_end
    WHEN TAG IN ('CREATE TABLE')
EXECUTE FUNCTION apply_audit_trigger();


-- #####################################################
-- INSERT TEST DATA
-- #####################################################

-- Insert Users

INSERT INTO contaminant_groups (name, description)
VALUES
    ('Volatile Organic Compounds', 'Group of organic chemicals that evaporate easily'),
    ('Heavy Metals', 'Metallic elements that can be toxic at low concentrations')
RETURNING id;

INSERT INTO contaminants (name, description, contaminant_group_id, created_at, updated_at)
VALUES
    ('Benzene', 'A volatile organic compound found in industrial emissions', 1, NOW(), NOW()),
    ('Lead', 'A heavy metal that can cause serious health problems', 2, NOW(), NOW())
RETURNING id;


INSERT INTO users (id, username, email, role, created_at, updated_at)
VALUES
    ('22222222-2222-2222-2222-222222222222', 'john_doe', 'john.doe@example.com', 'admin', NOW(), NOW()),
    ('33333333-3333-3333-3333-333333333333', 'jane_smith', 'jane.smith@example.com', 'technician', NOW(), NOW());

-- Insert Companies
INSERT INTO companies (name, address, contact_person, email, phone, country, city)
VALUES
    ('Tech Solutions Ltd.', '123 Tech Street', 'Alice Johnson', 'contact@techsolutions.com', '+123456789', 'USA', 'New York'),
    ('Industrial Safety Inc.', '456 Safety Ave', 'Bob Brown', 'info@safetyinc.com', '+987654321', 'Germany', 'Berlin')
RETURNING id;

-- Insert Locations
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES
    (1, 'Tech Solutions HQ', '123 Tech Street', 'Alice Johnson', 'alice.johnson@techsolutions.com', '+123456789', 'USA', 'New York', '10001'),
    (2, 'Industrial Safety Branch', '456 Safety Ave', 'Bob Brown', 'bob.brown@safetyinc.com', '+987654321', 'Germany', 'Berlin', '10115')
RETURNING id;

-- Insert Clients
INSERT INTO clients (name, contact_person, email, phone, address, country, city, postal_code, tax_number)
VALUES
    ('Global Engineering', 'Michael Scott', 'michael.scott@globaleng.com', '+1122334455', '789 Industrial Road', 'USA', 'Los Angeles', '90001', 'TAX123456'),
    ('Green Energy Ltd.', 'Sarah Connor', 'sarah.connor@greenenergy.com', '+2233445566', '321 Eco Street', 'UK', 'London', 'E1 6AN', 'TAX987654');

-- Insert Projects
INSERT INTO projects (project_number, client_id, project_name, start_date, end_date, status, description)
VALUES
    ('PROJ-001', 1, 'Air Quality Testing', '2024-01-01', '2024-12-31', 'ONGOING', 'Testing air quality at industrial sites.'),
    ('PROJ-002', 2, 'Safety Compliance Audit', '2024-03-01', NULL, 'ONGOING', 'Ensuring safety standards are met.');

-- Insert Equipments
INSERT INTO equipments (name, description, producer, measuring_range, resolution, accuracy, identifier)
VALUES
    ('Air Quality Monitor', 'Detects air pollutants', 'EnviroTech', '0-500 ppm', '0.01 ppm', '±2%', 'AQM-001'),
    ('Gas Analyzer', 'Analyzes gas composition', 'SafeAir', '0-100%', '0.1%', '±1%', 'GA-002');

-- Insert Standards
INSERT INTO standards (standard_number, description, standard_type, identifier)
VALUES
    ('ISO 9001', 'Quality Management System', 'ISO', 'ISO-9001'),
    ('OSHA 1910.1000', 'Occupational Safety and Health Standards', 'OSHA', 'OSHA-1910');

-- Insert Sampling Types
INSERT INTO sampling_types (code, description)
VALUES
    ('ST1', 'Air sampling'),
    ('ST2', 'Gas sampling');

-- Insert Adjustment Methods
INSERT INTO adjustment_methods (code, description)
VALUES
    ('AM1', 'Manual adjustment'),
    ('AM2', 'Automatic adjustment');

-- Insert Sampling Records
INSERT INTO sampling_records_dat_m200 (sampling_date, conducted_by, site_location_id, company_id, tested_plant, technology, shift_count_and_duration, workers_per_shift, exposure_time, temperature, humidity, wind_speed, pressure1, pressure2, other_environmental_conditions, air_flow_conditions, operation_mode, operation_break, local_air_extraction, serial_numbers_of_samples, project_number)
VALUES
    ('2024-02-15 08:00:00', '22222222-2222-2222-2222-222222222222', 1, 1, 'Factory 1', 'Modern Tech', 3, 50, 8, 22.5, 45.0, 3.5, 1013.2, 1012.8, 'None', 'Good', 'Normal', 'None', 'Yes', 'SN001, SN002', 1),
    ('2024-02-16 09:00:00', '33333333-3333-3333-3333-333333333333', 2, 2, 'Plant 2', 'Traditional Tech', 2, 40, 6, 18.0, 50.0, 2.5, 1012.5, 1011.7, 'Slight dust', 'Moderate', 'High', 'Frequent', 'No', 'SN003, SN004', 2);

-- Insert Measurement Units
INSERT INTO measurement_units (unit_code, description, unit_category)
VALUES
    ('mg/m³', 'Milligrams per cubic meter', 'Concentration'),
    ('ppm', 'Parts per million', 'Concentration');

-- Insert Samples
INSERT INTO samples (sampling_record_id, sample_identifier, location, employee_name, temperature, humidity, pressure, sample_volume_flow_rate, sample_volume_flow_rate_unit, start_time, end_time, sample_type, status, sampling_type_id, adjustment_method_id, sampling_flow_rate)
VALUES
    (1, 'SMP-001', 'Factory 1 - Zone A', 'Worker A', 22.5, 45.0, 1013.2, 2.5, 1, '2024-02-15 08:30:00', '2024-02-15 10:00:00', 'AK', 'ACTIVE', 1, 1, 1.5),
    (2, 'SMP-002', 'Plant 2 - Zone B', 'Worker B', 18.0, 50.0, 1012.5, 3.0, 2, '2024-02-16 09:30:00', '2024-02-16 11:00:00', 'CK', 'ACTIVE', 2, 2, 2.0);

-- Insert Analytical Laboratories
INSERT INTO laboratories (name, accreditation, contact_email, phone, address, website)
VALUES
    ('National Lab', 'NAH-1-1666/2019', 'lab@nationallab.com', '+4455667788', 'Lab Street 1, Berlin', 'www.nationallab.com'),
    ('EcoLab', 'ISO-17025', 'contact@ecolab.com', '+3355667788', 'Eco Road 5, London', 'www.ecolab.com');

-- Insert Analytical Lab Reports
INSERT INTO analytical_lab_reports (report_number, issue_date, laboratory_id)
VALUES
    ('LAB-001', '2024-02-17', 1),
    ('LAB-002', '2024-02-18', 2);

-- Insert Sample Analytical Results
INSERT INTO sample_analytical_results (sample_id, contaminant_id, result_main, measurement_unit, detection_limit, measurement_uncertainty, analysis_method, lab_report_id, analysis_date, calculated_concentration)
VALUES
    (1, 1, 0.35, 1, 0.1, 5.0, 'GC-MS', 1, '2024-02-17 12:00:00', 0.35),
    (2, 2, 1.25, 2, 0.2, 4.5, 'HPLC', 2, '2024-02-18 14:00:00', 1.25);

-- Insert Test Reports
INSERT INTO test_reports (report_number, title, approved_by, prepared_by, checked_by, aim_of_test, project_id, location_id, sampling_record_id, technology, sampling_conditions_dates, determination_of_pollutant_concentration, issue_date, report_status)
VALUES
    ('TR-001', 'Air Quality Test Report', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222', 'Evaluate air quality in factory', 1, 1, 1, 'Modern Tech', '2024-02-15', 'Detailed analysis', '2024-02-20', 'FINALIZED');


