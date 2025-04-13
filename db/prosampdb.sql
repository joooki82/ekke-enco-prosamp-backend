SET ROLE postgres;
--
DROP SCHEMA public CASCADE;
--
CREATE SCHEMA public;

SET ROLE prosamp;

-- #############################################################################
-- TABLE: Users
-- #############################################################################

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         UUID PRIMARY KEY, -- Keycloak User ID
    username   VARCHAR(100) UNIQUE NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- #############################################################################
-- TABLE: ROLES
-- #############################################################################

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id        BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(100) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS user_role_assignments;
CREATE TABLE user_role_assignments
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    UUID REFERENCES users (id) ON DELETE CASCADE,
    role_id    BIGINT REFERENCES roles (id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW()
);



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
    start_time                   TIMESTAMP(0),
    end_time                     TIMESTAMP(0),
    sample_type                  VARCHAR(10) CHECK (sample_type IN ('AK', 'CK')) DEFAULT 'AK',
    status                       VARCHAR(50)                                     DEFAULT 'ACTIVE', -- Státusz: 'active', 'lost', 'broken', 'invalid'
    remarks                      VARCHAR(255),
    sampling_type_id             BIGINT,
    adjustment_method_id         BIGINT,
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
    report_number VARCHAR(50) UNIQUE NOT NULL,
    issue_date    DATE               NOT NULL,
    laboratory_id BIGINT             NOT NULL REFERENCES laboratories (id) ON DELETE RESTRICT,
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


CREATE TABLE audit_logs
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    UUID REFERENCES users (id),
    table_name TEXT NOT NULL,
    action     TEXT CHECK (action IN ('INSERT', 'UPDATE', 'DELETE')),
    record_id  TEXT,
    changes    JSONB,
    timestamp  TIMESTAMP DEFAULT NOW()
);


CREATE OR REPLACE FUNCTION get_current_user_id()
    RETURNS UUID AS
$$
BEGIN
    RETURN COALESCE(
            NULLIF(current_setting('session.currentUserId', TRUE), '')::UUID,
            '11111111-1111-1111-1111-111111111111' -- Default: Anonymous User
           );
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION audit_log()
    RETURNS TRIGGER AS
$$
DECLARE
    current_user_uuid UUID;
    rec_id            TEXT;
BEGIN
    current_user_uuid := current_setting('session.currentUserId', true)::UUID;

    IF NOT EXISTS (SELECT 1 FROM users WHERE id = current_user_uuid) THEN
        RAISE NOTICE 'User ID % not found, skipping audit log.', current_user_uuid;
        RETURN NEW;
    END IF;

    IF TG_OP IN ('DELETE', 'UPDATE') THEN
        rec_id := OLD.id::TEXT;
    ELSE
        rec_id := NEW.id::TEXT;
    END IF;

    INSERT INTO audit_logs (user_id, table_name, action, record_id, changes)
    VALUES (current_user_uuid, TG_TABLE_NAME, TG_OP, rec_id,
            jsonb_build_object(
                    'old', CASE WHEN TG_OP IN ('UPDATE', 'DELETE') THEN row_to_json(OLD) ELSE NULL END,
                    'new', CASE WHEN TG_OP IN ('INSERT', 'UPDATE') THEN row_to_json(NEW) ELSE NULL END
            ));

    RETURN CASE WHEN TG_OP = 'DELETE' THEN OLD ELSE NEW END;
END;
$$ LANGUAGE plpgsql;



-- #############################################################################
-- Event Trigger: Automatically Applies Logging to New Tables
-- #############################################################################

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
