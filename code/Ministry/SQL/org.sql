select * from SECTION;

select * from FAMILY;

select * from YEAR;

select * from CLASS;

select section.name, family.name, year.name, class.name from section, family, year, class
where section.id = family.section_id
and family.id = year.family_id
and year.id = class.year_id
order by section.name, family.name, year.name, class.name


select name from member where id in (select id from minister where class_id = (select id from class where name = '$CLASS_NAME')) order by name
