<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        .actions {
            display: flex;
            justify-content: center;
        }
        .actions button {
            margin: 0 2px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Project List</h2>
        <a href="${pageContext.request.contextPath}/project/add" class="btn btn-primary">Add New Project</a>
    </div>
    <table class="table table-striped table-hover table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Start Date</th>
            <th>Estimated End Date</th>
            <th>End Date</th>
            <th>Description</th>
            <th>Status</th>
            <th>Budget</th>
            <th>Risk</th>
            <th>Manager</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="project" items="${projects}">
            <tr>
                <td><c:out value="${project.id}"/></td>
                <td><c:out value="${project.name}"/></td>
                <td><c:out value="${project.startDate}"/></td>
                <td><c:out value="${project.estimatedEndDate}"/></td>
                <td><c:out value="${project.endDate}"/></td>
                <td><c:out value="${project.description}"/></td>
                <td><c:out value="${project.status}"/></td>
                <td><c:out value="${project.budget}"/></td>
                <td><c:out value="${project.risk}"/></td>
                <td><c:out value="${project.manager.name}"/></td>
                <td class="actions">
                    <button class="btn btn-primary btn-sm" onclick="editProject(${project.id})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="showDeleteModal(${project.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                    <button class="btn btn-info btn-sm" onclick="manageEmployees(${project.id})">
                        <i class="fas fa-users"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Deletion</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this project?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" id="confirmDeleteButton">Delete</button>
            </div>
        </div>
    </div>
</div>

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
                Project has been deleted successfully!
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" id="okButton">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updatedModal" tabindex="-1" role="dialog" aria-labelledby="updatedModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updatedModalLabel">Success</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Project has been updated successfully!
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" id="okButton">Close</button>
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

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    let projectIdToDelete;

    function showDeleteModal(id) {
        projectIdToDelete = id;
        $('#confirmDeleteModal').modal('show');
    }

    $('#confirmDeleteButton').on('click', function() {
        deleteProject(projectIdToDelete);
    });

    function deleteProject(id) {
        $.ajax({
            url: "/project/delete/" + id,
            type: 'DELETE',
            success: function(result) {
                $('#confirmDeleteModal').modal('hide');
                $('#successModal').modal('show');
                $('#okButton').on('click', function() {
                    window.location.href = "/project/list";
                });
            },
            error: function() {
                $('#confirmDeleteModal').modal('hide');
                $('#errorModal').modal('show');
            }
        });
    }

    function editProject(projectId) {
        window.location.href = "/project/edit/" + projectId;
    }

    function manageEmployees(projectId) {
        window.location.href = "/project/associate/" + projectId;
    }

    $(document).ready(function() {
        var urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('success') === 'true') {
            $('#successModal').modal('show');
            $('#okButton').on('click', function() {
                window.location.href = "/project/list";
            });
        }

        if (urlParams.get('updated') === 'true') {
            $('#updatedModal').modal('show');
            $('#okButton').on('click', function() {
                window.location.href = "/project/list";
            });
        }

        if (urlParams.get('error') === 'true') {
            $('#errorModal').modal('show');
        }
    });
</script>
</body>
</html>
