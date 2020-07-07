<#import "component/basic.ftlh" as base>
<#import "component/navbar.ftlh" as navbar>
<#import "component/planList.ftlh" as planList>
<#import  "component/themeList.ftlh" as themeList>

<@base.page>
    <#if currentTheme??>
        <@navbar.navbarWithParent user.name currentTheme!/>
    <#else>
        <@navbar.navbarWithoutParent user.name/>
    </#if>

    <div class="container">
        <#if currentTheme??>
            <@themeList.themesWithParent themes, currentTheme/>
            <@planList.plansWithParent plans, currentTheme/>
        <#else>
            <@themeList.themesWithoutParent themes/>
            <@planList.plansWithoutParent plans/>
        </#if>
    </div>
</@base.page>