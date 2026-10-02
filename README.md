<div align="center">
  <h1>🚀 AI-Powered Test Automation Platform</h1>
  <p>An enterprise-grade, self-diagnosing Web UI & REST API automation framework.</p>
</div>

---

## 📖 What is this?
This is a modern, highly scalable **Test Automation Framework** built from the ground up using **Java**, **Playwright**, and **JUnit 5**. 

Unlike standard testing suites, this framework features a custom-built **AI Diagnostic Engine** powered by OpenRouter. It doesn't just tell you *that* a test failed—it intercepts the failure, takes a screenshot, analyzes the stack trace, and explains exactly *why* it failed and *how* to fix it.

## 🎯 What problem does it solve?
In traditional QA automation, a failed test pipeline generates cryptic Java exceptions. Automation engineers often spend hours debugging false positives, network timeouts, or hidden DOM changes just to figure out what went wrong.

**This framework solves the "Triage Bottleneck" by:**
1. Eliminating flaky waits using Playwright's native auto-waiting architecture.
2. Automatically capturing visual evidence (screenshots) the exact millisecond a failure occurs.
3. Using Artificial Intelligence to translate complex stack traces into plain-English root-cause analysis, saving teams hundreds of debugging hours.
4. Integrating directly into a CI/CD pipeline, catching bugs before they ever reach production.

## ⚙️ How does it work?
The framework is built on a strict **Page Object Model (POM)** architecture and relies on several advanced integrations:
* **UI Automation:** Uses Playwright to simulate user interactions across modern browsers. It natively handles shadow DOMs, iframes, and dynamic content without hardcoded `Thread.sleep()` commands.
* **API Validation:** Uses Playwright's `APIRequestContext` to securely validate backend REST APIs.
* **AI Interception (`AiTestWatcher`):** A custom JUnit 5 Extension that listens for test failures. Upon failure, it securely bundles the error context and sends it to the **OpenRouter LLM API** to generate an immediate diagnostic report in the console.
* **Secure Configuration:** Zero hardcoded secrets. All environment variables and API keys are strictly managed via a `.env` configuration file.
* **Continuous Integration:** Fully containerized via **GitHub Actions** (`tests.yml`) to execute headlessly on Ubuntu cloud servers on every code push.

## 🛠️ Technology Stack
* **Language:** Java 17
* **Core Engine:** Microsoft Playwright
* **Test Runner:** JUnit 5 / Maven Surefire
* **AI Integration:** OpenRouter API (Nemotron/Llama LLMs)
* **CI/CD:** GitHub Actions
* **Reporting:** Maven Surefire HTML Plugin

## 🚀 How to Run & Evaluate

### Local Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/YourUsername/Test-Automation-Platform.git
   cd Test-Automation-Platform
   ```
2. **Configure your environment:**
   Copy `.env.example` to `.env` and add your secure variables (including your OpenRouter API Key for AI diagnostics).
   ```bash
   cp .env.example .env
   ```
3. **Download Playwright Browsers:**
   Install the necessary browser binaries (Chromium, Firefox, WebKit):
   ```bash
   npx playwright install --with-deps
   ```
4. **Execute the Suite:**
   Run all UI and API tests:
   ```bash
   mvn clean test
   ```
5. **View the Reports:**
   After execution, open the generated HTML dashboard to view the test matrix:
   ```bash
   start target/site/surefire-report.html
   ```
   *Any generated failure screenshots will be saved in `reports/screenshots/`.*

## ☁️ CI/CD Pipeline (GitHub Actions)
This project is configured for Continuous Integration. Every time code is pushed to the `main` branch:
1. GitHub provisions a secure Ubuntu cloud runner.
2. The pipeline dynamically injects repository secrets to generate the `.env` configuration.
3. Playwright natively installs Linux OS dependencies and browsers.
4. The test suite executes in `headless` mode.
5. HTML test reports and AI screenshots are packaged and uploaded as downloadable CI Artifacts.

---
*Built with ❤️ to demonstrate modern quality engineering and AI-augmented software testing.*
