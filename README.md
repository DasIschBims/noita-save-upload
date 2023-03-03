# noita-save-upload

(heavily wip)
aims to archive and upload noita save files to google drive because steamcloud doesn't work with long runs

## Building

### Requirements
- gradle
- java 19

### Steps
1. Clone the repo
2. Run gradle task `clean shadowJar`
3. The jar will be in `out/nsu.jar`
4. Run the jar with `java -jar out/nsu.jar`