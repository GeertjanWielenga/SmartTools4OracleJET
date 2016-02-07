/**
 * ${name} module
 */
define(['ojs/ojcore', 'knockout', 'ojs/ojtable', 'ojs/ojdatacollection-common'
], function (oj, ko) {
    /**
     * The view model for the main content view template
     */
    function ${name}ContentViewModel() {
        var self = this;
        var deptObservableArray = ko.observableArray([]);
        self.datasource = new oj.ArrayTableDataSource(deptObservableArray, {idAttribute: 'id'});
        self.addData = function () {
            $.ajax({
                url: "cd_catalog.xml",
                type: 'GET',
                dataType: 'xml',
                success: function (data, textStatus, jqXHR) {
                    var x = data.getElementsByTagName("CD");
                    for (i = 0; i < x.length; i++) {
                        l_artist = x[i].getElementsByTagName("ARTIST")[0].childNodes[0].nodeValue;
                        l_title = x[i].getElementsByTagName("TITLE")[0].childNodes[0].nodeValue;
                        deptObservableArray.push({
                            id: i, 
                            artist: l_artist, 
                            title: l_title});
                    }
                }
            });
        };
        //Maybe call the function from the 'click' binding of a button,
        //but for the moment let's just call it automatically:
        self.addData();
    }
    return ${name}ContentViewModel;
});