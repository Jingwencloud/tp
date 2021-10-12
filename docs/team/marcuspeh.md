---
layout: page
title: Marcus Peh's Project Portfolio Page
---

### Project: Around the World in $80

Around the World in $80 is a desktop application that splits bills between different contacts. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project. [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=marcuspeh&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

### Features
* **New Feature**: Added the ability to change between contacts and groups view.
  * What it does: Allows the user to see groups / contacts with a command.
  * Justification: This feature is crucial as the user should be able to view the groups and contacts created.
  * Highlights: This implementation added in a new command `groups` to see all groups. It changes the command `list` to `contacts`. `contacts` allows the user to see all contacts.
  * RepoSense: [link](https://app.codecov.io/gh/AY2122S1-CS2103T-F13-1/tp/compare/121)

* **Enhancements to existing features**:
  * Updated the UI interface layout. [\#121](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/121)
  * Added in buttons to toggle between contacts page and group page [\#121](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/121)

### Others
* **Project management**
  * Managed releases: -

* **Documentation**:
  * User Guide:
    * Added documentation for the features `view groups` and `expenses`
  * Developer Guide:
    * Added use cases for `view groups`, `find person` and `view expenses`.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#113](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/113), [\#116](https://github.com/AY2122S1-CS2103T-F13-1/tp/pull/116)
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())
