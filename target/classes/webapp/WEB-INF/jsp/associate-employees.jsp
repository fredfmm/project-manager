<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Associate Employees</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap4-duallistbox/dist/bootstrap-duallistbox.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="mb-0">Associate Employees to Project</h2>
    <a href="${pageContext.request.contextPath}/project/list" class="btn btn-primary mb-4">List Projects</a>
    </div>
    <form id="associateEmployeesForm" action="${pageContext.request.contextPath}/project/associate/${project.id}" method="post">
        <input type="hidden" name="_method" value="put"/>
        <input type="hidden" name="projectId" value="${project.id}">
        <div class="form-group">
            <label for="employees">Select Employees</label>
            <select class="form-control" id="employees" name="employees" multiple="multiple">
                <c:forEach var="employee" items="${employees}">
                    <option value="${employee.id}" <c:if test="${employee.selected}">selected</c:if>>${employee.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap4-duallistbox/dist/jquery.bootstrap-duallistbox.min.js"></script>

<script>
    $(document).ready(function() {
        var dualListbox = $('#employees').bootstrapDualListbox({
            nonSelectedListLabel: 'Available Employees',
            selectedListLabel: 'Associated Employees',
            preserveSelectionOnMove: 'moved',
            moveOnSelect: false
        });

        $('#associateEmployeesForm').on('submit', function(event) {
            event.preventDefault();
            var selectedEmployees = dualListbox.val();
            $.ajax({
                url: $(this).attr('action'),
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(selectedEmployees),
                success: function() {
                    $('#successModal').modal('show');
                },
                error: function() {
                    $('#errorModal').modal('show');
                }
            });
        });

    });
</script>

<div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="successModalLabel">Success</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Employees have been successfully associated with the project!
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="errorModalLabel">Error</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                An error occurred while processing the request.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
