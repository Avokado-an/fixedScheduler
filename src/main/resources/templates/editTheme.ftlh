<#import "components/basic.ftlh" as base>
<#import "components/Validation/inputTheme.ftlh" as inputTheme>

<@base.page>
    <div class="container">
        <@inputTheme.inputTheme "${theme.name}", "Edit Theme"/>
    </div>
</@base.page>