package main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import link.LinkFilter;
import link.Links;
import page.Page;
import page.PageParserTool;
import page.RequestAndResponseTool;
import util.FileTool;

/**  
 * 类名: MyCrawler  
 * 作者： pfliu
 * 日期: 2018年11月19日
 * 时间：下午2:51:15                
 */
public class MyCrawler
{
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static ResourceBundle resource=ResourceBundle.getBundle("target");
    public static Set<String> set = new TreeSet<String>();
    /**
     * 使用种子初始化 URL 队列
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }
 
    /**
     * 抓取过程
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) {
 
        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);
 
        //定义过滤器，提取以 http://www.baidu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith(resource.getString("url"))) {
                    return true;
                }else {
                    return false;
                }
            }
        };
 
        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Links.unVisitedUrlQueueIsEmpty()  && Links.getVisitedUrlNum() <= 200) {
            log.info("已访问={}",Links.getVisitedUrlNum());
            log.info("待访问={}",Links.getUnVisitedUrlQueue());
            log.info("得到地址路径数: ={}" ,set.size());
            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                continue;
            }
 
            //根据URL得到page;
            Page page =  null;
            try {
                page= RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);
            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();  
            }
            log.info("页面信息={}",page.getDoc());
            if(page ==null) {
                continue;
            }
            //对page进行处理： 访问DOM的某个标签
            Elements es = PageParserTool.select(page,resource.getString("cssSelector"));
            if(!es.isEmpty()){
                log.info("打印所有标签={}",es);
            }
 
            //将保存文件
            //FileTool.saveToLocal(page);
 
            //将已经访问过的链接放入已访问的链接中；
            Links.addVisitedUrlSet(visitUrl);
            //得到超链接
            Set<String> links = PageParserTool.getLinks(page,"a");
            log.info("links:={}",links);
            Pattern pattern1 = Pattern.compile("/.+(thunder[^\"]+)[^>]+[>]{1}([^<]+)/g");
            for (String link : links) {
                    Links.addUnvisitedUrlQueue(link);
                    log.info("新增爬取路径: ={}" ,link);
                    try{
                        File f=new File("F:\\out.txt");
                        f.createNewFile();
                        FileOutputStream outputStream=new FileOutputStream(f);
                        DataOutputStream out=new DataOutputStream(outputStream);
                        out.write(link.getBytes());
                        out.flush();
                        out.close();
                    } catch (IOException e){
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                        
                    }
                if (pattern1.matcher(link).matches()) {
                    set.add(link);
                    log.info("得到地址路径: ={}" ,link);
                    try{
                        File f=new File("F:\\out.txt");
                        f.createNewFile();
                        FileOutputStream outputStream=new FileOutputStream(f);
                        DataOutputStream out=new DataOutputStream(outputStream);
                        out.write(link.getBytes());
                        out.flush();
                        out.close();
                    } catch (IOException e){
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                        
                    }
                    
                }
            }
        }
        log.info("爬取去次数: ={}" ,Links.getVisitedUrlNum());
        for (Iterator iterator = set.iterator(); iterator.hasNext();)
        {
            String string = (String) iterator.next();
            log.info("得到地址路径: ={}" ,string);
        }
    }
 
 
    //main 方法入口
    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{resource.getString("url")});
    }
}
  
