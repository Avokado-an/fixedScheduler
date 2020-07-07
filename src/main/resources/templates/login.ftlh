<#import "component/basic.ftlh" as base>
<#import "component/logHelper.ftlh" as log>

<@base.page>
    <@log.loginsample "/login" false />
    <p><a href="/restorePassword">Forgot password?</a></p>
</@base.page>
