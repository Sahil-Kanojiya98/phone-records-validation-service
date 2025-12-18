# Guide: Importing Postman Tests and Running Validation for Phone Records Service

This guide will help you import the Postman API tests and validate the Phone Records REST API. Follow the steps below to complete the setup and testing process.

---

## 1. Import the Postman Collection

1. Open your [Postman](https://www.postman.com/) application.
2. Click on the **Import** button located at the top-left corner of Postman.
3. Navigate to the `postman` folder in your project directory which contains the `Postman_Records_API_Collection.json` file.
4. Select the `Postman_Records_API_Collection.json` file and click **Open** or **Import**.
5. Verify that the collection titled **Phone Records REST API** appears under the **Collections** tab in Postman.

---

## 2. Define the Environment Variable

1. Select the **Manage Environments** tab (gear icon in Postman).
2. Create a new environment and name it, e.g., `Phone Records Validation`.
3. Under the `Variable` column, add a **key** named `baseURL` and set its **value** to the API's base URL (e.g., `http://localhost:8080`).
4. Save the environment.

---

## 3. Run the Tests

1. Click on the **Collections** tab and select the **Phone Records REST API** collection.
2. Expand the collection and review the different requests prepared for testing:
    - **Create a valid phone record**
    - **Create a valid saved phone record (unique mobile number check)**
    - **Create an invalid phone record**
    - **Get all phone records**
    - **Get phone record by existing id**
    - **Get phone record by non-existing id**.
3. To run a specific test:
    - Select the test (e.g., `Create a valid phone record`) and click **Send** in the request editor.
    - Verify the response for each test case (e.g., HTTP status code, response body validation).
4. To run all tests together:
    - Navigate to the menu and click on the **Run** option to open the collection runner.
    - Choose the environment defined earlier (e.g., `Phone Records Validation`).
    - Click **Start Run** and observe the results for all test cases.

---

## 4. Interpret Test Results

1. Look for HTTP status code and response body for each endpoint:
    - **POST /api/phones**:
        - Valid Creation (200 OK or 201 Created)
        - Invalid Creation (400 Bad Request)
    - **GET /api/phones**:
        - All Records (200 OK)
        - Records by ID (200 OK or 404 Not Found)
2. Verify that each endpoint request meets its expected behavior.

---

## 5. Debugging Failed Tests

1. If a test fails, verify that:
    - The server hosting the API is running.
    - The `baseURL` is correctly set in your environment.
    - The request data in the body or headers is correctly formatted.
2. Refer to application logs or the server console for more details on the failure.

---

Happy testing!