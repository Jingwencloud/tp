---
layout: page
title: Ramapriyan Srivatsan Purisai Devarajan's Project Portfolio Page
---

### Project: Around the World in $80

Around the World in $80 is a desktop application that allows group travellers to split bills between different contacts. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project. [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=ramapriyan&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=false&zFR=false)

### Features
* **New Feature**: Constructed the `deletegroup` feature
    * What it does: Allows the user to delete a group with a command.
    * Justification: This feature is crucial as the user should be able to delete groups once they are obsolete or if the user has made an error.
    * Highlights: This implementation added in a new command `deletegroup` to delete a group by name.
    * Contribution: Built the entire framework for the feature including command and parser classes.

* **New Feature**: Contributed to `creategroup` feature.
  * What it does: Allows the user to create a group with select members with a command.
  * Justification: This feature is crucial as the user should be able to create groups with select members based on their travels.
  * Highlights: This implementation added in a new command `creategroup` to create a group with select members.
  * Contribution: Adapted command format and parser. Contributed significantly to the logic for storage of groups.

* **New Feature**: Contributed to `deleteexpense` feature.
    * What it does: Allows the user to delete an expense with a command.
    * Justification: This feature is crucial as the user should be able to delete errant expense entries.
    * Highlights: This implementation added in a new command `deleteexpense` to delete an expense by index position on list shown on screen.
    * Contribution: Built the entire framework for the feature including command and parser classes.

* **New Feature**: Contributed to `calculatepayments` feature.
    * What it does: Provides the users with a set of easy transactions for them to settle their debts from the trip.
    * Justification: This feature is crucial as the users should be able to efficiently settle their debts in a manner that leaves no room for disputes, and error by manual computation.
    * Highlights: This implementation added in a new command `calculatepayments` to provide users with a number of suggested transactions to settle their debts from the trip.
    * Contribution: Built the entire framework for the feature including command and parser classes.

### Others
* **Project management**
    * Managed releases: -
    
* **Miscellaneous**
    * Created storage classes for expenses and groups.
    * Created UI classes for displaying of payments to be made via the `calculate payments` command.

* **Documentation**:
    * User Guide:
        * Documented `delete group`, `create group`, `delete expense` and `calculatepayments` features.
        * Added details of `Quick Start`.
        * Added details of `delete group`, `create group`, `delete expense`, `calculatepayments` and `help` to Command Summary table.
    * Developer Guide:
        * Added details for `creategroup`, `deletegroup`, `deleteexpense`, and `calculatepayments` features, including sequence and activity diagrams.
        * Added use cases for `delete group`, `delete expense`, `calculatepayments`, and `help`.
    * Created labels for issue-tracking and documentation.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#113](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/113), [\#121](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/121), [\#166](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/166)
    * Worked together with [@chrus-chong](https://github.com/chrus-chong) on group features.
