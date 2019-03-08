select id, name from member where id in (select id from child where minister_id = (select id from member where name = 'M1' and type = (select id from member_type where des = 'minister'))) order by name


select id, name from member where id in (select child_id from minister_child_assigned where minister_id = (select id from member where name = 'M1' and type = (select id from member_type where des = 'minister')) and active_from >= to_date('$ACTION_DATE', 'dd-mm-yyyy') and (active_to <= to_date('$ACTION_DATE', 'dd-mm-yyyy') or active_to is null)) order by name

select * from minister_child_assigned