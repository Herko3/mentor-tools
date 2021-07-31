alter table training_classes
add syllabus_id bigint;

create table syllabuses(
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
)