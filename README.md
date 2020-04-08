#Penn State Burger Review Website for IST 411

Deployed URL: https://secure-headland-18052.herokuapp.com/

Sprint 3 Details Link: https://docs.google.com/document/d/1AfADvwhYcnvgJ8waEwfh6O5V_NuMBryd29wAvBGMqPY/edit?usp=sharing

Everything is now working. UI has been overhauled, searching works, display works.

To use the application, please create an account and then log in with the account.
To have admin rights, sign in using user="admin" password="admin"
  ->  Admin rights let you create a restaurant at /restaurantAdmin
  

Dev notes:

To get db working locally for testing:

Install postgresql:
https://www.openscg.com/bigsql/postgresql/installers.jsp/ Set password to "password" during install

Install pgAdmin
https://www.pgadmin.org/ Connect to db using this tool

Drop all tables (make sure you use drop cascade), then run this:


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



