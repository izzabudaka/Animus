JAVAC=javac
JVFLAGS = -Xlint:unchecked -Xlint:deprecation

JPATHS=-cp :./src/:./build/:./bin/sphinx4-core-1.0-SNAPSHOT.jar:./bin/sphinx4-data-1.0-SNAPSHOT.jar
JUNITPATHS=-cp :./src/:./build/

src_dir = src
build_dir = build

main:
	$(JAVAC) $(JVFLAGS) $(JPATHS) -d $(build_dir) $(src_dir)/animus/*.java

clean: clean-ibms, clean-dbi

clean-ibms:
	rm -f $(build_dir)/animus/*.class

run:
	java $(JPATHS) animus.Animus
