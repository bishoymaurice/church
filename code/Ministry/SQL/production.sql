select * from member where type = (select id from member_type where des = 'father')

select count(1) from father

--------------------------------------------------------------------------------------------------------------------------

select * from member where type = (select id from member_type where des = 'minister')

select count(1) from minister

--------------------------------------------------------------------------------------------------------------------------

select * from member where type = (select id from member_type where des = 'child')

select count(1) from child