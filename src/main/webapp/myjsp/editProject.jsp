<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Edit Project</title>
</head>
<body>

<h2>Edit Project</h2>

<form action="<%= request.getContextPath() %>/updateProject"
      method="post">

    <input type="hidden"
           name="projectId"
           value="${projectId}">

    <p>
        Title:
        <input type="text"
               name="title"
               value="${title}">
    </p>

    <p>
        Description:
        <textarea name="description"
                  rows="5"
                  cols="50">${description}</textarea>
    </p>

    <p>
        Tags:
        <input type="text"
               name="tags"
               value="${tags}">
    </p>

    <button type="submit">
        Update Project
    </button>

</form>

</body>
</html>