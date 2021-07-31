create table lessons
(
    id   bigint auto_increment,
    name varchar(255) not null,
    url  varchar(255) not null,
    module_id bigint not null,
    primary key (id)
);