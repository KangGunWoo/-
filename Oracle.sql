create table member(
id varchar2(10) not null,
pw varchar2(10) not null,
name varchar2(10) not null,
email varchar2(20) not null,
PRIMARY KEY(id)
);


CREATE SEQUENCE Write_ID INCREMENT BY 1 START WITH 1;


create table write(
idx Number not null primary key,
title varchar2(50) not null,
content varchar2(500),
data timestamp default current_timestamp,
hit int not null,
writer varchar2(20) not null
);
create table cart(
DelNum Number,
Num Number,
img varchar2(30),
title varchar2(30),
price varchar2(30)
);

create table jspdbmember (
	id number ,
    name varchar2(10) not null,
    age number
); 

drop table jspdbmember
drop table member


INSERT INTO jspdbmember values(1,'¼ÛÀ¯Áø', 23);


