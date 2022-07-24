CREATE TABLE cinemas_users(
    user_id int PRIMARY KEY AUTO_INCREMENT,
    city_id int,
    email varchar(30),
    password varchar(225),
    image text
);

CREATE TABLE cinemas_studio(
    studio_id int PRIMARY KEY AUTO_INCREMENT,
    studio_obj text
);

CREATE TABLE cinemas_orders(
    order_id int PRIMARY KEY AUTO_INCREMENT,
    order_status varchar(20),
    user_id int,
    movie_id varchar(30),
    movie_status varchar(20),
    regular_ticket int,
    sweetbox_ticket int,
    chair varchar(225),
    tax int,
    total int
);