<#import "components/basic.ftlh" as base>
<#import  "components/Validation/inputSingleElement.ftlh" as validate>

<@base.page>
    <div class="container">
        <form method="post" enctype="multipart/form-data">
            <@validate.input "password", "", "text", "Provide valid password"/>
            <@validate.input "passwordChecker", "", "text", "Provide copy of password"/>
            <button type="submit">Confirm</button>
        </form>
    </div>
</@base.page>