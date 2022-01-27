Kahoot fork of recurly-java-library
===================================

A fork of [Java Library for Recurly](https://github.com/killbilling/recurly-java-library)
, originally developed for [Kill Bill](http://killbill.io), 
an open-source subscription management and billing system. It works around limitations 
in the client which prevent it from connecting to our in-house recurly-emulator, which 
allows us to run our integration tests concurrently, without fear of being rate limited. 

Getting started
---------------

The library is distributed via [Nexus](https://nexus.jx.jenkinsx.kahoost.com/#admin/repository/repositories:maven-releases)

```
<dependency>
    <groupId>com.ning.billing</groupId>
    <artifactId>recurly-java-library</artifactId>
    <version>0.34.0</version>
</dependency>
```

To build the project, use maven:

    mvn clean install -Pdocker
