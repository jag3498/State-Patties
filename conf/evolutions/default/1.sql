# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table person (
  id                            serial not null,
  username                      varchar(255),
  password                      varchar(255),
  constraint pk_person primary key (id)
);

create table restaurant (
  id                            serial not null,
  name                          varchar(255),
  location                      varchar(255),
  constraint pk_restaurant primary key (id)
);

create table review (
  id                            serial not null,
  text                          varchar(255),
  rating                        bigint not null,
  person_id                     integer,
  restaurant_id                 integer,
  constraint pk_review primary key (id)
);

create table tag (
  review_id                     integer,
  text                          varchar(255)
);

create table vote (
  person_id                     integer,
  review_id                     integer,
  created_at                    timestamptz
);

create index ix_review_person_id on review (person_id);
alter table review add constraint fk_review_person_id foreign key (person_id) references person (id) on delete restrict on update restrict;

create index ix_review_restaurant_id on review (restaurant_id);
alter table review add constraint fk_review_restaurant_id foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;

create index ix_tag_review_id on tag (review_id);
alter table tag add constraint fk_tag_review_id foreign key (review_id) references review (id) on delete restrict on update restrict;

create index ix_vote_person_id on vote (person_id);
alter table vote add constraint fk_vote_person_id foreign key (person_id) references person (id) on delete restrict on update restrict;

create index ix_vote_review_id on vote (review_id);
alter table vote add constraint fk_vote_review_id foreign key (review_id) references review (id) on delete restrict on update restrict;


# --- !Downs

alter table if exists review drop constraint if exists fk_review_person_id;
drop index if exists ix_review_person_id;

alter table if exists review drop constraint if exists fk_review_restaurant_id;
drop index if exists ix_review_restaurant_id;

alter table if exists tag drop constraint if exists fk_tag_review_id;
drop index if exists ix_tag_review_id;

alter table if exists vote drop constraint if exists fk_vote_person_id;
drop index if exists ix_vote_person_id;

alter table if exists vote drop constraint if exists fk_vote_review_id;
drop index if exists ix_vote_review_id;

drop table if exists person cascade;

drop table if exists restaurant cascade;

drop table if exists review cascade;

drop table if exists tag cascade;

drop table if exists vote cascade;

