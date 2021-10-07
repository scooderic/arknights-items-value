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
        context.disable = function (control) {
            control.prop("disabled", true);
        };
        context.enable = function (control) {
            control.prop("disabled", false);
        };
        context.hide = function (control) {
            control.hide();
        };
        context.show = function (control) {
            control.show();
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
        // 计算T1、T2、T4物品价值（仅根据用户输入）
        // 修正值是考虑龙门币的参与后的约数值
        context.calcT1T2T4 = function () {
            // 取T3物品价值
            var nzct3 = parseFloat($("#input_nzct3").val());
            var qmkt3 = parseFloat($("#input_qmkt3").val());
            var ymst3 = parseFloat($("#input_ymst3").val());
            var rma7012t3 = parseFloat($("#input_rma7012t3").val());
            var gyyzt3 = parseFloat($("#input_gyyzt3").val());
            var qxzzt3 = parseFloat($("#input_qxzzt3").val());
            var jszzt3 = parseFloat($("#input_jszzt3").val());
            var tzt3 = parseFloat($("#input_tzt3").val());
            var ytzt3 = parseFloat($("#input_ytzt3").val());
            var tnjzt3 = parseFloat($("#input_tnjzt3").val());
            var njt3 = parseFloat($("#input_njt3").val());
            var chjt3 = parseFloat($("#input_chjt3").val());
            var jtyjt3 = parseFloat($("#input_jtyjt3").val());
            var bzrrjt3 = parseFloat($("#input_bzrrjt3").val());
            var hhqxyt3 = parseFloat($("#input_hhqxyt3").val());
            // 算T4（基于T3修正：1.03）
            var t4fix = 1.03;
            $("#input_bmct4").val(Number(t4fix * (nzct3 + tzt3 + rma7012t3)).toFixed(1));
            $("#input_ssmkt4").val(Number(t4fix * (2 * qmkt3 + jszzt3 + nzct3)).toFixed(1));
            $("#input_wsymst4").val(Number(t4fix * (ymst3 + ytzt3 + qxzzt3)).toFixed(1));
            $("#input_rma7024t4").val(Number(t4fix * (rma7012t3 + 2 * gyyzt3 + tnjzt3)).toFixed(1));
            $("#input_tcyyt4").val(Number(t4fix * (4 * gyyzt3)).toFixed(1));
            $("#input_glzzt4").val(Number(t4fix * (qxzzt3 + 2 * gyyzt3 + ymst3)).toFixed(1));
            $("#input_jszkt4").val(Number(t4fix * (2 * jszzt3 + tnjzt3 + nzct3)).toFixed(1));
            $("#input_tjkt4").val(Number(t4fix * (2 * tzt3 + ytzt3 + qmkt3)).toFixed(1));
            $("#input_ytkt4").val(Number(t4fix * (2 * ytzt3 + qxzzt3 + jszzt3)).toFixed(1));
            $("#input_tzlt4").val(Number(t4fix * (2 * tnjzt3 + tzt3 + qmkt3)).toFixed(1));
            $("#input_jhnjt4").val(Number(t4fix * (ytzt3 + njt3 + chjt3)).toFixed(1));
            $("#input_chjkt4").val(Number(t4fix * (qxzzt3 + ymst3 + chjt3)).toFixed(1));
            $("#input_jtdlt4").val(Number(t4fix * (2 * jtyjt3 + njt3 + chjt3)).toFixed(1));
            $("#input_jlrjt4").val(Number(t4fix * (bzrrjt3 + hhqxyt3 + njt3)).toFixed(1));
            $("#input_qxyyt4").val(Number(t4fix * (hhqxyt3 + jtyjt3 + rma7012t3)).toFixed(1));
            // 算T2（基于T3修正：0.96）
            var t2fix = 0.96;
            $("#input_gyyt2").val(Number(t2fix * gyyzt3 / 5).toFixed(1));
            $("#input_zzt2").val(Number(t2fix * qxzzt3 / 4).toFixed(1));
            $("#input_jszt2").val(Number(t2fix * jszzt3 / 4).toFixed(1));
            $("#input_tt2").val(Number(t2fix * tzt3 / 4).toFixed(1));
            $("#input_ytt2").val(Number(t2fix * ytzt3 / 4).toFixed(1));
            $("#input_tnjt2").val(Number(t2fix * tnjzt3 / 4).toFixed(1));
            // 算T1（基于T3修正：0.9024）
            var t1fix = 0.96 * 0.94;
            $("#input_yyt1").val(Number(t1fix * gyyzt3 / 15).toFixed(1));
            $("#input_pszzt1").val(Number(t1fix * qxzzt3 / 12).toFixed(1));
            $("#input_zylt1").val(Number(t1fix * jszzt3 / 12).toFixed(1));
            $("#input_dtt1").val(Number(t1fix * tzt3 / 12).toFixed(1));
            $("#input_ytspt1").val(Number(t1fix * ytzt3 / 12).toFixed(1));
            $("#input_stt1").val(Number(t1fix * tnjzt3 / 12).toFixed(1));
        };
        // 提交运算，并渲染结果
        context.getFullReport = function (control, data) {
            $("#tbody_report").empty();
            $.ajax({
                "url": "/calculator/getFullReport",
                "method": "POST",
                "data": data,
                "async": true,
                "dataType": "json",
                "success": function (resp) {
                    var tbody = $("#tbody_report");
                    if (resp) {
                        for (var i0 = 0; i0 < resp.length; i0 ++) {
                            var report = resp[i0];
                            var row = "<tr class='tr_result_row' data-itemid='" + report["mainItemId"] + "'><th scope=\"row\">" + (i0 + 1) + "</th><td>" + report["stageName"] + "</td><td>" + report["mainItemName"] + "</td><td>" + Number(report["totalValue"]).toFixed(1) + "</td></tr>";
                            tbody.append(row);
                        }
                        context.show($("#form_filter"));
                        context.filterResult(context.getCheckedFilter());
                    } else {
                        context.alert("数据加载失败");
                    }
                },
                "error": function () {
                    context.errorAlert();
                },
                "complete": function () {
                    context.enable(control);
                }
            });
        };
        // 检查结果过滤勾了的复选框
        context.getCheckedFilter = function () {
            var checked = formFilterDom.find(".checkbox_filter:checked");
            var itemIdArr = [];
            for (var k = 0; k < checked.length; k ++) {
                itemIdArr.push($(checked[k]).val());
            }
            return itemIdArr;
        };
        // 执行结果过滤
        context.filterResult = function (itemIdArr) {
            var rowList = $("#tbody_report").find(".tr_result_row");
            context.hide(rowList);
            // 如果传入空数组，就不用麻烦了
            if (itemIdArr.length > 0) {
                for (var l = 0; l < rowList.length; l ++) {
                    var row = $($(rowList[l]));
                    var rowMainItemIds = String(row.data("itemid")).split(",");
                    for (var m = 0; m < rowMainItemIds.length; m ++) {
                        var rowMainItemId = rowMainItemIds[m];
                        if (itemIdArr.indexOf(rowMainItemId) > -1) {
                            context.show(row);
                        }
                    }
                }
            }
        };
        // ++++++++++++++++++++++++++++++++ CONTEXT执行区 ++++++++++++++++++++++++++++++++
        context.alert("冀ICP备17024835号-1 © 歌词酱");
        var formFilterDom = $("#form_filter");
        context.hide(formFilterDom);
        context.loadAllPrices();
        // ++++++++++++++++++++++++++++++++ CONTEXT事件区 ++++++++++++++++++++++++++++++++
        // 刷新
        $("#button_refresh").on("click", function () {
            context.calcT1T2T4();
        });
        // 提交
        $("#button_submit").on("click", function () {
            var control = $(this);
            context.disable(control);
            context.hide($("#form_filter"));
            context.calcT1T2T4();
            var inputList = $("#form_price").find(".input_price");
            var data = {};
            for (var j = 0; j < inputList.length; j ++) {
                var inputDom = $(inputList[j]);
                data[inputDom.data("itemid")] = inputDom.val();
            }
            context.getFullReport(control, data);
        });
        // 结果报表过滤
        formFilterDom.find(".checkbox_filter").off("click").on("click", function () {
            context.filterResult(context.getCheckedFilter());
        });
        // 清空过滤
        $("#btn_clear_filter").on("click", function () {
            formFilterDom.find(".checkbox_filter").prop("checked", false);
            context.filterResult([]);
        });
    });
})(jQuery);
