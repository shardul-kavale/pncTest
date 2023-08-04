# User Registration simulation 

## Author
**Shardul Kavale**
- Associate Application Developer
- Band 6G  -Halifax CIC
## Description

Pre-Interview assesment test for client - PNC Bank 

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Contact](#contact)

## Installation



## Usage



## Features

This is a microservice that simulates user registration. The REST API accepts username, password and ip address as a JSON payload. All the payload fields are first validated 
to make sure the payload is not null or empty and that the password is greater than 8 characters, containing at least 1 number, 1 Capitalized letter,
1 special character in this set “_ # $ % .”. When all validation is passed, it returns a JSON payload containging a random uuid and a success message with 
the registered user's username, city  (resolved using ip-geolocation api). An external service - https://ip-api.com/docs/api:json is called to lookup the provided ip.
If the IP is not in Canada, users are barred from registering and error messages are thrown in JSON format


## Contact
Shardul.Kavale@ibm.com
