Here's a basic template for your `CONTRIBUTING.md` file. This guide will help developers understand how to contribute to your project, covering everything from setting up the development environment to making a pull request.

---

# Contributing to Hijri Date Picker

Thank you for your interest in contributing to our project! We appreciate all types of contributions, from code improvements to bug reports and documentation updates. To make sure the process is smooth for everyone, please take a moment to review the following guidelines.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [How to Contribute](#how-to-contribute)
3. [Development Environment Setup](#development-environment-setup)
4. [How to Report a Bug](#how-to-report-a-bug)
5. [How to Suggest a Feature](#how-to-suggest-a-feature)
6. [Submitting a Pull Request](#submitting-a-pull-request)
7. [Style Guidelines](#style-guidelines)

---

## Code of Conduct

Please review our [Code of Conduct](CODE_OF_CONDUCT.md) to understand how we expect everyone to behave in the community.

## How to Contribute

You can contribute in several ways:
- Reporting bugs
- Suggesting features
- Improving documentation
- Writing or refactoring code
- Reviewing and testing pull requests

## Development Environment Setup

To contribute to the codebase, you need to set up your local development environment:

1. **Fork the repository**:
   - Go to the repository on GitHub and click the **Fork** button.

2. **Clone your fork**:
   ```bash
   git clone https://github.com/your-username/hijri-date-picker.git
   cd hijri-date-picker
   ```

3. **Install dependencies**:
   - Ensure you have [JDK 21](https://www.azul.com/downloads/?package=jdk) installed.
   - For Android Studio users, import the project and sync the Gradle dependencies.

4. **Run the project**:
   - For Android:
     ```bash
     ./gradlew assembleDebug
     ```

5. **Create a new branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## How to Report a Bug

If you find a bug, please open an issue [here](https://github.com/your-repo/issues). When reporting a bug, include:
- A clear and descriptive title.
- Steps to reproduce the issue.
- The expected result and what actually happens.
- The version of the app or library you are using.
- Any error logs or screenshots that might help diagnose the issue.

## How to Suggest a Feature

If you have an idea for a new feature or an improvement, open a feature request [here](https://github.com/your-repo/issues) and provide:
- A clear and descriptive title.
- A detailed explanation of the feature.
- How the feature would benefit the project.
- Any alternatives you’ve considered.

## Submitting a Pull Request

Before submitting your pull request (PR), make sure your changes follow these steps:

1. **Fork and clone** the repository (see the [Development Setup](#development-environment-setup)).
2. **Create a new branch** for your changes:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**.
4. **Write tests** for any new features.
5. **Commit your changes** following the [commit message guidelines](#commit-message-guidelines).
6. **Push your changes** to your branch:
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Submit a pull request**:
   - Go to the repository on GitHub and click **New pull request**.
   - In the pull request description, explain what changes were made and why.
   - Link any relevant issues (e.g., `Closes #123`).

8. **Wait for approval**: Your pull request will be reviewed, and feedback might be provided. Once approved, it will be merged into the `main` branch.

## Style Guidelines

We aim to keep the codebase consistent. Please follow these guidelines:

- **Code formatting**: Ensure code is clean and follows our coding style. We recommend using the built-in formatter in your IDE (Android Studio, IntelliJ).
- **Kotlin guidelines**: Follow [Kotlin’s official style guide](https://kotlinlang.org/docs/coding-conventions.html).
- **Document your code**: Add comments for complex logic or anything non-obvious.
- **Write unit tests**: If you are adding or modifying features, write unit tests to ensure everything works as expected.

## Commit Message Guidelines

- **Format**: `<type>(scope): <message>`.
- **Types**:
  - `feat`: New feature
  - `fix`: Bug fix
  - `docs`: Documentation changes
  - `style`: Code style or formatting
  - `refactor`: Code refactoring
  - `test`: Adding or updating tests

Example:
```bash
git commit -m "feat(date-picker): add support for year range customization"
```

---

Thank you for your contributions and helping improve the project!

---

### Additional Resources

- [GitHub Documentation](https://docs.github.com/en)
- [Effective Pull Requests](https://github.com/blog/1943-how-to-write-the-perfect-pull-request)
