#!/usr/bin/env bash

REPOSITORY="/home/ec2-user/app/git"
PROJECT_NAME="kintai"

DEPLOY_LOG="$REPOSITORY/deploy.log"

TIME_NOW=$(date +%c)

# 現在起動中のアプリケーションpidの確認
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

# プロセスが起動中であれば終了
if [ -z $CURRENT_PID ]; then
  echo ">>> $TIME_NOW > 現在実行中のアプリケーションがありません" >> $DEPLOY_LOG
else
  echo ">>> $TIME_NOW > アプリケーション「 $CURRENT_PID 」終了 " >> $DEPLOY_LOG
  kill -15 $CURRENT_PID
fi