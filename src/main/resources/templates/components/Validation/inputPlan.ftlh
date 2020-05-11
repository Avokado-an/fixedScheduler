<#import "inputValidator.ftlh" as validator>
<#import "inputSingleElement.ftlh" as input>

<#macro inputPlan description, date, time, submitName>
    <@validator.input "${submitName}">
        <div class="form-row">
            <@input.input "description", "${description}", "text", "Please provide a valid description."/>
            <div class="col-md-6 mb-3">
                <label for="date">Date</label>
                <input type="date" name="deadlineDate" min="${minDate}" max="2045-12-12" value="${date}" id="date"
                       placeholder="date" class="form-control" required>
                <div class="invalid-feedback">
                    Please provide a valid date.
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="time">Time</label>
                <input type="time" name="deadlineTime" id="time" placeholder="time" value="${time}"
                       class="form-control" required>
                <div class="invalid-feedback">
                    Please provide a valid time.
                </div>
            </div>
        </div>
    </@validator.input>
</#macro>