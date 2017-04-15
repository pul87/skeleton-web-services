insert into greeting ( text ) values ('Hello world');
insert into greeting ( text ) values ('Ciao');

-- password is 'password'
INSERT INTO Account (username, password, enabled, credentialexpired, expired, locked) VALUES ('user', '$2a$04$IcFHYeHWokLa6Y0K0KYcz.pLqk7b0tEpCJhXmxReiItiaax3B2mr6', true, false, false, false);
-- password is 'operations'
INSERT INTO Account (username, password, enabled, credentialexpired, expired, locked) VALUES ('operations', '$2a$04$Nrbz6lvvbjsr6EiNe.VlAu0TpAkmyGoK40ua5zCX.iO3/.r0KwyUi', true, false, false, false);

INSERT INTO Role (id, code, label) VALUES (1, 'ROLE_USER', 'User');
INSERT INTO Role (id, code, label) VALUES (2, 'ROLE_ADMIN', 'Admin');
INSERT INTO Role (id, code, label) VALUES (3, 'ROLE_SYSADMIN', 'System Admin');

INSERT INTO Account_Role (account_Id, role_Id) SELECT a.id, r.id FROM Account a, Role r WHERE a.username = 'user' and r.id = 1;
INSERT INTO Account_Role (account_Id, role_Id) SELECT a.id, r.id FROM Account a, Role r WHERE a.username = 'operations' and r.id = 3;