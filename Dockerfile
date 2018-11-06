FROM airhacks/payara5
COPY ./target/trainingservice.war ${DEPLOYMENT_DIR}
