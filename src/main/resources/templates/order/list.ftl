<html>
<#include "../common/header.ftl">
<body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">

        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>下单时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                                <#list orderMasterDTOPage.content as orderDTO>
                                <tr class="<#if orderDTO.getPayStatusEnums().msg == "支付失败" || orderDTO.getPayStatusEnums().msg == "退款失败">
                                              error
                                           <#elseif orderDTO.getPayStatusEnums().msg == "支付成功" || orderDTO.getPayStatusEnums().msg == "退款成功">
                                              success
                                           <#elseif orderDTO.getPayStatusEnums().msg == "待退款" || orderDTO.getPayStatusEnums().msg == "待支付">
                                              info
                                           <#elseif orderDTO.getPayStatusEnums().msg == "退款中">
                                              warning
                                           </#if>
                                ">
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatusEnums().msg}</td>
                                    <td>${orderDTO.getPayStatusEnums().msg}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td><a href="/wxorder/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                    <td>
                                        <#if orderDTO.getOrderStatusEnums().msg == "新订单">
                                            <a href="/wxorder/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>

                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">上一页</a></li>
                            <#else>
                                <li>
                                    <a href="/wxorder/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a>
                                </li>
                            </#if>
                            <#list 1..<orderMasterDTOPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled">
                                        <a href="/wxorder/seller/order/list?page=${index}&size=${size}">${index}</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/wxorder/seller/order/list?page=${index}&size=${size}">${index}</a>
                                    </li>
                                </#if>

                            </#list>
                            <#if currentPage gte orderMasterDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">下一页</a></li>
                            <#else>
                                <li>
                                    <a href="/wxorder/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a>
                                </li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
<#include "../common/content.ftl">
</body>
</html>