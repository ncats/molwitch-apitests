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
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class ApiContractChecker extends ExternalResource {

    public enum ComplianceLevel{
        FULLY,
        PARTIALLY,
        NOT_COMPLIANT;
    }
    private Map<String, Map<ComplianceLevel, SingleThreadCounter>> map;
    private Map<String, Map<ComplianceLevel, Set<String>>> complianceMessageMap;

    private final Function<Map<String, Map<ComplianceLevel, SingleThreadCounter>>, String> failIf;

    private static final boolean ERROR_ON_FAILURE = Boolean.parseBoolean(System.getProperty("molwitch.apiContract.ErrorOnFailure", "false"));

    public ApiContractChecker(){
        this.failIf = m-> null;
    }
    public ApiContractChecker(Function<Map<String, Map<ComplianceLevel, SingleThreadCounter>>, String> failIf){
        this.failIf = Objects.requireNonNull(failIf);
    }
    @Override
    protected void before() throws Throwable {
        map = new HashMap<>();
        complianceMessageMap = new HashMap<>();
    }

    @Override
    protected void after() {
        String errorMessage = failIf.apply(map);
        GlobalApiContractChecker.INSTANCE.addComplianceReport(map, complianceMessageMap);
        if(errorMessage !=null && !errorMessage.trim().isEmpty()){
            throw new AssertionError(errorMessage);
        }
    }

    @Override
    public Statement apply(Statement base, Description description) {


            if(base.getClass().getName().startsWith("org.junit.runners.ParentRunner")){
                //this is from the ClassRule which will never throw an exception
                //do nothing
                return super.apply(base, description);
            }else {
               return new Statement() {
                   @Override
                   public void evaluate() throws Throwable {
                       try {
                           base.evaluate();
                           handlePassingTest();
                       }catch(ApiContractException e){
//                           e.printStackTrace();
                           addComplianceReport(e.getCategory(), e.getComplianceLevel(), e.getComplianceMessage());
                           if(e.getComplianceLevel() == ComplianceLevel.NOT_COMPLIANT){
                               handleFailingTest();
                               if(ERROR_ON_FAILURE) {
                                   throw e;
                               }
                           }
                       }catch(Throwable t){
                           ApiContract contract = description.getAnnotation(ApiContract.class);


                           if(contract!=null){
                               String category = contract.category();
                               String message = contract.message();
                               if(ApiContract.DEFAULT_NULL_MESSAGE.equals(message)){
                                   message =null;
                               }
                               addComplianceReport(category, contract.complianceLevelOnFail, message);
                           }
                           t.printStackTrace();
                           handleFailingTest();
                           if(ERROR_ON_FAILURE) {
                               throw t;
                           }
                       }
                   }
               };
            }

    }
    protected void handleFailingTest() {
    }
    protected void handlePassingTest() {
    }

    protected void handleUncaughtThrowable(Throwable t){

    }
    public void addComplianceReport(String category, ComplianceLevel complianceLevel){
        addComplianceReport(category, complianceLevel, Optional.empty());
    }
    public void addComplianceReport(String category, ComplianceLevel complianceLevel, String complianceMessage){
        addComplianceReport(category, complianceLevel, Optional.ofNullable(complianceMessage));
    }
    public void addComplianceReport(String category, ComplianceLevel complianceLevel, Optional<String> complianceMessage){
        map.computeIfAbsent(category, c->  new EnumMap<>(ComplianceLevel.class))
                .computeIfAbsent(complianceLevel, l -> new SingleThreadCounter()).increment();
        complianceMessage.ifPresent(m->{
            complianceMessageMap.computeIfAbsent(category, c->  new EnumMap<>(ComplianceLevel.class))
                    .computeIfAbsent(complianceLevel, l -> new LinkedHashSet<>()).add(m);
        });
    }
}
