#!/bin/bash

# Script to update IMC message definitions from a provided IMC folder
# Usage: ./update-imc.sh <path-to-imc-folder>

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if IMC folder argument is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <path-to-imc-folder>"
    echo ""
    echo "The IMC folder should contain the IMC.xml message definitions file."
    echo ""
    echo "Example:"
    echo "  $0 ../imc"
    echo "  $0 /path/to/imc"
    exit 1
fi

IMC_DIR="$1"

# Convert to absolute path if relative
if [[ ! "$IMC_DIR" = /* ]]; then
    IMC_DIR="$(cd "$IMC_DIR" 2>/dev/null && pwd)" || {
        echo "Error: Cannot access directory '$1'"
        exit 1
    }
fi

# Check if directory exists
if [ ! -d "$IMC_DIR" ]; then
    echo "Error: Directory '$IMC_DIR' does not exist."
    exit 1
fi

# Check if IMC.xml exists in the directory
if [ ! -f "$IMC_DIR/IMC.xml" ]; then
    echo "Error: IMC.xml not found in '$IMC_DIR'"
    echo "The provided folder should contain the IMC.xml message definitions file."
    exit 1
fi

echo "Updating IMC message definitions from: $IMC_DIR"
echo ""

# Run the gradle generate task
cd "$SCRIPT_DIR"
./gradlew generate -PimcDir="$IMC_DIR"

echo ""
echo "IMC message definitions updated successfully."
echo "Generated files are in: $SCRIPT_DIR/src/generated/"
