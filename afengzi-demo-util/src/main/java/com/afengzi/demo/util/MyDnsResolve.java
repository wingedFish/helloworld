package com.afengzi.demo.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by winged fish on 2015/3/26.
 */
public class MyDnsResolve {
    public static void main(String[] args) throws Exception {

        connect();

    }

    private static void connect() throws IOException {
        //  创建自定义的 ConnectionManager

        BasicClientConnectionManager connectionManager = new BasicClientConnectionManager() {
            @Override
            protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg) {

                return new DefaultClientConnectionOperator(schreg, new MyDnsResolver());
//                return new DefaultClientConnectionOperator(schreg);

            }

        };

        //  创建 HttpClient 对象

        DefaultHttpClient httpclient = new DefaultHttpClient(connectionManager);

        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);//连接时间1s

        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);//

        //  构造请求

        HttpGet httpget = new HttpGet("http://chongzhi.jd.com/");

        httpget.addHeader("Cookie", "__jdu=954460900; aaa=12313123");

        System.out.println(httpget.getRequestLine());

        //  发送请求并返回结果

        HttpResponse response = httpclient.execute(httpget);

        System.out.println("*********"+response.getEntity().getContentType());

        System.out.println("*********"+response.getStatusLine());

        System.out.println("*********"+EntityUtils.toString(response.getEntity()));

        //  这句不是必须的，只是让程序结束更快点

        httpclient.getConnectionManager().shutdown();
    }



    //  自定义的 DNS 解析类

    private static class MyDnsResolver implements DnsResolver {

        private static final Map<String, InetAddress[]> MAPPINGS = new HashMap<String, InetAddress[]>();

        static {

            addResolve("chongzhi.jd.com", "172.17.24.40");

        }



        private static void addResolve(String host, String ip) {

            try {

                MAPPINGS.put(host, new InetAddress[] { InetAddress.getByName(ip) });

            }

            catch (UnknownHostException e) {

                e.printStackTrace();

            }

        }

        public InetAddress[] resolve(String host) throws UnknownHostException {

            return MAPPINGS.containsKey(host) ? MAPPINGS.get(host) : new InetAddress[0];

        }

    }



}
