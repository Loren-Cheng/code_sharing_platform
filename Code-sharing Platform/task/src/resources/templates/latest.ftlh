<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
</head>
<body>

<#list latests as code>
<#--    <p>${code.date}</p>-->
<#--    <pre id="code_snippet">${code.code}</pre>-->
    <span id="load_date">${code.date}</span>
    <pre id="code_snippet">${code.code}</pre>
</#list>


</body>
</html>