create table relation
(
    id            bigint auto_increment primary key,
    create_time   datetime(6)  null,
    delete_time   datetime(6)  null,
    update_time   datetime(6)  null,
    vsn           int          null,
    owner_id      bigint       not null,
    recipient_id  bigint       not null,
    status        char(32) null,
    group_id      bigint       null,
    in_black_list tinyint          null,
    constraint unq_key
        unique (owner_id, recipient_id)
);