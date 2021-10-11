# svc-belong-customer

### About the project
svc-belong-customer is a sample Microservice which provides the below functionalities.

* It lists all the phone numbers with in the company
* It lists all the phone numbers of a given customer
* It can activate the given phone number
* It lists all the customers
* It can retrieve a given customer
 
### How to run the project
#### Prerequisites
* Java 11
* Maven

#### Build and Run
```
Apply below command to Build and Run the Application.

> mvn clean install
    The above command will build the application and create a jar file with name 'svc-belong-customer-<version>-SNAPSHOT.jar'

> mvn spring-boot:run
or
> java -jar target/svc-belong-customer-1.0.0-SNAPSHOT.jar
    The above two command will run the application from the command line
```

#### Functionalities
##### Initial Customer Details
```
Assume that the application is already inprogress and it holds a list of customers in DB.
To achieve this, the application will load a list of customers with their phone numbers from local /src/java/resources/customerData/customers.json

```

##### List all Phone Numbers
```
curl --location --request GET 'http://localhost:8080/v1/belong/phonenumbers'

```
##### List all Phone Numbers of a given customer
```
curl --location --request GET 'http://localhost:8080/v1/belong/phonenumbers?customerId=CUS001'
```

##### Activate a given number
```
For this functionality, please use the Method-2 api call from the postman collection.

curl --location --request PATCH 'http://localhost:8080/v1/belong/phoneNumbers/0469001001' \
--header 'Content-Type: application/json' \
--data-raw '{
    "state" : "Active"
}'
```
##### List all Customers
```
curl --location --request GET 'http://localhost:8080/v1/belong/customers'

```
##### Retrieve a given customer
```
curl --location --request GET 'http://localhost:8080/v1/belong/customers?customerId=CUS001'
```

#### Swagger Details
```
Swagger Details are placed in the root project
json: svc-belong-customer/svc-belong-customer.json
yml: svc-belong-customer/svc-belong-customer.yml

Copy the content from any of the above files and place it under https://editor.swagger.io/ for UI representation
```

#### Postman collection
```
Postman collection is placed in the root project
collection : svc-belong-customer/svc-belong-customer.postman_collection
```

#### Jacoco Coverage Report
```
In this application used Jacoco Plug-In to verify the coverage report, can found after the build under: target/site/jacoco/index.html
To buidl and generate report, please type below command
> mvn clean install
```


