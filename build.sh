wget http://www.plan-x.org/maven/plan-x/jars/koala-xmlstore-0.5.4.jar
mvn install:install-file -DgroupId=plan-x -DartifactId=koala-xmlstore -Dversion=0.5.4 -Dpackaging=jar -Dfile=./koala-xmlstore-0.5.4.jar
mvn compile

