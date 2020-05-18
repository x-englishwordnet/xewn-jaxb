<p align="center">
<img width="256" height="256" src="images/xewn2.png">
</p>
<p align="center">
<img width="256" src="images/mavencentral.png">
</p>

### Java Architecture for XML Binding (JAXB) for XEWN XML schema


Provides access to Xtended English Wordnet (XEWN) *src* files through Plain Old Java Object (Pojo) classes. The Java objects are generated from the XSD typed schema by the JAXB compiler, effectively **binding the objects to their XML** representation (or *unmarshalling* XML to Java objects).

It uses the **JAXB** framework which is is one of the APIs in the Jakarta EE platform (formerly called Java EE).

Please refer to the test classes in the source files to get a glimpse as to how it can be used.

![ ](images/dataflow4.png  "JAXB")

GroupID and ArtifactID on Maven Central:

	<groupId>io.github.x-englishwordnet</groupId>
	<artifactId>xewn-jaxb</artifactId>
	<version>1.0.1</version>
	<name>XEWN JAXB</name>