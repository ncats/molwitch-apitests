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

import gov.nih.ncats.common.Tuple;
import gov.nih.ncats.common.util.SingleThreadCounter;
import org.junit.rules.ExternalResource;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiContractSuiteRule extends ExternalResource {
    @Override
    protected void after() {
        System.out.println("====== COMPLIANCE CONTRACT RESULTS ========");
        for(Map.Entry<String, Map<ApiContractChecker.ComplianceLevel, SingleThreadCounter>> entry : GlobalApiContractChecker.INSTANCE.getMap().entrySet()){
            String category = entry.getKey();
            String result;
            if(entry.getValue().size()  ==1){
                Map.Entry<ApiContractChecker.ComplianceLevel, SingleThreadCounter> singleEntry = entry.getValue().entrySet().iterator().next();
                result = singleEntry.getKey().toString();
                System.out.println(category +"\t"+ result);
                System.out.println("--------------------------");
            }else {
                Comparator<Map.Entry<ApiContractChecker.ComplianceLevel, Long>> SortByLargest = Comparator.<Map.Entry<ApiContractChecker.ComplianceLevel, Long>>comparingLong(e->e.getValue()).reversed();
                Map<ApiContractChecker.ComplianceLevel, Long> map = entry.getValue().entrySet().stream()
                                .map(e-> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().getAsLong()))
                                .sorted(SortByLargest)
                                .collect(Collectors.toMap(e-> e.getKey(), e->e.getValue(),
                                        (u, v) -> {
                                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                                        },
                                        LinkedHashMap::new));

                for (Map.Entry<ApiContractChecker.ComplianceLevel, Long> entry2 : map.entrySet()) {
                    System.out.println(category +"\t"+ entry2.getKey() + " ( "+ entry2.getValue() + " )");
                }
                System.out.println("--------------------------");
            }
        }
    }
}
