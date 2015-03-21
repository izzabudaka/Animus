JAVAC=javac
JVFLAGS = -Xlint:unchecked -Xlint:deprecation

JPATHS=-cp :./src/:./bin/mysql-connector-java-5.1.12-bin.jar:./build/
JUNITPATHS=-cp :./src/:./bin/hamcrest-core-1.3.jar:./bin/junit-4.12.jar:./bin/mysql-connector-java-5.1.12-bin.jar:./build/

src_dir = src
build_dir = build

ibms:
	$(JAVAC) $(JVFLAGS) $(JPATHS) -d $(build_dir) $(src_dir)/animus/*.java

clean: clean-ibms, clean-dbi

clean-ibms:
	rm -f $(build_dir)/animus/*.class

run:
	java $(JPATHS) animus.animus
