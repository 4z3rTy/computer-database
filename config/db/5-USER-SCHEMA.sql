use `test-db`;

  drop table if exists authority;
  drop table if exists user;
  drop table if exists user_authority;

create table authority (
  	id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_authority primary key (id))
  ;
  
  create table user (
    id                        bigint not null auto_increment,
    username                  varchar(255),
    password               	  varchar(255),
    dateCreated            	  DATE NULL,
    constraint pk_user primary key (id))
  ;
  
  create table user_authority(
  	user_id                        bigint,
  	authority_id                   bigint,
  	constraint pk_user_authority primary key (user_id,authority_id))
  ;
  
  alter table user_authority add constraint fk_user_authority_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
  alter table user_authority add constraint fk_user_authority_2 foreign key (authority_id) references authority (id) on delete restrict on update restrict;