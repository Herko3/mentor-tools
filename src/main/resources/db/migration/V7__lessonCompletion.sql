create table lesson_completions
(
    id              bigint auto_increment,
    student_id      bigint      not null,
    lesson_id       bigint      not null,
    video_status    varchar(20) not null,
    video_date      datetime,
    exercise_status varchar(20) not null,
    exercise_date   datetime,
    commit_url      varchar(255),
    primary key (id)
)