#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

TIME_NOW=$(date +%c)
DEPLOY_LOG="$REPOSITORY/deploy.log"

IDLE_PORT=$(find_idle_port)

# 現在起動中のアプリケーションpidの確認
echo ">>> $TIME_NOW > $IDLE_PORT で実行中のアプリケーションPID確認" >> $DEPLOY_LOG
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

# プロセスが起動中であれば終了
if [ -z ${IDLE_PID} ]
then
  echo ">>> $TIME_NOW > 現在実行中のアプリケーションがありません" >> $DEPLOY_LOG
else
  echo ">>> $TIME_NOW > アプリケーション「 $IDLE_PID 」終了 " >> $DEPLOY_LOG
  kill -15 ${IDLE_PID}
  sleep 5
fi