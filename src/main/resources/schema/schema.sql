drop table if exists attached_photo;
drop table if exists member;
drop table if exists point;
drop table if exists review;

create table attached_photo (
    id bigint not null auto_increment,
    created_at datetime(6),
    modified_at datetime(6),
    photo_id Binary(16),
    review_id Binary(16),
    primary key (id)
) engine=InnoDB;

create table member (
    member_id Binary(16) not null,
    created_at datetime(6),
    modified_at datetime(6),
    count bigint,
    primary key (member_id)
) engine=InnoDB;

create table point (
    point_id Binary(16) not null,
    created_at datetime(6),
    modified_at datetime(6),
    action_case integer,
    amount bigint,
    review_id Binary(16),
    user_id Binary(16),
    primary key (point_id)
) engine=InnoDB;

create table review (
    review_id Binary(16) not null,
    created_at datetime(6),
    modified_at datetime(6),
    content varchar(255),
    is_bonus bit,
    place_id Binary(16),
    user_id Binary(16),
    primary key (review_id)
) engine=InnoDB;

alter table review
    add unique review_userToplace (user_id, place_id);

alter table review
    add index review_place (place_id);

alter table point
    add index point_userId (user_id);