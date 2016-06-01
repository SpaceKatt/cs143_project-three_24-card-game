## 24 Card Game

A Java implementation of the card game "24", for my CS 143 class (Spr16, SCC).

#### Project Objective:

To create a GUI in which users may create accounts and keep track of their
score as they play the card game "24".

Four cards are dealt and the player must use four arithmetic operators (i.e.,
"+", "-", "\*", "/") to create an algebraic expression which evaluates to 24.

#### Features:

  - Redundant mathematical expressions are checked for and not counted as
      new solutions (e.g., a + b - c, and, a - (c - b), are not counted
      as two distinct answers).
  - Infix expressions are evaluated using two Generic Stacks.
  - Users may select, create, edit, and search for players.
  - User may login as Guest Player and choose to not record their statistics.
  - Players are stored in an XML database.
  - User may shuffle cards if they cannot find more solutions to the current
      hand.
  - Player statistics are displayed on the right side of the screen.
  - Player statistics may be printed.
  - Entire GUI may be printed.
  - High scores are recorded and kept.
  - Total games played are recorded and kept.
  - Player class inherits from Person class. (project requirement)
  - DeckOfCards class creates entire deck of images at startup and doesn't
      reload images every time the hand is shuffled.
  - Custom icon is displayed on every form.
  - Project Logo is displayed on every form.
  - Full ToolTip provided.
  - Project fully documented.
  - Splash-Screen on startup.

##### Contact:

    Thomas Kercheval -> spacekattpoispin@gmail.com

##### Notes:

  - This project is backed up on GitHub.
  - This project is licensed under GPL v3.0
  - This project was developed on Windows 10.
