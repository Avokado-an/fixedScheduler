<#macro input name, value, type, feedback>
    <div class="col-md-3 mb-3">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <label for="${name}">${name}</label>
        <input type="${type}" name="${name}" id="${name}" placeholder="${name}" value="${value}"
               class="form-control" required>
        <div class="invalid-feedback">
            ${feedback}
        </div>
    </div>
</#macro>
