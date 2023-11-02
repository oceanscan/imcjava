imcjava
=======

Java bindings for IMC.

# Generating Messages

* IMC.xml resides in PROJECT_ROOT/..:
gradle generate

* IMC.xml resides in \<FOLDER\>:
gradle generate -PimcDir=\<FOLDER\>

# Deploying a new version

* Set the right (new) version in the *version* parameter of build.gradle file.
* run the script ```./mvn_update.sh```
