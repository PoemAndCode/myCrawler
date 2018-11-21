package page;  
/**  
 * 类名: Page  
 * 作者： pfliu
 * 日期: 2018年11月16日
 * 时间：下午4:00:35  
 * 描述：保存获取到的响应相关内容              
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.CharsetDetector;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Page
{
    private byte[] content;
    private String html;//网页源码串
    private Document doc;//网页DOM文档
    private String charset;//字符编码
    private String url;//路径
    private String contentType;//内容类型
    
    public Page(byte[] content,String url,String contentType) {
        this.url=url;
        this.content=content;
        this.contentType=contentType;
    }

    /**  
     * getHtml:返回网页源码字符串
     * @author pfliu  
     * @return  
     */
    public String getHtml() {
        if(html!=null) {
            return html;
        }
        if(content==null) {
            return null;
        }
        if(charset==null) {
            charset = CharsetDetector.guessEncoding(content); // 根据内容来猜测 字符编码
        }
        try
        {
            this.html=new String(content,charset);
            return html;
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return null;
        }
        
    }
    /**  
     * getDoc:得到文档.   
     * @author pfliu  
     * @return  
     */
    public Document getDoc(){
        if(doc!=null) {
            return doc;
        }
        this.doc=Jsoup.parse(getHtml(),url);
        return doc;
    }    
    
    public byte[] getContent()
    {
        return content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }
    public void setHtml(String html)
    {
        this.html = html;
    }


    public void setDoc(Document doc)
    {
        this.doc = doc;
    }

    public String getCharset()
    {
        return charset;
    }

    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    @Override
    public String toString()
    {
        return "Page [content=" + Arrays.toString(content) + ", html=" + html + ", doc=" + doc + ", charset=" + charset
                + ", url=" + url + ", contentType=" + contentType + "]";
    }
    
}
  
