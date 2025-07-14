create table admin (
    id bigint not null auto_increment,
    name varchar(25) not null,
    email varchar(50) not null,
    password varchar(50) not null,

    primary key(id)
);