#Serverside RESTful API

##Instalation

###Dependencies
* Python >= 2.7
* MySQL database instance
* virtualenv (optional)
* pip

(If during instalation you have any errors please install missing dependencies listed in dependancies.txt

###Instructions
After installing the dependencies run:

```
pip install -r requirements_mysql.txt
mysql -u "root" -p < init_db.sql
python manage.py migrate
```

###Running
To run the server simply run:

```
python manage.py runserver
```

###Dependencies

```
apt-get install libssl-dev libffi-dev
```
