package com.afengzi.util.domain;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiuhai on 2015/3/28.
 */
public interface RechargeOrder {
    /**
     * 查询历史订单数据总数
     *
     * @return
     */
    public Integer queryHistoryOrderListCount();

    /**
     * 根据orderGuid字段加载RechargeOrder的信息
     * Guid
     * @return
     */
    public RechargeOrder getRechargeOrderByOrderGuid(String orderGuid);

    /**
     * 批量插入历史表的数据
     *   条件
     * @return
     * @throws SQLException
     */
    int moveOrderToHistory(final List batchOrderList) throws SQLException;
    /**
     * 查询历史表的数据
     *   条件
     * @return
     */
    int queryOrderHistoryList();
    /**
     * 删除一个月前的数据
     *   条件
     * @return
     */
    int moveOrderDelete();
    /**
     * 查询一个前的数据
     *   条件
     * @return
     */
    List queryMoreMonthList();
    /**
     * 查询条件查询列表
     *    条件
     * @return
     */
    List getOrderListByQuery();
    /**
     * 查询条件查询列表定向券
     *    条件
     * @return
     */
    List getOrderListByQueryDxq();

    /**
     * 查询历史订单数据
     *
     * @return
     */
    List queryHistoryOrderList();

    /**
     * 查询线上订单数据
     *
     * @return
     */
    List queryOnLineOrderList();

    /**
     * 按条件查询已经超时列表
     *    条件
     * @return
     */
    List getTimeOutOrderListByQuery();

    /**
     * 更新父订单状态worker
     * 查询对应父订单下的子订单
     *    条件
     * @return
     */
    List getChildRefreshOrderList();

    /**
     * 根据订单id得到订单详情
     *
     * Id 订单id
     * @return RechargeOrder 充值订单
     */
    RechargeOrder getOrderById(long orderId);
    /**
     * 根据企销id得到订单详情
     *
     * Id 订单id
     * @return RechargeOrder 充值订单
     */
    RechargeOrder getOrderByqiXiaoId(String qiXiaoId);
    /**
     * 根据订单id得到订单详情
     *
     * Id 订单id
     * @return RechargeOrder 充值订单
     */
    RechargeOrder getOrderTodayById(long orderId);
    /**
     * 根据订单id,用户名得到订单详情
     *
     *  orderId 订单id,userPin
     * @return RechargeOrder 充值订单
     */
    RechargeOrder getOrderByIdAndUser();
    /**
     * 得到行数
     *    查询条件
     * @return
     */
    int getRowsByQuery();
    /**
     * 得到定向券行数
     *    查询条件
     * @return
     */
    int getRowsByQueryDxq();
    /**
     * 得到超时行数
     *    查询条件
     * @return
     */
    int getTimeOutRowsByQuery();
    /**
     * 更新订单状态
     *
     *
     * @return
     */
    int updateOrderStatusByOrderId();
    /**
     * 批量充值
     * 更新子订单状态
     *
     * @return
     */
    int updateOrderStatusByParentOrderId();
    /**
     * 更新订单状态
     * 更新父订单退款金额
     *
     * @return
     */
    int updateOrderByOrderId();
    /**
     * 更新订单结算状态
     *
     * @return
     */
    int updateOrderFinStatusByOrderId();
    /**
     * 更新订单信息
     *
     * @return
     */
    public int updateByPrimaryKeySelective();

    /**
     * 根据OrderGuid字段更新订单信息
     *
     * @return
     */
    public int updateByOrderGuid();

    /**
     * 根据OrderGuid字段更新订单信息QX
     */
    public int updateByOrderGuidForQX();
    /**
     * 更新修改时间
     *
     * Id 订单ID
     * @return 返回操作条数
     */
    int updateOrderModifiedTime(long orderId);
    /**
     * 删除未支付，超时的定单
     * 更新状态为已取消
     * @return
     */
    int updateUnpaidOrderStatus();
    /**
     * 删除未支付批量充值订单，超时的订单
     * 更新状态为已取消
     * @return
     */
    int updateUnpaidBatchOrderStatus();
    /**
     *提交订单时插入订单表信息
     *
     */
    void insertOrder();
    /**
     * 插入老订单
     *
     */
    void insertOldOrder();
    /**
     * 充值回调更新订单状态、充值金额、退款金额
     *
     * @return
     */
    int updateOrderStatusForFillBack();
    /**
     * 混合支付订单时   订单取消worder及订单取消后支付回调
     * 单个订单
     *
     * @return
     */
    int updateOrderCancelByOrderId();
    /**
     * 混合支付订单时   订单取消worder及订单取消后支付回调
     * 批量修改
     *
     * @return
     */
    int updateOrderCancelByOrderIds();
    /**
     * 支付回调更新订单状态、payId、Paytime
     *
     * @return
     */

    int updatePayInfo() ;
    /**
     * 批量插入子订单
     * @return
     */
    public void addBatch(final List list,final long parentOrderID) throws SQLException;
    /**
     * 批量更新子订单的父订单号
     * @return
     */
    public int updateParentOrderNo(Map map);
    /**
     * 更新父订单的订单号为新生成的ERP订单号
     * @return
     */
    public int updateBatchParentOrderNo(Map map);

    /**
     * 查询子订单列表
     */
    public List getBatchOrderListByObject();

    /**
     * 根据父订单id更新子订单状态
     */
    public int updateOrderStatusByparentOrderNo();

    /**
     * 根据父订单id查出所有子订单
     */
    public List findOrderByParentNo();

    /**
     * 查询当前时间前24小时内等待充值记录，遍历这些记录进行支付回调
     *
     *      条件
     * @return
     */
    List findNeedPayCallBackOrders();
    /**
     * 查询当前时间前一小时内的已经取消的记录，遍历这些记录进行支付回调
     *
     *      条件
     * @return
     */
    List findNeedPayCallBackCanelOrders();
    /**
     * 根据订单号,或者订单号+订单类型查询订单记录
     *
     *      条件
     * @return
     */
    void findRefundOrderInfobyId();
    /**
     * 按照退款单ID查询订单信息，用来补全退款单信息
     *
     * @return
     */

    List findOrderByRefundIds();
    /**
     * 根据id list  查询多条记录
     *
     *      条件
     * @return
     */
    List findOrderByIds();
    /**
     * 查询余额或积分支付的订单
     */
    public List findOrderJFAndBalance();
    /**
     * 根据修改订单的features字段，加入erpStatus:done来标注erp状态为已完成
     */
    int updateFeaturesByOrderId();
    /**
     * 根据orderid和venderId清空venderId
     */
    int updateVenderIdNullByOrderId();
    /**
     * 充值回调更新订单状态
     *
     * @return
     */
    int updateOrderStatusForDxq();
    /**
     * 云盘系统-通过用户名查询用户是否在活动期间有成功支付记录
     */
    public String getYunPanSuccessBuyUserPin();

    /**
     * 更新订单表
     *
     * @return
     */
    public int updErpConfirmMsgFlagRecharge();


    public RechargeOrder queryRechargeOrderByOrderId(String orderId);

    List queryRechargeOrders();
    /**
     * 查询近一个月充值成功面值大于50
     */
    List alreadyRechargeNearlAMonth();
    /**
     * 判断在线上查还是在历史库中查询
     */
    public int queryIsLineOrHis();

    /**
     * @add by lixiuhai210131119
     * 根据条件查询总订单数和总金额
     *
     * @return
     */
    public Map<String,String> queryCountAndAmount() ;
    /**
     * @add by lixiuhai210131119
     * 根据+品牌标志条件查询北京移动订单
     *
     * @return
     */
    public List getBjMobileOrderListByQuery() ;
    /**
     * 更新企销支付异常的订单
     *
     * @return
     */
    public int updatePayExceptionQixiaoOrder();
    /**
     * 根据GUID更新订单ID
     *
     * @return
     */
    public int updateOrderIdByOrderGuid();
    /**
     * 删除超时未支付的父订单
     *
     * @return
     */
    public int delUnpaidParentOrder();

    /**
     * 根据Condition查询总条数及总金额
     *
     * @return
     */
    public Map<String, String> getCountAndAmountByCondition();

    /**
     * 获取监控值
     *
     * @return
     */
    public Integer queryCountByStatus() ;

    /**
     * 分库分表
     * @param dataSource
     */
    public void setSpecialDataSource(DataSource dataSource) ;
    /**
     * 分库分表时保存订单
     * @param
     */
    public void insertOrderBySharding() ;
}
