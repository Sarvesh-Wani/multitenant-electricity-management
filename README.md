# ⚡ Multi-Tenant Utility Onboarding & Field Operations Platform

A Spring Boot backend that lets a **platform operator** onboard multiple **electricity service providers** (utility companies) onto a single system, and lets each provider run its own field operations — technicians, billers, and customer support — in a fully isolated tenant space.

Instead of every utility company standing up its own software, they plug into one shared platform. Each company gets its own **isolated database schema** (true schema-per-tenant multi-tenancy), while the platform operator's team manages onboarding, geography, and field-force hierarchy centrally.

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Core Concepts](#-core-concepts)
- [Core Features](#-core-features)
- [Tech Stack](#-tech-stack)
- [Multi-Tenancy Architecture](#-multi-tenancy-architecture)
- [Role Hierarchy](#-role-hierarchy)
- [Project Structure](#-project-structure)
- [API Reference](#-api-reference)
- [Error Handling](#-error-handling)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Roadmap](#-roadmap)

---

## 🔎 Overview

Utility providers (electricity, in this implementation) that want to reach customers PAN-country need three things: a way to onboard onto shared infrastructure, a field-operations chain that scales from state → district → city → street level, and a support workflow that doesn't collapse the moment a query gets complicated. This platform provides all three, split into two distinct operating spaces:

- **The Platform Space** (`public` schema) — owned and run by the platform operator. This is where service providers get onboarded, where the country-wide geography (states → districts → cities → areas) is defined, and where the operator's own field force (state heads, district heads, city heads, technicians, billers, CRM staff) lives and is managed.
- **Tenant Spaces** (one schema per client company) — each onboarded electricity provider gets its own isolated schema. Inside it, the provider runs its own BPO-style support desk (operations head → managers → agents), configures its own meter types and rates, and handles its own customer queries end-to-end.

A request is routed to the correct tenant schema at the database connection level, so provider data never leaks across tenants — down to a shared `X-Tenant-ID` header resolving the active schema per request.

---

## 🧩 Core Concepts

| Concept | What it means here |
|---|---|
| **Platform Operator** | The company running the shared system. Its team onboards providers and manages the geographic field hierarchy. |
| **Client Company** | An electricity/utility provider onboarded onto the platform. Each one is a tenant with its own schema. |
| **Tenant** | An isolated Postgres schema, one per client company, switched per-request via `X-Tenant-ID`. |
| **Field Hierarchy** | State → District → City → Area, each with a designated head who manages the level below. |
| **BPO Desk** | Each client company's internal support desk — Operations Head → Manager L1/L2 → Agents — that handles customer queries with escalation. |

---

## ✨ Core Features

### 🔐 Authentication & Authorization
- Stateless **JWT authentication** with **refresh token support** (access + refresh token pair, refresh-token expiry handling).
- A custom `OncePerRequestFilter` validates the JWT *and* resolves the active tenant schema from the `X-Tenant-ID` header on every request.
- **Fine-grained, method-level authorization** via Spring Security `@PreAuthorize`, enforced independently for every one of the platform's and every tenant's roles.
- Passwords hashed with **BCrypt**.

### 🏢 Cascading Onboarding (Platform Side)
A strict, role-gated onboarding chain — nobody can onboard above their own level:
- A super-admin onboards management-team members (and other super-admins).
- Management onboards state heads and sales team members, and assigns sales tasks.
- A state head onboards district heads and defines/owns states.
- A district head onboards city heads and defines districts.
- A city head onboards local technicians, billers, and CRM staff for their city, and defines service areas — assigning a technician and a biller to each one.
- A sales team member onboards client companies (the electricity providers themselves) and links them to the states they'll operate in.

### 🏭 Cascading Onboarding (Tenant Side — per Client Company)
Once a client company is onboarded, its own BPO desk builds itself out the same way, isolated inside its own schema:
- A tenant admin onboards a sales-team member.
- That sales-team member onboards an operations head.
- The operations head onboards BPO agents, Level-1 and Level-2 managers, and assigns BPO agents to the states they'll cover.
- The operations head also configures the company's **meter types and per-unit rates** (household, industrial, solar).

### 👤 Customer Lifecycle
- **CRM staff** register new customers against a specific service area and assign them a service provider.
- Customers raise support **queries**, which route to the local technician assigned to their area.
- Queries **escalate** cleanly: unresolved issues move from the technician to **Manager Level 1**, and from there to **Manager Level 2** — matching a real support desk's escalation path.

### 🧾 Billing & Meter Reading
- Billers upload meter-reading evidence (photo capture) via a dedicated file-upload endpoint, stored against the customer/meter record.
- Bills are generated against a customer, tracked with a due date and a `PENDING`/`PAID` status.
- Billers can additionally raise and track their own operational queries (e.g. equipment or access issues) separate from customer support queries.

### 🛡️ Centralized, Consistent Error Handling
- A single `@RestControllerAdvice` handles every domain and framework exception — resource-not-found, duplicate resource, invalid credentials, unauthenticated access, role mismatch, expired refresh tokens, and validation failures — and returns a uniform, predictable shape.
- Every response, success or failure, is wrapped in the same `ApplicationResponse<T>` envelope.

### 🧱 Clean, Layered Architecture
- Strict **Controller → Service → Repository** separation, mirrored for both platform-side and tenant-side domains.
- **MapStruct** for compile-time-safe DTO ⇄ Entity mapping.
- Centralized route and constant definitions (`ApiPath`, `ExceptionConstants`) instead of magic strings.
- **SLF4J** structured logging, with SQL/bind-level logging available for local debugging.
- **springdoc-openapi** wired in for live Swagger/OpenAPI documentation.

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JJWT (JSON Web Tokens + Refresh Tokens), BCrypt |
| Persistence | Spring Data JPA + Hibernate (schema-per-tenant multi-tenancy), PostgreSQL |
| Migrations | Flyway (tenant schema bootstrap) |
| Mapping | MapStruct |
| Docs | springdoc-openapi (Swagger UI) |
| Build | Maven |
| Boilerplate | Lombok |

---

## 🏗 Multi-Tenancy Architecture

This is a **schema-per-tenant** implementation built directly on Hibernate's multi-tenancy SPI — not a manual `WHERE tenant_id = ?` filter bolted onto every query.

```
Incoming Request
   │  Header: X-Tenant-ID: <client-company-schema>
   ▼
JWTFilter
   │  1. Authenticates the JWT
   │  2. Pushes the tenant ID into TenantContext (an InheritableThreadLocal)
   ▼
TenantIdentifierResolver  ──►  tells Hibernate which schema is "current"
   │
   ▼
MultiTenantConnectionProviderImpl
   │  Issues `SET search_path = <tenant-schema>` on the JDBC connection
   │  before the query runs, and resets to `public` when the connection
   │  is released back to the pool.
   ▼
PostgreSQL  (one schema per client company; shared platform tables in `public`)
```

**Why this matters:** platform-side data (states, districts, cities, client company registry, the operator's own staff) lives permanently in the `public` schema. The moment a request carries a client company's tenant header, every JPA query in that request transparently executes against *that company's* schema instead — same entities, same repositories, zero per-query tenant filtering logic in the service layer.

---

## 🗺 Role Hierarchy

### Platform-side roles (public schema)
```
SUPER_ADMIN
   └─ MANAGER (management team)
        ├─ SALES_TEAM_MEMBER  → onboards client companies
        └─ STATE_HEAD
             └─ DISTRICT_HEAD
                  └─ CITY_HEAD
                       ├─ LOCAL_TECHNICIAN
                       ├─ BILLER
                       └─ CRM  → registers & assigns customers

CUSTOMER  (end consumer, raises support queries)
```

### Tenant-side roles (per client-company schema)
```
ADMIN
  └─ SALES_TEAM
       └─ OPERATION_HEAD
            ├─ MANAGER1  (first-line escalation)
            ├─ MANAGER2  (second-line escalation)
            └─ BPO       (front-line query handling, assigned per state)
```

Every level can only onboard the level directly beneath it — enforced by `@PreAuthorize` at the controller, not by convention.

---

## 📁 Project Structure

```
src/main/java/.../multitenantelectricitymanagement
├── config/             # JWTFilter (auth + tenant resolution), SecurityConfig
├── tenant/             # Multi-tenancy core: TenantContext, TenantIdentifierResolver,
│                        # MultiTenantConnectionProviderImpl, HibernateConfig
├── constants/          # ApiPath, ExceptionConstants
├── controller/          # Platform-side controllers (SuperAdmin, ManagementTeam,
│   └── tenant/          # SalesTeam, StateHead, DistrictHead, CityHead, Area,
│                        # LocalTechnician, Biller, BillQuery, Billing, CRM,
│                        # Customer, ClientCompany, File) + tenant-side controllers
│                        # (Employee, ClientMeter, CustomerQuery)
├── dto/                 # Request/response contracts, grouped by domain
├── entity/              # Public-schema entities + entity/tenant (per-tenant entities)
├── enums/               # Role, TenantRole, MeterType, CompanyStatus, QueryStatus,
│                        # BillStatus, TaskStatus
├── exception/           # Custom exceptions + GlobalExceptionHandler
├── mapper/               # MapStruct mappers
├── repository/           # Spring Data JPA repositories
└── service/              # One service per domain, mirroring the controllers
```

---

## 📡 API Reference

Base path: `/api/v1`. Tenant-scoped endpoints require an `X-Tenant-ID` header identifying the client company's schema.

### Auth
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/login` | Public | Authenticate, receive an access + refresh token |
| `POST` | `/refresh` | Public | Exchange a valid refresh token for a new access token |

### Platform Onboarding & Geography
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/onboard-superadmin` | Super Admin | Onboard another super-admin |
| `POST` | `/onboard-management-team-member` | Super Admin | Onboard a management-team member |
| `GET` | `/management-team`, `/management-team/{id}` | Super Admin | List / fetch management-team members |
| `POST` | `/onboard-sales-team-member` | Manager | Onboard a sales team member |
| `GET` | `/sales-team`, `/sales-team/{sid}` | Manager | List / fetch sales team members |
| `POST` | `/sales-task` | Manager | Assign a sales task |
| `GET` | `/sales-task` | Manager | List sales tasks |
| `GET` | `/sales-team/{sid}/sales-task` | Sales Team Member | View own assigned tasks |
| `POST` | `/states` | Super Admin | Create a state |
| `PUT` | `/states/{id}` | Super Admin, Manager | Assign/update a state head |
| `GET` | `/states`, `/states/{id}` | Super Admin | List / fetch states |
| `POST` | `/state-head` | Manager | Onboard a state head |
| `GET` | `/state-head`, `/state-head/{id}` | Manager | List / fetch state heads |
| `POST` | `/districts` | State Head, Manager | Create a district |
| `PUT` | `/districts/{id}` | Super Admin, Manager, State Head | Assign/update a district head |
| `GET` | `/districts`, `/districts/{id}` | State Head, Manager | List / fetch districts |
| `POST` | `/district-head` | State Head | Onboard a district head |
| `GET` | `/district-head`, `/district-head/{id}` | State Head | List / fetch district heads |
| `POST` | `/cities` | State Head, District Head, Manager | Create a city |
| `PUT` | `/cities/{id}` | Super Admin, Manager, State Head, District Head | Assign/update a city head |
| `GET` | `/cities`, `/cities/{id}` | State Head, District Head, Manager | List / fetch cities |
| `POST` | `/city-head` | District Head | Onboard a city head |
| `GET` | `/city-head`, `/city-head/{id}` | District Head | List / fetch city heads |

### Field Force & Service Areas
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/cities/{city-id}/areas` | City Head | Create a service area |
| `POST` | `/cities/{city-id}/areas/{area-id}/technicians` | City Head | Assign a technician to an area |
| `POST` | `/cities/{city-id}/areas/{area-id}/billers` | City Head | Assign a biller to an area |
| `GET` | `/cities/{city-id}/areas` | State/District/City Head, Manager | List areas |
| `POST` | `/cities/{city-id}/localtechnicians` | City Head | Onboard a local technician |
| `GET` | `/cities/{city-id}/localtechnicians`, `.../{id}` | City Head | List / fetch technicians |
| `POST` | `/cities/billers` | City Head | Onboard a biller |
| `POST` | `/cities/crm` | City Head | Onboard a CRM staff member |
| `GET` | `/cities/crm`, `/cities/crm/{id}` | City Head | List / fetch CRM staff |

### Client Companies & Customers
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/sales-team/{sid}/client-companies` | Sales Team Member | Onboard a client company |
| `GET` | `/sales-team/client-companies`, `.../{id}`, `/sales-team/{sid}/client-companies` | Sales Team Member | List / fetch client companies |
| `POST` | `/areas/{area-id}/customers` | CRM | Register a customer in an area |
| `GET` | `/areas/{area-id}/customers` | Authenticated | List customers |
| `POST` | `/areas/{area-id}/customers/assign-service-providers` | CRM | Assign a service provider to a customer |

### Billing & Bill Queries
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/customers/{id}/bills` | Biller | Generate a bill for a customer |
| `POST` | `/cities/billers/queries` | Biller | Raise an operational query |
| `GET` | `/cities/billers/queries` | Biller | List own queries |
| `POST` | `/saveFile` | — | Upload a file (e.g. meter-reading photo) |

### Tenant Onboarding (Client Company's Own BPO Desk)
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/employees/sales` | Tenant Admin | Onboard the tenant's sales-team member |
| `POST` | `/employees/operations-head` | Tenant Sales Team | Onboard the operations head |
| `POST` | `/employees/bpo` | Operations Head | Onboard a BPO agent |
| `POST` | `/employees/manager-1` | Operations Head | Onboard a Level-1 manager |
| `POST` | `/employees/manager-2` | Operations Head | Onboard a Level-2 manager |
| `POST` | `/employees/bpo-states` | Operations Head | Assign a BPO agent to a state |
| `GET` | `/employees/sales/{id}` | Authenticated | Fetch a tenant employee |
| `POST` | `/meters` | Operations Head | Configure a meter type and its rate per unit |

### Customer Queries (Tenant Side)
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/customers/{customer-id}/queries` | Customer | Raise a support query |
| `PATCH` | `/queries/{id}/resolved` | Local Technician | Mark a query resolved |
| `PATCH` | `/queries/{id}/escalated-m1` | Local Technician | Escalate to Manager Level 1 |
| `PATCH` | `/queries/{id}/escalated-m2` | Local Technician | Escalate to Manager Level 2 |

Full interactive documentation is available via Swagger UI once the app is running (see [Getting Started](#-getting-started)).

---

## ⚠️ Error Handling

All exceptions are caught centrally and mapped to consistent HTTP responses:

| Exception | HTTP Status | Meaning |
|---|---|---|
| `ResourceNotFoundException` | 404 | The requested entity doesn't exist |
| `DuplicateResourceException` | 409 | A unique resource (e.g. email, tenant ID) already exists |
| `InvalidCredentialsException` | 401 | Login credentials didn't match |
| `UnAuthenticatedUserException` | 401 | No valid security context present |
| `RoleMisMatchException` | 400 | The action doesn't fit the caller's role in context |
| `RefreshTokenExpiredException` | 401 | Refresh token expired — re-authentication required |
| `MethodArgumentNotValidException` | 400 | Request body failed validation |
| `AccessDeniedException` | 403 | Authenticated, but not authorized for this action |
| `HttpMediaTypeNotSupportedException` | 415 | Unsupported request content type |

Every error response follows the same shape:

```json
{
  "success": false,
  "message": "Exception Occurred",
  "errors": {
    "status": 404,
    "message": "Resource not found for the provided ID",
    "timestamp": "2026-07-11T10:15:30Z",
    "path": "http://localhost:8080/api/v1/states/42"
  }
}
```

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven (or use the bundled `./mvnw`)
- PostgreSQL running locally, with multi-tenant schema support (`CREATE SCHEMA` privileges)

### Setup

```bash
# 1. Clone the repository
git clone <this-repository-url>
cd multitenant-electricity-management

# 2. Create the database
psql -U postgres -c "CREATE DATABASE multitenant_db;"

# 3. Set required environment variables (see Configuration below)
export JWT_SECRET=your_jwt_secret_key

# 4. Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/api/v1`, with Swagger UI at `http://localhost:8080/swagger-ui.html`.

For any tenant-scoped call (client company onboarding requests, that company's own BPO desk, meter config, customer queries), include:

```
X-Tenant-ID: <client_company_schema_name>
```

---

## ⚙️ Configuration

Set the following in `src/main/resources/application.properties` (or as environment variables):

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/multitenant_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.multiTenancy=SCHEMA

# JWT
jwt.SECRET=${JWT_SECRET}
jwt.EXPIRATION=8640000

# Flyway (tenant schema bootstrap)
spring.flyway.enabled=true
```

> **Note:** `spring.jpa.hibernate.ddl-auto=none` means schemas are not auto-generated — each new tenant schema is expected to be provisioned (e.g. via Flyway migrations under `db/migration/tenant`) as part of the client-company onboarding flow.
>
> Never commit real secrets. Use environment variables or a local, git-ignored properties file in any shared or public repository.

---

## 🗺 Roadmap

- [ ] Automated schema provisioning triggered directly from the client-company onboarding endpoint (currently expects the schema to exist)
- [ ] Automated billing suspension when a client company misses a payment due date, blocking that tenant's BPO operations until resolved
- [ ] Notification layer (email/SMS) for bill generation, due-date reminders, and query escalations
- [ ] Dashboards/reporting per state, district, and city for field-force performance
- [ ] Stronger tenant-boundary tests to guarantee no cross-schema data leakage under load
