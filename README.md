imcjava
=======

Java bindings for IMC.

# Generate Messages

IMC.xml resides in conf/imc/
imcjava supports multiple IMC versions.

Use `./gradlew generate` to create the bindings.

Use `./gradlew publishToMavenLocal` to test locally.

`mvn_update.sh` to publish the current build to omst's maven repository.