# event-table-generator-maven-plugin

This plugin generates the markdown table based on the project‘s event definition, the resulting MD file can be displayed on GitHub

## How to build

#### Prerequisites

* Java 1.7+
* Maven 3.3+

#### Sample

~~~~
<plugin>
    <groupId>com.expedia.lodging</groupId>
    <artifactId>event-table-generator-maven-plugin</artifactId>
    <version>1.0</version>
    <configuration>
        <eventClass>com.expedia.www.lodging.bmcbot.event.SystemEvent</eventClass>
        <idGetter>getId</idGetter>
        <messageGetter>getDescription</messageGetter>
        <outputPath>EVENTMESSAGE.md</outputPath>
    </configuration>
    <executions>
        <execution>
            <phase>compile</phase>
            <goals>
                <goal>errorCodeGen</goal>
            </goals>
        </execution>
    </executions>
</plugin>
~~~~
 
#### More detail

[Use Guide](https://confluence.expedia.biz/display/~halin/Event+Table+Generator+Maven+Plugin)