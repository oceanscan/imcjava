imcjava
=======

Java bindings for IMC.

# Generating Messages

* IMC.xml resides in PROJECT_ROOT/..:
./gradlew generate

* IMC.xml resides in \<FOLDER\>:
./gradlew generate -PimcDir=\<FOLDER\>

# Deploying a new version

* Set the right (new) version in the *version* parameter of build.gradle file.
* run the script ```./mvn_update.sh```
