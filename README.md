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
Assume that the application is already inprogress and it holds a list of customers in DB as below
{
  "customer": [
    {
      "id" : "CUS001",
      "name" : "Mahesh Babu",
      "phone" : [
        {
          "number" : "0469001001",
          "state" : "InActive",
          "type" : "Office"
        },
        {
          "number" : "0469001002",
          "state" : "Active",
          "type" : "Home"
        },
        {
          "number" : "0469001003",
          "state" : "Active",
          "type" : "Mobile"
        }
      ]
    },
    {
      "id" : "CUS002",
      "name" : "Gopi Krishna",
      "phone" : [
        {
          "number" : "0469002001",
          "state" : "Active",
          "type" : "Office"
        },
        {
          "number" : "0469002002",
          "state" : "Active",
          "type" : "Home"
        },
        {
          "number" : "0469002003",
          "state" : "InActive",
          "type" : "Mobile"
        }
      ]
    },
    {
      "id" : "CUS003",
      "name" : "Balu",
      "phone" : [
        {
          "number" : "0469003001",
          "state" : "InActive",
          "type" : "Office"
        },
        {
          "number" : "0469003002",
          "state" : "InActive",
          "type" : "Home"
        },
        {
          "number" : "0469003003",
          "state" : "InActive",
          "type" : "Mobile"
        }
      ]
    },
    {
      "id" : "CUS004",
      "name" : "Sarat Chandra",
      "phone" : [
        {
          "number" : "0469004001",
          "state" : "Active",
          "type" : "Office"
        },
        {
          "number" : "0469004002",
          "state" : "Active",
          "type" : "Home"
        },
        {
          "number" : "0469004003",
          "state" : "Active",
          "type" : "Mobile"
        }
      ]
    },
    {
      "id" : "CUS005",
      "name" : "Mahendar",
      "phone" : [
        {
          "number" : "0469005001",
          "state" : "Active",
          "type" : "Office"
        },
        {
          "number" : "0469005002",
          "state" : "Active",
          "type" : "Home"
        },
        {
          "number" : "0469005003",
          "state" : "Active",
          "type" : "Mobile"
        }
      ]
    }
  ]
}

```

##### List all Phone Numbers
```
curl --location --request GET 'http://localhost:8080/v1/belong/phonenumbers'
Response: 200 OK
{
    "records": 15,
    "phone": [
        {
            "number": "0469001001",
            "type": "Office",
            "state": "Active"
        },
        {
            "number": "0469001002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469001003",
            "type": "Mobile",
            "state": "Active"
        },
        {
            "number": "0469002001",
            "type": "Office",
            "state": "Active"
        },
        {
            "number": "0469002002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469002003",
            "type": "Mobile",
            "state": "InActive"
        },
        {
            "number": "0469003001",
            "type": "Office",
            "state": "InActive"
        },
        {
            "number": "0469003002",
            "type": "Home",
            "state": "InActive"
        },
        {
            "number": "0469003003",
            "type": "Mobile",
            "state": "InActive"
        },
        {
            "number": "0469004001",
            "type": "Office",
            "state": "Active"
        },
        {
            "number": "0469004002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469004003",
            "type": "Mobile",
            "state": "Active"
        },
        {
            "number": "0469005001",
            "type": "Office",
            "state": "Active"
        },
        {
            "number": "0469005002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469005003",
            "type": "Mobile",
            "state": "Active"
        }
    ]
}

```
##### List all Phone Numbers of a given customer
```
curl --location --request GET 'http://localhost:8080/v1/belong/phonenumbers?customerId=CUS001'
Response: 200 OK
{
    "records": 3,
    "phone": [
        {
            "number": "0469001001",
            "type": "Office",
            "state": "InActive"
        },
        {
            "number": "0469001002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469001003",
            "type": "Mobile",
            "state": "Active"
        }
    ]
}
```

##### Activate a given number
```
curl --location --request PATCH 'http://localhost:8080/v1/belong/phoneNumbers/0469001001' \
--header 'Content-Type: application/json' \
--data-raw '{
    "state" : "Active"
}'
Response: 200 OK
{
    "id": "CUS001",
    "name": "Mahesh Babu",
    "phone": [
        {
            "number": "0469001001",
            "type": "Office",
            "state": "Active"
        },
        {
            "number": "0469001002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469001003",
            "type": "Mobile",
            "state": "Active"
        }
    ]
}
```
##### List all Customers
```
curl --location --request GET 'http://localhost:8080/v1/belong/customers'
Response: 200 OK
{
    "records": 5,
    "customer": [
        {
            "id": "CUS001",
            "name": "Mahesh Babu",
            "phone": [
                {
                    "number": "0469001001",
                    "type": "Office",
                    "state": "Active"
                },
                {
                    "number": "0469001002",
                    "type": "Home",
                    "state": "Active"
                },
                {
                    "number": "0469001003",
                    "type": "Mobile",
                    "state": "Active"
                }
            ]
        },
        {
            "id": "CUS002",
            "name": "Gopi Krishna",
            "phone": [
                {
                    "number": "0469002001",
                    "type": "Office",
                    "state": "Active"
                },
                {
                    "number": "0469002002",
                    "type": "Home",
                    "state": "Active"
                },
                {
                    "number": "0469002003",
                    "type": "Mobile",
                    "state": "InActive"
                }
            ]
        },
        {
            "id": "CUS003",
            "name": "Balu",
            "phone": [
                {
                    "number": "0469003001",
                    "type": "Office",
                    "state": "InActive"
                },
                {
                    "number": "0469003002",
                    "type": "Home",
                    "state": "InActive"
                },
                {
                    "number": "0469003003",
                    "type": "Mobile",
                    "state": "InActive"
                }
            ]
        },
        {
            "id": "CUS004",
            "name": "Sarat Chandra",
            "phone": [
                {
                    "number": "0469004001",
                    "type": "Office",
                    "state": "Active"
                },
                {
                    "number": "0469004002",
                    "type": "Home",
                    "state": "Active"
                },
                {
                    "number": "0469004003",
                    "type": "Mobile",
                    "state": "Active"
                }
            ]
        },
        {
            "id": "CUS005",
            "name": "Mahendar",
            "phone": [
                {
                    "number": "0469005001",
                    "type": "Office",
                    "state": "Active"
                },
                {
                    "number": "0469005002",
                    "type": "Home",
                    "state": "Active"
                },
                {
                    "number": "0469005003",
                    "type": "Mobile",
                    "state": "Active"
                }
            ]
        }
    ]
}

```
##### Retrieve a given customer
```
curl --location --request GET 'http://localhost:8080/v1/belong/customers?customerId=CUS001'
{
    "id": "CUS001",
    "name": "Mahesh Babu",
    "phone": [
        {
            "number": "0469001001",
            "type": "Office",
            "state": "Active"
        },
        {
            "number": "0469001002",
            "type": "Home",
            "state": "Active"
        },
        {
            "number": "0469001003",
            "type": "Mobile",
            "state": "Active"
        }
    ]
}
```

####
