create table schedule (
    id bigint not null auto_increment,
    data not null,
    hora not null,
    nome varchar(100) not null,
    telefone varchar(20) not null,
    servico varchar(100) not null,
    serviceCode varchar(10) not null,

    primary key(id)
);