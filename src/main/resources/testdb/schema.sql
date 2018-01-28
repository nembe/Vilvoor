drop table T_ACCOUNT if exists;
drop table T_TRANSACTION if exists;
drop table T_TRANSFER if exists;
drop table T_STATUS if exists;
drop sequence S_TRANSFERS_NUMBER if exists;


create table T_ACCOUNT (ID bigint identity primary key, OVERDRAFT decimal(8,2), BALANCE decimal(8,2), NAME varchar(50) not null);
create table T_TRANSACTION (ID integer identity primary key, ACCOUNT_ID bigint not null, TRANSFER_ID bigint not null, unique(TRANSFER_ID));
create table T_TRANSFER (ID bigint identity primary key, FROM_ACCOUNT bigint not null, TO_ACCOUNT bigint not null, STATUS_ID integer not null, TRANSFER_DATE date not null, AMOUNT decimal(8,2) not null);
create table T_STATUS (ID integer identity primary key, TRANSFER_STATUS varchar(25) not null);

create sequence S_TRANSFERS_NUMBER start with 1;

alter table T_TRANSFER add constraint FK_ACCOUNT_FROM foreign key (FROM_ACCOUNT) references T_ACCOUNT(ID) on delete cascade;
alter table T_TRANSACTION add constraint FK_ACCOUNT_TRANSACTION foreign key (TRANSFER_ID) references T_TRANSFER(ID) on delete cascade;
alter table T_TRANSACTION add constraint FK_ACCOUNT foreign key (ACCOUNT_ID) references T_ACCOUNT(ID) on delete cascade;