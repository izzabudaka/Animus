JAVAC=javac
JVFLAGS = -Xlint:unchecked -Xlint:deprecation

JPATHS=-cp :./src/:./build/
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
