<#import "components/basic.ftlh" as base>
<#import "components/Validation/inputPlan.ftlh" as planInputter>

<@base.page>
    <div class="container">
        <@planInputter.inputPlan "${plan.description}", "${plan.date?string('yyyy-MM-dd')}", "${plan.time?string('kk:mm')}", "Edit plan"/>
    </div>
</@base.page>