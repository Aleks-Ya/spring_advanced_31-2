INSERT INTO AUDITORIUM (ID, NAME, seatsNumber, vipSeats) VALUES (1, 'Blue hall', 500, '25,26,27,28,29,30,31,32,33,34,35');
INSERT INTO AUDITORIUM (ID, NAME, seatsNumber, vipSeats) VALUES (2, 'Red hall', 800, '25,26,27,28,29,30,31,32,33,34,35,75,76,77,78,79,80,81,82,83,84,85');
INSERT INTO AUDITORIUM (ID, NAME, seatsNumber, vipSeats) VALUES (3, 'Yellow hall', 1000, '25,26,27,28,29,30,31,32,33,34,35,75,76,77,78,79,80,81,82,83,84,85,105,106,107,108,109,110,111,112,113,114,115');

INSERT INTO USER (ID, EMAIL, NAME, BIRTHDAY, PASSWORD, ROLES) VALUES (1, 'john@gmail.com', 'John', '1980-06-20','jpass', 'REGISTERED_USER,BOOKING_MANAGER');
INSERT INTO USER (ID, EMAIL, NAME, BIRTHDAY, PASSWORD, ROLES) VALUES (2, 'mary@gmail.com', 'Mary', '1990-11-30', 'mpass', 'REGISTERED_USER');

INSERT INTO EVENT (id, name, rate, basePrice, dateTime, auditorium_id) VALUES (1, 'New Year Party', 'HIGH', 1500.5, '2019-12-31 23:30:00', 1);
INSERT INTO EVENT (id, name, rate, basePrice, dateTime, auditorium_id) VALUES (2, 'Summer Party', 'MID', 500, '2018-06-01 10:30:00', 2);

INSERT INTO TICKET (ID, event_id, dateTime, SEATS, PRICE) VALUES (1, 1, '2019-12-31 23:30:00', '1,2,3', 3000.5);
INSERT INTO TICKET (ID, event_id, dateTime, SEATS, PRICE) VALUES (2, 1, '2019-12-31 23:30:00', '13,14', 2000);

INSERT INTO BOOKING (ID, user_id, ticket_id) VALUES (1, 1, 1);
INSERT INTO BOOKING (ID, user_id, ticket_id) VALUES (2, 1, 2);