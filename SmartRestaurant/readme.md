#Serverside RESTful API

##Instalation

###Dependencies
* Python >= 2.7
* MySQL database instance
* virtualenv (optional)
* pip

(If during instalation you have any errors please install missing dependencies listed in dependancies.txt

###Instructions


```
sudo apt install python-pip
sudo apt install virtualenv
sudo apt install libmysqlclient-dev
sudo apt install python-mysqldb
sudo apt install libssl-dev libffi-dev

virtualenv env
source env/bin/activate

pip install -r requierments.txt


python manage.py makemigrations
python manage.py migrate

python manage.py loaddata export/fixtures/groups.json
python manage.py loaddata export/fixtures/users.json
python manage.py loaddata export/fixtures/tables.json
python manage.py loaddata export/fixtures/initial_ingredient_data.json
python manage.py loaddata export/fixtures/initial_meal_data.json

python manage.py runserver



//MYSQL
sudo apt-get update
sudo apt-get install mysql-server
sudo mysql_secure_installation
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
