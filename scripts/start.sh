#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY="/home/ec2-user/app/git"
PROJECT_NAME="kintai"

JAR_FILE=$(ls -tr $REPOSITORY/*.jar | grep SNAPSHOT.jar | tail -n 1)
echo "> JAR FILE : $JAR_FILE" >> $DEPLOY_LOG

APP_LOG="$REPOSITORY/application.log"
ERROR_LOG="$REPOSITORY/error.log"
DEPLOY_LOG="$REPOSITORY/deploy.log"

TIME_NOW=$(date +%c)

# buildファイルコピー
echo ">>> $TIME_NOW > $JAR_FILE ファイルコピー" >> $DEPLOY_LOG
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

IDLE_PROFILE=$(find_idle_profile)

# jar 実行
echo ">>> $TIME_NOW > $JAR_FILE を${IDLE_PROFILE} で実行" >> $DEPLOY_LOG
nohup java -jar \
  -Dspring.config.location=classpath:/application.yml,classpath:/application-$IDLE_PROFILE.yml \
  -Dspring.profiles.active=$IDLE_PROFILE \
  $REPOSITORY/$JAR_FILE > $APP_LOG 2> $ERROR_LOG &

sleep 20
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
echo ">>> $TIME_NOW > 実行したプロセス PID： $CURRENT_PID" >> $DEPLOY_LOG