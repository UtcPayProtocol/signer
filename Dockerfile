FROM openjdk:8
EXPOSE 9980

ENV JAVA_PATH /home/java

VOLUME /tmp
ADD /PayTool.jar ${JAVA_PATH}/PayTool.jar

RUN bash -c 'touch ${JAVA_PATH}/PayTool.jar'

ENTRYPOINT ["java","-jar","/home/java/PayTool.jar"]
