USE thePond_db;

INSERT INTO category (name)
VALUES ('Bills'),
       ('Utilities'),
       ('Entertainment'),
       ('Food/Dining'),
       ('Fuel'),
       ('Lodging'),
       ('Loans'),
       ('Bills'),
       ('Miscellaneous'),
       ('Shopping'),
       ('Groceries'),
       ('Gifts/Donations'),
       ('Personal'),
       ('Budget Goal');
SELECT t.amount_in_cents FROM Transaction t WHERE t.is_income = true;
SELECT t.amount_in_cents FROM Transaction t JOIN budget b ON b.id = t.budget_id JOIN users u ON u.id = b.user_id WHERE t.is_income = true AND u.id = ? ;

SELECT t.amount_in_cents FROM Transaction t JOIN budget b ON b.user_id = t.budget_id WHERE t.is_income = false;

