
# Microservice for Cache Management
## Requirements
- Docker
- JDK 11
- Apache Maven 3

## Run the project

```bash
# Build the MS
mvn clean package

# Build
docker-compose build

# Run
docker-compose up
```

## Endpoint
- In the project there is only one Endpoint: `POST /cache`
### Body `POST /cache`
. Save the Cache
```json
{
  "key": "MY_CUSTOM_KEY",
  "content" :{
    "optional_field": "optional_value"
  },
  "ttl": 200,
  "isAsync": true
}
```
* The `content` is required
### Body `PUT /cache`
. Update the Cache
```json
{
  "key": "MY_CUSTOM_KEY",
  "content" :{
    "optional_field": "optional_value"
  },
  "ttl": 200,
  "isAsync": true
}
```
* The `key` and `content` is required
* If the `ttl` is not sent, will be considered the current `ttl` of the Cache. If it is sent, it will be changed to the value sent.

### Response `GET /cache`
. Get all Cache Keys
```json
[
  "MY_CUSTOM_KEY",
  "053fd85a-dba8-4ff7-9b38-72ff41879727",
  "0b2967ee-bd96-4229-85b8-ec5818c6f490"
]
```

### Response `GET /cache/{key}`
##### Query parameter `response-model`
. Get Cache by the `key`
```json
{
  "key": "MY_KEY",
  "content": {
    "optional_field": "optional_value"
  },
  "ttl": 200,
  "current_ttl": 190
}
```
* The Query parameter `response-model` is Optional
* `response-model` is an Array of Strings, the Strings are the fields you want to return. An example below

##### Request `GET /cache/MY_KEY?response-model=key,content`
##### Response
```json
{
  "key": "MY_KEY",
  "content": {
    "optional_field": "optional_value"
  }
}
```
#### Values accepted in `response-model`: `key`, `content`, `ttl`, `current_ttl`

### Body `DELETE /cache`
```json
{
  "key": "MY_KEY",
  "isAsync": true
}
```
#### Comments
* `ttl` is in seconds 
* In `POST /cache` if not sent to `key` in Body the operation is automatically changed to synchronous, disregarding `isAsync`. Because if the `key` is not sent, we will generate a random key and we will return.
#### Default values
- `key` is a random UUID
- `ttl` is `2000`
- `isAsync` is `false`

#### Description of fields
- `content` is your Object
- `isAsync` is to identify the action is asynchronous or synchronous. If asynchronous, the task will run with no return. If synchronous, the request will return the operation status
- `ttl` is the time to expire the Cache. When the cache expires it is deleted
## Thanks🖖
#### Created by https://github.com/henriquemrc12
#### Issues and doubts: henriquemarcel2244@gmail.com