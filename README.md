# DD2480-CI

Continuous Integration implementation, part of DD2480 Software Engineering Fundamentals (KTH, 2024)

## Configuration

Any repository which implements this CI has to provide a `test.sh` script in the root directory, which is then executed in order to test the repository.

## How to Run

Clone this repository and have Maven installed. This repository is also configured with certain tests for testing the CI itself, which can be executed by `mvn test`.

- To start the CI server, do the following in one terminal:

  1. Create a copy of `config.env-default` and rename it to `config.env`
  2. Fill in the fields of `config.env`
  3. Do `cd ci`
  4. Run `mvn exec:java`

- To make the local server externally visible using ngrok, do the following in a separate terminal:

  - Configure ngrok:

    1. `curl -LO --tlsv1 https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-linux-amd64.zip`
    2. `unzip ngrok-stable-linux-amd64.zip`
    3. [Register an account on ngrok to get your authentication token](https://dashboard.ngrok.com/get-started/your-authtoken)
    4. `./ngrok authtoken YOUR_TOKEN`

  - Run ngrok (in a separate terminal):

    1. `./ngrok http 8021`

- With the CI server running, add the public link as a Webhook to your GitHub repository and set the "Content type" to be `application/json`.

### Maven commands

Maven grants several useful commands that can be used to compile, test and package the project. All commands must be executed in the location of the `pom.xml` file, that is in `/ci`:

- To compile the project: `mvn compile`

- To test the project: `mvn test`

- To package the project: `mvn package`

The phases are executed sequantially, `mvn test` also compiles the project, and `mvn package` also compiles and tests the project.

Other useful phases can be found in the [Maven Introduction to Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).


## Repository structure

The repository has the following structure:

- ci

  - src

    - main\java\com\group21\ci

      - Config.java

      - ContinuousIntegrationServer.java

      - RepositoryInfo.java

      - RepositoryTester.java

      - StatusSender.java

    - test\java\com\group21\ci

      - ContinuousIntegrationServerTest.java


  - A `target` folder will appear here after building the project, containing the executables and jar files.

  - A `build_history` folder will appear while using the project, containing files about the commits

  - A `repos` folder will appear while using the project, containing files previous repos

  - pom.xml

## Supported Versions

Other versions can work but are not guaranteed to. The following versions were used during development and are guaranteed to work:

- Java: OpenJDK 17.0.9 (OpenJDK Runtime Envirnoment)

- Maven: Apache Maven 3.9.1 (Red Hat 3.9.1-3)

## Statement of Contributions

**Alex Gunnarsson**

- RepositoryTester
- Get and display build history
- Refactoring and improvement of POST-handler
- Reviewed:

**Anne Haaker**

- POST-handler and extract data
- RepositoryInformation class
- Clean log files from ANSI color codes
- Reviewed:


**Hugo Tricot**

- StatusSender sending commit status
- Implement maven
- Reviewed: 

**Juan Bautista Lavagnini Portela**

- RepositoryTester to work for both windows and linux
- Local CI
- Text sanitizer
- Reviewed:

## Way of working - Essence


## P+
