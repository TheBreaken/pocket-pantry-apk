.PHONY: help sync format lint lint-fix test check build clean install

sync:
	./gradlew tasks

lint:
	./gradlew ktlintFormat
	./gradlew ktlintCheck detekt lint
	./gradlew lint

lint-fix:
	./gradlew ktlintCheck lintFix
	./gradlew lintFix

detekt:
	./gradlew detekt

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
