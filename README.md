<div align="center">
  <h1>🚀 AI-Powered Test Automation Platform</h1>
  <p>An enterprise-grade, self-diagnosing Web UI & REST API automation framework.</p>
</div>

---

## 📌 Project Overview
This project is an advanced, scalable test automation framework built to handle both Web UI and REST API testing. It differentiates itself from standard testing suites by acting as an intelligent "robotic QA engineer"—capable of not only executing tests rapidly but also automatically diagnosing the root cause of any failures using Artificial Intelligence (OpenRouter/Nemotron). 

## 🚨 Problem Statement
In traditional QA environments, continuous integration pipelines are frequently blocked by flaky tests, ambiguous failure logs, or unexpected DOM changes. Automation engineers spend a significant portion of their time reading cryptic Java stack traces, reproducing bugs manually, and staring at failed pipelines just to figure out *why* a test failed. This manual triage creates a massive bottleneck in software delivery.

## 💡 Solution
This framework solves the "Triage Bottleneck" by leveraging AI. When a test fails, the framework immediately intercepts the error, captures the visual state of the application, and bundles the context. It then queries a Large Language Model (LLM) which analyzes the stack trace and outputs a plain-English, actionable root-cause analysis directly into the console. What used to take 30 minutes of manual debugging now takes 5 seconds.

## 📐 Architecture
The framework is designed with strict adherence to industry best practices:
* **Page Object Model (POM):** UI logic and locators are abstracted into dedicated Page classes, keeping tests clean and maintainable.
* **JUnit 5 Extensions:** Lifecycle hooks (like `@BeforeAll` and `TestWatcher`) are utilized to manage browser contexts and intercept failures without cluttering test methods.
* **Environment Isolation:** Zero hardcoded secrets. All environments and credentials are provided dynamically via `.env` files.
* **Unified Pipeline:** Both API and UI tests share the same execution engine, generating a single, unified HTML report.

## ⭐ Features
- **Auto-Waiting UI Automation:** Powered by Playwright, eliminating the need for `Thread.sleep()` or explicit waits.
- **Integrated API Testing:** Built-in REST API validation using Playwright's `APIRequestContext`.
- **Intelligent Error Handling:** Custom `AiTestWatcher` intercepts failures and fetches AI diagnostics.
- **Visual Evidence Logging:** Auto-captures high-resolution screenshots the exact millisecond a failure occurs.
- **Cloud-Native CI/CD:** Runs completely headlessly on Ubuntu cloud servers via GitHub Actions on every push.

## 🛠️ Tech Stack
- **Language:** Java 17
- **Automation Engine:** Microsoft Playwright (Java)
- **Test Runner:** JUnit 5
- **Build Tool:** Maven
- **AI Integration:** OpenRouter API (Nemotron/Llama LLMs)
- **Reporting:** Maven Surefire HTML Plugin
- **CI/CD:** GitHub Actions

## 🧪 Test Coverage
The current test suite validates both frontend interactions and backend responses:
- **UI Tests (`AuthUiTest`)**: Validates positive and negative login scenarios on public sandbox sites.
- **API Tests (`ApiValidationTest`)**: Validates successful REST payloads (`200 OK`) and handles endpoint unavailability (`404 Not Found`).

## 🧠 AI Failure Diagnosis Flow
1. **Execution:** JUnit runs the test suite.
2. **Failure:** A test assertion fails or a timeout occurs.
3. **Interception:** The custom `AiTestWatcher` catches the `Throwable` exception.
4. **Visual Capture:** The watcher takes a screenshot of the exact failure state.
5. **AI Request:** The watcher bundles the test name and error message and sends it via HTTP to OpenRouter.
6. **Diagnosis:** The AI returns a plain-English explanation of why the test failed and how to fix it, printing it directly to the console.

## 📁 Project Structure
```text
project/
├── src/main/java/com/automation/
│   ├── ai/           # OpenRouter LLM API Client
│   ├── config/       # Environment & Secrets management (.env parser)
│   └── pages/        # Page Object Models (e.g., LoginPage)
├── src/test/java/com/automation/tests/
│   ├── api/          # REST API Tests
│   ├── base/         # Test fixtures (BaseTest, AiTestWatcher)
│   └── ui/           # UI End-to-End Tests
├── reports/
│   └── screenshots/  # Automatically captured on test failure
├── .github/workflows/# GitHub Actions CI/CD Pipeline
├── .env.example      # Template for environment variables
└── pom.xml           # Maven dependencies and plugins
```

## ⚙️ Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/YourUsername/Test-Automation-Platform.git
   cd Test-Automation-Platform
   ```
2. **Install dependencies:**
   ```bash
   mvn clean install -DskipTests
   ```
3. **Install Playwright Browsers:**
   ```bash
   npx playwright install --with-deps
   ```

## 🔐 Configuration
Copy the provided `.env.example` file to create your local `.env` file. You must add your OpenRouter API Key for the AI diagnostics to function.
```bash
cp .env.example .env
```
*Note: `.env` is safely added to `.gitignore` to prevent secret leakage.*

## ▶️ Running Tests
To execute the entire test suite (API and UI) locally:
```bash
mvn clean test
```

## 📊 Reporting
Once tests finish, Maven automatically generates a rich HTML report detailing passes, failures, and execution times.
To view the report, open the following file in any browser:
```bash
start target/site/surefire-report.html
```

## ☁️ CI/CD
This project features a fully automated Continuous Integration pipeline using **GitHub Actions**. 
On every push to the `main` branch, GitHub servers will:
1. Spin up an `ubuntu-latest` runner.
2. Set up Java 17 and download Maven dependencies.
3. Dynamically inject your repository secrets to generate the `.env` file securely.
4. Install Playwright Linux dependencies natively via `npx`.
5. Execute the tests in headless mode.
6. Upload the HTML Report and Failure Screenshots as downloadable Artifacts.

## 📸 Screenshots Section
When a test fails, screenshots are instantly captured and stored in the `reports/screenshots/` directory. If running in CI/CD, these are uploaded to the GitHub Actions Artifacts tab for easy download by the QA team.

## 🤖 Example AI Diagnosis
*An example of terminal output when a timeout occurs during a test run:*
```text
=======================================================
❌ TEST FAILED: testInvalidLogin()
📸 Screenshot saved to: reports/screenshots/testInvalidLogin__.png
🧠 Asking AI for root-cause analysis (via OpenRouter)...

🤖 AI DIAGNOSIS:
The timeout occurred because Playwright navigated to the login page but the page did not emit a "load" state within 30 seconds. This is typically caused by slow network conditions or single-page application routing. To resolve, explicitly set `waitUntil: WaitUntilState.DOMCONTENTLOADED` in the navigation call to ensure the page has loaded sufficiently before interacting with elements.
=======================================================
```

## ⚠️ Limitations
- **Sandbox Flakiness:** Tests rely on public sandbox websites (like Heroku), which may occasionally drop requests or throttle IP addresses from shared CI/CD datacenters.
- **AI Rate Limiting:** The AI diagnosis relies on free-tier OpenRouter models, which may occasionally face rate limiting during high-traffic periods.

## 🚀 Future Improvements
- **Data-Driven Testing (DDT):** Implement JSON or CSV data providers to execute tests across multiple data sets automatically.
- **Parallel Execution:** Configure JUnit 5 and Maven Surefire to run UI and API tests in parallel to drastically reduce execution time.
- **Cross-Browser Matrix:** Expand the CI/CD pipeline to run tests simultaneously across Chromium, Firefox, and WebKit.
