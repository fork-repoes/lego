use relation_0;
create table relation_0
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


create table relation_1
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


create table relation_2
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


create table relation_3
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


create table relation_4
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


create table relation_5
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


create table relation_6
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


create table relation_7
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




use relation_1;
create table relation_8
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


create table relation_9
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


create table relation_10
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


create table relation_11
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


create table relation_12
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


create table relation_13
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


create table relation_14
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


create table relation_15
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