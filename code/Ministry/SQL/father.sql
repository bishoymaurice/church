--name 50

--add no 10
--add street 50
--add region 50
--add district 50 
--add free 100

--phone 50
--mobile1 50
--mobile2 50

--birthday 

--email 50

--education 200
--ecc education 200
--courses 200
--skills 200

--notes 250

--id, type, active_date

select * from MEMBER;

insert into member (id, type, name, active_date) values (1, (select id from member_type where des = 'father'), 'bishoy', sysdate)

select * from FATHER;

insert into father (id) values (1)

select * from PROFILE;

insert into profile (id, birthday, education, ecclesiastic_education, courses, skills) values (1, to_date('21-02-2014', 'dd-mm-yyyy'), 'edu', 'ecc edu', 'courses', 'skills')

select * from CONTACT;

insert into contact (id, phone, mobile1, mobile2, address_num, address_street, address_region, address_district, address_free, email_address) values (1, '$PHONE', '$MOBILE1', '$MOBILE2', '$ADDRESS_NUM', (select id from streets where name = '$ADDRESS_STREET'), (select id from regions where name = '$ADDRESS_REGION'), (select id from districts where name = '$ADDRESS_DISTRICT'), '$ADDRESS_FREE', '$EMAIL_ADDRESS')

select member.id, member.name, member.active_date, father.notes, to_char(profile.birthday, 'dd-mm-yyyy'), profile.education, profile.ecclesiastic_education, profile.courses, profile.skills, contact.phone, contact.mobile1, contact.mobile2, contact.address_num, (select name from streets where streets.id = contact.address_street), (select name from regions where regions.id = contact.address_region),(select name from districts where districts.id = contact.address_district), contact.address_free, contact.email from member, father, profile, contact where member.id = father.id and member.id = profile.id and member.id = contact.id

and member.id = '$ID'
and lower(member.name) like lower('%$NAME%')
and lower(father.notes) like lower('%$NOTES%')
and profile.birthday = to_date ('$BIRTHDAY', 'dd-mm-yyyy')
and lower(profile.education) like lower('%$EDUCATION%')
and lower(profile.ecclesiastic_education) like lower('%$ECC_EDUCATION%')
and lower(profile.courses) like lower('%$COURSES%')
and lower(profile.skills) like lower('%$SKILLS%')
and lower(contact.phone) like lower('%$PHONE%')
and lower(contact.mobile1) like lower('%$MOBILE1%')
and lower(contact.mobile2) like lower('%$MOBILE2')
and lower(contact.address_num) like lower('%$ADDRESS_NUM%')
and contact.address_street = (select id from streets where name = '$ADDRESS_STREET')
and contact.address_region = (select id from regions where name = '$ADDRESS_REGION')
and contact.address_district = (select id from districts where name = '$ADDRESS_DISTRICT')
and lower(contact.address_free) like lower('%$ADDRESS_FREE')
and lower(contact.email) like lower('%$EMAIL')