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

DROP TABLE IF EXISTS users;
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
VALUES ('11111111-1111-1111-1111-111111111111', 'Iga Benedek', 'anonymous@example.com', 'manager', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO users (id, username, email, role)
VALUES ('22222222-2222-2222-2222-222222222222', 'dr. Csókási Pál', 'benonymous@encotech.hu', 'műszaki igazgató'),
       ('33333333-3333-3333-3333-333333333333', 'Mászáros Poci László', 'vazulnymus@ananas.hu', 'technician');

INSERT INTO users (id, username, email, role, created_at, updated_at)
VALUES ('44444444-2222-2222-2222-222222222222', 'Poremba Marcell Áron', 'john.doe@example.com', 'vizsgáló mérnök',
        NOW(), NOW()),
       ('55555555-3333-3333-3333-333333333333', 'Göndös Dorottya', 'jane.smith@example.com', 'vizsgáló mérnök', NOW(),
        NOW());

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
    standard_number VARCHAR(255)                                                  NOT NULL,
    description     TEXT,
    standard_type   VARCHAR(50) CHECK (standard_type IN ('SAMPLING', 'ANALYSES')) NOT NULL,
    identifier      VARCHAR(255) UNIQUE                                           NOT NULL,
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
    sample_volume_flow_rate      NUMERIC(7, 4),
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
    is_below_detection_limit                  BOOLEAN                                                        NOT NULL        DEFAULT FALSE,
    detection_limit                           NUMERIC(10, 4) CHECK (detection_limit IS NULL OR detection_limit >= 0),
    measurement_uncertainty                   NUMERIC(5, 2) CHECK (measurement_uncertainty IS NULL OR
                                                                   measurement_uncertainty BETWEEN 0 AND 100),
    analysis_method                           VARCHAR(255),
    lab_report_id                             BIGINT                                                         NOT NULL REFERENCES analytical_lab_reports (id) ON DELETE CASCADE,
    analysis_date                             TIMESTAMP CHECK (analysis_date <= CURRENT_TIMESTAMP),
    calculated_concentration                  NUMERIC(10, 4) CHECK (calculated_concentration >= 0),
    calculated_concentration_measurement_unit BIGINT                                                         NOT NULL REFERENCES measurement_units (id) ON DELETE RESTRICT,
    created_at                                TIMESTAMP                                                                      DEFAULT NOW(),
    updated_at                                TIMESTAMP                                                                      DEFAULT NOW(),
    CONSTRAINT unique_sample_contaminant UNIQUE (sample_contaminant_id),
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

CREATE TABLE laboratory_standards
(
    id            BIGSERIAL PRIMARY KEY,
    laboratory_id BIGINT NOT NULL,
    standard_id   BIGINT NOT NULL,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW(),
    UNIQUE (laboratory_id, standard_id),
    FOREIGN KEY (laboratory_id) REFERENCES laboratories (id) ON DELETE CASCADE,
    FOREIGN KEY (standard_id) REFERENCES standards (id) ON DELETE CASCADE
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

    -- Determine the ID type depending on the operation
    IF TG_OP IN ('DELETE', 'UPDATE') THEN
        IF pg_typeof(OLD.id)::text = 'uuid' THEN
            rec_uuid := OLD.id;
            rec_bigint := NULL;
        ELSIF pg_typeof(OLD.id)::text = 'bigint' THEN
            rec_bigint := OLD.id;
            rec_uuid := NULL;
        ELSE
            rec_uuid := NULL;
            rec_bigint := NULL;
        END IF;
    ELSE
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
    END IF;
    -- Insert audit log only if a valid ID was found
    IF rec_uuid IS NOT NULL OR rec_bigint IS NOT NULL THEN
        INSERT INTO audit_logs (
            user_id,
            table_name,
            action,
            record_id_uuid,
            record_id_bigint,
            changes
        ) VALUES (
                     current_user_uuid,
                     TG_TABLE_NAME,
                     TG_OP,
                     rec_uuid,
                     rec_bigint,
                     jsonb_build_object(
                             'old', CASE WHEN TG_OP IN ('UPDATE', 'DELETE') THEN row_to_json(OLD) ELSE NULL END,
                             'new', CASE WHEN TG_OP IN ('INSERT', 'UPDATE') THEN row_to_json(NEW) ELSE NULL END
                     )
                 );
    ELSE
        RAISE NOTICE 'Could not determine ID type for audit log entry on table %', TG_TABLE_NAME;
    END IF;

    -- Return the appropriate record
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

INSERT INTO contaminant_groups (name, description, created_at, updated_at)
VALUES ('Volatile Organic Compounds', 'Group of organic chemicals that evaporate easily', NOW(), NOW()),
       ('Heavy Metals', 'Metallic elements that can be toxic at low concentrations', NOW(), NOW()),
       ('Particulate Matter', 'Small solid or liquid particles in the air', NOW(), NOW()),
       ('Pesticides', 'Chemicals used to kill pests, can be harmful to humans', NOW(), NOW()),
       ('Industrial Gases', 'Gases emitted from industrial processes', NOW(), NOW()),
       ('Combustion Byproducts', 'Substances produced during burning of fuels', NOW(), NOW()),
       ('Radioactive Contaminants', 'Radioactive substances that pose health risks', NOW(), NOW()),
       ('Pathogenic Microorganisms', 'Microbes that can cause disease', NOW(), NOW()),
       ('Endocrine Disruptors', 'Chemicals that interfere with hormonal functions', NOW(), NOW()),
       ('Pharmaceutical Residues', 'Traces of pharmaceutical substances in the environment', NOW(), NOW());

INSERT INTO contaminant_groups (name, description)
VALUES ('Metals', 'Various metal contaminants'),
       ('Acids', 'Acidic contaminants'),
       ('Alcohols', 'Alcohol-based contaminants'),
       ('Solvents', 'Organic solvents and hydrocarbons'),
       ('Other', 'Miscellaneous contaminants');


INSERT INTO contaminants (name, description, contaminant_group_id, created_at, updated_at)
VALUES ('Benzene', 'A volatile organic compound found in industrial emissions', 1, NOW(), NOW()),
       ('Toluene', 'A solvent commonly used in paints and coatings', 1, NOW(), NOW()),
       ('Lead', 'A heavy metal that can cause serious health problems', 2, NOW(), NOW()),
       ('Mercury', 'A toxic heavy metal often found in fish', 2, NOW(), NOW()),
       ('PM10', 'Particulate matter less than 10 micrometers in diameter', 3, NOW(), NOW()),
       ('PM2.5', 'Fine particulate matter that can penetrate deep into the lungs', 3, NOW(), NOW()),
       ('DDT', 'A banned pesticide with long-lasting environmental effects', 4, NOW(), NOW()),
       ('Glyphosate', 'A widely used herbicide with potential health concerns', 4, NOW(), NOW()),
       ('Carbon Monoxide', 'A colorless, odorless gas produced by combustion', 5, NOW(), NOW()),
       ('Sulfur Dioxide', 'A gas produced by burning fossil fuels', 5, NOW(), NOW()),
       ('Dioxins', 'Highly toxic compounds formed during combustion', 6, NOW(), NOW()),
       ('Polycyclic Aromatic Hydrocarbons (PAHs)', 'A class of chemicals formed during incomplete combustion', 6,
        NOW(), NOW()),
       ('Radon', 'A radioactive gas naturally found in soil and rock', 7, NOW(), NOW()),
       ('Cesium-137', 'A radioactive isotope from nuclear fallout', 7, NOW(), NOW()),
       ('E. coli', 'A bacteria that can cause serious foodborne illness', 8, NOW(), NOW()),
       ('Legionella', 'A bacteria that thrives in water systems', 8, NOW(), NOW()),
       ('Bisphenol A (BPA)', 'A chemical used in plastics that may disrupt hormones', 9, NOW(), NOW()),
       ('Phthalates', 'Plasticizers that can affect human development', 9, NOW(), NOW()),
       ('Ibuprofen', 'A common painkiller that can be found in water sources', 10, NOW(), NOW()),
       ('Antibiotic Residues', 'Traces of antibiotics in the environment leading to resistance', 10, NOW(), NOW());

INSERT INTO contaminants (name, description, contaminant_group_id)
VALUES ('Kálium-hidroxid', 'Potassium hydroxide', (SELECT id FROM contaminant_groups WHERE name = 'Other')),
       ('Nátrium-hidroxid', 'Sodium hydroxide', (SELECT id FROM contaminant_groups WHERE name = 'Other')),
       ('Nikkel', 'Nickel', (SELECT id FROM contaminant_groups WHERE name = 'Metals')),
       ('Mangán (respirabilis)', 'Respirable Manganese', (SELECT id FROM contaminant_groups WHERE name = 'Metals')),
       ('Mangán (belélegezhető)', 'Inhalable Manganese', (SELECT id FROM contaminant_groups WHERE name = 'Metals')),
       ('Kobalt', 'Cobalt', (SELECT id FROM contaminant_groups WHERE name = 'Metals')),
       ('Cink-oxid por', 'Zinc Oxide Dust', (SELECT id FROM contaminant_groups WHERE name = 'Metals')),
       ('Foszforsav', 'Phosphoric Acid', (SELECT id FROM contaminant_groups WHERE name = 'Acids')),
       ('Fluorid', 'Fluoride', (SELECT id FROM contaminant_groups WHERE name = 'Other')),
       ('Toluol', 'Toluene', (SELECT id FROM contaminant_groups WHERE name = 'Solvents')),
       ('i-Propil-alkohol', 'Isopropyl Alcohol', (SELECT id FROM contaminant_groups WHERE name = 'Alcohols')),
       ('Etanol', 'Ethanol', (SELECT id FROM contaminant_groups WHERE name = 'Alcohols')),
       ('n-Butanol', 'n-Butanol', (SELECT id FROM contaminant_groups WHERE name = 'Alcohols')),
       ('Aceton', 'Acetone', (SELECT id FROM contaminant_groups WHERE name = 'Solvents')),
       ('n-Butil-acetát', 'n-Butyl Acetate', (SELECT id FROM contaminant_groups WHERE name = 'Solvents')),
       ('1-Metoxi-2-propil-acetát', '1-Methoxy-2-propyl Acetate',
        (SELECT id FROM contaminant_groups WHERE name = 'Solvents')),
       ('1-Metoxi-2-propanol', '1-Methoxy-2-Propanol', (SELECT id FROM contaminant_groups WHERE name = 'Solvents')),
       ('2-Butoxi-etanol', '2-Butoxy Ethanol', (SELECT id FROM contaminant_groups WHERE name = 'Solvents')),
       ('n-Heptán', 'n-Heptane', (SELECT id FROM contaminant_groups WHERE name = 'Solvents'));


-- Insert Companies
INSERT INTO companies (name, address, contact_person, email, phone, country, city)
VALUES ('Robert Bosch Automotive Steering Kft.', '3397 Maklár, Havasi László u. 2.', 'Alice Johnson',
        'contact@techsolutions.com', '+123456789', 'USA',
        'Maklár'),
       ('Industrial Safety Inc.', '456 Safety Ave', 'Bob Brown', 'info@safetyinc.com', '+987654321', 'Germany',
        'Berlin')
RETURNING id;

-- Insert Locations
INSERT INTO locations (company_id, name, address, contact_person, email, phone, country, city, postal_code)
VALUES (1, 'Robert Bosch Automotive Steering Kft.', '3397 Maklár, Havasi László u. 2.', 'Alice Johnson',
        'alice.johnson@techsolutions.com', '+123456789',
        'USA', 'Maklár', '10001'),
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
VALUES ('M-177/2024', 1, 'Munkahelyi légszennyezettség mérés', '2024-01-01', '2024-12-31', 'ONGOING',
        'Testing air quality at industrial sites.'),
       ('PROJ-002', 2, 'Safety Compliance Audit', '2024-03-01', NULL, 'ONGOING', 'Ensuring safety standards are met.');

-- Insert Equipments
INSERT INTO equipments (name, identifier, description, manufacturer, type, serial_number, measuring_range, resolution,
                        accuracy, calibration_date, next_calibration_date, created_at, updated_at)
VALUES ('TESTOTHERM', 'AGD-4001',
        'A hőmérséklet és a nedvességtartalom meghatározását TESTO 410-2 típusú digitális áramlás, hőmérséklet és relatív páratartalom mérővel végeztük. A mérőműszer jellemzői:',
        'TESTOTHERM',
        'TESTO 410-2', 'SN-3001-GAS', '0,4 ... 20 m/s; 5...95 % relatív páratartalom; -10 ... +50 °C',
        '0,1 m/s; 0,1 %; 0,1 °C', '±0,2 m/s; ±2,5 %; ±0,5 °C', '2023-06-01', '2024-06-01', NOW(), NOW()),

       ('TESTOTHERM', 'AGD-5001',
        'A légköri nyomás méréséhez TESTO 511 típusú barométert alkalmaztunk. A mérőműszer jellemzői:', 'TESTOTHERM',
        'TESTO 511', 'SN-3001-GAS', '300...1200 mbar', '0,1 mbar', null, '2023-06-01', '2024-06-01', NOW(), NOW()),

       ('Air Quality Monitor', 'AQM-001', 'Detects air pollutants', 'EnviroTech', 'Air Monitoring', 'SN-AQM-1001',
        '0-500 ppm', '0.01 ppm', '±2%', '2023-05-15', '2024-05-15', NOW(), NOW()),

       ('Gas Analyzer', 'GA-002', 'Analyzes gas composition', 'SafeAir', 'Gas Detection', 'SN-GA-2002', '0-100%',
        '0.1%', '±1%', '2023-07-10', '2024-07-10', NOW(), NOW()),

       ('High Precision Thermometer', 'THERMO-12345', 'A thermometer with high precision for lab testing.',
        'ThermoTech Inc.', 'Temperature Measurement', 'SN-98765', '-50°C to 150°C', '0.01°C', '±0.1°C', '2024-02-20',
        '2025-02-20', NOW(), NOW()),

       ('Advanced Gas Detector', 'AGD-3001', 'Detects hazardous gases in industrial settings.', 'GasSecure',
        'Safety Equipment', 'SN-3001-GAS', '0-1000 ppm', '0.5 ppm', '±0.5%', '2023-06-01', '2024-06-01', NOW(), NOW());

-- Insert Standards
INSERT INTO standards (standard_number, description, standard_type, identifier, created_at, updated_at)
VALUES ('MSZ EN 689:2018+AC:2019',
        'Munkahelyi levegő. Útmutató az inhalatív vegyianyag-expozíció becslésére a határértékekkel való összehasonlításhoz és mérési stratégiához.',
        'SAMPLING', 'MSZ_EN_689_2018', NOW(), NOW()),
       ('MSZ EN 482:2012+A1:2016', 'A vegyi anyagok mérési eljárásai teljesítőképességének általános követelményei.',
        'SAMPLING', 'MSZ_EN_482_2012', NOW(), NOW()),
       ('MSZ 21452-1:1975', 'A levegő állapotjelzőinek meghatározása. Nedvességtartalom mérése.', 'SAMPLING',
        'MSZ_21452_1_1975', NOW(), NOW()),
       ('MSZ 21452-3:1975', 'A levegő állapotjelzőinek meghatározása. Hőmérséklet mérése.', 'SAMPLING',
        'MSZ_21452_3_1975', NOW(), NOW()),
       ('MSZ ISO 8756:1995', 'Levegőminőség. A hőmérséklet-, a légnyomás- és a légnedvességi adatok figyelembevétele.',
        'SAMPLING', 'MSZ_ISO_8756_1995', NOW(), NOW()),
       ('MDHS 14/4:2014',
        'Respirábilis, torakális és belélegezhető por mintavételének és gravimetriás elemzésének általános eljárásai.',
        'ANALYSES', 'MDHS_14_4_2014', NOW(), NOW()),
       ('MSZ 21862-22:1982',
        'Munkahelyek gázállapotú légszennyezőinek vizsgálata. Gázkromatográfiás mintavétel és vizsgálat általános előírásai.',
        'SAMPLING', 'MSZ_21862_22_1982', NOW(), NOW()),
       ('MDHS 70:1993', 'Mintavétel illékony szerves anyagok meghatározásához.', 'SAMPLING', 'MDHS_70_1993', NOW(),
        NOW()),
       ('OSHA ID-165SGS:1985', 'Mintavétel szervetlen savak meghatározásához.', 'SAMPLING', 'OSHA_ID_165SGS_1985',
        NOW(), NOW()),
       ('MSZ EN ISO 10882-2:2001',
        'Egészségvédelem és biztonság a hegesztés és a rokon eljárások területén. A szilárd por és a gázok mintavétele a hegesztő légzési zónájában. 2. rész: A gázok mintavétele.',
        'SAMPLING', 'MSZ_EN_ISO_10882_2_2001', NOW(), NOW()),
       ('MSZ EN 45544-4:2016', 'Szervetlen gázok mérése folyamatos gázelemző készülékkel.', 'SAMPLING',
        'MSZ_EN_45544_4_2016', NOW(), NOW()),
       ('ISO 16200-1:2001', 'Illékony szerves komponensek meghatározása.', 'ANALYSES', 'ISO_16200_1_2001', NOW(),
        NOW()),
       ('MSZ 448-18:2009', 'Oldott orto-foszfát tartalom meghatározása.', 'ANALYSES', 'MSZ_448_18_2009', NOW(), NOW()),
       ('MSZ 21862-9:1981', 'HF tartalom meghatározása.', 'ANALYSES', 'MSZ_21862_9_1981', NOW(), NOW()),
       ('EPA 1-3.5:1998', 'Mintaelőkészítés elemek meghatározásához.', 'ANALYSES', 'EPA_1_3_5_1998', NOW(), NOW()),
       ('EPA 60208:2014', 'Elemtartalom meghatározása (ICP-MS).', 'ANALYSES', 'EPA_60208_2014', NOW(), NOW());

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

-- Insert Analytical Lab Reports
INSERT INTO analytical_lab_reports (report_number, issue_date, laboratory_id)
VALUES ('LAB-001', '2024-02-17', 1),
       ('LAB-002', '2024-02-18', 2);


-- Insert Test Reports
INSERT INTO test_reports (report_number, title, approved_by, prepared_by, checked_by, aim_of_test, project_id,
                          location_id, sampling_record_id, technology, sampling_conditions_dates,
                          determination_of_pollutant_concentration, issue_date, report_status)
VALUES ('1-177 /2024', 'Robert Bosch Automotive Steering Kft. maklári telephelyén
végzett munkahelyi légszennyezettség mérésekről', '22222222-2222-2222-2222-222222222222',
        '33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222',
        'Munkahelyi légszennyezettség mérés', 1, 1, 1, 'A Robert Bosch Automotive Steering Kft. maklári telephelyén elektromos kormányművek,
kormányművekhez fogaslécek és kormányanyák gyártását végzik. A vizsgált területek a
festősor környezetében találhatóak.', 'vizsgálatok ideje alatt a telephelyen folyamatos, normál üzemmenetnek megfelelő
munka folyt. Az üzemvitelt megzavaró körülményt nem tapasztaltunk. A mintavételek
ideje alatt 45 db kormánymű festését végezték óránként. A vizsgálatokat a megrendelő
által kijelölt, alábbi táblázatokban összefoglalt telepített pontokon hajtottuk végre.', 'A szennyező anyagok koncentrációjának meghatározásához SKC típusú személyi
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
szabvány figyelembevételével.', '2024-02-20', 'FINALIZED');



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



INSERT INTO sample_contaminants (fk_sample_id, fk_contaminant_id, created_at, updated_at)
VALUES (3, 7, '2025-03-15 09:04:47', '2025-03-15 09:04:47'),
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

-- -- Insert into sample_analytical_results (Using sample_contaminant_id)
-- INSERT INTO sample_analytical_results (sample_contaminant_id, result_main, result_measurement_unit, detection_limit,
--                                        measurement_uncertainty, analysis_method, lab_report_id, analysis_date,
--                                        calculated_concentration, calculated_concentration_measurement_unit)
-- VALUES ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 3 AND fk_contaminant_id = 7), 0.35, 1, 0.1, 5.0,
--         'GC-MS', 1, '2024-02-17 12:00:00', 0.35, 1),
--        ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 12 AND fk_contaminant_id = 6), 1.25, 2, 0.2, 4.5,
--         'HPLC', 2, '2024-02-18 14:00:00', 1.25, 2);
INSERT INTO sample_analytical_results (sample_contaminant_id,
                                       result_main,
                                       result_control,
                                       result_main_control,
                                       result_measurement_unit,
                                       detection_limit,
                                       measurement_uncertainty,
                                       analysis_method,
                                       lab_report_id,
                                       analysis_date,
                                       calculated_concentration,
                                       calculated_concentration_measurement_unit,
                                       created_at,
                                       updated_at,
                                       is_below_detection_limit)
VALUES ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 3 AND fk_contaminant_id = 7), 0.50, 0.48, 0.49, 1,
        0.5, 4.5, 'Gas Chromatography', 1, '2024-02-17 10:30:00', 0.50, 1, NOW(), NOW(), TRUE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 12 AND fk_contaminant_id = 6), 1.80, 1.75, 1.78, 2,
        0.10, 5.0, 'Liquid Chromatography', 2, '2024-02-18 11:15:00', 1.80, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 8 AND fk_contaminant_id = 14), 0.92, 0.89, 0.91, 1,
        0.05, 4.0, 'Mass Spectrometry', 1, '2024-02-19 09:45:00', 0.92, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 18 AND fk_contaminant_id = 7), 2.35, 2.30, 2.32, 2,
        0.08, 3.8, 'Atomic Absorption', 2, '2024-02-20 13:10:00', 2.35, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 22 AND fk_contaminant_id = 2), 0.76, 0.73, 0.75, 1,
        0.03, 2.5, 'UV-Vis Spectroscopy', 1, '2024-02-21 15:20:00', 0.76, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 6 AND fk_contaminant_id = 8), 3.20, 3.15, 3.18, 2,
        0.20, 6.0, 'Inductively Coupled Plasma', 2, '2024-02-22 10:00:00', 3.20, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 12 AND fk_contaminant_id = 12), 1.14, 1.10, 1.12, 1,
        0.07, 4.5, 'Ion Chromatography', 1, '2024-02-23 12:30:00', 1.14, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 12 AND fk_contaminant_id = 13), 2.60, 2.55, 2.58, 2,
        0.05, 3.2, 'Fourier Transform Infrared', 2, '2024-02-24 14:00:00', 2.60, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 22 AND fk_contaminant_id = 18), 1.05, 1.02, 1.03, 1,
        0.02, 2.0, 'Spectrophotometry', 1, '2024-02-25 11:30:00', 1.05, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 8 AND fk_contaminant_id = 16), 0.89, 0.87, 0.88, 2,
        0.04, 3.0, 'Electrochemical Analysis', 2, '2024-02-26 16:45:00', 0.89, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 21 AND fk_contaminant_id = 15), 3.45, 3.40, 0.42, 1,
        0.50, 4.8, 'Microbial Analysis', 1, '2024-02-27 09:15:00', 3.45, 1, NOW(), NOW(), TRUE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 4 AND fk_contaminant_id = 7), 1.76, 1.72, 1.74, 2,
        0.06, 3.5, 'Flame Photometry', 2, '2024-02-28 10:20:00', 1.76, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 14 AND fk_contaminant_id = 20), 0.63, 0.60, 0.62, 1,
        0.03, 2.2, 'Neutron Activation Analysis', 1, '2024-03-01 14:30:00', 0.63, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 14 AND fk_contaminant_id = 17), 2.89, 2.85, 2.87, 2,
        0.09, 5.0, 'UV-Vis Spectroscopy', 2, '2024-03-02 12:45:00', 2.89, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 6 AND fk_contaminant_id = 9), 1.35, 1.30, 1.32, 1,
        0.07, 3.8, 'Gas Chromatography', 1, '2024-03-03 15:00:00', 1.35, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 19 AND fk_contaminant_id = 2), 0.97, 0.95, 0.96, 2,
        0.04, 3.0, 'Liquid Chromatography', 2, '2024-03-04 09:10:00', 0.97, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 25 AND fk_contaminant_id = 7), 1.72, 1.68, 1.70, 1,
        0.08, 4.5, 'Mass Spectrometry', 1, '2024-03-05 13:40:00', 1.72, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 11 AND fk_contaminant_id = 7), 2.58, 2.55, 2.57, 2,
        0.05, 3.5, 'Atomic Absorption', 2, '2024-03-06 11:25:00', 2.58, 2, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 27 AND fk_contaminant_id = 18), 0.82, 0.79, 0.81, 1,
        0.03, 2.8, 'Gas Chromatography', 1, '2024-03-07 16:10:00', 0.82, 1, NOW(), NOW(), FALSE),

       ((SELECT id FROM sample_contaminants WHERE fk_sample_id = 30 AND fk_contaminant_id = 15), 1.45, 1.42, 1.43, 2,
        0.06, 3.2, 'UV-Vis Spectroscopy', 2, '2024-03-08 14:55:00', 1.45, 2, NOW(), NOW(), FALSE);



INSERT INTO sampling_record_equipments (fk_sampling_record_id, fk_equipment_id, created_at)
VALUES (1, 1, '2025-03-15 14:21:45'),
       (1, 2, '2025-03-15 14:21:45'),
       (2, 3, '2025-03-15 14:21:45'),
       (2, 4, '2025-03-15 14:21:45');


INSERT INTO laboratory_standards (laboratory_id, standard_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (2, 9),
       (2, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (2, 15),
       (2, 16);

INSERT INTO test_report_standards (fk_test_report_id, fk_standard_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16);

INSERT INTO test_report_samplers (fk_test_report_id, fk_user_id)
VALUES (1, '44444444-2222-2222-2222-222222222222'),
       (1, '55555555-3333-3333-3333-333333333333');

