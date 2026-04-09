# IMCJava — Build, Generate & Publish

## Prerequisites

- JDK 11+
- Gradle wrapper included (`./gradlew`)

## Compile

```bash
./gradlew build
```

Source sets include `src/main/java`, `src/generated/java`, `src/sender/java`, and `src/test/java`.

## Regenerate IMC Messages

The code generator reads an `IMC.xml` definitions file and produces Java bindings under `src/generated/`.

Message definitions come from the [oceanscan/imc](https://github.com/oceanscan/imc) repository, included as a git submodule under `imc/`. After cloning, initialise it with:

```bash
git submodule update --init
```

**Using the default IMC submodule (`imc/`):**

```bash
./gradlew generate
```

**Using a custom IMC directory:**

```bash
./gradlew generate -PimcDir=/path/to/imc
```

Or use the helper script:

```bash
./update-imc.sh /path/to/imc
```

The target directory must contain an `IMC.xml` file. Archived versions are kept in `conf/imc/`.

## Publish

### Version

The current version is set in `build.gradle` (`version` property). Update it before publishing a new release.

Print the current version:

```bash
./gradlew printVersion -q
```

### Local (for testing)

```bash
./gradlew publishToMavenLocal
```

This installs the artifact into `~/.m2/repository`.

### GitHub Packages

Requires GitHub credentials via properties or environment variables:

```bash
export GITHUB_ACTOR=<username>
export GITHUB_TOKEN=<token>
./gradlew publishMavenJavaPublicationToGitHubPackagesRepository
```

A GitHub Actions workflow (`.github/workflows/publish.yml`) publishes automatically when a GitHub release is created or the workflow is manually triggered.

### OMST Maven Repositories

Requires OMST credentials in `~/.gradle/gradle.properties`:

```properties
omst.user=your-username
omst.password=your-password
```

Or as environment variables (`OMST_USER`, `OMST_PASSWORD`).

Publish to both OMST public and releases repositories:

```bash
./gradlew publishToOmstMaven
```

### All Repositories at Once

The `mvn_update.sh` script publishes to every configured repository (local, OMST, GitHub Packages):

```bash
./mvn_update.sh
```

### Maven Coordinates

```
groupId:    pt.lsts
artifactId: imcjava
```
