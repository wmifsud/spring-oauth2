CREATE EXTENSION IF NOT EXISTS citext;

DROP SCHEMA IF EXISTS oauth2 CASCADE;

DROP USER IF EXISTS oauth2;

CREATE schema oauth2;

CREATE USER oauth2 WITH PASSWORD 'oauth2';

CREATE TABLE oauth2.client_details
(
    id bigserial PRIMARY KEY,
    client_id citext NOT NULL,
    secret citext NOT NULL,
    scope citext NOT NULL,
    authorized_grant_types citext NOT NULL,
    access_token_validity bigint NOT NULL,
    refresh_token_validity bigint NOT NULL,
    CONSTRAINT client_details_id_unique UNIQUE(client_id)
);

CREATE TABLE oauth2.user_details
(
    id bigserial PRIMARY KEY,
    username citext NOT NULL,
    password citext NOT NULL,
    enabled boolean NOT NULL,
    CONSTRAINT username_unique UNIQUE(username)
);

CREATE TABLE oauth2.authority
(
    id bigserial PRIMARY KEY,
    authority citext NOT NULL,
    CONSTRAINT authority_check_enum CHECK (authority = ANY (ARRAY['ROLE_ADMIN', 'ROLE_USER']))
);

-- mapping tables
CREATE TABLE oauth2.client_details_user_details_map
(
    client_details_id bigint NOT NULL,
    user_details_id bigint NOT NULL,
    CONSTRAINT fk_client_details_id_client_map FOREIGN KEY (client_details_id) REFERENCES oauth2.client_details(id),
    CONSTRAINT fk_user_details_id_user_map FOREIGN KEY (user_details_id) REFERENCES oauth2.user_details(id),
    CONSTRAINT client_details_id_user_details_id_primary PRIMARY KEY (client_details_id, user_details_id)
);

CREATE TABLE oauth2.user_details_authority_map
(
    user_details_id bigint NOT NULL,
    authority_id bigint NOT NULL,
    CONSTRAINT fk_user_details_id_user_map FOREIGN KEY (user_details_id) REFERENCES oauth2.user_details(id),
    CONSTRAINT fk_authority_id_client_map FOREIGN KEY (authority_id) REFERENCES oauth2.authority(id),
    CONSTRAINT user_details_id_authority_id_primary PRIMARY KEY (user_details_id, authority_id)
);

-- insert input data
INSERT INTO oauth2.client_details (client_id,secret,scope,authorized_grant_types,access_token_validity,refresh_token_validity)
VALUES ('my-trusted-client','secret','read,write,trust','password,refresh_token',120,600);

INSERT INTO oauth2.client_details (client_id,secret,scope,authorized_grant_types,access_token_validity,refresh_token_validity)
VALUES ('my-trusted-client2','secret2','read,write,trust','password,refresh_token',200,1000);

INSERT INTO oauth2.user_details (username, password, enabled)
VALUES ('bill', '$2a$10$cE/lgJzBNZHo571y7YhsnuFVBfVamt5Lq5wOZfZdSZ5Z.uCtmXjnm', true);

INSERT INTO oauth2.user_details (username, password, enabled)
VALUES ('bill2', '$2a$10$cE/lgJzBNZHo571y7YhsnuFVBfVamt5Lq5wOZfZdSZ5Z.uCtmXjnm', true);

INSERT INTO oauth2.client_details_user_details_map (client_details_id, user_details_id)
VALUES (1,1);
INSERT INTO oauth2.client_details_user_details_map (client_details_id, user_details_id)
VALUES (2,1);
INSERT INTO oauth2.client_details_user_details_map (client_details_id, user_details_id)
VALUES (2,2);

INSERT INTO oauth2.authority (authority)
VALUES ('ROLE_ADMIN');
INSERT INTO oauth2.authority (authority)
VALUES ('ROLE_USER');

INSERT INTO oauth2.user_details_authority_map (user_details_id, authority_id)
VALUES (1,1);
INSERT INTO oauth2.user_details_authority_map (user_details_id, authority_id)
VALUES (1,2);
INSERT INTO oauth2.user_details_authority_map (user_details_id, authority_id)
VALUES (2,1);

GRANT ALL ON SCHEMA oauth2 TO oauth2;

GRANT ALL ON TABLE oauth2.client_details TO oauth2;
GRANT ALL ON TABLE oauth2.user_details TO oauth2;
GRANT ALL ON TABLE oauth2.authority TO oauth2;
GRANT ALL ON TABLE oauth2.client_details_user_details_map TO oauth2;
GRANT ALL ON TABLE oauth2.user_details_authority_map TO oauth2;

CREATE schema users;

CREATE TABLE users.person
(
    id bigserial PRIMARY KEY,
    name citext NOT NULL,
    surname citext NOT NULL,
    id_card citext NOT NULL,
    CONSTRAINT id_card_unique UNIQUE(id_card)
);

CREATE TABLE users.teacher
(
    person_id bigint NOT NULL,
    subject citext NOT NULL,
    CONSTRAINT fk_teacher_person_link FOREIGN KEY (person_id) REFERENCES users.person(id)
);

CREATE TABLE users.student
(
    person_id bigint NOT NULL,
    current_year smallint NOT NULL,
    lessons_per_week smallint NOT NULL,
    CONSTRAINT fk_student_person_link FOREIGN KEY (person_id) REFERENCES users.person(id)
);

INSERT INTO users.person (name, surname, id_card)
VALUES ('user1','surname1','123456M');
INSERT INTO users.student (person_id, current_year, lessons_per_week)
VALUES (1, 3, 15);
INSERT INTO users.person (name, surname, id_card)
VALUES ('John','Borg','654321M');
INSERT INTO users.teacher (person_id, subject)
VALUES (2, 'Mathematics');

GRANT ALL ON SCHEMA users TO oauth2;

GRANT ALL ON TABLE users.person TO oauth2;
GRANT ALL ON TABLE users.student TO oauth2;
GRANT ALL ON TABLE users.teacher TO oauth2;

GRANT ALL ON SEQUENCE users.person_id_seq TO oauth2;