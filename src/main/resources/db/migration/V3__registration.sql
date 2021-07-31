create table registration(
    id bigint auto_increment,
    student_id bigint,
    class_id bigint,
    status varchar(20),
    primary key (id)
)