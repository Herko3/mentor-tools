create table modules
(
    id   bigint auto_increment,
    name varchar(255) not null,
    url  varchar(255) not null,
    primary key (id)
);

create table syl_mod
(
    id     bigint auto_increment,
    syl_id bigint not null,
    mod_id bigint not null,
    primary key (id),
    foreign key (syl_id) references syllabuses(id) on delete cascade,
    foreign key (mod_id) references modules(id) on delete cascade
)