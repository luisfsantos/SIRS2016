CREATE USER IF NOT EXISTS 'SmartRestaurant'@'localhost' IDENTIFIED BY 'skqDKG4dkD456Dsja2DC6X';
CREATE DATABASE IF NOT EXISTS smartrestaurant_data;
GRANT ALL ON smartrestaurant_data.* TO 'SmartRestaurant'@'localhost';

CREATE USER IF NOT EXISTS 'UserAdmin'@'localhost' IDENTIFIED BY 'priV5Te34Dj71CVB5Vas3DF';
CREATE DATABASE IF NOT EXISTS user_data;
GRANT ALL ON user_data.* TO 'UserAdmin'@'localhost';