<#import "component/basic.ftlh" as base>
<#import "component/Validation/inputPlan.ftlh" as planInputter>

<@base.page>
    <div class="container">
        <@planInputter.inputPlan "", "", "", "Add plan"/>
    </div>

    <div class="container">
        <#list plans as plan>
            <div class="container">
                <div class="card">
                    <div class="card-header">
                        Plan
                    </div>
                    <div class="card-body">
                        <blockquote class="blockquote mb-0">
                            <p>${plan.description}</p>
                        </blockquote>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@base.page>