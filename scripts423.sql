select s.name, s.age, f.name
from students s
         join faculties f on s.faculty_id = f.id;

select s.name, a.file_path
from students s
         join faculties a on s.student_id = s.id;
