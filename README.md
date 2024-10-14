# User Rate Limiting Application

## Overview
This application implements rate limiting on user operations stored in a PostgreSQL database. Redis is used to manage the rate limits with expiration keys, ensuring efficient request handling. If a user exceeds their allowed rate limit, abnormal behavior is logged into a file.

## Features
- **Rate Limiting**: Limits the number of user requests within a configurable time frame.
- **Redis Cache**: Caches user request counts with expiration settings.
- **PostgreSQL**: Stores user details and rate limit configurations.
- **Logging**: Logs abnormal user behavior to a file when rate limits are exceeded.

## Project Structure
```plaintext
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.example.controller      # API Controllers
│   │   │   ├── com.example.repository      # Database Layer (PostgreSQL)
│   │   │   ├── com.example.service         # Business Logic and Redis Integration
│   │   │   └── com.example.model           # Models
├── resources
│   └── application.properties              # Configurations (DB & Redis)
└── README.md                               # Project Documentation
```

