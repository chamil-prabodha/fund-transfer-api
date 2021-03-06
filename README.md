# fund-transfer-api
This is a RESTful api develooped using Spark Java microframework to simulate fund transfer between accounts. The API runs on port 8000 by default and has been developed to run on JRE 1.8 and above

## Key Assumptions
* Assume transfer using only one currency(ex: USD). Transfer across multiple currencies are not supported.
* Transfer amount has to be in exactly two decimal places.
* Transfer amount has to be always positive in the request.
* Transfer to same account is possible.
* Account number is unique to an account.
* Only two accounts are involved in a transfer request(sender and receiver) 
* For testing v1.0.0 has been setup with only two accounts in memory(account numbers: 100, 101)

## API Spec
#### POST /api/transfer
```
request
{
  "fromAccount": "100",
  "toAccount": "101",
  "amount": 10.00
}
```
```
response
{
    "code": 0,
    "message": "transfer successful"
}
```
#### GET /api/balance/101
```
response
{
    "code": 0,
    "message": "90.00"
}
```
#### Error Scenarios
```
response
{
    "code": 5,
    "message": "insufficient balance"
}
```

## Test
```
mvn clean test
mvn clean verify
```

## Build
```
mvn clean package -DskipTests
```

## Binaries
* [v1.0.1](https://github.com/chamil-prabodha/fund-transfer-api/releases/tag/v1.0.1)
* [v1.0.0](https://github.com/chamil-prabodha/fund-transfer-api/releases/tag/v1.0.0)
