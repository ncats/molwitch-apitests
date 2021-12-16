/*
 * NCATS-WITCH-APITESTS
 *
 * Copyright 2020 NIH/NCATS
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gov.nih.ncats.molwitch.tests.contract;

import gov.nih.ncats.common.util.SingleThreadCounter;

import java.util.EnumMap;
import java.util.Map;

public class BasicApiContractChecker extends ApiContractChecker {

    private String defaultCategory;

    public enum TestResult{
        PASS,
        FAIL;
    }

    public BasicApiContractChecker(String defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    @Override
    protected String getDefaultCategory() {
        return defaultCategory;
    }

    private Map<TestResult, SingleThreadCounter> testResult = new EnumMap<>(TestResult.class);

    @Override
    protected void handleUncaughtThrowable(Throwable t) {
        addComplianceReport(defaultCategory, ComplianceLevel.NOT_COMPLIANT);
    }

    @Override
    protected void handlePassingTest() {
        testResult.computeIfAbsent(TestResult.PASS, p-> new SingleThreadCounter()).increment();
    }

    @Override
    protected void handleFailingTest(Throwable t) {
        t.printStackTrace();
        testResult.computeIfAbsent(TestResult.FAIL, p-> new SingleThreadCounter()).increment();

    }

    @Override
    protected void after() {
        long numRight = testResult.computeIfAbsent(TestResult.PASS, k-> new SingleThreadCounter()).getAsLong();
        long numFailed = testResult.computeIfAbsent(TestResult.FAIL, k-> new SingleThreadCounter()).getAsLong();
//        System.out.println(defaultCategory + " : " + numRight + " vs " + numFailed);
        if(numFailed==0 && numRight>0){
            addComplianceReport(defaultCategory, ComplianceLevel.FULLY);
        }else if(numFailed>0){
            if(numRight >0){
                addComplianceReport(defaultCategory, ComplianceLevel.PARTIALLY);
            }else{
                addComplianceReport(defaultCategory, ComplianceLevel.NOT_COMPLIANT);
            }
        }

        super.after();
    }
}
