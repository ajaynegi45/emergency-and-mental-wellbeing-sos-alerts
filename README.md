# **SOS Alerts Microservice Documentation**

## **Overview**
The SOS Alerts Microservice is designed to help users send emergency alerts via email with their current location. Users can save emergency contacts (name, email, and phone numbers) and retrieve them when required. The service also provides Google Maps links for accurate location tracking.

### **Key Features**
- Store emergency contacts (name, email, and phone number).
- Send SOS alerts to saved contacts.
- Provide Google Maps links for location tracking.
- Detailed validation for all user inputs.
- Error handling with meaningful messages.
- Production-ready, scalable, and secure.

---

## **Table of Contents**
1. [Technologies Used](#technologies-used)
2. [System Requirements](#system-requirements)
3. [Installation and Setup](#installation-and-setup)
4. [API Endpoints](#api-endpoints)
    - [SOS Alert Endpoints](#sos-alert-endpoints)
    - [Contact Management Endpoints](#contact-management-endpoints)
5. [Request and Response Models](#request-and-response-models)
6. [Error Handling](#error-handling)
7. [Examples](#examples)
8. [Best Practices](#best-practices)
9. [Future Enhancements](#future-enhancements)

---

## **Technologies Used**
- **Backend**: Spring Boot (Java)
- **Database**: MongoDB
- **Mailing Service**: Gmail SMTP via JavaMailSender
- **Validation**: Spring Validation
- **Build Tool**: Maven

---

## **System Requirements**
- Java 17 or higher
- Maven 3.8.1 or higher
- MongoDB 6.0 or higher
- Internet connection for Gmail SMTP

---

## **Installation and Setup**
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/sos-alerts.git
   ```
2. Navigate to the project directory:
   ```bash
   cd sos-alerts
   ```
3. Configure Gmail SMTP:
   - Add the following properties in `application.properties`:
     ```properties
     spring.mail.host=smtp.gmail.com
     spring.mail.port=587
     spring.mail.username=your-email@gmail.com
     spring.mail.password=your-app-password
     spring.mail.properties.mail.smtp.auth=true
     spring.mail.properties.mail.smtp.starttls.enable=true
     ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application at: `http://localhost:8080`

---

## **API Endpoints**

### **Base URL**
`http://localhost:8080/api/sos`

---

### **1. SOS Alert Endpoints**

#### **1.1 Send SOS Alert**
**Endpoint**:  
`POST /alert`

**Description**:  
Sends an SOS email to all saved emergency contacts with a Google Maps link to the user's location.

**Request Parameters**:
| Parameter  | Type   | Description                        | Required |
|------------|--------|------------------------------------|----------|
| `userId`   | String | Unique identifier of the user.     | Yes      |
| `latitude` | Double | Latitude of the user's location.   | Yes      |
| `longitude`| Double | Longitude of the user's location.  | Yes      |

**Request Example**:
```http
POST /api/sos/alert?userId=12345&latitude=28.7041&longitude=77.1025
```

**Response**:
| Status Code | Description                              |
|-------------|------------------------------------------|
| `200 OK`    | SOS alerts sent successfully.            |
| `404 Not Found` | User not found.                       |
| `500 Internal Server Error` | Failed to send SOS alerts. |

**Response Example**:
```json
{
  "message": "SOS alerts sent successfully!"
}
```

---

### **2. Contact Management Endpoints**

#### **2.1 Add Contact**
**Endpoint**:  
`POST /add-contacts`

**Description**:  
Adds a new contact (name, email, and phone number) for a user.

**Request Body**:
| Field           | Type   | Description                          | Required |
|------------------|--------|--------------------------------------|----------|
| `userId`         | String | Unique identifier of the user.       | Yes      |
| `contact.name`   | String | Name of the contact.                 | Yes      |
| `contact.email`  | String | Email of the contact.                | Optional |
| `contact.phoneNumber` | String | 10-digit phone number.           | Optional |

**Request Example**:
```json
{
  "userId": "12345",
  "contact": {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "9876543210"
  }
}
```

**Response**:
| Status Code | Description                              |
|-------------|------------------------------------------|
| `200 OK`    | Contact saved successfully.              |
| `400 Bad Request` | Validation failed.                   |
| `500 Internal Server Error` | Failed to save the contact. |

**Response Example**:
```json
{
  "message": "Contact saved successfully!"
}
```

---

#### **2.2 Get All Contacts**
**Endpoint**:  
`GET /get-contacts/{userId}`

**Description**:  
Fetches all saved contacts (name, email, and phone number) for a user.

**Path Parameters**:
| Parameter  | Type   | Description                        | Required |
|------------|--------|------------------------------------|----------|
| `userId`   | String | Unique identifier of the user.     | Yes      |

**Response**:
| Status Code | Description                              |
|-------------|------------------------------------------|
| `200 OK`    | Contacts fetched successfully.            |
| `404 Not Found` | User not found.                       |
| `500 Internal Server Error` | Failed to fetch contacts.   |

**Response Example**:
```json
{
  "userId": "12345",
  "contacts": [
    {
      "name": "John Doe",
      "email": "john.doe@example.com",
      "phoneNumber": "9876543210"
    },
    {
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "phoneNumber": "8765432109"
    }
  ]
}
```

---

## **Request and Response Models**

### **ContactDTO**
```json
{
  "userId": "string",
  "contact": {
    "name": "string",
    "email": "string",
    "phoneNumber": "string"
  }
}
```

### **ContactDetailsDTO**
```json
{
  "userId": "string",
  "contacts": [
    {
      "name": "string",
      "email": "string",
      "phoneNumber": "string"
    }
  ]
}
```

---

## **Error Handling**

### **Validation Errors**
- Missing or invalid inputs will return a `400 Bad Request` with details about the failed validation.

### **Common Error Responses**:
| Status Code | Message                                  |
|-------------|------------------------------------------|
| `404`       | User not found.                         |
| `400`       | Validation failed for request body.     |
| `500`       | Internal Server Error.                  |

---

## **Examples**

### **Send SOS Alert**
```bash
curl -X POST "http://localhost:8080/api/sos/alert?userId=12345&latitude=28.7041&longitude=77.1025"
```

**Expected Response**:
```json
{
  "message": "SOS alerts sent successfully!"
}
```

---

## **Best Practices**
1. Use a valid Gmail account with an app password for email services.
2. Secure sensitive credentials (e.g., email passwords) using environment variables.
3. Validate all inputs on both the frontend and backend.

---

## **Future Enhancements**
1. SMS-based SOS alerts.
2. Multi-language email templates.
3. Push notifications for mobile devices.
