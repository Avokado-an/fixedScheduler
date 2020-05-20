<#macro themesWithParent themes, currentTheme>
    <@themeList themes>${currentTheme.getId()}/</@themeList>
</#macro>

<#macro themesWithoutParent themes>
    <@themeList themes></@themeList>
</#macro>

<#macro themeList themes>
    <#list themes as theme>
        <div class="card">
            <div class="card-header">
                Theme
                <a href="/tasks/<#nested>editTheme/${theme.id}"
                   class="btn btn-primary">Edit</a>
                <form method="post" action="/tasks/<#nested>deleteTheme/${theme.id}" style="display: inline-block">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary">Delete</button>
                </form>
            </div>
            <div class="card-body">
                <blockquote class="blockquote mb-0">
                    <p><a href="/tasks/${theme.id}">${theme.name}</a></p>
                </blockquote>
            </div>
        </div>
    </#list>
</#macro>