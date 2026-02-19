#!/usr/bin/env bash

# Check for OMST credentials
GRADLE_PROPS="$HOME/.gradle/gradle.properties"
OMST_CREDS_SET=false

if [[ -n "$OMST_USER" && -n "$OMST_PASSWORD" ]]; then
    OMST_CREDS_SET=true
elif [[ -f "$GRADLE_PROPS" ]]; then
    if grep -q "omst.user" "$GRADLE_PROPS" && grep -q "omst.password" "$GRADLE_PROPS"; then
        OMST_CREDS_SET=true
    fi
fi

if [[ "$OMST_CREDS_SET" != "true" ]]; then
    echo "ERROR: OMST Maven credentials not configured."
    echo ""
    echo "Please configure credentials using one of these methods:"
    echo ""
    echo "1. Add to $GRADLE_PROPS:"
    echo "   omst.user=your-username"
    echo "   omst.password=your-password"
    echo ""
    echo "2. Set environment variables:"
    echo "   export OMST_USER=your-username"
    echo "   export OMST_PASSWORD=your-password"
    echo ""
    exit 1
fi

version=$(./gradlew printVersion -q | tail -1)

echo "Publishing IMCJava v$version to all Maven repositories..."
echo ""

echo "Publishing to Maven Local..."
./gradlew publishToMavenLocal

echo ""
echo "Publishing to OMST Maven Public Repository..."
./gradlew publishMavenJavaPublicationToOMSTPublicRepository

echo ""
echo "Publishing to OMST Maven Releases Repository..."
./gradlew publishMavenJavaPublicationToOMSTReleasesRepository

echo ""
echo "Publishing to GitHub Packages..."
./gradlew publishMavenJavaPublicationToGitHubPackagesRepository

echo ""
echo "Publishing to Local file repository..."
./gradlew publishMavenJavaPublicationToLocalRepository

echo ""
echo "Done publishing IMCJava v$version to all repositories."
