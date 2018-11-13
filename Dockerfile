FROM iselisa/wildfly:14.0.0

COPY src/main/webapp/WEB-INF/lib/fit_16.60.0.jar $INSTALL_DIR/
COPY target/trainingservice.war $DEPLOYMENT_DIR
