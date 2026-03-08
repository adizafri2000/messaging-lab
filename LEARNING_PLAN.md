# Messaging Learning Plan: Spring Boot (RabbitMQ & Kafka)

## 📍 Current Status
- **Project Structure:** Multi-module Maven (`lab-common`, `lab-rabbitmq`).
- **Tech Stack:** Java 21, Spring Boot 3.4, Docker (implied).
- **Achievements:** 
  - Point-to-Point messaging implemented.
  - JSON Serialization (Jackson) configured.
  - Externalized configuration (`application.yml`).

---

## 🚀 Phase 1: Reliability & Resilience (RabbitMQ)
*Goal: Ensure no data is lost when things go wrong.*

### 1. Dead Letter Queues (DLQ)
- **Concept:** What happens when `RabbitConsumer` throws an exception?
- **Task:** Configure a DLQ so failed messages are moved aside instead of lost or infinitely retried.
- **Key Spring Classes:** `QueueBuilder`, `x-dead-letter-exchange`.

### 2. Retry Policies
- **Concept:** Transient failures (e.g., DB blip) vs. Permanent failures (e.g., bad data).
- **Task:** Configure Spring's `RetryTemplate` or `application.yml` retry settings (backoff policies).

---

## 📡 Phase 2: Messaging Patterns (RabbitMQ)
*Goal: Move beyond simple 1-to-1 messaging.*

### 1. Pub/Sub (Fanout Exchange)
- **Concept:** One event (Order Placed) triggers multiple independent actions (Email Service + Inventory Service).
- **Task:** Create a second consumer service (simulated) and bind it to the same exchange.

### 2. Competing Consumers (Scaling)
- **Concept:** High load handling.
- **Task:** Run two instances of `RabbitConsumer` and watch RabbitMQ load balance the messages (Round Robin).

---

## 🪵 Phase 3: Introduction to Kafka (The "Log" Model)
*Goal: Understand the paradigm shift from "Queue" to "Log".*

### 1. Setup `lab-kafka` Module
- **Task:** Create a new Maven module parallel to `lab-rabbitmq`.
- **Dependencies:** `spring-kafka`.

### 2. Kafka Producer & Consumer
- **Concept:** Topics, Partitions, and Offsets.
- **Task:** Replicate the "Order Created" flow using Kafka.

### 3. Push vs. Poll
- **Theory:** 
  - RabbitMQ pushes messages to consumers (Smart Broker, Dumb Consumer).
  - Kafka consumers poll for messages (Dumb Broker, Smart Consumer).

---

## ⚖️ Phase 4: Advanced Kafka
*Goal: Handling scale and ordering.*

### 1. Consumer Groups
- **Concept:** The Kafka equivalent of "Competing Consumers".
- **Task:** Start multiple consumers with the same `groupId` and observe partition assignment.

### 2. Partitions & Ordering
- **Concept:** Kafka only guarantees order *within* a partition.
- **Task:** Send messages with Keys (e.g., `orderId`) to ensure related events stay in order.

---

## 🏗️ Phase 5: Event-Driven Architecture
*Goal: Big picture design.*

### 1. Sagas (Distributed Transactions)
- **Concept:** How to roll back a transaction across microservices using events.

### 2. Idempotency
- **Concept:** Handling duplicate messages (processing the same order twice).