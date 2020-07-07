<#import "logHelper.ftlh" as log>

<#macro navbarWithParent username, currentTheme>
    <nav class="nav">
        <a href="#" class="nav-link disabled">${username}</a>
        <a href="/" class="nav-link">Home</a>
        <#if currentTheme.parentTheme??>
            <a href="/tasks/${currentTheme.parentTheme.id}" class="nav-link">Back</a>
        <#else>
            <a href="/tasks" class="nav-link">Back</a>
        </#if>
        <a href="/tasks/${currentTheme.getId()}/addPlan" class="nav-link">Add new plan</a>
        <a href="/tasks/${currentTheme.getId()}/addTheme" class="nav-link">Add new Theme</a>
    </nav>
</#macro>

<#macro navbarWithoutParent username>
    <nav class="nav">
        <a href="#" class="nav-link disabled">${username}</a>
        <a href="/" class="nav-link">Home</a>
        <a href="/tasks/addPlan" class="nav-link">Add new plan</a>
        <a href="/tasks/addTheme" class="nav-link">Add new Theme</a>
    </nav>
</#macro>