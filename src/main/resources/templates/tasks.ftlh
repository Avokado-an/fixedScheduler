<#import "components/basic.ftlh" as base>
<#import "components/navbar.ftlh" as navbar>
<#import "components/planList.ftlh" as planList>
<#import  "components/themeList.ftlh" as themeList>

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