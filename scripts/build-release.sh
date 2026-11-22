#!/usr/bin/env bash
# Build a signed release APK (and AAB) and archive the APK into seniorhigh-script/android-release-apk.
# Requires keystore.properties (see keystore.properties.template).
set -euo pipefail
cd "$(dirname "$0")/.."

if [[ ! -f keystore.properties ]]; then
  echo "keystore.properties not found. Copy keystore.properties.template and fill in the passwords." >&2
  exit 1
fi

./gradlew --quiet clean assembleRelease bundleRelease

VERSION_NAME=$(sed -nE 's/^[[:space:]]*versionName = "([^"]+)".*/\1/p' app/build.gradle)
VERSION_CODE=$(sed -nE 's/^[[:space:]]*versionCode = ([0-9]+).*/\1/p' app/build.gradle)
APK=app/build/outputs/apk/release/app-release.apk
AAB=app/build/outputs/bundle/release/app-release.aab

BUILD_TOOLS=$(ls -d "${ANDROID_HOME:-$HOME/Library/Android/sdk}"/build-tools/* | sort -V | tail -1)
"$BUILD_TOOLS/apksigner" verify --print-certs "$APK" | head -3

ARCHIVE_DIR=../seniorhigh-script/android-release-apk
mkdir -p "$ARCHIVE_DIR"
cp "$APK" "$ARCHIVE_DIR/ver_${VERSION_NAME}_build_${VERSION_CODE}.apk"
cp "$AAB" "$ARCHIVE_DIR/ver_${VERSION_NAME}_build_${VERSION_CODE}.aab"
echo "Signed APK: $ARCHIVE_DIR/ver_${VERSION_NAME}_build_${VERSION_CODE}.apk"
echo "Signed AAB: $ARCHIVE_DIR/ver_${VERSION_NAME}_build_${VERSION_CODE}.aab"
