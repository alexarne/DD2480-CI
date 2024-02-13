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

    1. Download a zip file suitable for your operating system from [the ngrok website](https://ngrok.com/download)
    2. `sudo tar xvzf ~/Downloads/ngrok-v3-stable-linux-amd64.tgz -C /usr/local/bin`
    3. [Register an account on ngrok to get your authentication token](https://dashboard.ngrok.com/get-started/your-authtoken)
    4. `ngrok config add-authtoken YOUR_TOKEN`

  - Run ngrok (in a separate terminal):

    1. `ngrok http 8021`

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

## Implementation and unit-testing

The webhooks is triggered by a push event, which sends a POST request to the CI server with data about the latest push. Relevant data, for example the URL to clone the repository, the branch which the push was made to and the latest commit SHA, is extracted. The repository is then cloned from the extracted URL and the correct branch is checked out. A bash script, test.sh, is executed, in which the maven command 'mvn test' is run. Mvn test compiles the files and runs the tests. The exit status from these commands decide what commit status should be sent back as notification.

The implementation was tested both through unit testing, mock tests and user testing. For example, a mock-payload from the github webhook was used to test the extraction of data from the payload. Testing was also done through observation of the results. A commit was pushed to the repo, and the logs of the build history were looked at to determine if the repository was properly cloned and the tests run. The mvn test command outputs the build status (for example 'success') and shows the tests that were ran and if they passed or not. Similar tests were conducted to make sure the notification through commit statuses was correct, with both correct builds and builds with errors, that should not stop execution but return a 'failure' commit status.

## Statement of Contributions

**Alex Gunnarsson**

- RepositoryTester
- Get and display build history
- Refactoring and improvement of POST-handler

**Anne Haaker**

- POST-handler and extract data
- RepositoryInformation class
- Clean log files from ANSI color codes

**Hugo Tricot**

- StatusSender sending commit status
- Implement maven

**Juan Bautista Lavagnini Portela**

- RepositoryTester to work for both windows and linux
- Local CI
- Text sanitizer

## Essence - Team

We are somewhere between the "Collaborating" and "Performing" states. The group is actively working together and everyone mostly know what to do. Issues are self-assigned in an agile manner in order to make use of everyone's potential talents, such as some being more inclined towards frontend/backend work. The work process is not as smooth as it would have to be for us to be in the "Performing" state, with certain obstacles being uncovered due to improper planning and preparation for independent development. This leads to, for example, some backtracking where certain parts have to be reworked, which is what has to be fixed in order to get us to the next state. To further get to the "Adjourned" state, we would have to be completely finished with the project, which we almost are.

## P+
