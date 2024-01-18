# Playwright Java TestNG Automation PurrWeb Project

## Installation Steps

In order to use the framework:

- Fork the repository.
- Clone, i.e, download your copy of the repository to your local machine 
- Import the project in IntelliJ IDEA.

- Create a copy of the file 'config.properties.TEMPLATE' located in the 'src/test/resources' folder.
- Place this copy in the same location, removing the '.TEMPLATE' from its name.
- In the newly created file 'config.properties', specify the username and password.

## Languages and Frameworks

The project uses the following:

- *[Java 17](https://openjdk.org/projects/jdk/17/)* as the programming language.
- *[Maven](https://maven.apache.org/index.html)* as a builder and manager of project.
- *[Playwright](https://playwright.dev/)* as the framework for Web Testing and Automation.
- *[TestNG](https://testng.org/doc/)* as the Testing Framework.
- *[IntelliJ IDEA](https://www.jetbrains.com/idea/)* as the Integrated Development Environment.

## Project Structure

The project is structured as follows:

```bash
📦:.
└───test
    └───java

```
####
* Generate local <b>allure report</b>: `mvn allure:serve`
####
* Check the last allure report on CI: [Allure Report] (https://dragonpwjur.github.io/DragonJur/)

