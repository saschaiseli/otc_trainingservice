FROM iselisa/wildfly:14.0.0

COPY target/trainingservice.war $DEPLOYMENT_DIR
