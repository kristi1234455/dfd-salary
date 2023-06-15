个人maven的setting.xml的配置参考 https://packages.aliyun.com/maven

打包到私服命令: mvn clean install org.apache.maven.plugins:maven-deploy-plugin:2.8:deploy -DskipTests -Pdev

打快照版的包,在命令后面加 -Pdev或-Ptest






