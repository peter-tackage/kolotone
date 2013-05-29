Explores motion, colour and sound for Android using PureData.

I've derived most of the PD interfacing from PlasmaSound: https://github.com/rjmarsan/PlasmaSound

Installation
============

To install PDCore, clone pd-for-android and run

Use Gradle to build AndroidMidi (update Android tools version to 17)

> gradle build

Then install into local Maven repository

> mvn install:install-file -Dfile=./build/libs/AndroidMidi.aar -DgroupId=com.noisepages.nettoyeur -DartifactId=androidmidi -Dversion=1.0.0 -Dpackaging=aar

Then (after updating PdCore Gradle build to reference the local Maven repo)

> mvn install:install-file -Dfile=./PdCore/jni/libpd/dist/libpd.jar -DgroupId=org.puredata.core -DartifactId=pdcore -Dversion=1.0.0 -Dpackaging=jar



