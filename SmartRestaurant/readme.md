#Serverside RESTful API and POS management.

##Instalation

###Dependencies
* Python >= 2.7
* MySQL database instance
* virtualenv (optional)
* pip

###Instructions
A fresh install of Ubunto 16.04 - Gnome with the following commands resulted in an installed version of the site running on localhost:8000/ :

```
//MYSQL
  sudo apt-get update
  sudo apt-get install mysql-server
  sudo mysql_secure_installation
//

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

```

###Running
To run the server simply run:

```
python manage.py runserver
```

###Production Server
A production server was setup by us for using the android app with a secure connection to the site because we used letencrypt https://letsencrypt.org/ to provide a certificate for the site https://smartrestaurant.xyz which we own and we used Apache to serve the requests with the following apache config file: smartrestaurant.xyz.conf located in this folder.
