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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class PercentageApiContractChecker extends ApiContractChecker{

    public PercentageApiContractChecker(Predicate<Map<String, Double>> failIf){
        super(map ->{
            //Function<Map<String, Map<ComplianceLevel, SingleThreadCounter>>, String> failIf
            Map<String, Double> percentMap = new HashMap<>();
            for(Map.Entry<String,Map<ComplianceLevel, SingleThreadCounter> > entry : map.entrySet()) {
                String key = entry.getKey();
                SingleThreadCounter totalCount = new SingleThreadCounter();
                SingleThreadCounter passCount = new SingleThreadCounter();
                SingleThreadCounter failCount = new SingleThreadCounter();
                for (Map.Entry<ComplianceLevel, SingleThreadCounter> entry2 : entry.getValue().entrySet()) {
                    totalCount.increment(entry2.getValue().getAsLong());
                    switch (entry2.getKey()) {
                        case FULLY:
                            passCount.increment(entry2.getValue().getAsLong());
                            break;
                        default:
                            failCount.increment(entry2.getValue().getAsLong());
                    }


                }
                percentMap.put(key, passCount.getAsLong() / (double) totalCount.getAsLong());
            }
            return failIf.test(percentMap)? "failing test threshold " + percentMap : null;
        } );
    }
}
