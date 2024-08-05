<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">Edit Project</h2>
        <a href="${pageContext.request.contextPath}/project/list" class="btn btn-primary mb-4">List Projects</a>
    </div>

    <form id="editProjectForm">
        <input type="hidden" id="id" name="id" value="${project.id}"/>
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${project.name}" required>
        </div>
        <div class="form-group">
            <label for="startDate">Start Date</label>
            <input type="date" class="form-control" id="startDate" name="startDate" value="${project.startDate}" required>
        </div>
        <div class="form-group">
            <label for="estimatedEndDate">Estimated End Date</label>
            <input type="date" class="form-control" id="estimatedEndDate" name="estimatedEndDate" value="${project.estimatedEndDate}" required>
        </div>
        <div class="form-group">
            <label for="endDate">End Date</label>
            <input type="date" class="form-control" id="endDate" name="endDate" value="${project.endDate}">
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" name="description" required>${project.description}</textarea>
        </div>
        <div class="form-group">
            <label for="status">Status</label>
            <select class="form-control" id="status" name="status" required>
                <option value="EM_ANALISE" ${project.status == 'EM_ANALISE' ? 'selected' : ''}>Em Análise</option>
                <option value="ANALISE_REALIZADA" ${project.status == 'ANALISE_REALIZADA' ? 'selected' : ''}>Análise Realizada</option>
                <option value="ANALISE_APROVADA" ${project.status == 'ANALISE_APROVADA' ? 'selected' : ''}>Análise Aprovada</option>
                <option value="INICIADO" ${project.status == 'INICIADO' ? 'selected' : ''}>Iniciado</option>
                <option value="PLANEJADO" ${project.status == 'PLANEJADO' ? 'selected' : ''}>Planejado</option>
                <option value="EM_ANDAMENTO" ${project.status == 'EM_ANDAMENTO' ? 'selected' : ''}>Em Andamento</option>
                <option value="ENCERRADO" ${project.status == 'ENCERRADO' ? 'selected' : ''}>Encerrado</option>
                <option value="CANCELADO" ${project.status == 'CANCELADO' ? 'selected' : ''}>Cancelado</option>
            </select>
        </div>
        <div class="form-group">
            <label for="budget">Budget</label>
            <input type="number" class="form-control" id="budget" name="budget" value="${project.budget}" required>
        </div>
        <div class="form-group">
            <label for="risk">Risk</label>
            <select class="form-control" id="risk" name="risk" required>
                <option value="BAIXO" ${project.risk == 'BAIXO' ? 'selected' : ''}>Baixo</option>
                <option value="MEDIO" ${project.risk == 'MEDIO' ? 'selected' : ''}>Médio</option>
                <option value="ALTO" ${project.risk == 'ALTO' ? 'selected' : ''}>Alto</option>
            </select>
        </div>
        <div class="form-group">
            <label for="managerId">Manager</label>
            <select class="form-control" id="managerId" name="managerId" required></select>
        </div>
        <button type="submit" class="btn btn-primary">Update Project</button>
    </form>
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
    $(document).ready(function() {

        var selectedManagerId = '${project.manager.id}';


        $.ajax({
            url: '${pageContext.request.contextPath}/api/employee',
            type: 'GET',
            success: function(data) {
                var managerSelect = $('#managerId');
                managerSelect.empty();
                $.each(data, function(index, manager) {
                    var selected = manager.id == selectedManagerId ? 'selected' : '';
                    managerSelect.append('<option value="' + manager.id + '" ' + selected + '>' + manager.name + '</option>');
                });
            },
            error: function(xhr) {
                console.error('Error loading managers:', xhr);
            }
        });

        $('#editProjectForm').on('submit', function(e) {
            e.preventDefault();

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

            var projectId = $('#id').val();

            $.ajax({
                type: 'PUT',
                url: '/project/update/' + projectId,
                data: JSON.stringify(formData),
                contentType: 'application/json',
                success: function(response) {
                    window.location.href = '/project/list?updated=true';
                },
                error: function(xhr, status, error) {
                    $('#errorModal').modal('show');
                }
            });
        });
    });


</script>
</body>
</html>
