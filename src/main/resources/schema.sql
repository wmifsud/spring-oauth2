CREATE schema external;

CREATE TABLE external.client_details
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

CREATE TABLE external.user_details
(
    id bigserial PRIMARY KEY,
    username citext NOT NULL,
    password citext NOT NULL,
    enabled boolean NOT NULL,
    CONSTRAINT username_unique UNIQUE(username)
);

CREATE TABLE external.authority
(
    id bigserial PRIMARY KEY,
    authority citext NOT NULL,
    CONSTRAINT authority_check_enum CHECK (authority = ANY (ARRAY['ROLE_ADMIN', 'ROLE_USER']))
);

-- mapping tables
CREATE TABLE external.client_details_user_details_map
(
    client_details_id bigint NOT NULL,
    user_details_id bigint NOT NULL,
    CONSTRAINT fk_client_details_id_client_map FOREIGN KEY (client_details_id) REFERENCES external.client_details(id),
    CONSTRAINT fk_user_details_id_user_map FOREIGN KEY (user_details_id) REFERENCES external.user_details(id),
    CONSTRAINT client_details_id_user_details_id_primary PRIMARY KEY (client_details_id, user_details_id)
);

CREATE TABLE external.user_details_authority_map
(
    user_details_id bigint NOT NULL,
    authority_id bigint NOT NULL,
    CONSTRAINT fk_user_details_id_user_map FOREIGN KEY (user_details_id) REFERENCES external.user_details(id),
    CONSTRAINT fk_authority_id_client_map FOREIGN KEY (authority_id) REFERENCES external.authority(id),
    CONSTRAINT user_details_id_authority_id_primary PRIMARY KEY (user_details_id, authority_id)
);

-- insert input data
INSERT INTO external.client_details (client_id,secret,scope,authorized_grant_types,access_token_validity,refresh_token_validity)
VALUES ('my-trusted-client','secret','read,write,trust','password,refresh_token',120,600);

INSERT INTO external.client_details (client_id,secret,scope,authorized_grant_types,access_token_validity,refresh_token_validity)
VALUES ('my-trusted-client2','secret2','read,write,trust','password,refresh_token',200,1000);

INSERT INTO external.user_details (username, password, enabled)
VALUES ('bill', '$2a$10$cE/lgJzBNZHo571y7YhsnuFVBfVamt5Lq5wOZfZdSZ5Z.uCtmXjnm', true);

INSERT INTO external.user_details (username, password, enabled)
VALUES ('bill2', '$2a$10$cE/lgJzBNZHo571y7YhsnuFVBfVamt5Lq5wOZfZdSZ5Z.uCtmXjnm', true);

INSERT INTO external.client_details_user_details_map (client_details_id, user_details_id)
VALUES (1,1);
INSERT INTO external.client_details_user_details_map (client_details_id, user_details_id)
VALUES (2,1);
INSERT INTO external.client_details_user_details_map (client_details_id, user_details_id)
VALUES (2,2);

INSERT INTO external.authority (authority)
VALUES ('ROLE_ADMIN');
INSERT INTO external.authority (authority)
VALUES ('ROLE_USER');

INSERT INTO external.user_details_authority_map (user_details_id, authority_id)
VALUES (1,1);
INSERT INTO external.user_details_authority_map (user_details_id, authority_id)
VALUES (1,2);
INSERT INTO external.user_details_authority_map (user_details_id, authority_id)
VALUES (2,1);