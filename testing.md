# Testing

## How-to guide
#### Logging in to POS as a regular employee

1. Go to https://smartrestaurant.xyz/
2. Enter the login credentials username:'employee1' password:'sirs2016'. You are now logged in as a regular employee and you can:
	- see the orders and manage them

#### Logging in to POS as the manager

1. Go to https://smartrestaurant.xyz/
2. Enter the login credentials username:'Admin' password:'sirs2016'. You are now logged in as the manager and you can:
	- see the orders and manage them
	- see user information when an order has been placed
	- handle fraud attempts (see (B) Make a malicious order)

#### Logging in to Smart Restaurant App as a regular customer
1. Open the Smart Restaurant App on your phone
2. Press the Login button and enter the login credentials username:'luissantos' password:'sirs2016'. You are now logged in as a regular customer

#### Logging in to Smart Restaurant App as a malicious custumer
1. Open the Smart Restaurant App on your phone
2. Press the Login button and enter the login credentials username:'pedrofilipe' password:'sirs2016'. You are now logged in as the default malicious customer. 
	- A malicious customer is someone who managed to run a modified version of the mobile app on their phone, and altered the application such that, when paying with PayPal, the payment will amount to €0.01 for each ordered item. This behaviour is demonstrated in the method OrderPaymentActivity.maliciousUserModifiesPriceWithinApp()

#### Making an order from the Smart Restaurant App 
1. In order to be able to locate you inside the hypothetical restaurant, you will be prompted to Scan the QR code on your table after logging in. The QR codes for each table can be found at:
	- https://smartrestaurant.xyz/media/qrcode/table1.jpeg
	- https://smartrestaurant.xyz/media/qrcode/table2.jpeg
	- https://smartrestaurant.xyz/media/qrcode/table3.jpeg
	- https://smartrestaurant.xyz/media/qrcode/table4.jpeg
	
2. Please scan one of them by clicking the camera icon and you will be able to proceed to the menu:
	- You can also try to scan any other QR code you generate, and you'll see that you will not be able to proceed to the menu unless you scan one of the valid QR codes mentioned above

You are now seeing the Smart Restaurant menu and can follow the instructions below to make an order:

1. To add an item to your order, click one of the items on the list, and then click the Add to shopping cart button next to the item name
2. Go back to the menu list. To see your order, click the shopping cart button at the end of the screen
3. Click the payment button at the end of the screen to proceed to the payment
4. You can now choose whether to pay using cash or PayPal:

##### 4.1 Pay using cash: 
- Choose cash and click Proceed
- Click the Pay With Cash button at the bottom of the screen
- Your order will now be sent to the server, and you will see the 'Hooray!' screen

##### 4.2 Pay using PayPal
- Choose PayPal and click Proceed
- Click the Pay With PayPal button at the bottom of the screen
- Your order will now be sent to the server, and you will be prompted to enter your PayPal credentials:
	- Enter the following credentials — email:'smartbuyer@restaurant.com' password:'sirs2016' (PayPal is configured to run on sandbox environment so please do not enter any other credentials besides the ones mentioned before)
- Finish the PayPal payment by clicking on the Pay button
- A confirmation for your order will now be sent to the server, and you will see the 'Hooray!' screen


## (A) Make a successful (and legit) order
To make a successfull order follow the steps below, as detailed in the *How-to guide*:

1. Login in to Smart Restaurant App as a regular customer
2. Login to POS as a regular employee 
3. Make an order from the Smart Restaurant App
	- **if you choose to pay using cash**, the employee will now be able to see an order on the POS webpage, which has a reminder that they need to go fetch the order payment. As soon as this is done, the employee can mark that order as paid by clicking the button Confirm Cash and the order will be marked as Processing
	- **if you choose to pay using PayPal**, the employee will be able to see an order on the POS webpage as soon as you click the Pay With PayPal button on the app. Follow the remaining steps detailed in *4.2. Pay using Paypal* and the employee will see the order marked as Processing

Note that, to protect client privacy, a regular employee cannot see the customer's personal information — only a View Order Info button is displayed. On the other hand, if instead of logging in to POS as a regular employee, you login as the manager, the button will now say View Client Info and you'll be able to see the customer's information.

## (B) Make a malicious order
	
