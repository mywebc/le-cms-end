/**
init sql
 */
CREATE TABLE user
(
    id       int         not null primary key auto_increment,
    username varchar(45) not null,
    password varchar(45) not null
);

CREATE TABLE role
(
    id       int         not null primary key auto_increment,
    roleName varchar(45) not null
);
