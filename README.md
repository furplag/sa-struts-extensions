# sa-struts-extensions
[![deprecated](https://img.shields.io/badge/deprecated-too%20stale%20to%20maintain-red.svg)](https://img.shields.io/badge/deprecated-too%20stale%20to%20maintain-red.svg)
Extensions for SAStruts-S2JDBC.

## Getting Start
Add the following snippet to any project's pom that depends on your project
```xml
<repositories>
  ...
  <repository>
    <id>sa-struts-extensions</id>
    <url>https://raw.github.com/furplag/sa-struts-extensions/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>
...
<dependencies>
  ...
  <dependency>
    <groupId>jp.furplag.sastruts</groupId>
    <artifactId>sa-struts-extensions</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>
</dependencies>
```

## License
Code is under the [Apache Licence v2](LICENCE).
