# molwitch-apitests

Unit test library to make sure molwitch implementations correctly
implement the API.

# How to Use
To run these tests for your implementation add this as a dependency in your maven POM

    <dependency>
            <groupId>gov.nih.ncats</groupId>
            <artifactId>molwitch-apitests</artifactId>
            <version>0.6.0</version>
            <type>test-jar</type>
            <scope>test</scope>
        </dependency>


Maven won't run these extra tests by default it is recommended you explicitly run the API tests
from your molwitch implementation root directory:

`mvn clean test -Dtest=ApiCheckerSuite`

This will run thousands of automated tests that will produce a report when it's
 complete to show which features are Fully Compliant, Partially Compliant or Not Compliant.  
 There may be additional notes or comments about which specific sub-features are not passing.