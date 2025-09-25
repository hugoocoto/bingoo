compile:
	javac source/*.java

run: compile
	kitty --hold -d "${PWD}" -e java source.Client &
	kitty --hold -d "${PWD}" -e java source.Client &
	kitty --hold -d "${PWD}" -e java source.Client &
	kitty --hold -d "${PWD}" -e java source.Client &
	java source.Server
