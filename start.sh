mvn clean package -DskipTests

cd target
cp app.war /home/zeculesu/Downloads/wildfly-34.0.0.Final/standalone/deployments

cd /home/zeculesu/Downloads/wildfly-34.0.0.Final/bin
./standalone.sh
