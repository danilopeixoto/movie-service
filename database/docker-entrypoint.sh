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

sed "/host=0.0.0.0/a alt-host=${ALT_HOST:-127.0.0.1}" -i transactor.properties
sed "s/host=0.0.0.0/host=${DATOMIC_HOST:-0.0.0.0}/" -i transactor.properties
sed "s/port=4334/port=${DATOMIC_PORT:-4334}/" -i transactor.properties
sed "s/# encrypt-channel=true/encrypt-channel=${ENCRYPT_CHANNEL-:true}/" -i transactor.properties

file_environment_variable "ADMIN_PASSWORD"
file_environment_variable "DATOMIC_PASSWORD"
file_environment_variable "OLD_ADMIN_PASSWORD"
file_environment_variable "OLD_DATOMIC_PASSWORD"

if [ -n "${ADMIN_PASSWORD}" ]; then
  sed "s/# storage-admin-password=/storage-admin-password=${ADMIN_PASSWORD}/" -i transactor.properties
fi

if [ -n "${DATOMIC_PASSWORD}" ]; then
  sed "s/# storage-datomic-password=/storage-datomic-password=${DATOMIC_PASSWORD}/" -i transactor.properties
fi

if [ -n "${OLD_ADMIN_PASSWORD}" ]; then
  sed "s/# old-storage-admin-password=/old-storage-admin-password=${OLD_ADMIN_PASSWORD}/" -i transactor.properties
fi

if [ -n "${OLD_DATOMIC_PASSWORD}" ]; then
  sed "s/# old-storage-datomic-password=/old-storage-datomic-password=${OLD_DATOMIC_PASSWORD}/" -i transactor.properties
fi

exec "$@"
