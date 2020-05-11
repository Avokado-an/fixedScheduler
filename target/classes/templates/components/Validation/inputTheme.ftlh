<#import "inputValidator.ftlh" as validator>
<#import "inputSingleElement.ftlh" as input>

<#macro inputTheme description, submitName>
    <@validator.input "${submitName}">
        <@input.input "description", "${description}", "text", "Please provide a valid description."/>
    </@validator.input>
</#macro>