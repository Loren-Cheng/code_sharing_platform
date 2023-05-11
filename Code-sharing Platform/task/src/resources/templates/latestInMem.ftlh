<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
</head>
<body>

<#list latests as codeInMem>
<#--    <p>${codeInMem.date}</p>-->
<#--    <pre id="code_snippet">${codeInMem.codeInMem}</pre>-->
    <span id="load_date">${codeInMem.date}</span>
    <pre id="code_snippet">${codeInMem.code}</pre>
</#list>


</body>
</html>