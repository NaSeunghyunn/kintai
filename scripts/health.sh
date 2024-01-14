#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

REPOSITORY="/home/ec2-user/app/git"
DEPLOY_LOG="$REPOSITORY/deploy.log"

TIME_NOW=$(date +%c)

IDLE_PORT=$(find_idle_port)

echo ">>> ${TIME_NOW} > Health Check Start!" >> $DEPLOY_LOG
echo ">>> ${TIME_NOW} > IDLE PORT: ${IDLE_PORT}" >> $DEPLOY_LOG
echo ">>> ${TIME_NOW} > curl -s http://localhost:${IDLE_PORT}/profile " >> $DEPLOY_LOG
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo $RESPONSE | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $UP_COUNT >= 1 (realの存在チェック)
    echo ">>> ${TIME_NOW} > Health check 成功" >> $DEPLOY_LOG
    switch_proxy # proxy設定を変更する（switch.shで実行）
    break
  else
    echo ">>> ${TIME_NOW} > Health checkの応答が不正か実行中ではありません。" >> $DEPLOY_LOG
    echo ">>> ${TIME_NOW} > Health check : ${RESPONSE}" >> $DEPLOY_LOG
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo ">>> ${TIME_NOW} > Health check 失敗"
    echo ">>> ${TIME_NOW} > Deployを終了します。"
    exit 1
  fi

  echo ">>> ${TIME_NOW} > Health check 接続失敗"
  echo ">>> ${TIME_NOW} > 再起動中。。。"
  sleep 10
done