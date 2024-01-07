#!/usr/bin/env bash

REPOSITORY="/home/ec2-user/app/git"
PROJECT_NAME="kintai"
JAR_FILE=$(ls -tr $REPOSITORY/ | grep SNAPSHOT.jar | tail -n 1)

APP_LOG="$REPOSITORY/application.log"
ERROR_LOG="$REPOSITORY/error.log"
DEPLOY_LOG="$REPOSITORY/deploy.log"

TIME_NOW=$(date +%c)

# buildファイルコピー
echo ">>> $TIME_NOW > $JAR_FILE ファイルコピー" >> $DEPLOY_LOG
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

# jar 実行
echo ">>> $TIME_NOW > $JAR_FILE 実行" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo ">>> $TIME_NOW > 実行したプロセス： $CURRENT_PID" >> $DEPLOY_LOG