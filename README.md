imcjava
=======

Java bindings for IMC.

# Generate Messages

IMC.xml resides in conf/imc/
imcjava supports multiple IMC versions.

Use `./gradlew generate` to create the bindings.

# Publishing

## Publish to GitHub Packages

To publish to GitHub Packages, you need to authenticate with your GitHub credentials:

```bash
./gradlew publish -Pgpr.user=YOUR_GITHUB_USERNAME -Pgpr.token=YOUR_GITHUB_TOKEN
```

Or set environment variables:

```bash
export GITHUB_ACTOR=YOUR_GITHUB_USERNAME
export GITHUB_TOKEN=YOUR_GITHUB_TOKEN
./gradlew publish
```

This will publish the artifacts to: https://maven.pkg.github.com/oceanscan/imcjava

## Publish to Local Maven Repository

Use `./gradlew publishToMavenLocal` to test locally.

## Using the Published Package

Add the GitHub Packages repository to your `build.gradle`:

```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/oceanscan/imcjava")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'pt.lsts:imcjava:5.5.5-unify'
}
```

For Maven users, add to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/oceanscan/imcjava</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>pt.lsts</groupId>
        <artifactId>imcjava</artifactId>
        <version>5.5.5-unify</version>
    </dependency>
</dependencies>
```

And configure authentication in `~/.m2/settings.xml`:

```xml
<servers>
    <server>
        <id>github</id>
        <username>YOUR_GITHUB_USERNAME</username>
        <password>YOUR_GITHUB_TOKEN</password>
    </server>
</servers>
```