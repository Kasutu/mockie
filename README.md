# Usage Guideline

should work

**Clone the Repository**

```
  git clone https://github.com/splitscale/mockie.git
```

**Run the server**

```
  mvn clean install && java -jar ./target/mockie-0.0.1-SNAPSHOT.jar
```

### Javascript interface :)

- POST method payload

  **Object you need to send**

Body

```
  interface User {
    id: number;
    username: string;
    password: string;
  }
```

- GET method payload
  **Object you will get**

Body

```
  interface UserDisplayable {
    id: number;
    username: string;
  }
```



### Endpoints

- `/api/user/create`

  - Request body: `User`
  - Response body: `UserDisplayable`

- `/api/user/get`

Correct username is: `stevenBallaret`

- Request body: none
- Response body: `UserDisplayable`

- `/api/user/update`

  - Request body: `User`
  - Response body on error: `username or password is empty`: `status code: 400`

- `/api/user/delete`

  - Request body: `User`
  - Response body on error: `username or password is empty`: `status code: 400`

---

Sure, here's an API usage guide for the `EnergyConsumptionController`:

## Overview

The `EnergyConsumptionController` is a REST API that allows you to manipulate `EnergyConsumption` objects. You can add, edit, delete, read, filter, and search for `EnergyConsumption` objects.

## Base URL

The base URL for the API is:

```
http://your-domain.com/api/energy/consumption
```

Replace `your-domain.com` with the domain name of the server hosting the API.

## Authentication

The API does not require any authentication.

## Endpoints

The following endpoints are available:

### Add Energy Consumption

```
POST /add
```

Adds a new `EnergyConsumption` object to the `energyConsumptionMap` hashmap.

#### Request Body

| Field            | Type   | Required | Description                                        |
|------------------|--------|----------|----------------------------------------------------|
| id               | string | yes      | A unique identifier for the energy consumption.   |
| energyConsumption | string | yes      | The energy consumption amount.                     |
| description      | string | yes      | A description of the energy consumption.           |
| importance       | string | yes      | The importance of the energy consumption.          |

#### Response

If the `EnergyConsumption` object is added successfully, the API returns a `201 Created` response with the added `EnergyConsumption` object in the response body.

### Edit Energy Consumption

```
PUT /edit/{id}
```

Edits an existing `EnergyConsumption` object in the `energyConsumptionMap` hashmap.

#### Parameters

| Parameter | Type   | Required | Description                                      |
|-----------|--------|----------|--------------------------------------------------|
| id        | string | yes      | The ID of the `EnergyConsumption` object to edit. |

#### Request Body

| Field            | Type   | Required | Description                                        |
|------------------|--------|----------|----------------------------------------------------|
| energyConsumption | string | yes      | The energy consumption amount.                     |
| description      | string | yes      | A description of the energy consumption.           |
| importance       | string | yes      | The importance of the energy consumption.          |

#### Response

If the `EnergyConsumption` object is edited successfully, the API returns a `200 OK` response with the edited `EnergyConsumption` object in the response body.

### Delete Energy Consumption

```
DELETE /delete/{id}
```

Deletes an existing `EnergyConsumption` object from the `energyConsumptionMap` hashmap.

#### Parameters

| Parameter | Type   | Required | Description                                      |
|-----------|--------|----------|--------------------------------------------------|
| id        | string | yes      | The ID of the `EnergyConsumption` object to delete. |

#### Response

If the `EnergyConsumption` object is deleted successfully, the API returns a `204 No Content` response.

### Read Energy Consumption

```
GET /read/{id}
```

Reads an existing `EnergyConsumption` object from the `energyConsumptionMap` hashmap.

#### Parameters

| Parameter | Type   | Required | Description                                      |
|-----------|--------|----------|--------------------------------------------------|
| id        | string | yes      | The ID of the `EnergyConsumption` object to read. |

#### Response

If the `EnergyConsumption` object is found, the API returns a `200 OK` response with the `EnergyConsumption` object in the response body. If the `EnergyConsumption` object is not found, the API returns a `404 Not Found` response.

### Filter Energy Consumptions

```
GET /filter/{importance}
```

Filters the
