#!/usr/bin/env bash
# verify-docs.sh — invariant agar dokumen tidak basi. Bahasa-agnostik.
#
# Sumber: Claude Code docs (enforce via hook, bukan prompt),
# HumanLayer (back-pressure), OpenAI (custom linter).
#
# Konfigurasi:
#   SRC_GLOB   direktori source (default: src)
#   DEP_FILES  file dependency (default: package.json pyproject.toml go.mod Cargo.toml)
#   BASE_REF   ref pembanding (default: origin/main)

set -uo pipefail

SRC_GLOB="${SRC_GLOB:-app/src}"
DEP_FILES="${DEP_FILES:-gradle/libs.versions.toml app/build.gradle.kts build.gradle.kts settings.gradle.kts gradle/wrapper/gradle-wrapper.properties}"
BASE="${BASE_REF:-origin/main}"

if ! git rev-parse --verify "$BASE" >/dev/null 2>&1; then
  if git rev-parse --verify HEAD~1 >/dev/null 2>&1; then
    BASE="HEAD~1"
  else
    echo "verify-docs: no base ref, skipping (first commit)."
    exit 0
  fi
fi

changed() { git diff --name-only "$BASE"...HEAD; }
fail=0
err() {
  echo "FAIL: $1" >&2
  echo "  fix: $2" >&2
  fail=1
}

# --- 1. source changed => spec changed ------------------------------------
echo "==> source/spec pairing"
if changed | grep -qE "^${SRC_GLOB}/"; then
  if ! changed | grep -qE '^docs/specs/'; then
    err "source changed but docs/specs/ did not" \
        "update the feature spec under docs/specs/<feature>/"
  fi
fi

# --- 2. dependency changed => ADR ----------------------------------------
echo "==> dependency/ADR pairing"
for f in $DEP_FILES; do
  if git diff "$BASE"...HEAD -- "$f" 2>/dev/null | grep -qE '^\+[^+]'; then
    if ! changed | grep -qE '^docs/decisions/'; then
      err "$f changed but no ADR was added" \
          "add docs/decisions/NNNN-<slug>.md explaining why"
    fi
    break
  fi
done

# --- 3. env var => .env.example ------------------------------------------
echo "==> env var pairing"
if git diff "$BASE"...HEAD -- "$SRC_GLOB" 2>/dev/null \
   | grep -qE '^\+.*(process\.env\.|os\.environ|os\.getenv|getenv\(|std::env::var|BuildConfig\.)'; then
  if ! changed | grep -qE '^\.env\.example$'; then
    err "a new env var was used but .env.example was not updated" \
        "add the variable (without a real value) to .env.example"
  fi
fi

# --- 4. feature marked done => evidence ----------------------------------
echo "==> feature evidence"
if changed | grep -qE '^feature_list\.json$'; then
  if ! python3 - <<'PY'
import json, sys
try:
    data = json.load(open("feature_list.json"))
except Exception as e:
    print(e); sys.exit(1)
bad = [f.get("id") for f in data.get("features", [])
       if f.get("passes") and not f.get("evidence")]
sys.exit(1 if bad else 0)
PY
  then
    err "a feature is marked passes:true without evidence" \
        "run converge and paste the evidence into feature_list.json"
  fi
fi

# --- 5. spec has open questions => do not implement ----------------------
echo "==> spec clarity"
for s in docs/specs/*/spec.md; do
  [ -f "$s" ] || continue
  if grep -q '\[NEEDS CLARIFICATION' "$s"; then
    if [ -f "$(dirname "$s")/tasks.md" ]; then
      err "$s still has [NEEDS CLARIFICATION] but tasks.md exists" \
          "resolve the questions before implementation"
    fi
  fi
done

# --- 6. progress updated --------------------------------------------------
echo "==> session memory"
if ! changed | grep -qE '^progress\.md$'; then
  err "progress.md was not updated" \
      "append a session entry (see progress.md format)"
fi

echo ""
if [ "$fail" -ne 0 ]; then
  echo "verify-docs: FAILED. Do not mark the task done yet." >&2
  exit 1
fi
echo "verify-docs: OK"
