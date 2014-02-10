# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog (
  id                        bigint not null,
  title                     varchar(255),
  post_date                 timestamp,
  user_name                 varchar(255),
  text                      varchar(255),
  constraint pk_blog primary key (id))
;

create table user (
  name                      varchar(255) not null,
  password                  varchar(255),
  constraint pk_user primary key (name))
;

create sequence blog_seq;

create sequence user_seq;

alter table blog add constraint fk_blog_user_1 foreign key (user_name) references user (name) on delete restrict on update restrict;
create index ix_blog_user_1 on blog (user_name);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blog;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blog_seq;

drop sequence if exists user_seq;

