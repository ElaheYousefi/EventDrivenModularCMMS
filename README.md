# CMMS - Computerized Maintenance Management System

## Overview

This project is a simple form of Computerized Maintenance Management System (CMMS) designed for industrial facilities and power plants. The system manages preventive maintenance activities, work orders, equipment status, employee assignments, and notifications.

The application is built using a modular event-driven architecture to improve maintainability, scalability, and separation of concerns.

---

## Business Workflow

### 1. Maintenance Scheduling

Maintenance tasks are predefined and stored in the database.

A scheduler periodically checks for maintenance tasks that are due for execution.

### 2. Work Order Creation

When due tasks are detected, the Assignment Module publishes a `ReadyAssignTasksEvent`.

The Work Order Module listens to this event and automatically creates work orders.

### 3. Employee Assignment

A supervisor assigns a work order to a maintenance employee.

The Work Order Module publishes a `WorkOrderAssignedEvent`.

### 4. Notification

The Notification Module listens for assignment events and sends notifications to the assigned employee through SMS and email.

### 5. Maintenance Execution

The employee performs the maintenance activity and records:

* Observation results
* Completion date
* Equipment condition

### 6. Equipment Status Update

After completion, the Work Order Module publishes a `WorkOrderCompletedEvent`.

The Equipment Module consumes the event and updates the equipment status.

---

## Architecture

The application follows a modular monolith architecture with event-driven communication between modules.

### Modules

#### Assignment Module

Responsibilities:

* Maintenance scheduling
* Detecting due tasks
* Publishing maintenance events

#### Work Order Module

Responsibilities:

* Creating work orders
* Assigning employees
* Tracking maintenance execution
* Publishing work order events

#### Notification Module

Responsibilities:

* Sending email notifications
* Sending SMS notifications

#### Equipment Module

Responsibilities:

* Managing equipment information
* Updating equipment status
* Maintaining equipment lifecycle information

---

## Event Flow

Assignment Scheduler
→ ReadyAssignTasksEvent

ReadyAssignTasksEvent
→ Work Order Module

Employee Assignment
→ WorkOrderAssignedEvent

WorkOrderAssignedEvent
→ Notification Module

Maintenance Completion
→ WorkOrderCompletedEvent

WorkOrderCompletedEvent
→ Equipment Module

---

## Technologies

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Flyway
* Maven
* Event-Driven Architecture
* Modular Monolith Architecture

---

## Database Migration

Flyway is used for database versioning and schema management.

Responsibilities:

* Table creation
* Initial data insertion
* Database version control

---

## Design Principles

* Domain-oriented modular design
* Event-driven communication
* Separation of concerns
* Transactional consistency
* Extensible architecture for future microservices migration

---

## Project Goal

The goal of this project is to demonstrate enterprise-level backend development skills including:

* Spring Boot development
* Event-driven architecture
* Modular system design
* JPA/Hibernate persistence
* Database migration management
* Maintainable and scalable software architecture
