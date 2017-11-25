# Vertica 9+ compatibility plugin for [pentaho-kettle](https://github.com/pentaho/pentaho-kettle)

[![Kettle 6.x](https://img.shields.io/badge/pentaho_kettle-6.x--8.x-4c7e9f.svg)](https://github.com/pentaho/pentaho-kettle)
[![Java 7+](https://img.shields.io/badge/java-7+-4c7e9f.svg)](http://java.oracle.com)
[![License](https://img.shields.io/badge/license-Apache--2.0-4c7e9f.svg)](https://raw.githubusercontent.com/twineworks/vertica-9-for-pentaho-kettle/master/LICENSE.txt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.twineworks/vertica-9-for-pentaho-kettle/badge.svg)](http://search.maven.org/#search|gav|1|g:"com.twineworks"%20AND%20a:"vertica-9-for-pentaho-kettle")

An plugin for Pentaho kettle (PDI) providing support for modern versions of Vertica. Tested with Vertica 9. 

## Supported versions of pentaho-kettle
The plugin is built against the most recent versions of Kettle 6.x, 7.x and 8.x.

## How to get it?
Grab the latest release from the [releases](https://github.com/twineworks/vertica-9-for-pentaho-kettle/releases) page.
You can also get the plugin zip as a maven dependency from [maven central](http://search.maven.org/#search|gav|1|g:"com.twineworks"%20AND%20a:"vertica-9-for-pentaho-kettle"). 

## How to install?
Decompress the release zip to `<kettle-dir>/plugins` and restart Spoon. The "Vertica 9+" database type will appear in the "Database Connection" dialogs. You still need to supply your own JDBC driver for vertica.
The plugin is tested with the official vertica 9 jdbc drivers. 

## Why would I want this?
Official Vertica support in kettle has not been updated in a while. Among other things, this plugin fixes the following issues:
- [PDI-7769 - Vertica: Database Explorer shows views in the schema tree instead of the view tree](http://jira.pentaho.com/browse/PDI-7769)
- [PDI-10234 - Vertica database logging does not log time information](http://jira.pentaho.com/browse/PDI-10234) (In fact, the issue is more severe: time is **always** truncated in DATETIME inserts.)
- [PDI-13018 - lazy conversion for vertica table input does not work](http://jira.pentaho.com/browse/PDI-13018)
- [PDI-12040 - Getting data of TIME type from Vertica DB (or sending to Vertica DB) is failed with the errors from Vertica JDBC](http://jira.pentaho.com/browse/PDI-12040)
- [PDI-16462 - Support for Vertica 8](http://jira.pentaho.com/browse/PDI-16462) (Sort of, the plugin is tested against Vertica 9. If anybody wants to try on 8, there's a good chance it's going to work just fine.)
   
## Where do I report bugs and issues?
Just open [issues](https://github.com/twineworks/vertica-9-for-pentaho-kettle/issues) on github.

## How do I build the project?
```bash
mvn clean package
```
It creates the plugin zip in `target/vertica-9-for-pentaho-kettle-{version}-plugin.zip`.

## How can I contribute?
If you'd like to contribute please fork the project, add the feature or bugfix and send a pull request.

## License
The plugin uses the [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) license. Same as kettle.

## Support
Open source does not mean you're on your own. The plugin is developed by [Twineworks GmbH](http://twineworks.com). Twineworks offers commercial support and consulting services. [Contact us](mailto:hi@twineworks.com) if you'd like us to help with a project.
  