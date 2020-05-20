<#import "../components/basic.ftlh" as base>
<#import "../components/navbar.ftlh" as navbar>

<@base.page>
    <#if currentTheme??>
        <@navbar.navbarWithParent user.name currentTheme!/>
    <#else>
        <@navbar.navbarWithoutParent user.name/>
    </#if>

    <div class="container">
        Check your email something is wrong with it
    </div>
</@base.page>