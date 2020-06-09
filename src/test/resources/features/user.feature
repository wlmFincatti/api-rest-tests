@user
Feature: Api user

  Background:
    Given i have user id 1

  Scenario: Test to find one user
    When i make a request "GET"
    Then receive status code 200
    And validate the user name "william da silva" and age 31
    And validate schema json "jsonSchemas/user.json"

  Scenario: Test to delete user
    When i make a request "DELETE"
    Then receive status code 204

  Scenario: Test to user not found
    When i make a request "GET"
    Then receive status code 404
    And validate message "User not found with id 1"

  Scenario: Test to user not found to delete
    When i make a request "DELETE"
    Then receive status code 404
    And validate message "user not exist to delete with id 1"

