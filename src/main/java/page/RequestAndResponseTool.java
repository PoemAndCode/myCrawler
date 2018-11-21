
package page;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**  
 * 类名: RequestAndResponseTool  
 * 作者： pfliu
 * 日期: 2018年11月16日
 * 时间：下午3:59:44                
 */
public class RequestAndResponseTool
{
    public static Page sendRequstAndGetResponse(String url)
    {
        Page page=null;
        //生成HttpClient对象
        HttpClient httpClient=new HttpClient(); 
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        //生成GetMethod对象并设置参数 5s超时
        GetMethod getMethod=new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //设置请求重试
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        //执行get请求
        try
        {
            int statusCode=httpClient.executeMethod(getMethod);
            if(statusCode!=HttpStatus.SC_OK ) {
                System.err.println("Method failed:"+getMethod.getStatusLine());
            }
            //处理响应
            byte[] responseBody=getMethod.getResponseBody();
            String contentType =getMethod.getResponseHeader("Content-Type").getValue();
            page=new Page(responseBody, url, contentType);
        }catch (HttpException e) {
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();  
            
        }finally {
            //释放连接
            getMethod.releaseConnection();
        }
        return page;
        
    }
    
    
}
  
