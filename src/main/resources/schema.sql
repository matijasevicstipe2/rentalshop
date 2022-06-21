create table if not exists vhs(
    id long generated always as identity,
    name varchar(50) not null,
    info varchar(1000) not null,
    release_year integer not null,
    rating varchar(10) not null,
    runtime integer not null,
    score float not null,
    price integer not null,
    primary key (id)
);

create table if not exists users(
    id identity,
    username varchar(100) not null unique,
    password varchar(1000) not null
);

create table if not exists rental(
    id long generated always as identity,
    user_id long not null,
    vhs_id long not null,
    rental_date date not null,
    return_date date not null,
    paid integer not null,
    status varchar(50) not null,
    primary key (id),
    foreign key (user_id) references users(id),
    foreign key (vhs_id) references vhs(id)

);

create table if not exists authority(
    id identity,
    authority_name varchar(100) not null unique
);

create table if not exists user_authority(
    user_id bigint not null,
    authority_id bigint not null,
    constraint fk_user foreign key (user_id) references users(id), --daje ime constraintu
    constraint fk_authority foreign key (authority_id) references authority(id)
);