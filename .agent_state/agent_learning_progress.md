# Agent's Learning Progress Tracker

This file is for the agent's internal state management and should not be edited manually. Especially for the learning 
summaries, the contents is best to be **appended**. If need to modify existing content, then a content restructuring may
be required when necessary. This is so that when learning new stuff, the existing knowledge does not get simply lost
due to writing the new knowledge contents only. The user wants a learning summary, so it should contain the summaries of 
all learning parts throughout the study and not only recent ones.

## User's Reference Documents
- `C:/Users/adizafri/Documents/rah/messaging-lab/LEARNING_PLAN.md`
- `C:/Users/adizafri/Documents/rah/messaging-lab/learning-summary/00-project-journey.md`

## Current Status (as of last interaction)

### Last Completed Milestone
- **Phase:** Introduction to Kafka
- **Achievement:** Successfully set up the `lab-kafka` module and implemented a basic Kafka Producer and Consumer.
  - **Module Setup:** Created `lab-kafka` with `pom.xml` and updated parent `pom.xml`.
  - **Configuration:** Created `application.yml` with Kafka broker settings, including correct deserialization.
  - **Producer:** Implemented `KafkaProducer` using `KafkaTemplate` and `KafkaController` for HTTP trigger.
  - **Consumer:** Implemented `KafkaConsumer` using `@KafkaListener`.
  - **Testing:** Successfully tested end-to-end flow, and logs were explained.
  - **Documentation:** `00-project-journey.md` updated with Kafka concepts, code changes, and diagrams.
- **Evidence:** Code files created in `lab-kafka/src/main/java/org/adi/`, `application.yml` updated, and `00-project-journey.md` reflects these changes.

### Current Active Topic
- **Phase:** Introduction to Kafka - COMPLETED

### Next Action
- **Phase 4: Advanced Kafka**
- **First Task:** As per `LEARNING_PLAN.md`, the next step is to explore **Consumer Groups**.
