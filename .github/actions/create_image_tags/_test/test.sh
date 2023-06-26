#!/bin/bash

cd $(dirname "$0")/..

RED="\033[0;31m"
GREEN="\033[0;32m"
YELLOW="\033[0;33m"
NC="\033[0m"

failed=0
passed=0

test() {
  local expected_exit=
  local expected_result=

  local REF_NAME=
  local BUILD_NUMBER=
  local SEMVER=

  for args in $(echo "${@}" | tr ';' ' '); do {
   IFS="=" read -r -a arr <<< "${args}"
   declare "${arr[0]}"="${arr[1]/\%\%/ }"
  } done

  local check="input: '$2'\n"


  export REF_NAME=${REF_NAME}
  export BUILD_NUMBER=${BUILD_NUMBER}
  export SEMVER=${SEMVER}

  local actual_result=
  actual_result=$(./create_image_tags.sh)
  local actual_exit="$?"

  local pass=false
  if [[ -n "${expected_exit}" ]]; then
    check="${check}expected exit code: '${expected_exit}'\nactual:  '${actual_exit}'"
    [[ "${expected_exit}" == "${actual_exit}" ]] && pass=true
  fi

  if [[ -n "${expected_result}" ]]; then
    check="${check}expected result: '${expected_result}'\nactual: '${actual_result}'"
    [[ "${expected_result}" == "${actual_result}" ]] && pass=true
  fi

  if [[ "${pass}" == true ]]; then
    passed=$(($passed + 1))
    check="${check}\n${GREEN}result: pass${NC}"
  else
    failed=$(($failed + 1))
    check="${check}\n${RED}result: fail${NC}"
  fi

  echo >&2 -e "\n${check}"
}


main() {

  test "expected_exit=1"
  test "expected_exit=1" "REF_NAME="
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER="
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER="
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1"
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2"
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2-"
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2-0"

  test "expected_exit=0" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3"
  test "expected_exit=0" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3-SNAPSHOT"

  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3-"
  test "expected_exit=0" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3-rc.1"
  test "expected_exit=0" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3-rc.1.rxr.232"
  test "expected_exit=1" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3-rc.1.rxr.232-"

  test "expected_result=1.2.3-42" "REF_NAME=v5.0.0;BUILD_NUMBER=42;SEMVER=1.2.3"
  test "expected_result=1.2.3-42" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=1.2.3"
  test "expected_result=1.2.3-beta-42%%latest" "REF_NAME=development;BUILD_NUMBER=42;SEMVER=1.2.3"
  test "expected_result=1.2.3-feature-D603345-6-42%%1.2.3-feature-D603345-6-latest" "REF_NAME=feature/D603345-6-setup-shared-base-docker-images;BUILD_NUMBER=42;SEMVER=1.2.3"
  test "expected_result=1.2.3-bugfix-D603345-6-42%%1.2.3-bugfix-D603345-6-latest" "REF_NAME=bugfix/D603345-6-setup-shared-base-docker-images;BUILD_NUMBER=42;SEMVER=1.2.3"
  test "expected_result=1.2.3-hotfix-D603345-6-42%%1.2.3-hotfix-D603345-6-latest" "REF_NAME=hotfix/D603345-6-setup-shared-base-docker-images;BUILD_NUMBER=42;SEMVER=1.2.3"

  test "expected_result=6.11.11-42" "REF_NAME=main;BUILD_NUMBER=42;SEMVER=6.11.11"
  test "expected_result=6.11.11-rc-42%%6.11.11-rc-latest" "REF_NAME=release/milestone-4;BUILD_NUMBER=42;SEMVER=6.11.11"

  test "expected_result=6.11.11-dependabot-42%%6.11.11-dependabot-latest" "REF_NAME=dependabot/gradle/app/development/org.projectlombok-lombok-1.18.26;BUILD_NUMBER=42;SEMVER=6.11.11"

  echo "passed=${passed};failed=${failed}"

}

main

