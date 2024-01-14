#!/usr/bin/env bash

ABSPATH=${readlink -f $0}
ABSDIR=${dirname ${ABSPATH}}
source ${ABSDIR}/profile.sh

DEPLOY_LOG="$ABSDIR/../logs/deploy.log"

TIME_NOW=$(date +%c)

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo ">>> ${TIME_NOW} > 切り替えるPort: ${IDLE_PORT}" >> $DEPLOY_LOG
    echo ">>> ${TIME_NOW} > Portを切り変えます。" >> $DEPLOY_LOG
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo ">>> ${TIME_NOW} > nginx リロード" >> $DEPLOY_LOG
    sudo service nginx reload
}