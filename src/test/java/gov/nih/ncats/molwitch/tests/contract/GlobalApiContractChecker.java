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

import java.util.*;

public enum GlobalApiContractChecker {

    INSTANCE;

    private Map<String, Map<ApiContractChecker.ComplianceLevel, SingleThreadCounter>> map = new HashMap<>();
    private Map<String, Map<ApiContractChecker.ComplianceLevel, Set<String>>> complianceMessageMap = new HashMap<>();

    public void addComplianceReport(Map<String, Map<ApiContractChecker.ComplianceLevel, SingleThreadCounter>> map,
                                    Map<String, Map<ApiContractChecker.ComplianceLevel, Set<String>>> complianceMessageMap){
        for(Map.Entry<String, Map<ApiContractChecker.ComplianceLevel, SingleThreadCounter>> entry : map.entrySet()){
            Map<ApiContractChecker.ComplianceLevel, SingleThreadCounter> value =this.map.computeIfAbsent(entry.getKey(), c->  new EnumMap<>(ApiContractChecker.ComplianceLevel.class));

            for(Map.Entry<ApiContractChecker.ComplianceLevel, SingleThreadCounter> entry2 : entry.getValue().entrySet()){
                value.computeIfAbsent(entry2.getKey(), l-> new SingleThreadCounter()).increment(entry2.getValue().getAsLong());
            }
        }

        for(Map.Entry<String, Map<ApiContractChecker.ComplianceLevel, Set<String>>> entry : complianceMessageMap.entrySet()){
            Map<ApiContractChecker.ComplianceLevel, Set<String>> value =this.complianceMessageMap.computeIfAbsent(entry.getKey(), c->  new EnumMap<>(ApiContractChecker.ComplianceLevel.class));

            for(Map.Entry<ApiContractChecker.ComplianceLevel, Set<String>> entry2 : entry.getValue().entrySet()) {
                value.computeIfAbsent(entry2.getKey(), l -> new LinkedHashSet<>()).addAll(entry2.getValue());
            }
        }

    }


}
