insert into pricecategories (pricecategory_id, pricecategory_type) values (1, 'Regular');
insert into pricecategories (pricecategory_id, pricecategory_type) values (2, 'Children');
insert into pricecategories (pricecategory_id, pricecategory_type) values (3, 'NewRelease');

insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (1, '2017-05-11', 'Marie Curie', true, 1);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (2, '2017-07-20', 'Curchill', true, 1);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (3, '2017-08-18', 'The Boss Baby', true, 2);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (4, '2017-08-31', 'Pirates of the Caribean: Salazar''s Revenge', false, 3);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (5, '2017-09-07', 'Die göttliche Ordnung', false, 3);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (6, '2018-05-25', 'Loving Vincent', false, 1);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (7, '2018-08-13', 'Fast & Furious 7', false, 3);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (8, '2018-10-01', 'Momo', false, 3);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (9, '2018-10-03', 'Swimming with Men', false, 3);
insert into movies (movie_id, movie_releasedate, movie_title, movie_rented, pricecategory_fk) values (10,'2018-10-22', 'Jurassic World', false, 3);

insert into users (user_id, user_name, user_firstname, user_email) values (1, 'Keller', 'Marc', 'marc.keller@gmail.com');
insert into users (user_id, user_name, user_firstname, user_email) values (2, 'Knecht', 'Werner', 'werner.knecht@gmail.com');
insert into users (user_id, user_name, user_firstname, user_email) values (3, 'Meyer', 'Barbara', 'barbara.meyer@gmail.com');
insert into users (user_id, user_name, user_firstname, user_email) values (4, 'Kummer', 'Adolf', 'adolf.kummer@gmail.com');

insert into rentals (rental_id, movie_id, user_id, rental_rentaldate, rental_rentaldays) values (1, 1, 1, '2017-10-01', 7);
insert into rentals (rental_id, movie_id, user_id, rental_rentaldate, rental_rentaldays) values (2, 2, 1, '2017-10-01', 365);
insert into rentals (rental_id, movie_id, user_id, rental_rentaldate, rental_rentaldays) values (3, 3, 3, '2017-10-01', 365);

