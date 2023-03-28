CURRENT_PID=$(pgrep -f socialdiary.api)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

echo "> socialdiary.api 배포"
cd /var/lib/jenkins/workspace
sudo cp ./application.properties ./server/build/libs/application.properties
sudo nohup java -jar ./server/build/libs/socialdiary.api-0.0.1-SNAPSHOT.jar &
