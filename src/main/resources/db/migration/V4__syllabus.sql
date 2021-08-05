create table syllabuses(
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);

alter table training_classes
    add syllabus_id bigint,
    add constraint foreign key (syllabus_id) references syllabuses(id) on delete cascade;