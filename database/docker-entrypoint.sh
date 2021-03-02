#!/bin/bash

set -eu

file_environment_variable() {
  local variable="$1"
  local file_variable="${variable}_FILE"
  local value="${2:-}"

  if [ "${!variable:-}" ]; then
    value="${!variable}"
  elif [ "${!file_variable:-}" ]; then
    value="$(< "${!file_variable}")"
  fi

  unset "$file_variable"

  export "$variable"="$value"
}

if [ "$DATOMIC_HOST" ]; then
  sed "s/host=0.0.0.0/host=${DATOMIC_HOST}/" -i transactor.properties
fi

if [ "$DATOMIC_PORT" ]; then
  sed "s/port=4334/port=${DATOMIC_PORT}/" -i transactor.properties
fi

file_environment_variable "ADMIN_PASSWORD"
file_environment_variable "DATOMIC_PASSWORD"

sed "s/# storage-admin-password=/storage-admin-password=${ADMIN_PASSWORD}/" -i transactor.properties
sed "s/# storage-datomic-password=/storage-datomic-password=${DATOMIC_PASSWORD}/" -i transactor.properties

exec "$@"
