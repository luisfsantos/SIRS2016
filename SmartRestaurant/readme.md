#Serverside RESTful API

##Instalation

###Dependancies
* Python >= 2.7
* MySQL database instance
* virtualenv (optional)
* pip

(If during instalation you have any errors please install missing dependancies listed in dependancies.txt

###Instructions
After instaling the dependancies run:

```
pip install -r requirements.txt
mysql -u "root" -p < init_db.sql
python manage.py migrate

```

###Running
To run the server simply run:

```
python manage.py runserver

```
