rm *.jar
gradle clean
gradle build
mv build/libs/6.Assembler-1.0-SNAPSHOT.jar ./
gradle clean
