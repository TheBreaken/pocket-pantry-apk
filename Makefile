.PHONY: help sync format lint lint-fix test check build clean install

sync:
	./gradlew tasks

lint:
	./gradlew ktlintFormat
	./gradlew ktlintCheck lint
	./gradlew lint

lint-fix:
	./gradlew ktlintCheck lintFix
	./gradlew lintFix

test:
	./gradlew testDebugUnitTest

check:
	./gradlew check

build:
	./gradlew assembleDebug

clean:
	./gradlew clean

install:
	./gradlew installDebug
