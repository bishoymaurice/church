select* from
(select round(((count(1) / ((to_date('30-10-2015', 'dd-mm-yyyy') - to_date('23-10-2015', 'dd-mm-yyyy')) / 7)) * 100), 2) || ' %' as ministers_meeting
from MINISTER_EVALUATION where id = 2415 and action_date >= to_date('23-10-2015', 'dd-mm-yyyy') and action_date <= to_date('30-10-2015', 'dd-mm-yyyy') and ministers_meeting = 1) a,
(select round(((count(1) / ((to_date('30-10-2015', 'dd-mm-yyyy') - to_date('23-10-2015', 'dd-mm-yyyy')) / 7)) * 100), 2) || ' %' as mass
from MINISTER_EVALUATION where id = 2415 and action_date >= to_date('23-10-2015', 'dd-mm-yyyy') and action_date <= to_date('30-10-2015', 'dd-mm-yyyy') and mass = 1) b,
(select round(((count(1) / ((to_date('30-10-2015', 'dd-mm-yyyy') - to_date('23-10-2015', 'dd-mm-yyyy')) / 7)) * 100), 2) || ' %' as ministry_attendance
from MINISTER_EVALUATION where id = 2415 and action_date >= to_date('23-10-2015', 'dd-mm-yyyy') and action_date <= to_date('30-10-2015', 'dd-mm-yyyy') and ministry = 1) c,
(select round(((count(1) / ((to_date('30-10-2015', 'dd-mm-yyyy') - to_date('23-10-2015', 'dd-mm-yyyy')) / 7)) * 100), 2) || ' %' as lesson_preparation
from MINISTER_EVALUATION where id = 2415 and action_date >= to_date('23-10-2015', 'dd-mm-yyyy') and action_date <= to_date('30-10-2015', 'dd-mm-yyyy') and lesson_preparation = 1) d,
(select round(((count(1) / ((to_date('30-10-2015', 'dd-mm-yyyy') - to_date('23-10-2015', 'dd-mm-yyyy')) / 7)) * 100), 2) || ' %' as illustration_tool
from MINISTER_EVALUATION where id = 2415 and action_date >= to_date('23-10-2015', 'dd-mm-yyyy') and action_date <= to_date('30-10-2015', 'dd-mm-yyyy') and illustration_tool = 1) e,
(select round(((count(1) / ((to_date('30-10-2015', 'dd-mm-yyyy') - to_date('23-10-2015', 'dd-mm-yyyy')) / 7)) * 100), 2) || ' %' as family_meeting
from MINISTER_EVALUATION where id = 2415 and action_date >= to_date('23-10-2015', 'dd-mm-yyyy') and action_date <= to_date('30-10-2015', 'dd-mm-yyyy') and family_meeting = 1) f

select nvl(sum(attendants_count), 0) from MINISTER_EVALUATION where id = 2415 and action_date = to_date('30-10-2015', 'dd-mm-yyyy')

select nvl(sum(attendants_followup_count), 0) from MINISTER_EVALUATION where id = 2415 and action_date = to_date('30-10-2015', 'dd-mm-yyyy')

select nvl(sum(followup_count), 0) from MINISTER_EVALUATION where id = 2415 and action_date = to_date('30-10-2015', 'dd-mm-yyyy')

select count(distinct child_id) from
(select a.child_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a,
(select child_id, max (active_from) as active_from from minister_child_assigned where active_from <= to_date ('30-10-2015', 'dd-mm-yyyy') and minister_child_assigned.inactive_date is null group by child_id) b
where a.child_id = b.child_id and a.active_from = b.active_from
and a.minister_id = 2415)