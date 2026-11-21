#!/usr/bin/env bash
# init.sh — install, build, test. Bahasa-agnostik.
#
# Sumber: Anthropic, "Effective Harnesses for Long-Running Agents".
# Tujuan: setiap sesi baru dimulai dengan environment yang DIKETAHUI bekerja.
#
# Isi INSTALL_CMD / BUILD_CMD / TEST_CMD di bawah, atau biarkan kosong
# supaya dideteksi otomatis.

set -uo pipefail

MODE="${1:-}"
INSTALL_CMD="${INSTALL_CMD:-}"
BUILD_CMD="${BUILD_CMD:-}"
TEST_CMD="${TEST_CMD:-}"

say() { printf '%s\n' "$*"; }
fail() { printf 'FAIL: %s\n' "$*" >&2; exit 1; }

detect() {
  if [ -f gradlew ]; then
    [ -z "$INSTALL_CMD" ] && INSTALL_CMD="./gradlew --version"
    [ -z "$BUILD_CMD" ] && BUILD_CMD="./gradlew assembleDebug"
    [ -z "$TEST_CMD" ] && TEST_CMD="./gradlew testDebugUnitTest"
    return
  fi
  if [ -f package.json ]; then
    [ -z "$INSTALL_CMD" ] && INSTALL_CMD="npm ci"
    [ -z "$TEST_CMD" ] && TEST_CMD="npm test"
    return
  fi
  if [ -f pyproject.toml ] || [ -f requirements.txt ]; then
    [ -z "$INSTALL_CMD" ] && INSTALL_CMD="python -m pip install -e ."
    [ -z "$TEST_CMD" ] && TEST_CMD="pytest -q"
    return
  fi
  if [ -f go.mod ]; then
    [ -z "$INSTALL_CMD" ] && INSTALL_CMD="go mod download"
    [ -z "$TEST_CMD" ] && TEST_CMD="go test ./..."
    return
  fi
  if [ -f Cargo.toml ]; then
    [ -z "$TEST_CMD" ] && TEST_CMD="cargo test"
    return
  fi
  if [ -f Makefile ] && grep -qE '^test:' Makefile; then
    [ -z "$TEST_CMD" ] && TEST_CMD="make test"
    return
  fi
  return
}

[ -n "$INSTALL_CMD$BUILD_CMD$TEST_CMD" ] || detect

[ -n "$TEST_CMD" ] || fail "no test command. Set TEST_CMD or add a known project file."

say "==> install"
if [ -n "$INSTALL_CMD" ]; then
  eval "$INSTALL_CMD" || fail "install"
else
  say "  (skipped)"
fi

say "==> build"
if [ -n "$BUILD_CMD" ]; then
  eval "$BUILD_CMD" || fail "build"
else
  say "  (skipped)"
fi

say "==> test"
eval "$TEST_CMD" || fail "test"

if [ "$MODE" = "--check" ]; then
  say ""
  say "Environment OK (check mode)."
  exit 0
fi

say ""
say "Environment OK."
say "  test: $TEST_CMD"
say ""
say "Contract drift, doc drift: bash scripts/verify-docs.sh"
