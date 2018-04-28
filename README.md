# JFugue 5.0.9

* this is a Mavenized version of JFugue (http://www.jfugue.org/)

## Maven

### Step 1. Add the JitPack repository to your *pom.xml* file

	<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>

### Step 2. Add the JFugue dependency

	<dependency>
		<groupId>com.github.JensPiegsa</groupId>
		<artifactId>jfugue</artifactId>
		<version>5.0.9</version>
	</dependency>

## Gradle

### Step 1. Add JitPack in your root *build.gradle* at the end of repositories

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Step 2. Add the JFugue dependency

	dependencies {
		implementation 'com.github.JensPiegsa:jfugue:5.0.9'
	}
