create table if not exists member (
                        id binary(16),
                        email varchar(255) not null unique,
                        password varchar(255) not null,
                        role varchar(255) not null check(role in('REGULAR', 'ADMIN')),
                        primary key(id)
);

create table if not exists product (
                        id binary(16),
                        name varchar(255) not null,
                        price int not null,
                        image_url text not null,
                        member_id binary(16) not null,
                        primary key (id),
                        foreign key (member_id) references member(id) on delete cascade
);

create table if not exists wish_product(
                        id binary(16),
                        quantity int,
                        owner_id binary(16),
                        product_id binary(16),
                        primary key(id),
                        foreign key (owner_id) references member(id) on delete cascade,
                        foreign key (product_id) references product(id) on delete cascade
)