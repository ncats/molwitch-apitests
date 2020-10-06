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
 
 ## How To make Tests Fail
 By default, the API Checker will still make all the junit tests pass
 even if they throw exceptions or assertion errors .  This is so a molwitch-implementation
 that isn't fully compliant can still be move through all the phases of the maven pipeline
 to be packaged and deployed even if some molwitch features are missing.
 
 To make the failing tests fail the build, set this parameter ot true `molwitch.apiContract.ErrorOnFailure`
 
 ```
 mvn clean test -Dtest=ApiCheckerSuite -Dmolwitch.apiContract.ErrorOnFailure=true
``` 