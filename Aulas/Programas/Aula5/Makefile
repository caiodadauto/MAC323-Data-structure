# ***************************************************************
#
# Makefile genérico para compilar programas em Java
#
# ***************************************************************

JFLAGS = -g
JC = javac-algs4
JAVA = java-algs4

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $<

clean:
	$(RM) *.class *~

path:
	echo $(PATH)
