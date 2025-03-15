-- Enable necessary extensions
-- CREATE EXTENSION IF NOT EXISTS pg_trgm;

SELECT current_user, session_user;
SET ROLE postgres;

DROP SCHEMA public CASCADE;

CREATE SCHEMA public;

SET session "app.current_user" = '22222222-2222-2222-2222-222222222222';

-- ALTER DATABASE prosampdb SET "app.current_user" = '33333333-3333-3333-3333-333333333333';

SELECT pg_reload_conf();

SET ROLE prosamp;

SELECT current_user, session_user;

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

INSERT INTO users (id, username, email, role)
VALUES ('22222222-2222-2222-2222-222222222222', 'benonymous', 'benonymous@encotech.hu', 'admin'),
       ('33333333-3333-3333-3333-333333333333', 'vazulnymus', 'vazulnymus@ananas.hu', 'technician');


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
    email          VARCHAR(255),
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
    id                    BIGSERIAL PRIMARY KEY,
    name                  VARCHAR(255)        NOT NULL,
    identifier            VARCHAR(255) UNIQUE NOT NULL,
    description           TEXT,
    manufacturer          VARCHAR(255),
    type                  VARCHAR(255),
    serial_number         VARCHAR(255),
    measuring_range       VARCHAR(255),
    resolution            VARCHAR(255),
    accuracy              VARCHAR(255),
    calibration_date      DATE,
    next_calibration_date DATE,
    created_at            TIMESTAMP DEFAULT NOW(),
    updated_at            TIMESTAMP DEFAULT NOW()
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
    conversion_factor NUMERIC(20, 10),                          -- Factor to convert to base unit
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
    start_time                   TIMESTAMP(0),                                                     -- Store only up to seconds
    end_time                     TIMESTAMP(0),                                                     -- Store only up to seconds
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
    updated_at        TIMESTAMP DEFAULT NOW(),
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
    id                                        BIGSERIAL PRIMARY KEY,
    sample_contaminant_id                     BIGINT                                                         NOT NULL REFERENCES sample_contaminants (id) ON DELETE CASCADE,
    result_main                               NUMERIC(10, 4) CHECK (result_main IS NULL OR result_main >= 0) NOT NULL,
    result_control                            NUMERIC(10, 4) CHECK (result_control IS NULL OR result_control >= 0)           DEFAULT NULL,
    result_main_control                       NUMERIC(10, 4) CHECK (result_main_control IS NULL OR result_main_control >= 0) DEFAULT NULL,
    result_measurement_unit                   BIGINT                                                         NOT NULL REFERENCES measurement_units (id) ON DELETE RESTRICT,
    detection_limit                           NUMERIC(10, 4) CHECK (detection_limit IS NULL OR detection_limit >= 0),
    measurement_uncertainty                   NUMERIC(5, 2) CHECK (measurement_uncertainty IS NULL OR
                                                                   measurement_uncertainty BETWEEN 0 AND 100),
    analysis_method                           VARCHAR(255) CHECK (LENGTH(analysis_method) > 3),
    lab_report_id                             BIGINT                                                         NOT NULL REFERENCES analytical_lab_reports (id) ON DELETE CASCADE,
    analysis_date                             TIMESTAMP CHECK (analysis_date <= CURRENT_TIMESTAMP),
    calculated_concentration                  NUMERIC(10, 4) CHECK (calculated_concentration >= 0),
    calculated_concentration_measurement_unit BIGINT                                                         NOT NULL REFERENCES measurement_units (id) ON DELETE RESTRICT,
    created_at                                TIMESTAMP                                                                      DEFAULT NOW(),
    updated_at                                TIMESTAMP                                                                      DEFAULT NOW(),
    CONSTRAINT fk_sample_contaminant FOREIGN KEY (sample_contaminant_id)
        REFERENCES sample_contaminants (id) ON DELETE CASCADE
);

-- #############################################################################
-- Test reports
-- #############################################################################
CREATE TABLE test_reports
(
    id                                       BIGSERIAL PRIMARY KEY,
    report_number                            VARCHAR(50) UNIQUE NOT NULL,
    title                                    VARCHAR(255)       NOT NULL,
    approved_by                              UUID REFERENCES users (id) ON DELETE RESTRICT,
    prepared_by                              UUID REFERENCES users (id) ON DELETE RESTRICT,
    checked_by                               UUID REFERENCES users (id) ON DELETE RESTRICT,
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


CREATE TABLE regulatory_limits_workplace
(
    id                     BIGSERIAL PRIMARY KEY,
    fk_contaminant_id      BIGINT NOT NULL REFERENCES contaminants (id) ON DELETE CASCADE,
    fk_measurement_unit_id BIGINT NOT NULL REFERENCES measurement_units (id) ON DELETE RESTRICT,
    fk_sample_type         VARCHAR(10) CHECK (fk_sample_type IN ('AK', 'CK')) DEFAULT 'AK',
    limit_value            NUMERIC(10, 4) CHECK (limit_value >= 0), -- Maximum allowed concentration
    created_at             TIMESTAMP                                          DEFAULT NOW(),
    updated_at             TIMESTAMP                                          DEFAULT NOW(),
    UNIQUE (fk_contaminant_id, fk_sample_type)
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

CREATE TABLE IF NOT EXISTS audit_logs
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          UUID REFERENCES users (id),
    table_name       TEXT NOT NULL,
    action           TEXT CHECK (action IN ('INSERT', 'UPDATE', 'DELETE')),
    record_id_uuid   UUID,   -- Stores UUID-based IDs
    record_id_bigint BIGINT, -- Stores BIGINT-based IDs
    changes          JSONB,  -- Stores old and new values
    timestamp        TIMESTAMP DEFAULT NOW(),
    CHECK (
        (record_id_uuid IS NOT NULL AND record_id_bigint IS NULL) OR
        (record_id_bigint IS NOT NULL AND record_id_uuid IS NULL)
        )                    -- Ensures only one is filled
);


-- # TODO set when the user make transactions
-- # SET LOCAL app.current_user = 'some-user-id';


CREATE OR REPLACE FUNCTION get_current_user_id()
    RETURNS UUID AS
$$
BEGIN
    RETURN COALESCE(
            NULLIF(current_setting('app.current_user', TRUE), '')::UUID,
            '11111111-1111-1111-1111-111111111111' -- Default: Anonymous User
           );
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION audit_log()
    RETURNS TRIGGER AS
$$
DECLARE
    current_user_uuid UUID;
    rec_uuid          UUID;
    rec_bigint        BIGINT;
BEGIN
    -- Retrieve current user UUID from session settings
    BEGIN
        SELECT NULLIF(current_setting('app.current_user', TRUE), '')::UUID
        INTO current_user_uuid;
    EXCEPTION
        WHEN others THEN
            current_user_uuid := '11111111-1111-1111-1111-111111111111'; -- Default Anonymous User
    END;

    -- Determine the type of NEW.id and assign to local variables accordingly
    IF pg_typeof(NEW.id)::text = 'uuid' THEN
        rec_uuid := NEW.id;
        rec_bigint := NULL;
    ELSIF pg_typeof(NEW.id)::text = 'bigint' THEN
        rec_bigint := NEW.id;
        rec_uuid := NULL;
    ELSE
        rec_uuid := NULL;
        rec_bigint := NULL;
    END IF;

    -- Insert the audit log record with the correctly typed values
    INSERT INTO audit_logs (user_id,
                            table_name,
                            action,
                            record_id_uuid,
                            record_id_bigint,
                            changes)
    VALUES (current_user_uuid, -- current user
            TG_TABLE_NAME, -- table triggering the audit
            TG_OP, -- type of operation (INSERT, UPDATE, DELETE)
            rec_uuid,
            rec_bigint,
            jsonb_build_object(
                    'old', CASE WHEN TG_OP IN ('UPDATE', 'DELETE') THEN row_to_json(OLD) ELSE NULL END,
                    'new', CASE WHEN TG_OP IN ('INSERT', 'UPDATE') THEN row_to_json(NEW) ELSE NULL END
            ));

    -- Return the appropriate record for the operation
    RETURN CASE WHEN TG_OP = 'DELETE' THEN OLD ELSE NEW END;
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
-- #############################################################################
-- Event Trigger: Automatically Applies Logging to New Tables
-- #############################################################################
-- CREATE EVENT TRIGGER trigger_auto_audit
--     ON ddl_command_end
--     WHEN TAG IN ('CREATE TABLE')
-- EXECUTE FUNCTION apply_audit_trigger();


DO
$$
    DECLARE
        tbl TEXT;
    BEGIN
        FOR tbl IN
            SELECT table_name
            FROM information_schema.columns
            WHERE table_schema = 'public'
              AND column_name = 'id'
              AND table_name NOT IN ('audit_logs') -- Exclude itself
            LOOP
                EXECUTE format(
                        'CREATE TRIGGER audit_trigger_%I
                         AFTER INSERT OR UPDATE OR DELETE ON %I
                         FOR EACH ROW EXECUTE FUNCTION audit_log();',
                        tbl, tbl
                        );
            END LOOP;
    END;
$$;


-- #####################################################
-- INSERT TEST DATA
-- #####################################################

-- Insert Users

INSERT INTO contaminant_groups (id, name, description, created_at, updated_at)
VALUES (1, 'Volatile Organic Compounds', 'Group of organic chemicals that evaporate easily', NOW(), NOW()),
       (2, 'Heavy Metals', 'Metallic elements that can be toxic at low concentrations', NOW(), NOW()),
       (3, 'Particulate Matter', 'Small solid or liquid particles in the air', NOW(), NOW()),
       (4, 'Pesticides', 'Chemicals used to kill pests, can be harmful to humans', NOW(), NOW()),
       (5, 'Industrial Gases', 'Gases emitted from industrial processes', NOW(), NOW()),
       (6, 'Combustion Byproducts', 'Substances produced during burning of fuels', NOW(), NOW()),
       (7, 'Radioactive Contaminants', 'Radioactive substances that pose health risks', NOW(), NOW()),
       (8, 'Pathogenic Microorganisms', 'Microbes that can cause disease', NOW(), NOW()),
       (9, 'Endocrine Disruptors', 'Chemicals that interfere with hormonal functions', NOW(), NOW()),
       (10, 'Pharmaceutical Residues', 'Traces of pharmaceutical substances in the environment', NOW(), NOW());

INSERT INTO contaminants (id, name, description, contaminant_group_id, created_at, updated_at)
VALUES (1, 'Benzene', 'A volatile organic compound found in industrial emissions', 1, NOW(), NOW()),
       (2, 'Toluene', 'A solvent commonly used in paints and coatings', 1, NOW(), NOW()),
       (3, 'Lead', 'A heavy metal that can cause serious health problems', 2, NOW(), NOW()),
       (4, 'Mercury', 'A toxic heavy metal often found in fish', 2, NOW(), NOW()),
       (5, 'PM10', 'Particulate matter less than 10 micrometers in diameter', 3, NOW(), NOW()),
       (6, 'PM2.5', 'Fine particulate matter that can penetrate deep into the lungs', 3, NOW(), NOW()),
       (7, 'DDT', 'A banned pesticide with long-lasting environmental effects', 4, NOW(), NOW()),
       (8, 'Glyphosate', 'A widely used herbicide with potential health concerns', 4, NOW(), NOW()),
       (9, 'Carbon Monoxide', 'A colorless, odorless gas produced by combustion', 5, NOW(), NOW()),
       (10, 'Sulfur Dioxide', 'A gas produced by burning fossil fuels', 5, NOW(), NOW()),
       (11, 'Dioxins', 'Highly toxic compounds formed during combustion', 6, NOW(), NOW()),
       (12, 'Polycyclic Aromatic Hydrocarbons (PAHs)', 'A class of chemicals formed during incomplete combustion', 6,
        NOW(), NOW()),
       (13, 'Radon', 'A radioactive gas naturally found in soil and rock', 7, NOW(), NOW()),
       (14, 'Cesium-137', 'A radioactive isotope from nuclear fallout', 7, NOW(), NOW()),
       (15, 'E. coli', 'A bacteria that can cause serious foodborne illness', 8, NOW(), NOW()),
       (16, 'Legionella', 'A bacteria that thrives in water systems', 8, NOW(), NOW()),
       (17, 'Bisphenol A (BPA)', 'A chemical used in plastics that may disrupt hormones', 9, NOW(), NOW()),
       (18, 'Phthalates', 'Plasticizers that can affect human development', 9, NOW(), NOW()),
       (19, 'Ibuprofen', 'A common painkiller that can be found in water sources', 10, NOW(), NOW()),
       (20, 'Antibiotic Residues', 'Traces of antibiotics in the environment leading to resistance', 10, NOW(), NOW());



INSERT INTO users (id, username, email, role, created_at, updated_at)
VALUES ('44444444-2222-2222-2222-222222222222', 'john_doe', 'john.doe@example.com', 'admin', NOW(), NOW()),
       ('55555555-3333-3333-3333-333333333333', 'jane_smith', 'jane.smith@example.com', 'technician', NOW(), NOW());

-- Insert Companies
INSERT INTO companies (name, address, contact_person, email, phone, country, city)
VALUES ('Tech Solutions Ltd.', '123 Tech Street', 'Alice Johnson', 'contact@techsolutions.com', '+123456789', 'USA',
        'New York'),
       ('Industrial Safety Inc.', '456 Safety Ave', 'Bob Brown', 'info@safetyinc.com', '+987654321', 'Germany',
        'Berlin')
RETURNING id;

-- Insert Locations
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (1, 'Tech Solutions HQ', '123 Tech Street', 'Alice Johnson', 'alice.johnson@techsolutions.com', '+123456789',
        'USA', 'New York', '10001'),
       (2, 'Industrial Safety Branch', '456 Safety Ave', 'Bob Brown', 'bob.brown@safetyinc.com', '+987654321',
        'Germany', 'Berlin', '10115')
RETURNING id;

-- Insert Clients
INSERT INTO clients (name, contact_person, email, phone, address, country, city, postal_code, tax_number)
VALUES ('Global Engineering', 'Michael Scott', 'michael.scott@globaleng.com', '+1122334455', '789 Industrial Road',
        'USA', 'Los Angeles', '90001', 'TAX123456'),
       ('Green Energy Ltd.', 'Sarah Connor', 'sarah.connor@greenenergy.com', '+2233445566', '321 Eco Street', 'UK',
        'London', 'E1 6AN', 'TAX987654');

-- Insert Projects
INSERT INTO projects (project_number, client_id, project_name, start_date, end_date, status, description)
VALUES ('PROJ-001', 1, 'Air Quality Testing', '2024-01-01', '2024-12-31', 'ONGOING',
        'Testing air quality at industrial sites.'),
       ('PROJ-002', 2, 'Safety Compliance Audit', '2024-03-01', NULL, 'ONGOING', 'Ensuring safety standards are met.');

-- Insert Equipments
INSERT INTO equipments (name, identifier, description, manufacturer, type, serial_number, measuring_range, resolution,
                        accuracy, calibration_date, next_calibration_date, created_at, updated_at)
VALUES ('Air Quality Monitor', 'AQM-001', 'Detects air pollutants', 'EnviroTech', 'Air Monitoring', 'SN-AQM-1001',
        '0-500 ppm', '0.01 ppm', '±2%', '2023-05-15', '2024-05-15', NOW(), NOW()),

       ('Gas Analyzer', 'GA-002', 'Analyzes gas composition', 'SafeAir', 'Gas Detection', 'SN-GA-2002', '0-100%',
        '0.1%', '±1%', '2023-07-10', '2024-07-10', NOW(), NOW()),

       ('High Precision Thermometer', 'THERMO-12345', 'A thermometer with high precision for lab testing.',
        'ThermoTech Inc.', 'Temperature Measurement', 'SN-98765', '-50°C to 150°C', '0.01°C', '±0.1°C', '2024-02-20',
        '2025-02-20', NOW(), NOW()),

       ('Advanced Gas Detector', 'AGD-3001', 'Detects hazardous gases in industrial settings.', 'GasSecure',
        'Safety Equipment', 'SN-3001-GAS', '0-1000 ppm', '0.5 ppm', '±0.5%', '2023-06-01', '2024-06-01', NOW(), NOW());

-- Insert Standards
INSERT INTO standards (standard_number, description, standard_type, identifier)
VALUES ('ISO 9001', 'Quality Management System', 'ISO', 'ISO-9001'),
       ('OSHA 1910.1000', 'Occupational Safety and Health Standards', 'OSHA', 'OSHA-1910');

-- Insert Sampling Types
INSERT INTO sampling_types (code, description)
VALUES ('ST1', 'Air sampling'),
       ('ST2', 'Gas sampling');

-- Insert Adjustment Methods
INSERT INTO adjustment_methods (code, description)
VALUES ('AM1', 'Manual adjustment'),
       ('AM2', 'Automatic adjustment');

-- Insert Sampling Records
INSERT INTO sampling_records_dat_m200 (sampling_date, conducted_by, site_location_id, company_id, tested_plant,
                                       technology, shift_count_and_duration, workers_per_shift, exposure_time,
                                       temperature, humidity, wind_speed, pressure1, pressure2,
                                       other_environmental_conditions, air_flow_conditions, operation_mode,
                                       operation_break, local_air_extraction, serial_numbers_of_samples, project_number)
VALUES ('2024-02-15 08:00:00', '22222222-2222-2222-2222-222222222222', 1, 1, 'Factory 1', 'Modern Tech', 3, 50, 8, 22.5,
        45.0, 3.5, 1013.2, 1012.8, 'None', 'Good', 'Normal', 'None', 'Yes', 'SN001, SN002', 1),
       ('2024-02-16 09:00:00', '33333333-3333-3333-3333-333333333333', 2, 2, 'Plant 2', 'Traditional Tech', 2, 40, 6,
        18.0, 50.0, 2.5, 1012.5, 1011.7, 'Slight dust', 'Moderate', 'High', 'Frequent', 'No', 'SN003, SN004', 2);

-- Insert Measurement Units
INSERT INTO measurement_units (unit_code, description, unit_category, base_unit_id, conversion_factor)
VALUES ('mg/m³', 'Milligrams per cubic meter', 'Concentration', 1, 1),
       ('ppm', 'Parts per million', 'Concentration', 1, 1);

-- Insert Samples
INSERT INTO samples (sampling_record_id, sample_identifier, location, employee_name, temperature, humidity, pressure,
                     sample_volume_flow_rate, sample_volume_flow_rate_unit, start_time, end_time, sample_type, status,
                     sampling_type_id, adjustment_method_id, sampling_flow_rate)
VALUES (1, 'SMP-001', 'Factory 1 - Zone A', 'Worker A', 22.5, 45.0, 1013.2, 2.5, 1, '2024-02-15 08:30:00',
        '2024-02-15 10:00:00', 'AK', 'ACTIVE', 1, 1, 1.5),
       (2, 'SMP-002', 'Plant 2 - Zone B', 'Worker B', 18.0, 50.0, 1012.5, 3.0, 2, '2024-02-16 09:30:00',
        '2024-02-16 11:00:00', 'CK', 'ACTIVE', 2, 2, 2.0);

-- Insert Analytical Laboratories
INSERT INTO laboratories (name, accreditation, contact_email, phone, address, website)
VALUES ('National Lab', 'NAH-1-1666/2019', 'lab@nationallab.com', '+4455667788', 'Lab Street 1, Berlin',
        'www.nationallab.com'),
       ('EcoLab', 'ISO-17025', 'contact@ecolab.com', '+3355667788', 'Eco Road 5, London', 'www.ecolab.com');

-- Insert Analytical Lab Reports
INSERT INTO analytical_lab_reports (report_number, issue_date, laboratory_id)
VALUES ('LAB-001', '2024-02-17', 1),
       ('LAB-002', '2024-02-18', 2);



-- Insert Test Reports
INSERT INTO test_reports (report_number, title, approved_by, prepared_by, checked_by, aim_of_test, project_id,
                          location_id, sampling_record_id, technology, sampling_conditions_dates,
                          determination_of_pollutant_concentration, issue_date, report_status)
VALUES ('TR-001', 'Air Quality Test Report', '22222222-2222-2222-2222-222222222222',
        '33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222',
        'Evaluate air quality in factory', 1, 1, 1, 'Modern Tech', '2024-02-15', 'Detailed analysis', '2024-02-20',
        'FINALIZED');



INSERT INTO regulatory_limits_workplace
(fk_contaminant_id, fk_measurement_unit_id, fk_sample_type, limit_value)
VALUES
    -- Benzene (Workplace Air Limit)
    (1, 1, 'AK', 1.0),  -- 1.0 mg/m³ for AK samples
    (1, 1, 'CK', 0.5),  -- 0.5 mg/m³ for CK samples

    -- Lead (Workplace Exposure Limit)
    (2, 2, 'AK', 0.05), -- 0.05 ppm for AK samples
    (2, 2, 'CK', 0.03); -- 0.03 ppm for CK samples


INSERT INTO samples (sampling_record_id, sample_identifier, location, employee_name,
                     temperature, humidity, pressure, sample_volume_flow_rate,
                     sample_volume_flow_rate_unit, start_time, end_time,
                     sample_type, status, remarks, sampling_type_id,
                     adjustment_method_id, sampling_flow_rate, created_at, updated_at)
VALUES (1, 'SMP-101', 'Factory 1 - Zone A', 'Worker A', 20.45, 44.64, 1006.98, 2.1327, 1, '2024-02-15 11:00:00',
        '2024-02-15 14:00:00', 'CK', 'LOST', 'Auto-generated test data', 1, 1, 1.760, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-102', 'Factory 1 - Zone B', 'Worker B', 22.99, 39.92, 1004.40, 1.5644, 2, '2024-02-15 10:00:00',
        '2024-02-15 11:00:00', 'AK', 'ACTIVE', 'Auto-generated test data', 1, 1, 2.433, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-103', 'Factory 1 - Zone A', 'Worker C', 23.72, 47.40, 1005.01, 4.5677, 1, '2024-02-15 12:00:00',
        '2024-02-15 14:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 1, 1, 2.343, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-104', 'Factory 1 - Zone A', 'Worker A', 23.40, 48.32, 1010.34, 4.3860, 1, '2024-02-15 08:00:00',
        '2024-02-15 10:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 2, 1, 2.130, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-105', 'Factory 1 - Zone A', 'Worker D', 22.94, 69.15, 1008.94, 1.0780, 1, '2024-02-15 12:00:00',
        '2024-02-15 14:00:00', 'AK', 'ACTIVE', 'Auto-generated test data', 2, 2, 0.861, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-106', 'Factory 1 - Zone A', 'Worker D', 20.10, 61.63, 1005.10, 1.0223, 1, '2024-02-15 11:00:00',
        '2024-02-15 12:00:00', 'CK', 'LOST', 'Auto-generated test data', 1, 2, 2.814, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-107', 'Factory 1 - Zone C', 'Worker A', 27.96, 65.00, 1005.17, 1.0831, 2, '2024-02-15 09:00:00',
        '2024-02-15 11:00:00', 'AK', 'ACTIVE', 'Auto-generated test data', 1, 2, 1.787, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-108', 'Factory 1 - Zone B', 'Worker A', 22.78, 44.09, 1003.92, 3.0844, 1, '2024-02-15 08:00:00',
        '2024-02-15 09:00:00', 'AK', 'ACTIVE', 'Auto-generated test data', 1, 1, 2.639, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-109', 'Factory 1 - Zone A', 'Worker B', 27.75, 33.32, 1011.63, 2.0787, 2, '2024-02-15 11:00:00',
        '2024-02-15 14:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 2, 1, 2.546, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-110', 'Factory 1 - Zone C', 'Worker A', 16.74, 31.00, 1015.03, 4.3690, 2, '2024-02-15 08:00:00',
        '2024-02-15 10:00:00', 'AK', 'BROKEN', 'Auto-generated test data', 2, 1, 1.583, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-130', 'Factory 1 - Zone A', 'Worker B', 25.39, 46.53, 1016.33, 3.6220, 2, '2024-02-15 12:00:00',
        '2024-02-15 15:00:00', 'CK', 'BROKEN', 'Auto-generated test data', 2, 1, 2.010, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-111', 'Factory 1 - Zone C', 'Worker B', 21.11, 36.62, 1009.05, 3.8655, 1, '2024-02-15 11:00:00',
        '2024-02-15 13:00:00', 'CK', 'INVALID', 'Auto-generated test data', 1, 1, 2.515, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-112', 'Factory 1 - Zone B', 'Worker B', 22.37, 65.37, 1018.21, 4.0541, 1, '2024-02-15 09:00:00',
        '2024-02-15 11:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 2, 2, 2.686, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-113', 'Factory 1 - Zone A', 'Worker A', 28.97, 66.43, 1016.12, 1.5, 1, '2024-02-15 09:00:00',
        '2024-02-15 11:00:00', 'CK', 'LOST', 'Auto-generated test data', 2, 1, 0.714, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-114', 'Factory 1 - Zone B', 'Worker C', 20.03, 46.00, 1009.77, 4.9908, 2, '2024-02-15 11:00:00',
        '2024-02-15 12:00:00', 'CK', 'LOST', 'Auto-generated test data', 2, 2, 1.561, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-115', 'Factory 1 - Zone A', 'Worker A', 18.12, 34.47, 1012.89, 4.3047, 2, '2024-02-15 10:00:00',
        '2024-02-15 12:00:00', 'CK', 'BROKEN', 'Auto-generated test data', 2, 1, 2.833, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-116', 'Factory 1 - Zone B', 'Worker C', 17.53, 52.72, 1013.43, 4.6970, 1, '2024-02-15 10:00:00',
        '2024-02-15 11:00:00', 'AK', 'LOST', 'Auto-generated test data', 1, 1, 2.914, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-117', 'Factory 1 - Zone C', 'Worker A', 27.89, 38.41, 1002.54, 4.9744, 2, '2024-02-15 12:00:00',
        '2024-02-15 14:00:00', 'AK', 'ACTIVE', 'Auto-generated test data', 2, 1, 2.074, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-118', 'Factory 1 - Zone B', 'Worker D', 23.62, 57.82, 1008.68, 1.1326, 2, '2024-02-15 10:00:00',
        '2024-02-15 11:00:00', 'AK', 'BROKEN', 'Auto-generated test data', 2, 2, 2.375, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-119', 'Factory 1 - Zone B', 'Worker C', 24.84, 41.01, 1003.58, 3.2086, 1, '2024-02-15 12:00:00',
        '2024-02-15 14:00:00', 'CK', 'BROKEN', 'Auto-generated test data', 1, 1, 1.203, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-120', 'Factory 1 - Zone C', 'Worker B', 23.41, 49.23, 1014.26, 4.6209, 2, '2024-02-15 11:00:00',
        '2024-02-15 14:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 1, 2, 1.537, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-121', 'Factory 1 - Zone A', 'Worker A', 22.40, 42.01, 1016.50, 1.3755, 2, '2024-02-15 10:00:00',
        '2024-02-15 11:00:00', 'AK', 'INVALID', 'Auto-generated test data', 1, 1, 2.769, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-122', 'Factory 1 - Zone C', 'Worker C', 24.53, 69.08, 1004.96, 4.6258, 2, '2024-02-15 08:00:00',
        '2024-02-15 10:00:00', 'CK', 'INVALID', 'Auto-generated test data', 2, 1, 1.574, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-123', 'Factory 1 - Zone B', 'Worker C', 21.61, 54.46, 1012.06, 2.2112, 2, '2024-02-15 12:00:00',
        '2024-02-15 13:00:00', 'AK', 'ACTIVE', 'Auto-generated test data', 1, 2, 2.389, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-124', 'Factory 1 - Zone C', 'Worker D', 25.69, 65.78, 1009.42, 2.7788, 2, '2024-02-15 09:00:00',
        '2024-02-15 12:00:00', 'AK', 'BROKEN', 'Auto-generated test data', 2, 2, 2.644, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-125', 'Factory 1 - Zone B', 'Worker D', 20.74, 34.58, 1012.29, 3.9671, 2, '2024-02-15 08:00:00',
        '2024-02-15 10:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 1, 1, 2.354, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-130', 'Factory 1 - Zone A', 'Worker B', 25.39, 46.53, 1016.33, 3.6220, 2, '2024-02-15 12:00:00',
        '2024-02-15 15:00:00', 'CK', 'BROKEN', 'Auto-generated test data', 2, 1, 2.010, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34'),
       (1, 'SMP-131', 'Factory 1 - Zone A', 'Worker B', 25.39, 46.53, 1016.33, 3.6220, 2, '2024-02-15 12:00:00',
        '2024-02-15 15:00:00', 'CK', 'ACTIVE', 'Auto-generated test data', 2, 1, 2.010, '2025-03-15 08:42:34',
        '2025-03-15 08:42:34');



INSERT INTO sample_contaminants (
    fk_sample_id, fk_contaminant_id, created_at, updated_at
) VALUES
      (3, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (12, 6, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (8, 14, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (18, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (22, 2, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (6, 8, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (12, 12, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (12, 13, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (22, 18, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (8, 16, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (21, 15, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (4, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (14, 20, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (14, 17, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (6, 9, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (19, 2, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (25, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (11, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (13, 12, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (15, 5, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (26, 19, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (1, 4, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (9, 1, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (27, 9, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (27, 18, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (15, 8, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (21, 16, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (30, 15, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (15, 1, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
      (28, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47');

-- Insert into sample_analytical_results (Using sample_contaminant_id)
INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_measurement_unit, detection_limit,
                                       measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
                                       calculated_concentration, calculated_concentration_measurement_unit)
VALUES ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 3 AND fk_contaminant_id = 7), 0.35, 1, 0.1, 5.0,
        'GC-MS', 1, '2024-02-17 12:00:00', 0.35, 1),
       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 12 AND fk_contaminant_id = 6), 1.25, 2, 0.2, 4.5,
        'HPLC', 2, '2024-02-18 14:00:00', 1.25, 2);



