select * from STREETS;

select * from REGIONS;

select * from DISTRICTS;

------------------------------------

select * from SECTION;

select * from FAMILY;

select * from YEAR;

select * from CLASS;

select * from MINISTER_MINISTERED_ASSIGNED;

select * from MINISTERED_MINISTRY_ATTENDANCE;

select * from MINISTERED_MASS_ATTENDANCE;


select * from MINISTERED_FOLLOWUP;

select * from FOLLOWUP_METHODS;

--------------------

select * from MEMBER order by id;

select * from FATHER;

select * from MINISTER;

select * from MINISTERED;

select * from CONTACT order by id desc;

select * from PROFILE;

select * from member_type;

--------------------------

select * from USERS;

select * from USERS_PROFILE;

select * from MINISTER_EVALUATION;


--select * from MARITAL_STATUS;
--select * from SPECIALIZATION;
--select * from IMAGES;


--by class
select name from member where id in (select id from minister where class_id = (select id from class where name = 'class2')) and type = (select id from member_type where des = 'minister') and type = (select id from member_type where des = 'minister') order by name

--by year
select name from member where id in (select id from minister where class_id in (select id from class where year_id = (select id from year where name = 'year'))) and type = (select id from member_type where des = 'minister') and type = (select id from member_type where des = 'minister') order by name

--by family
select name from member where id in (select id from minister where class_id in (select id from class where year_id in (select id from year where family_id = (select id from family where name = 'family')))) and type = (select id from member_type where des = 'minister') and type = (select id from member_type where des = 'minister') order by name

--by section
select name from member where id in (select id from minister where class_id in (select id from class where year_id in (select id from year where family_id in (select id from family where section_id = (select id from section where name = 'section'))))) and type = (select id from member_type where des = 'minister') and type = (select id from member_type where des = 'minister') order by name


select * from minister_ministered_assigned


