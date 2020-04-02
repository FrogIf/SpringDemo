CREATE TABLE T_FOO (ID INT IDENTITY, BAR VARCHAR(64));

create table t_mcoffee(
    ID BIGINT not null auto_increment,
    name varchar(100),
    price bigint not null,
    create_time timestamp,
    update_time timestamp,
    primary key(ID)
);

create table t_gcoffee(
    ID BIGINT not null auto_increment,
    name varchar(100),
    price bigint not null,
    create_time timestamp,
    update_time timestamp,
    primary key(ID)
);