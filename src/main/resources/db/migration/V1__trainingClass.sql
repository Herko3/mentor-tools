create table training_classes
(
    id         bigint auto_increment,
    name       varchar(255) not null,
    start_time datetime,
    end_time datetime,
    primary key (id)
)