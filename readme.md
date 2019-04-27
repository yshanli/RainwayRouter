# How to run this project

## 1.Install maven
## 2.At project root path, run maven install
    mvn install    
 You will find a jar package under project root/target/trainRoute-1.0-SNAPSHOT.jar
 
## 3. run jar with command "java -jar trainRoute-1.0-SNAPSHOT.jar [inputFileName]" 
    Example: 
    E:\code\trainRoute\target>java -jar trainRoute-1.0-SNAPSHOT.jar ..\input.txt
trainRoute-1.0-SNAPSHOT.jar will try read input.txt by default if you do not provide a input file name.    
Input file content looks like: 
    AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
