#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <file_with_commands>"
    exit 1
fi

if [ ! -f "$1" ]; then
    echo "File not found!"
    exit 1
fi

while IFS= read -r command; do
    if [[ -z "$command" || "$command" =~ ^// ]]; then
        continue
    fi

    echo "Executing: $command"
    eval "$command"

    if [ $? -ne 0 ]; then
        echo "Error executing command: $command"
        exit 1
    fi
done < "$1"

echo "All commands executed successfully."
