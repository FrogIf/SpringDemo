drop table t_coffee if exists;
drop table t_order if exists;
drop table t_order_coffee if exists;
drop table T_FOO if exists;
drop table t_mcoffee if exists;
drop table t_gcoffee if exists;


CREATE TABLE T_FOO (ID INT IDENTITY, BAR VARCHAR(64));

create table t_coffee (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    name varchar(255),
    price bigint,
    primary key (id)
);

create table t_order (
    id bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    customer varchar(255),
    state integer not null,
    primary key (id)
);

create table t_order_coffee (
    coffee_order_id bigint not null,
    items_id bigint not null
);

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