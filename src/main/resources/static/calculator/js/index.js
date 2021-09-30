/**
 * @author Lyric
 * @since 2021-09-30
 */
(function ($) {
    "use strict";
    $(function () {
        // ++++++++++++++++++++++++++++++++ CONTEXT定义区 ++++++++++++++++++++++++++++++++
        var context = {};
        context.alert = function (text) {
            alert(text);
        };
        context.errorAlert = function () {
            context.alert("系统错误");
        };
        // 加载所有的价值
        context.loadAllPrices = function () {
            $.ajax({
                "url": "/calculator/getAllPrices",
                "method": "GET",
                "data": {},
                "async": true,
                "dataType": "json",
                "success": function (resp) {
                    if (resp) {
                        var properties = Object.getOwnPropertyNames(resp);
                        for (var i = 0; i < properties.length; i ++) {
                            var property = properties[i];
                            $("#input_" + property).val(Number(resp[property]).toFixed(1));
                        }
                    } else {
                        context.alert("数据加载失败");
                    }
                },
                "error": function () {
                    context.errorAlert();
                },
                "complete": function () {
                }
            });
        };
        // ++++++++++++++++++++++++++++++++ CONTEXT执行区 ++++++++++++++++++++++++++++++++
        context.loadAllPrices();
    });
})(jQuery);
