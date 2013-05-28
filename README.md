Explores motion, colour and sound.

Uses PureData.

For now, it leans very heavily on PlasmaSound: https://github.com/rjmarsan/PlasmaSound

To install PDCore, clone pd-for-android and run

mvn install:install-file -Dfile=./PdCore/jni/libpd/dist/libpd.jar -DgroupId=org.puredata.core -DartifactId=pdcore -Dversion=1.0.0 -Dpackaging=jar^C

