drop table if exists t_coffee;
drop table if exists t_order;
drop table if exists t_order_coffee;
drop table if exists T_FOO;
drop table if exists t_mcoffee;
drop table if exists t_gcoffee;


CREATE TABLE T_FOO (ID int primary key auto_increment, BAR VARCHAR(64));

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