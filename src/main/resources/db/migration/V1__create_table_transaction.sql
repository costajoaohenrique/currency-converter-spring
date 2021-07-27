create table TB_TRANSACTION (
    id bigint not null auto_increment,
    ID_USER bigint not null,
    ORIGIN_CURRENCY varchar(3) not null,
    ORIGINAL_VALUE decimal(10, 6) not null,
    DESTINATION_CURRENCY varchar(3) not null,
    CONVERSION_RATE decimal(10, 6) not null,
    TRANSACTION_DATE timestamp not null,
    primary key (id)
);

