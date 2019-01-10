#!/bin/bash
set -e

PATH=$PATH:/opt/gocd/build

source /opt/gocd/build/java/buildlib.sh

echo "*** Promoting snapshot to release" >&2
promote_snapshot_to_release_gradle

if [ ! "x${GIT_REV}" != "x" ] && [ ! "x${VERSION}" != "x" ]; then
    echo "Error: promote_snapshot_to_release_gradle didn't set \$GIT_REV or \$VERSION" >&2
    exit 1;
fi

echo "*** Tagging JIRA" >&2
jira_tag_issues "${GIT_REV}" "${VERSION}"

git_push_all_changes

