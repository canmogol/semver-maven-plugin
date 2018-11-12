Meta:

Narrative:
As a developer using semantic versioning for the project
I want to check if the feature branch's version is greater than the development branch's version
So that when I merge the feature branch to development branch it corresponds to semantic versioning

Scenario: check version of feature against development
Given a gitflow parameter map
Given the current branch is feature/issue-111-a-very-important-one
Given the target branch is master
Given the type is pom.xml
Given the directory is /Users/alicanmogol/projects/canmogol/semver-maven-plugin
Given the user is canmogol
Given the project is semver-maven-plugin
Given the url is https://raw.githubusercontent.com/$user/$project/$target/$type_file
Given a gitflow semver checker
When it checks current against target using parameter map
Then current version should be greater than the target version
