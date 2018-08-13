select EMAIL, PASSWORD, ENABLED
From EMPLOYEE
where EMAIL = "joachim-lebrun@hotmail.fr"
union
select EMAIL, PASSWORD, ENABLED
from CITIZEN
where EMAIL = "joachim-lebrun@hotmail.fr";
