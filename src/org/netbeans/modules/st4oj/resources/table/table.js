/**
 * ${name} module
 */
define(['ojs/ojcore', 'knockout', 'ojs/ojtable'
], function (oj, ko) {
    /**
     * The view model for the main content view template
     */
    function ${name}ContentViewModel() {
        var self = this;
        var deptArray = [{DepartmentId: 1001, DepartmentName: 'ADFPM 1001 neverending', LocationId: 200, ManagerId: 300},
            {DepartmentId: 556, DepartmentName: 'BB', LocationId: 200, ManagerId: 300},
            {DepartmentId: 10, DepartmentName: 'Administration', LocationId: 200, ManagerId: 300},
            {DepartmentId: 20, DepartmentName: 'Marketing', LocationId: 200, ManagerId: 300},
            {DepartmentId: 30, DepartmentName: 'Purchasing', LocationId: 200, ManagerId: 300},
            {DepartmentId: 40, DepartmentName: 'Human Resources1', LocationId: 200, ManagerId: 300},
            {DepartmentId: 50, DepartmentName: 'Administration2', LocationId: 200, ManagerId: 300},
            {DepartmentId: 60, DepartmentName: 'Marketing3', LocationId: 200, ManagerId: 300},
            {DepartmentId: 70, DepartmentName: 'Purchasing4', LocationId: 200, ManagerId: 300},
            {DepartmentId: 80, DepartmentName: 'Human Resources5', LocationId: 200, ManagerId: 300},
            {DepartmentId: 90, DepartmentName: 'Human Resources11', LocationId: 200, ManagerId: 300},
            {DepartmentId: 100, DepartmentName: 'Administration12', LocationId: 200, ManagerId: 300},
            {DepartmentId: 110, DepartmentName: 'Marketing13', LocationId: 200, ManagerId: 300},
            {DepartmentId: 120, DepartmentName: 'Purchasing14', LocationId: 200, ManagerId: 300},
            {DepartmentId: 130, DepartmentName: 'Human Resources15', LocationId: 200, ManagerId: 300}];
        self.datasource = new oj.ArrayTableDataSource(deptArray, {idAttribute: 'DepartmentId'});
    }

    return ${name}ContentViewModel;
});
