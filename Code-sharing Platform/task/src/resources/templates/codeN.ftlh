<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();

    </script>
</head>
<body>
<#--    <p>${codeN.date}</p>-->
<#--    <pre id="code_snippet">${codeN.codeInMem}</pre>-->
<span id="load_date">${codeN.date}</span>
<pre id="code_snippet"><code>${codeN.code}</code></pre>
<#if codeN.time gte 0>
    <span id="time_restriction">${codeN.time}</span>
</#if>
<#if   codeN.views  gte 0>
    <span id="views_restriction">${codeN.views}</span>
</#if>


</body>
</html>