drop table T_ACCOUNT if exists;
drop table T_TRANSACTION if exists;
drop table T_TRANSFER if exists;
drop table T_STATUS if exists;
drop sequence S_ACCOUNT_ID if exists;

create type UUID AS BINARY(16);
create sequence S_ACCOUNT_ID start with 0;
create table T_ACCOUNT (ID integer identity primary key, ACCOUNT_UUID UUID UNIQUE, OVERDRAFT decimal(8,2), BALANCE decimal(8,2), NAME varchar(50) not null);
create table T_TRANSACTION (ID integer identity primary key, ACCOUNT_ID UUID not null, TRANSFER_ID integer not null, unique(TRANSFER_ID));
create table T_TRANSFER (ID integer identity primary key, FROM_ACCOUNT UUID not null, TO_ACCOUNT UUID not null, STATUS_ID smallint not null, TRANSFER_DATE date not null, AMOUNT decimal(8,2) not null);
create table T_STATUS (ID smallint identity primary key, TRANSFER_STATUS varchar(20) not null);
CREATE INDEX IF NOT EXISTS myIndex on T_ACCOUNT (ACCOUNT_UUID)

alter table T_TRANSFER add constraint FK_ACCOUNT_FROM foreign key (FROM_ACCOUNT) references T_ACCOUNT(ACCOUNT_UUID) on delete cascade;
alter table T_TRANSFER add constraint FK_STATUS foreign key (STATUS_ID) references T_STATUS(ID) on delete cascade;
alter table T_TRANSACTION add constraint FK_TRANSFER foreign key (TRANSFER_ID) references T_TRANSFER(ID) on delete cascade;
alter table T_TRANSACTION add constraint FK_ACCOUNT foreign key (ACCOUNT_ID) references T_ACCOUNT(ACCOUNT_UUID) on delete cascade;