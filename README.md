imcjava
=======

Java bindings for IMC.

# Generate Messages

IMC.xml resides in conf/imc/
imcjava supports multiple IMC versions.

Use `./gradlew generate` to create the bindings.

# Publishing

## Test Locally

Use `./gradlew publishToMavenLocal` to test locally.

## Publish to GitHub Packages

To publish to GitHub Packages Maven repository:

```bash
export GITHUB_ACTOR=your-github-username
export GITHUB_TOKEN=your-github-token
./gradlew publish
```

Or publish only to GitHub Packages:

```bash
./gradlew publishAllPublicationsToGitHubPackagesRepository
```

Alternatively, you can set credentials in `~/.gradle/gradle.properties`:

```properties
gpr.user=your-github-username
gpr.key=your-github-token
```

## Using the Package

To use the published package in your project, add the following to your `build.gradle`:

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/oceanscan/imcjava")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'pt.lsts:imcjava:5.5.5-unify'
}
```

## Legacy Publishing

`mvn_update.sh` to publish the current build to omst's maven repository.