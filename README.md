**Authentication and Authorization completed in Spring boot with Spring version 3.1.7 **
Having User Table and Role Table which create User_roles table in junction ,using Postgress Database.

## Installation

Steps to clone and run the project in local enviroments.

### step1:

Git clone the repository.

### Step2:

Make sure to update the databse crendentials in resource/applicatin.properties file.

### step3:

```
 Run the project.
```

## Postman signup payload:

```sh
http://localhost:9090/customer/signup
{
    "name": "aamir",
    "email": "aamir@gmail.com",
    "password": "aamir",
    "roles": ["ROLE_CUSTOMER","ROLE_ADMIN","ROLE_SP"]
}

Curl Request For signup:

curl --location 'http://localhost:9090/customer/signup' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772' \
--data-raw '{
    "name": "aamir",
    "email": "aamir@gmail.com",
    "password": "aamir",
    "roles": ["ROLE_CUSTOMER","ROLE_ADMIN","ROLE_SP"]
}'
```

```sh
http://localhost:9090/customer/login
{
    "email": "aamir@gmail.com",
    "password": "aamir"
}

Curl request:

curl --location 'http://localhost:9090/customer/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772' \
--data-raw '{
    "email": "aamir@gmail.com",
    "password": "aamir"
}'
```

```sh
you can pass token in Bearer inside Authorization.

curl --location 'http://localhost:9090/customer/list' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYW1pckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9DVVNUT01FUiIsIlJPTEVfU1AiXSwiaWF0IjoxNzA1NDA2MDMzLCJleHAiOjE3MDU0MDc4MzN9.cDWg01Vbi-NqBKn1UviUozsRrHjIrEBMLVc9zzKjRuM' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'


For Customer dashboard to access only with customer role

Curl Request Customer Dashboard:

curl --location 'http://localhost:9090/api/dashboard' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YWhpckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0NVU1RPTUVSIl0sImlhdCI6MTcwNTQwNTk3MiwiZXhwIjoxNzA1NDA3NzcyfQ.-7-4mnGqYXcP_lj_2geEBCmUnLThKfUgRuukL2M7EzY' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'


```

```sh
Role Curd curl with Admin role access:
##CreateRole 

curl --location 'http://localhost:9090/roles/create' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772' \
--data '{
    "name":"ROLE_SP"
}'

##Get All roles
curl --location 'http://localhost:9090/roles' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYW1pckBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9DVVNUT01FUiIsIlJPTEVfU1AiXSwiaWF0IjoxNzA1NDA1ODg1LCJleHAiOjE3MDU0MDc2ODV9.v7YaqkTIqWg9rqlrxljORot2wlg0Fkr8zUC4U4qqDdc' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'

##Delete role by id
curl --location 'http://localhost:9090/roles/delete/4' \
--header 'Cookie: JSESSIONID=2090373FEB270D386A97E4AEB1BF1772'

```

```sh
 # Authoer - Aamir Nawaz
 # Principal Software Engineer - Java | Kotline | Spring boot | Node.js GraphQl | Golang Gogin, GoFiber| Aws ,GCP
 # Contact Details: aamirnawaz.dev@gmail.com
 # Portfolio url: aamirnawaz@netlify.app
```