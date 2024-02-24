#!/bin/sh
SOURCE=$1
rsync --archive --update --delete --verbose --human-readable \
  --exclude "sync-from.sh" \
  --exclude "sync-to.sh" \
  --exclude ".git" \
  --exclude "*lockfile" \
  "$SOURCE/" .
