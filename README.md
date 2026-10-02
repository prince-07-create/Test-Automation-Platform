# AI-Powered Test Automation Platform

A modern, professional test automation framework built with Java, Playwright, and JUnit 5, enhanced with AI for intelligent failure diagnosis.

## 🚀 Features
- **UI Automation**: Uses Playwright for fast, reliable, and auto-waiting cross-browser testing.
- **API Automation**: Fully integrated REST API testing using Playwright's `APIRequestContext`.
- **AI Diagnosis**: Custom JUnit `TestWatcher` that automatically intercepts failed tests, captures the error trace and a screenshot, and sends it to an LLM (via OpenRouter) to provide human-readable root-cause analysis in the terminal.
- **HTML Reporting**: Automatically generates test execution reports and failure screenshots.
- **Data-Driven & Configurable**: No hardcoded secrets. Environment variables are managed securely via `.env`.

## 📁 Architecture
```text
project/
├── src/main/java/com/automation/
│   ├── config/       # Environment & Secrets management
│   ├── pages/        # Page Object Models (POM) for UI tests
│   └── ai/           # OpenRouter LLM Client
├── src/test/java/com/automation/tests/
│   ├── ui/           # UI End-to-End Tests
│   ├── api/          # REST API Tests
│   └── base/         # Test fixtures (BaseTest, AiTestWatcher)
├── src/test/resources/
│   └── test-data/    # JSON/CSV files for data-driven testing
├── reports/
│   └── screenshots/  # Automatically captured on test failure
├── .env              # Local configurations (do not commit)
└── pom.xml           # Maven dependencies and plugins
```

## 🛠️ Setup & Execution
1. Create a `.env` file from the provided `.env.example` and add your OpenRouter API key.
2. Run the tests using Maven:
   ```bash
   mvn clean test
   ```
3. View the generated HTML report:
   ```bash
   start target/site/surefire-report.html
   ```

## 📸 Screenshots & AI Logging
When a UI test fails, the framework automatically:
1. Takes a screenshot and saves it to `reports/screenshots/`.
2. Queries the AI model to explain exactly what broke and how to fix it, printing the output directly to the console.
