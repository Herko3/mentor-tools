create table registration(
    id bigint auto_increment,
    student_id bigint,
    class_id bigint,
    status varchar(20),
    primary key (id),
    foreign key (student_id) references students(id) on delete cascade,
    foreign key (class_id) references training_classes(id) on delete cascade
)
