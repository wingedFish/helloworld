package com.afengzi.util.domain;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by winged fish on 2015/3/28.
 */
public interface RechargeOrder {
    /**
     * ��ѯ��ʷ������������
     *
     * @return
     */
    public Integer queryHistoryOrderListCount();

    /**
     * ����orderGuid�ֶμ���RechargeOrder����Ϣ
     * Guid
     * @return
     */
    public RechargeOrder getRechargeOrderByOrderGuid(String orderGuid);

    /**
     * ����������ʷ�������
     *   ����
     * @return
     * @throws SQLException
     */
    int moveOrderToHistory(final List batchOrderList) throws SQLException;
    /**
     * ��ѯ��ʷ�������
     *   ����
     * @return
     */
    int queryOrderHistoryList();
    /**
     * ɾ��һ����ǰ������
     *   ����
     * @return
     */
    int moveOrderDelete();
    /**
     * ��ѯһ��ǰ������
     *   ����
     * @return
     */
    List queryMoreMonthList();
    /**
     * ��ѯ������ѯ�б�
     *    ����
     * @return
     */
    List getOrderListByQuery();
    /**
     * ��ѯ������ѯ�б���ȯ
     *    ����
     * @return
     */
    List getOrderListByQueryDxq();

    /**
     * ��ѯ��ʷ��������
     *
     * @return
     */
    List queryHistoryOrderList();

    /**
     * ��ѯ���϶�������
     *
     * @return
     */
    List queryOnLineOrderList();

    /**
     * ��������ѯ�Ѿ���ʱ�б�
     *    ����
     * @return
     */
    List getTimeOutOrderListByQuery();

    /**
     * ���¸�����״̬worker
     * ��ѯ��Ӧ�������µ��Ӷ���
     *    ����
     * @return
     */
    List getChildRefreshOrderList();

    /**
     * ���ݶ���id�õ���������
     *
     * Id ����id
     * @return RechargeOrder ��ֵ����
     */
    RechargeOrder getOrderById(long orderId);
    /**
     * ��������id�õ���������
     *
     * Id ����id
     * @return RechargeOrder ��ֵ����
     */
    RechargeOrder getOrderByqiXiaoId(String qiXiaoId);
    /**
     * ���ݶ���id�õ���������
     *
     * Id ����id
     * @return RechargeOrder ��ֵ����
     */
    RechargeOrder getOrderTodayById(long orderId);
    /**
     * ���ݶ���id,�û����õ���������
     *
     *  orderId ����id,userPin
     * @return RechargeOrder ��ֵ����
     */
    RechargeOrder getOrderByIdAndUser();
    /**
     * �õ�����
     *    ��ѯ����
     * @return
     */
    int getRowsByQuery();
    /**
     * �õ�����ȯ����
     *    ��ѯ����
     * @return
     */
    int getRowsByQueryDxq();
    /**
     * �õ���ʱ����
     *    ��ѯ����
     * @return
     */
    int getTimeOutRowsByQuery();
    /**
     * ���¶���״̬
     *
     *
     * @return
     */
    int updateOrderStatusByOrderId();
    /**
     * ������ֵ
     * �����Ӷ���״̬
     *
     * @return
     */
    int updateOrderStatusByParentOrderId();
    /**
     * ���¶���״̬
     * ���¸������˿���
     *
     * @return
     */
    int updateOrderByOrderId();
    /**
     * ���¶�������״̬
     *
     * @return
     */
    int updateOrderFinStatusByOrderId();
    /**
     * ���¶�����Ϣ
     *
     * @return
     */
    public int updateByPrimaryKeySelective();

    /**
     * ����OrderGuid�ֶθ��¶�����Ϣ
     *
     * @return
     */
    public int updateByOrderGuid();

    /**
     * ����OrderGuid�ֶθ��¶�����ϢQX
     */
    public int updateByOrderGuidForQX();
    /**
     * �����޸�ʱ��
     *
     * Id ����ID
     * @return ���ز�������
     */
    int updateOrderModifiedTime(long orderId);
    /**
     * ɾ��δ֧������ʱ�Ķ���
     * ����״̬Ϊ��ȡ��
     * @return
     */
    int updateUnpaidOrderStatus();
    /**
     * ɾ��δ֧��������ֵ��������ʱ�Ķ���
     * ����״̬Ϊ��ȡ��
     * @return
     */
    int updateUnpaidBatchOrderStatus();
    /**
     *�ύ����ʱ���붩������Ϣ
     *
     */
    void insertOrder();
    /**
     * �����϶���
     *
     */
    void insertOldOrder();
    /**
     * ��ֵ�ص����¶���״̬����ֵ���˿���
     *
     * @return
     */
    int updateOrderStatusForFillBack();
    /**
     * ���֧������ʱ   ����ȡ��worder������ȡ����֧���ص�
     * ��������
     *
     * @return
     */
    int updateOrderCancelByOrderId();
    /**
     * ���֧������ʱ   ����ȡ��worder������ȡ����֧���ص�
     * �����޸�
     *
     * @return
     */
    int updateOrderCancelByOrderIds();
    /**
     * ֧���ص����¶���״̬��payId��Paytime
     *
     * @return
     */

    int updatePayInfo() ;
    /**
     * ���������Ӷ���
     * @return
     */
    public void addBatch(final List list,final long parentOrderID) throws SQLException;
    /**
     * ���������Ӷ����ĸ�������
     * @return
     */
    public int updateParentOrderNo(Map map);
    /**
     * ���¸������Ķ�����Ϊ�����ɵ�ERP������
     * @return
     */
    public int updateBatchParentOrderNo(Map map);

    /**
     * ��ѯ�Ӷ����б�
     */
    public List getBatchOrderListByObject();

    /**
     * ���ݸ�����id�����Ӷ���״̬
     */
    public int updateOrderStatusByparentOrderNo();

    /**
     * ���ݸ�����id��������Ӷ���
     */
    public List findOrderByParentNo();

    /**
     * ��ѯ��ǰʱ��ǰ24Сʱ�ڵȴ���ֵ��¼��������Щ��¼����֧���ص�
     *
     *      ����
     * @return
     */
    List findNeedPayCallBackOrders();
    /**
     * ��ѯ��ǰʱ��ǰһСʱ�ڵ��Ѿ�ȡ���ļ�¼��������Щ��¼����֧���ص�
     *
     *      ����
     * @return
     */
    List findNeedPayCallBackCanelOrders();
    /**
     * ���ݶ�����,���߶�����+�������Ͳ�ѯ������¼
     *
     *      ����
     * @return
     */
    void findRefundOrderInfobyId();
    /**
     * �����˿ID��ѯ������Ϣ��������ȫ�˿��Ϣ
     *
     * @return
     */

    List findOrderByRefundIds();
    /**
     * ����id list  ��ѯ������¼
     *
     *      ����
     * @return
     */
    List findOrderByIds();
    /**
     * ��ѯ�������֧���Ķ���
     */
    public List findOrderJFAndBalance();
    /**
     * �����޸Ķ�����features�ֶΣ�����erpStatus:done����עerp״̬Ϊ�����
     */
    int updateFeaturesByOrderId();
    /**
     * ����orderid��venderId���venderId
     */
    int updateVenderIdNullByOrderId();
    /**
     * ��ֵ�ص����¶���״̬
     *
     * @return
     */
    int updateOrderStatusForDxq();
    /**
     * ����ϵͳ-ͨ���û�����ѯ�û��Ƿ��ڻ�ڼ��гɹ�֧����¼
     */
    public String getYunPanSuccessBuyUserPin();

    /**
     * ���¶�����
     *
     * @return
     */
    public int updErpConfirmMsgFlagRecharge();


    public RechargeOrder queryRechargeOrderByOrderId(String orderId);

    List queryRechargeOrders();
    /**
     * ��ѯ��һ���³�ֵ�ɹ���ֵ����50
     */
    List alreadyRechargeNearlAMonth();
    /**
     * �ж������ϲ黹������ʷ���в�ѯ
     */
    public int queryIsLineOrHis();

    /**
     * @add by winged fish210131119
     * ����������ѯ�ܶ��������ܽ��
     *
     * @return
     */
    public Map<String,String> queryCountAndAmount() ;
    /**
     * @add by winged fish210131119
     * ����+Ʒ�Ʊ�־������ѯ�����ƶ�����
     *
     * @return
     */
    public List getBjMobileOrderListByQuery() ;
    /**
     * ��������֧���쳣�Ķ���
     *
     * @return
     */
    public int updatePayExceptionQixiaoOrder();
    /**
     * ����GUID���¶���ID
     *
     * @return
     */
    public int updateOrderIdByOrderGuid();
    /**
     * ɾ����ʱδ֧���ĸ�����
     *
     * @return
     */
    public int delUnpaidParentOrder();

    /**
     * ����Condition��ѯ���������ܽ��
     *
     * @return
     */
    public Map<String, String> getCountAndAmountByCondition();

    /**
     * ��ȡ���ֵ
     *
     * @return
     */
    public Integer queryCountByStatus() ;

    /**
     * �ֿ�ֱ�
     * @param dataSource
     */
    public void setSpecialDataSource(DataSource dataSource) ;
    /**
     * �ֿ�ֱ�ʱ���涩��
     * @param
     */
    public void insertOrderBySharding() ;
}
