#!/bin/bash

# Java ENV 【根据实际路径修改】
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.1.jdk/Contents/Home
export JRE_HOME=${JAVA_HOME}/jre

# Apps Info
# 应用存放地址【根据实际路径修改】
APP_HOME=/Users/xxxx/code/applications
# 应用名称
APP_NAME=$1

# Shell Info

# 使用说明，用来提示输入参数
usage() {
    echo "Usage: ./xxx.sh [APP_NAME] [start|stop|restart|status]"
    exit 1
}

# 检查程序是否在运行
is_exist(){
        # 获取PID
        # shellcheck disable=SC2009
        PID=$(ps -ef |grep "${APP_NAME}" | grep -v $0 |grep -v grep |awk '{print $2}')
        # -z "${pid}"判断pid是否存在，如果不存在返回1，存在返回0
        if [ -z "${PID}" ]; then
                # 如果进程不存在返回1
                return 1
        else
                # 进程存在返回0
                return 0
        fi
}

# 定义启动程序函数
start(){
        is_exist
        # shellcheck disable=SC2181
        if [ $? -eq "0" ]; then
                echo "${APP_NAME} is already running, PID=${PID}"
        else
                nohup ${JRE_HOME}/bin/java -jar ${APP_HOME}/"${APP_NAME}" >/dev/null 2>&1 &
                # shellcheck disable=SC2116
                PID=$(echo $!)
                echo "${APP_NAME} start success, PID=$!"
        fi
}

# 停止进程函数
stop(){
        is_exist
        # shellcheck disable=SC2181
        if [ $? -eq "0" ]; then
                kill -9 "${PID}"
                echo "${APP_NAME} process stop, PID=${PID}"
        else
                echo "There is not the process of ${APP_NAME}"
        fi
}

# 重启进程函数
restart(){
        stop
        start
}

# 查看进程状态
status(){
        is_exist
        if [ $? -eq "0" ]; then
                echo "${APP_NAME} is running, PID=${PID}"
        else
                echo "There is not the process of ${APP_NAME}"
        fi
}

case $2 in
"start")
        start
        ;;
"stop")
        stop
        ;;
"restart")
        restart
        ;;
"status")
       status
        ;;
	*)
	usage
	;;
esac
exit 0