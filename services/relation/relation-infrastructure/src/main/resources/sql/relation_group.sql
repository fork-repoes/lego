create table relation_group
(
    id          bigint auto_increment primary key,
    create_time datetime(6)  null,
    delete_time datetime(6)  null,
    update_time datetime(6)  null,
    vsn         int          null,
    owner       bigint       null,
    status      varchar(32) null,
    name        varchar(64) null,
    descr       varchar(1024) null,

    index idx_owner (owner)
);



