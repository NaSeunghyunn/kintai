#!/usr/bin/env bash

REPOSITORY="/home/ec2-user/app/git"
PROJECT_NAME="kintai"
JAR_FILE=$(ls -tr $REPOSITORY/ | grep SNAPSHOT.jar | tail -n 1)
echo "> JAR FILE : $JAR_FILE" >> $DEPLOY_LOG

APP_LOG="$REPOSITORY/application.log"
	@@ -15,9 +20,14 @@ TIME_NOW=$(date +%c)
echo ">>> $TIME_NOW > $JAR_FILE ファイルコピー" >> $DEPLOY_LOG
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

# jar 実行
echo ">>> $TIME_NOW > $JAR_FILE 実行" >> $DEPLOY_LOG
nohup java -jar $REPOSITORY/$JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
echo ">>> $TIME_NOW > 実行したプロセス PID： $CURRENT_PID" >> $DEPLOY_LOG