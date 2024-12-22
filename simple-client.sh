#!/bin/bash

set -eo pipefail

error() {
  echo -e "[ERROR]: $1" > /dev/stderr
  exit 1
}

verify_command() {
  if [ "$(command -v "$1")" = '' ]; then
      error "Command '$1' not found. Please install it using your package manager and try again."
  fi
}

verify_command 'curl'
verify_command 'jq'

state=''
low=''
high=''
url='http://localhost:8080'

while getopts 's:h:l:u:' arg; do
  case "$arg" in
    s)
      state="$OPTARG"
      ;;
    
    h)
      high="$OPTARG"
      ;;
    
    l)
      low="$OPTARG"
      ;;
    
    u)
      url="$OPTARG"
      ;;
    
    *)
      error "Invalid argument.\nUsage $(basename "$0") [-s state] [-h high] [-l low] [-u url]"
      ;;
  esac
done

url_args=''

add_url_args() {
    if [ -z "$url_args" ]; then
        url_args="?$1"
    else
      url_args"$url_args&$1"  
    fi
}

if [ -n "$state" ]; then
    add_url_args "state=$state"
fi

if [ -n "$low" ]; then
    add_url_args "lowRevenue=$low"
fi

if [ -n "$high" ]; then
    add_url_args "highRevenue=$high"
fi

curl --request GET -sL \
     --header 'Content-Type: application/x-www-form-urlencoded' \
     --header "Accept: application/json" \
     --url "${url}/api/lead${url_args}" | jq