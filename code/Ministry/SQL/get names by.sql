--by minister
select name from member where id in (select id from child where minister_id in (select id from member where name = '12347' and type = (select id from member_type where des = 'minister')))

--by class
select name from member where id in (select id from child where minister_id in (select id from minister where class_id in (select id from class where name = 'asdasdasdi')))

--by year
select name from member where id in (select id from child where minister_id in (select id from minister where class_id in (select id from class where year_id in (select id from year where name = 'year12'))))

--by family
select name from member where id in (select id from child where minister_id in (select id from minister where class_id in (select id from class where year_id in (select id from year where family_id in (select id from family where name = '1231232222')))))

--by section
select name from member where id in (select id from child where minister_id in (select id from minister where class_id in (select id from class where year_id in (select id from year where family_id in (select id from family where section_id in (select id from section where name = '1212212'))))))
