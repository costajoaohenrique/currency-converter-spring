create table tb_transaction (
    id bigint not null auto_increment,
    idUser bigint not null,
    originCurrency varchar(3) not null,
    originalValue decimal(5, 16) not null,
    destinationCurrency varchar(3) not null,
    conversionRate decimal(5, 16) not null,
    transactionDate datetime not null,
    primary key(id)
);

