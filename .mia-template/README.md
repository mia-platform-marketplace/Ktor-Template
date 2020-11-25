# Tag Project

## Run script

### Tag new project version

Note that this is a multi-module repository that means that the tag will be the same for all the modules.
It uses gradle, so we have implemented a gradle task to tag your version.

You need to be in the root folder of the project and run the following command:
```
./gradlew version --tag=[version]
```

### Push changes

Don't forget to push commit and tag:

```shell
git push
git push --tags
```

### Examples

Assuming your current version is `1.2.3`

|command   | result  |
|---|---|
|`./gradlew version --tag=1.2.3`   |`v1.2.3`   |