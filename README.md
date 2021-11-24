## Getting Started

This is a demo project for the library [spring-boot-starter-data-search-jpa](https://github.com/commerce-io/spring-boot-starter-data-search)

### Online Demo
This demo is deployed on [heroku](https://www.heroku.com/) and sleeps after 30 minutes of inactivity. 
Your first request could be slow if it occurs when the application is sleeping. In this case, please retry a few seconds after your first request.

[https://data-search-jpa-demo.herokuapp.com/customers](https://data-search-jpa-demo.herokuapp.com/customers?search=birthDate%20%3E:%201988-01-01%20and%20(email%20:%20*gmail.com%20or%20email:%20*outlook.com)&page=0&size=100&sort=name,desc)

### Requirements
**Java version** 11 or higher (_If java 8 support is needed, please vote for [this issue](https://github.com/commerce-io/spring-boot-starter-data-search/issues/3)_)

**SpringBoot version** 2.1.0 or higher

### Usage
Clone, build and start as any other SpringBoot application.

spring-boot-starter-data-search-jpa-demo comes with an embedded h2 and the customers' table is initialized 
on startup with 10000 documents. 

If you want to deactivate the data init, or you want more data for your tests, 
you can adapt the configuration file.

```properties
demo.data-init=true
demo.data-size=10000
```

#### Customer structure
```json
{
    "id": "13e3afc8-0832-41c4-bb12-812e516d0980",
    "ref": 1966,
    "title": "Ms.",
    "name": "Angelique",
    "surname": "VonRueden",
    "email": "angelique.vonrueden@gmail.com",
    "emailVerified": true,
    "birthDate": "1989-04-14",
    "addresses": [
        {
            "id": "011992a8-dbf1-4398-9aef-5d4563df5c0c",
            "street": "942 Charlette Forges",
            "zipCode": "98527-5724",
            "city": "Stiedemannberg",
            "country": "CW"
        },
        {
            "id": "10a09a89-2359-457c-b50a-308dda9d7add",
            "street": "400 Schamberger Lodge",
            "zipCode": "22998",
            "city": "Andreshire",
            "country": "CN"
        }
    ],
    "coins": 44.801514,
    "createdDate": "2021-06-12T11:21:13.897Z"
}
```

#### Search API

By default, with  [data-search](https://github.com/commerce-io/spring-boot-starter-data-search), all customer fields become searchable. 
```cURL
curl -L -X  GET 'http://localhost:8080/customers?search=birthDate >: 1988-01-01 and (email : *gmail.com or email: *protonmail.com)&page=0&size=10&sort=name,desc'
```