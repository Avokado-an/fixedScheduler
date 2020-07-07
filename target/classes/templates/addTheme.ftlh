<#import "component/basic.ftlh" as base>
<#import "component/Validation/inputTheme.ftlh" as inputTheme>

<@base.page>
    <div class="container">
        <@inputTheme.inputTheme "", "Add Theme"/>
    </div>

    <div class="container">
        <#list themes as theme>
            <div class="container">
                <div class="card">
                    <div class="card-header">
                        Theme
                    </div>
                    <div class="card-body">
                        <blockquote class="blockquote mb-0">
                            <p><a href="/tasks/${theme.id}">${theme.name}</a></p>
                        </blockquote>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@base.page>