Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition - Trying hard
    When I add 4 and 5
    Then the result is 9

  Scenario: Substraction - Trying enough
    When I substract 7 to 2 
    Then the result is 5

  Scenario Outline: Several additions - trying several times
    When I add <a> and <b>
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |
