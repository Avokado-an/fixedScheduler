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
                <form method="post" enctype="multipart/form-data">
                    <a href="/tasks/<#nested>editPlan/${plan.id}"
                       class="btn btn-primary">Edit</a>
                    <button type="submit" class="btn btn-primary">Delete</button>
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-6">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <input type="hidden" name="planId" placeholder="new task" value="${plan.id}"/>
                            </div>
                        </div>
                    </div>
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