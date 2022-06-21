insert into vhs(name,info,release_year,rating,runtime,score,price) values ('Top Gun','As students at the United States Navy''s elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.',1986,'PG',110,6.9,2);
insert into vhs(name,info,release_year,rating,runtime,score,price) values ('Titanic','A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.',1997,'PG13',194,7.9,3);
insert into vhs(name,info,release_year,rating,runtime,score,price) values ('Blade Runner','A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.',1982,'R',117,8.1,4);
insert into vhs(name,info,release_year,rating,runtime,score,price) values ('Casino','A tale of greed, deception, money, power, and murder occur between two best friends: a mafia enforcer and a casino executive compete against each other over a gambling empire, and over a fast-living and fast-loving socialite.',1995,'R',178,8.2,3);
insert into vhs(name,info,release_year,rating,runtime,score,price) values (' The Lion King','Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.',1994,'G',88,8.5,4);

--password = username

insert into users(id,username,password) values (1,'harrington_steve','$2a$12$0Lifqib8ey0WvzvqcilIeuMBRWIhdlLpr9tcXhJoNT4Y9r9LqCsPy');
insert into users(id,username,password) values (2,'buckley_robin','$2a$12$3A0LYjL7jFM/pniM4oYlQuRJlL0nzz7oho1jQ9nRyjNxJ5raz29gm');
insert into users(id,username,password) values (3,'wheeler_mike','$2a$12$xVlJb00mb4zsCV9AeSUv2OD1SmSn707yKo2veV3zsItTvEE0FkzL2');
insert into users(id,username,password) values (4,'sinclair_lucas','$2a$12$QMuSKSw6/ZiNGT2nol3Cru6v9jjIjLYDcCeo2pzuTsElU0mjNxzBi');
insert into users(id,username,password) values (5,'admin','$2a$12$yoWG9Wis1QhsUIHhLStvW.xONw2s4yMzPn8SpoQEl6IC2MrLP9UbO');

insert into rental(user_id,vhs_id,rental_date,return_date,paid,status) values (3,1,'2022-03-17','2022-03-20',6,'RETURNED');
insert into rental(user_id,vhs_id,rental_date,return_date,paid,status) values (3,3,'2022-04-20','2022-04-22',8,'RETURNED');
insert into rental(user_id,vhs_id,rental_date,return_date,paid,status) values (4,2,'2022-02-13','2022-04-15',6,'RETURNED');
insert into rental(user_id,vhs_id,rental_date,return_date,paid,status) values (4,4,'2022-05-11','2022-05-16',15,'RETURNED');

insert into authority(id,authority_name) values (1,'ROLE_ADMIN');
insert into authority(id,authority_name) values (2,'ROLE_USER');

insert into user_authority(user_id, authority_id) values (1,1);
insert into user_authority(user_id, authority_id) values (2,1);
insert into user_authority(user_id, authority_id) values (3,2);
insert into user_authority(user_id, authority_id) values (4,2);
insert into user_authority(user_id, authority_id) values (5,1);