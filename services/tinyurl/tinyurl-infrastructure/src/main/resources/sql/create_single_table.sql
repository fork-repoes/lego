create table tiny_url
(
    id           bigint       not null primary key,
    create_time  datetime(6)  null,
    delete_time  datetime(6)  null,
    update_time  datetime(6)  null,
    vsn          int          null,
    access_count int          null,
    begin_time  datetime(6)  null,
    expire_time  datetime(6)  null,
    max_count    int          null,
    status       varchar(16) null,
    type         varchar(16) null,
    url          varchar(2048) null
);

