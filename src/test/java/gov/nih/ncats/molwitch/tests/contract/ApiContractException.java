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

import java.util.Optional;

public class ApiContractException extends RuntimeException{

    private final String category;

    private final ApiContractChecker.ComplianceLevel complianceLevel;

    private final String complianceMessage;
    public ApiContractException(String message, String category, ApiContractChecker.ComplianceLevel complianceLevel) {
        this(message, category, complianceLevel, null);
    }
    public ApiContractException(String message, String category, ApiContractChecker.ComplianceLevel complianceLevel,  String complianceMessage) {
        super(message);
        this.category = category;
        this.complianceLevel = complianceLevel;
        this.complianceMessage = complianceMessage;
    }
    public ApiContractException(String message, Throwable cause, String category, ApiContractChecker.ComplianceLevel complianceLevel) {
        this(message, cause, category, complianceLevel, null);
    }
    public ApiContractException(String message, Throwable cause, String category, ApiContractChecker.ComplianceLevel complianceLevel, String complianceMessage) {
        super(message, cause);
        this.category = category;
        this.complianceLevel = complianceLevel;
        this.complianceMessage = complianceMessage;
    }

    public String getCategory() {
        return category;
    }

    public ApiContractChecker.ComplianceLevel getComplianceLevel() {
        return complianceLevel;
    }
    public Optional<String> getComplianceMessage(){
        return Optional.ofNullable(complianceMessage);
    }
}
