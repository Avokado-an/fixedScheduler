<#macro plansWithParent plans, currentTheme>
    <@planList plans>${currentTheme.getId()}/</@planList>
</#macro>

<#macro plansWithoutParent plans>
    <@planList plans></@planList>
</#macro>

<#macro planList plans>
    <#list plans as plan>
        <div class="card">
            <div class="card-header">
                Plan
                <a href="/tasks/<#nested>editPlan/${plan.id}"
                   class="btn btn-primary">Edit</a>
                <form method="post" action="/tasks/<#nested>deletePlan/${plan.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Delete</button>
                </form>
            </div>
            <div class="card-body">
                <blockquote class="blockquote mb-0">
                    <p>${plan.description}</p>
                    <footer class="blockquote-footer"> date - ${plan.date?string('dd.MM')}<cite
                                title="Source Title"> time - ${plan.time?string('HH:mm')}</cite></footer>
                </blockquote>
            </div>
        </div>
    <#else>
        <div>You got no plans</div>
    </#list>
</#macro>