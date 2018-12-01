FROM iselisa/wildfly:14.0.0

COPY src/main/webapp/WEB-INF/lib/fit_20.78.00.jar $INSTALL_DIR/
COPY target/trainingservice.war $DEPLOYMENT_DIR
