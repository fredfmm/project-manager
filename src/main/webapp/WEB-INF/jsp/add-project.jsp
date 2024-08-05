<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Project</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Add New Project</h2>
        <a href="${pageContext.request.contextPath}/project/list" class="btn btn-primary mb-4">List Projects</a>
    </div>
    <form id="addProjectForm">
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="startDate">Start Date</label>
            <input type="date" class="form-control" id="startDate" name="startDate" required>
        </div>
        <div class="form-group">
            <label for="estimatedEndDate">Estimated End Date</label>
            <input type="date" class="form-control" id="estimatedEndDate" name="estimatedEndDate" required>
        </div>
        <div class="form-group">
            <label for="endDate">End Date</label>
            <input type="date" class="form-control" id="endDate" name="endDate">
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" name="description" required></textarea>
        </div>
        <div class="form-group">
            <label for="status">Status</label>
            <select class="form-control" id="status" name="status" required>
                <option value="EM_ANALISE">Em Análise</option>
                <option value="ANALISE_REALIZADA">Análise Realizada</option>
                <option value="ANALISE_APROVADA">Análise Aprovada</option>
                <option value="INICIADO">Iniciado</option>
                <option value="PLANEJADO">Planejado</option>
                <option value="EM_ANDAMENTO">Em Andamento</option>
                <option value="ENCERRADO">Encerrado</option>
                <option value="CANCELADO">Cancelado</option>
            </select>
        </div>
        <div class="form-group">
            <label for="budget">Budget</label>
            <input type="number" class="form-control" id="budget" name="budget" required>
        </div>
        <div class="form-group">
            <label for="risk">Risk</label>
            <select class="form-control" id="risk" name="risk" required>
                <option value="BAIXO">Baixo</option>
                <option value="MEDIO">Médio</option>
                <option value="ALTO">Alto</option>
            </select>
        </div>
        <div class="form-group">
            <label for="managerId">Manager</label>
            <select class="form-control" id="managerId" name="managerId" required>
                <option selected> Select a Manager</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Add Project</button>
    </form>

    <!-- Success Modal -->
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
                    Project has been added successfully!
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" id="okButton">Close</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Error Modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalLabel">Error</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="errorMessage">
                    An error occurred while processing the request.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="closeButton">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function() {

        $.ajax({
            url: '${pageContext.request.contextPath}/api/employee',
            type: 'GET',
            success: function(data) {
                var managerSelect = $('#managerId');
                managerSelect.empty();
                $.each(data, function(index, manager) {
                    managerSelect.append('<option value="' + manager.id + '">' + manager.name + '</option>');
                });
            },
            error: function(xhr) {
                console.error('Error loading managers:', xhr);
            }
        });

        $('#addProjectForm').submit(function(event) {
            event.preventDefault();

            var formData = {
                name: $('#name').val(),
                start_date: $('#startDate').val(),
                estimated_end_date: $('#estimatedEndDate').val(),
                end_date: $('#endDate').val(),
                description: $('#description').val(),
                status: $('#status').val(),
                budget: $('#budget').val(),
                risk: $('#risk').val(),
                manager_id: $('#managerId').val()
            };

            $.ajax({
                url: '${pageContext.request.contextPath}/project/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    $('#successModal').modal('show');
                    $('#okButton').on('click', function() {
                        window.location.href = "${pageContext.request.contextPath}/project/list";
                    });
                },
                error: function(xhr) {
                    var errorMessage = xhr.responseJSON ? xhr.responseJSON.messages.join(', ') : 'An error occurred while processing the request.';
                    $('#errorMessage').text(errorMessage);
                    $('#errorModal').modal('show');
                }
            });
        });

        var urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('success') === 'true') {
            $('#successModal').modal('show');
            $('#okButton').on('click', function() {
                window.location.href = "${pageContext.request.contextPath}/project/list";
            });
        }

        if (urlParams.get('error') === 'true') {
            $('#errorModal').modal('show');
        }

        $('#okButton, #closeButton').on('click', function() {
            $('#successModal').modal('hide');
            $('#errorModal').modal('hide');
        });
    });
</script>

</body>
</html>
