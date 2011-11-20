
all:
	@find . -name *.java | xargs javac

clean:
	@find . -name *.class | xargs rm

run: all
	java idme.Main

.PHONY: all

