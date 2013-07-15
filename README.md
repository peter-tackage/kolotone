Explores motion, colour and sound for Android using PureData.

I've derived most of the PD interfacing from PlasmaSound: https://github.com/rjmarsan/PlasmaSound

Building
============

I would like to use the prebuilt JAR from the pd-for-android, lidpd submodule which contains the libpd Java dependencies and then install it into my repo like this -
Install libPD

> ls /jni/libpd/dist/libpd.jar
> mvn install:install-file -Dfile=./jni/libpd/dist/libpd.jar -DgroupId=org.puredata.core  -DartifactId=libpd -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar

But that JAR seems to be behind the latest source code. Both libpd has new methods which are used in pd-for-android but there are missing methods in the classes in the JAR file.

For example:

[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.1:compile (default-compile) on project pdcore: Compilation failure: Compilation failure:
[ERROR] .../git/pd-for-android/PdCore/src/org/puredata/android/service/PdService.java:[108,47] cannot find symbol
[ERROR] symbol:   method suggestSampleRate()
[ERROR] location: class org.puredata.core.PdBase
[ERROR] .../git/pd-for-android/PdCore/src/org/puredata/android/service/PdService.java:[119,45] cannot find symbol


Instead I have just copied the latest Java files from in from the submodule directory jni/libpd/java and compiled against that (removing the maven dependency).

The POM still has a dependency on the Android Midi artifact that I made (using Gradle!).

    <dependency>
      <groupId>com.noisepages.nettoyeur</groupId>
      <artifactId>androidmidi</artifactId>
      <version>1.0.0</version>
    </dependency>


pd-for-android must also compile against API Level 17, due to some of the low latency Audio parameters it uses.

*** Then ***

I found a project that builds the JAR -

git clone https://github.com/wivlaro/libpd-java-build.git

> cd libpd-java-build/
> git submodule init <--------- This is a reference to libpd module
> git submodule update
> make clean javalib
> ls -la libpd.jar

Then install this JAR into your repo (Don't reference the old one in the libpd module!) -

> mvn install:install-file -Dfile=./libpd.jar -DgroupId=org.puredata  -DartifactId=core-libpd -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar

*** BUT ***

The submodule references (which is a detached HEAD, a reference to a specific commit - not a branch) is still old in libpd-java-build project.

Just going back to copy it for now... this is a temporary measure!

I need to update the submodule in libpd-java-build, rebuild the JAR, copy that to libpd, make a pull request.

